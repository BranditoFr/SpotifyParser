package parser

import API.endpoints.ArtistEndpoints
import model.output.ArtistOut
import ujson.Value
import utils.StaticStrings.sArtists
import utils.converters.ArtistConverter

object ParserUtilities {
  def calculGroupMax(iMax: Int, iListLength: Int): Int ={
    if (iListLength % iMax == 0) {
      iListLength / iMax
    } else {
      (iListLength / iMax) + 1
    }
  }

  def ReadAndConvertArtist(iArtistList: List[String]): List[ArtistOut] ={
    val lArtistJson: List[Value] =
      ujson
        .read(ArtistEndpoints.getArtists(iArtistList))(sArtists)
        .arr
        .toList

      lArtistJson.foldLeft(List[ArtistOut]())((lAcc, lArtist) => {
        lAcc :+
          ArtistConverter.convert(lArtist)
      })
  }
}
