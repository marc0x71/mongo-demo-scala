package org.example
package org.example

import org.example.Model._
import org.example.PersonDAO._

import com.typesafe.scalalogging.Logger
import reactivemongo.api.bson.BSONObjectID

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

object Main {

  val logger: Logger = Logger("Main")

  def showInsertResult(result: Try[Unit]): Unit = {
    result.map(_ => logger.info(s"Inserito!"))
  }

  def showUpdateResult(result: Try[Int]): Unit = {
    result.map(count => logger.info(s"$count record aggiornati!"))
  }

  def main(args: Array[String]): Unit = {
    logger.info("Eccomi!")
    removeAll().onComplete(_ => {

      val p1 = Person(BSONObjectID.generate(), "Mario", "Rossi", 1971, Indirizzo("Via Lemani Dalnaso", 101, "Firenze"))
      val p2 = Person(BSONObjectID.generate(), "Lucia", "Verdi", 1977, Indirizzo("Via Diqui", 11, "Padova"))
      val p3 = Person(BSONObjectID.generate(), "Filippo", "Bianchi", 1954, Indirizzo("Viale del Tramonto", 6, "Roma"))

      val result = for {
        future1 <- createPerson(p1)
        future2 <- createPerson(p2)
        future3 <- createPerson(p3)
      } yield (future1, future2, future3)

      result.onComplete({
        case Success(value) =>
          logger.info("Valori inseriti con successo")
          findPersons().map(_.foreach(p => logger.info(s"> $p")))
        case Failure(e) =>
          // rollback
          removeAll().onComplete(_ => {})
          logger.error("Insert fallita", e)
      })

    })
    Thread.sleep(2000)
    logger.info("Termine applicazione")
    PersonDAO.driver.close()
  }
}
