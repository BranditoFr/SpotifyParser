package parser

import API.endpoints.{AlbumEndpoints, ArtistEndpoints}
import API.token.Token._
import com.typesafe.config.{Config, ConfigFactory}
import model.input.{AlbumIn, ArtistIn}
import ujson.{StringRenderer, Value}

import java.io.IOException



object Parser {
  val mToken: String = getToken

  def main(args: Array[String]): Unit = {
    val lConf: Config = ConfigFactory.load("parser.conf")

    val lArtistsList: List[String] = List("58wXmynHaAWI5hwlPZP3qL", "1ntQKIMIgESKpKoNXVBvQg")
    val NbArtist = lArtistsList.length

    val lArtistJson: Value = ujson.read(ArtistEndpoints.getArtists(lArtistsList))

    val lArtistsIn: List[ArtistIn] =
      (0 until NbArtist).foldLeft(List[ArtistIn]())((lAcc, i) => {
        lAcc :+
          ArtistIn(
            mId = lArtistJson("artists")(i)("id").str,
            mName = lArtistJson("artists")(i)("name").str,
            mNbFollowers = lArtistJson("artists")(i)("followers")("total").toString().toInt,
            mPopularity =  lArtistJson("artists")(i)("popularity").toString().toInt
          )
    })
    println(lArtistsIn)
//
//    val lAlbumsIn: List[AlbumIn] =
//      lArtistsIn.foldLeft(List[AlbumIn]())((lAcc, lArtist) => {
//        val lAlbumsJson: Value = ujson.read(ArtistEndpoints.getArtistAlbums(lArtist.mId))
//        println(lAlbumsJson)
//        while(true){
//          try {
//            val lName = lAlbumsJson("items")(0)("name")
//          }catch{
//            case e: IOException => println("Had an IOException trying to read that file")
//          }
//        }
//        lAcc :+
//          AlbumIn("a","a",1,"a",List("a"))
//      })
//    println(lAlbumsIn)
  }
}
