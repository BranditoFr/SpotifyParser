package parser

import API.endpoints.{AlbumEndpoints, ArtistEndpoints, BrowseEndpoints}
import API.token.Token._
import com.typesafe.config.{Config, ConfigFactory}
import model.input.AlbumIn
import model.output.{AlbumOut, ArtistOut}
import ujson.Value
import utils.StaticStrings._
import utils.converters._


object Parser {
  val mToken: String = getToken

  def main(args: Array[String]): Unit = {
    /**  CONF **/
    val lConf: Config = ConfigFactory.load("parser.conf")
    val lArtistsList: List[String] = lConf.getString("list.artists").split(",").toList

    /**  GET ARTISTS **/
    val lArtistJson: List[Value] =
      ujson
        .read(ArtistEndpoints.getArtists(lArtistsList))(sArtists)
        .arr
        .toList

    val ArtistsOut: List[ArtistOut] =
      lArtistJson.foldLeft(List[ArtistOut]())((lAcc, lArtist) => {
        lAcc :+
          ArtistConverter.convert(lArtist)
      })

    println("ArtistsIn \n" + ArtistsOut)

    /**  GET ALL ALBUMS FOR ARTISTS **/
    val lAlbumsIn: List[AlbumOut] = {
      ArtistsOut.foldLeft(List[AlbumOut]())((lAcc, lArtist) => {
        val lAlbumsList: List[Value] =
          ujson
            .read(ArtistEndpoints.getArtistAlbums(lArtist.mId))(sItems)
            .arr
            .toList

        lAcc ++
          lAlbumsList.foldLeft(List[AlbumOut]())((lAccAlbum, lAlbum) => {
            lAccAlbum :+
              AlbumConverter.convert(lAlbum)
          })
      })
    }
    println("lAlbumsIn \n" + lAlbumsIn)

    /**  NEW RELEASE **/
    val lNewReleaseJson: List[Value] =
      ujson
        .read(BrowseEndpoints.getNewRelease("FR", 30))(sAlbums)(sItems)
        .arr
        .toList

    val lNewReleaseAlbums: List[AlbumOut] = {
      lNewReleaseJson.foldLeft(List[AlbumOut]())((lAcc, lAlbum) => {
        lAcc :+
          AlbumConverter.convert(lAlbum)
      })
    }
    println(lNewReleaseAlbums)

  }
}
