package org.example
package org.example

import org.example.Model.Person

import reactivemongo.api.MongoConnection.ParsedURI
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.document
import reactivemongo.api.{AsyncDriver, Cursor, DB, MongoConnection}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object PersonDAO {

  val mongoUri = "mongodb://localhost:27017"
  val driver: AsyncDriver = AsyncDriver()
  val parsedUri: Future[ParsedURI] = MongoConnection.fromString(mongoUri)

  val futureConnection: Future[MongoConnection] = parsedUri.flatMap(uri => driver.connect(uri))

  def createPerson(person: Person): Future[Unit] =
    personCollection.flatMap(_.insert.one(person).map(_ => {}))

  def updatePerson(person: Person): Future[Int] = {
    val selector = document("_id" -> person._id)
    personCollection.flatMap(_.update.one(selector, person).map(_.n))
  }

  def personCollection: Future[BSONCollection] = db1.map(_.collection("person"))

  def db1: Future[DB] = futureConnection.flatMap(_.database("firstdb"))


  // or provide a custom one

  def findPersons(): Future[List[Person]] =
    personCollection.flatMap(_.find(document()). // query builder
      cursor[Person](). // using the result cursor
      collect[List](-1, Cursor.FailOnError[List[Person]]()))

  def findPersonByNascita(annoDiNascita: Int): Future[List[Person]] =
    personCollection.flatMap(_.find(document("annoDiNascita" -> annoDiNascita)). // query builder
      cursor[Person](). // using the result cursor
      collect[List](-1, Cursor.FailOnError[List[Person]]()))

  // ... deserializes the document using personReader

  def removeAll(): Future[Unit] = personCollection.flatMap(_.drop())
}
