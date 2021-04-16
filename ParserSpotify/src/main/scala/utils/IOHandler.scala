package utils

trait IOReader {
  def read(url: String): String
}

trait IOApiReader extends IOReader {
  override def read(url: String): String = ???
}

abstract class AbstractApiReader extends IOApiReader