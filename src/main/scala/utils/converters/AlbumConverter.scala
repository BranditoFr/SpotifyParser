package utils.converters

import API.endpoints.AlbumEndpoints
import API.endpoints.AlbumEndpoints.getAlbumTracks
import API.endpoints.ArtistEndpoints.getArtist
import API.endpoints.TrackEndpoints.getTracks
import model.input.{AlbumIn, ArtistIn}
import model.output.{AlbumOut, TrackOut}
import ujson.Value
import utils.StaticStrings.{sArtists, sId, sItems, sName, sReleaseDate, sTotalTracks}

object AlbumConverter extends Converter[Value, AlbumOut] {
  override def convert(iInput: Value): AlbumOut = {
    val lTracksJson = ujson.read(AlbumEndpoints.getAlbumTracks(iInput(sId).str))(sItems).arr.toList
    val lTracksNames: List[TrackOut] =
      lTracksJson.foldLeft(List[TrackOut]())((lAcc, lTrack) => {
        lAcc :+
          TrackConverter.convert(lTrack)
      })

    AlbumOut(
      iInput(sId).str,
      iInput(sName).str,
      iInput(sReleaseDate).str,
      iInput(sArtists)(0)(sName).str,
      iInput(sTotalTracks).num.toInt,
      lTracksNames
    )
  }
}
