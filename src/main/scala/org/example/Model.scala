package org.example
package org.example

import reactivemongo.api.bson.BSONObjectID

object Model {

  case class Indirizzo(via: String, civico: Int, citta: String)

  case class Person(_id: BSONObjectID, nome: String, cognome: String, annoDiNascita: Int, indirizzo: Indirizzo)

}
