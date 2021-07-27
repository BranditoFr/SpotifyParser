package parser

import API.endpoints.ArtistEndpoints.getArtists
import API.endpoints.{ArtistEndpoints, BrowseEndpoints}
import API.token.Token._
import com.typesafe.config.{Config, ConfigFactory}
import model.output.AlbumOut
import parser.ParserUtilities._
import ujson.Value
import utils.StaticStrings._
import utils.converters._
import utils.database.DatabaseManager

object Parser {
  val mToken: String = getToken
  val mDatabase: DatabaseManager = new DatabaseManager()

  def main(args: Array[String]): Unit = {
    /** CONF * */
    val lConf: Config = ConfigFactory.load("parser.conf")
    val lArtistsList: List[String] = lConf.getString("list.artists").split(",").toList

    /** GET ARTISTS * */
    val ArtistsOut = ReadAndConvertArtist(lArtistsList)
    println("ArtistsIn \n" + ArtistsOut)

    /** GET ALL ALBUMS FOR ARTISTS * */
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

    /** NEW RELEASE * */
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

    val lArtists: List[String] = lNewReleaseAlbums.foldLeft(List[String]())((lAcc, lElem) => {
      lAcc :+ lElem.mArtist
    }).distinct
    println(lArtists)

    val lArtistOut = ReadAndConvertArtist(lArtists)
    println(lArtistOut)

    mDatabase.closeConnection()
  }
}
