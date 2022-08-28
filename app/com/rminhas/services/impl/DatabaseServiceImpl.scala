package com.rminhas.services.impl

import akka.actor.ActorSystem
import com.rminhas.services.DatabaseService
import javax.inject.{Inject, Singleton}
import reactivemongo.api.{AsyncDriver, DefaultDB, MongoConnection}
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.Future
import play.api.Configuration

import scala.util.Try

@Singleton
class DatabaseServiceImpl @Inject() (conf: Configuration, system: ActorSystem)
    extends DatabaseService {
  implicit val myExecutionContext = system.dispatchers.lookup("mongodb.context")
  val mongoUri                    = conf.get[String]("mongodb.uri")

  // Connect to the database: Must be done only once per application
  val driver                                 = AsyncDriver()
  val parsedUri                              = MongoConnection fromString mongoUri
  val futureConnection                       = parsedUri.flatMap(driver.connect)
  def db1: Future[DefaultDB]                 = futureConnection.flatMap(_.database("scheduler"))
  override val LOCKS: Future[BSONCollection] = db1.map(_.collection("locks"))

}
