package utils.converters

import API.endpoints.ArtistEndpoints.getArtist
import model.input.TrackIn
import model.output.TrackOut
import ujson.Value
import utils.StaticStrings.{sArtists, sId, sName, sPopularity, sReleaseDate}

object TrackConverter extends Converter[Value, TrackOut] {
  override def convert(iInput: Value): TrackOut = {
    TrackOut(
      iInput(sName).str,
//      iInput(sReleaseDate).str,
//      iInput(sPopularity).num.toInt,
      iInput(sArtists)(0)(sId).str
    )
  }
}
