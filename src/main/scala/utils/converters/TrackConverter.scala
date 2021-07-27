package utils.converters

import API.endpoints.ArtistEndpoints.getArtist
import model.input.TrackIn
import model.output.TrackOut
import parser.Parser.mDatabase
import ujson.Value
import utils.StaticStrings.{sArtists, sId, sName, sPopularity, sReleaseDate, sTrack}

object TrackConverter extends Converter[Value, TrackOut] {
  override def convert(iInput: Value): TrackOut = {

    val lTrack =
      TrackOut(
        iInput(sId).str,
        iInput(sName).str,
        iInput(sArtists)(0)(sId).str
      )

    if (!mDatabase.checkExist(sTrack, sId, lTrack.mId)) {
      mDatabase.insertTrack(lTrack)
    }
    lTrack
  }
}
