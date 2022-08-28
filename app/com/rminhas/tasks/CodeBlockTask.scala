package com.rminhas.tasks

import java.net.InetAddress
import java.time.Instant

import akka.actor.ActorSystem
import javax.inject.Inject
import com.rminhas.services.DatabaseService

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.ReadConcern
import reactivemongo.bson.BSONDocument
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDateTime
import reactivemongo.api.bson.compat._

class CodeBlockTask @Inject() (dbService: DatabaseService, actorSystem: ActorSystem)(
    implicit executionContext: ExecutionContext
) {

  val id = InetAddress.getLocalHost.toString

  actorSystem.scheduler.scheduleAtFixedRate(initialDelay = 2.seconds, interval = 20.seconds) { () =>
    // the block of code that will be executed
    //actorSystem.log.info("Executing something..." + InetAddress.getLocalHost.toString)

    log("Getting locks count...")
    getLocksCount().map { count =>
      {
        log(s"Found $count locks.")
        if (count == 0) {
          log("Trying to acquire a lock...")
          setLock().map(writeResult => {
            if (writeResult.ok) {
              log("lock acquired.\n")
            } else {
              log("Unable to acquire the lock.\n")
            }
          })
        } else {
          log("Lock already exists, will try again.\n")
        }
      }
    }
  }

  def getLocksCount(): Future[Long] =
    dbService.LOCKS
      .flatMap(
        _.count(selector = None,
                limit = None,
                skip = 0,
                hint = None,
                readConcern = ReadConcern.Majority)
      )
      .recover(recovery)

  def setLock(): Future[WriteResult] =
    dbService.LOCKS.flatMap(
      _.insert
        .one(
          BSONDocument("container" -> id, "acquiredAt" -> BSONDateTime(System.currentTimeMillis))
        )
    )

  def log(msg: String): Unit = println(s"${Instant.now}: $id ---- $msg")

  def recovery: PartialFunction[Throwable, Long] = {
    case err =>
      log(err.getMessage)
      -1
  }
}
