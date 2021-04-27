package Parser

import com.typesafe.config.{Config, ConfigFactory}
import ujson.Value.Value
import ParserUtilities._

import scala.io.Source


object Parser {
  val mToken: String = getToken

  def main(args: Array[String]): Unit = {
    val lConf: Config = ConfigFactory.load("parser.conf")
    val lArtistePath: String = lConf.getString("artist")

    val lJsonSource = Source.fromFile(lArtistePath)
    val lArtistString = lJsonSource.getLines.mkString
    lJsonSource.close
    val lArtistJson: Value = ujson.read(lArtistString)

    println(List(lArtistJson("artists")))

    println(lArtistJson("artists")(0))
    println(lArtistJson("artists")(0)("name"))
  }
}
