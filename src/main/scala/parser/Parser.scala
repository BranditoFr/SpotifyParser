package parser

import API.endpoints.{AlbumEndpoints, ArtistEndpoints}
import API.token.Token._
import com.typesafe.config.{Config, ConfigFactory}
import model.input.{AlbumIn, ArtistIn}
import ujson.{StringRenderer, Value}

import scala.util.control.Breaks.{break, _}
import java.io.IOException
import scala.collection.mutable.ArrayBuffer



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


    val lAlbumsIn: List[AlbumIn] = {
      lArtistsIn.foldLeft(List[AlbumIn]())((lAcc, lArtist) => {
        val lAlbumsJson: Value = ujson.read(ArtistEndpoints.getArtistAlbums(lArtist.mId))

        val lAlbumsList: List[Value] = lAlbumsJson("items").arr.toList

        val lAlbumsPerArtist: List[AlbumIn] =
          lAlbumsList.foldLeft(List[AlbumIn]())((lAccAlbum, lAlbum) => {
            lAccAlbum :+
              AlbumIn(
                lAlbum("id").str,
                lAlbum("name").str,
                lAlbum("release_date").str,
                lArtist.mId,
                lAlbum("total_tracks").str.toInt,
                List("")
              )
          })

        lAcc ++ lAlbumsPerArtist
      })
    }

    println(lAlbumsIn)
  }
}
