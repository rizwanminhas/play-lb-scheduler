package com.rminhas.services

import com.google.inject.ImplementedBy
import com.rminhas.services.impl.DatabaseServiceImpl
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.Future

@ImplementedBy(classOf[DatabaseServiceImpl])
trait DatabaseService {
  val LOCKS: Future[BSONCollection]
}
