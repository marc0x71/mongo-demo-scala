package org.example
package org.example

import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, BSONObjectID, Macros}

object Model {

  case class Indirizzo(via: String, civico: Int, citta: String)

  case class Person(_id: BSONObjectID, nome: String, cognome: String, annoDiNascita: Int, indirizzo: Indirizzo);

  implicit def indirizzoReader: BSONDocumentReader[Indirizzo] = Macros.reader[Indirizzo]

  implicit def personReader: BSONDocumentReader[Person] = Macros.reader[Person]

  implicit def indirizzoWriter: BSONDocumentWriter[Indirizzo] = Macros.writer[Indirizzo]

  implicit def personWriter: BSONDocumentWriter[Person] = Macros.writer[Person]


}
