package utils.converters

import model.input.ArtistIn
import model.output.ArtistOut
import parser.Parser.mDatabase
import ujson.Value
import utils.StaticStrings.{sArtists, sArtist, sFollowers, sId, sName, sPopularity, sTotal}

object ArtistConverter extends Converter[Value, ArtistOut]{
    override def convert(iInput: Value): ArtistOut = {
        val lArtist =
            ArtistOut(
                mId = iInput(sId).str,
                mName = iInput(sName).str,
                mNbFollowers = iInput(sFollowers)(sTotal).toString().toInt,
                mPopularity = iInput(sPopularity).toString().toInt
          )
        if(!mDatabase.checkExist(sArtist, sId, lArtist.mId)) {
            mDatabase.insertArtist(lArtist)
        }
        lArtist
    }
}
