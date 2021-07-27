package utils.converters

import API.endpoints.AlbumEndpoints
import API.endpoints.AlbumEndpoints.getAlbumTracks
import API.endpoints.ArtistEndpoints.getArtist
import API.endpoints.TrackEndpoints.getTracks
import model.input.{AlbumIn, ArtistIn}
import model.output.{AlbumOut, TrackOut}
import parser.Parser.mDatabase
import ujson.Value
import utils.StaticStrings.{sAlbum, sArtists, sId, sItems, sName, sReleaseDate, sTotalTracks}
import utils.database.DatabaseManager

object AlbumConverter extends Converter[Value, AlbumOut] {
  override def convert(iInput: Value): AlbumOut = {
    val lTracksJson = ujson.read(AlbumEndpoints.getAlbumTracks(iInput(sId).str))(sItems).arr.toList
    val lTracksNames: List[TrackOut] =
      lTracksJson.foldLeft(List[TrackOut]())((lAcc, lTrack) => {
        lAcc :+
          TrackConverter.convert(lTrack)
      })

    val lAlbum = {
      AlbumOut(
        iInput(sId).str,
        iInput(sName).str,
        iInput(sReleaseDate).str,
        iInput(sArtists)(0)(sId).str,
        iInput(sTotalTracks).num.toInt,
        lTracksNames
      )
    }
    if(!mDatabase.checkExist(sAlbum, sId, lAlbum.mId)){
      mDatabase.insertAlbum(lAlbum)
    }

    lAlbum

  }
}
