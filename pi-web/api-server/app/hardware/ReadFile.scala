package hardware

import scala.io.Source
import scala.util.Try
import scala.util.Using

trait ReadFile {
  def readLines(path: String): Try[Array[String]]
}

object ReadFile extends ReadFile {
  def readLines(path: String): Try[Array[String]] =
    Using(Source.fromFile(path))(source => source.getLines().toArray)
}
