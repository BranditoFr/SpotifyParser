package utils.converters

import model.input.ArtistIn
import model.output.ArtistOut
import ujson.Value
import utils.StaticStrings.{sFollowers, sId, sName, sPopularity, sTotal}

object ArtistConverter extends Converter[Value, ArtistOut]{
    override def convert(iInput: Value): ArtistOut = {
        ArtistOut(
            mId = iInput(sId).str,
            mName = iInput(sName).str,
            mNbFollowers = iInput(sFollowers)(sTotal).toString().toInt,
            mPopularity = iInput(sPopularity).toString().toInt
      )
    }
}
