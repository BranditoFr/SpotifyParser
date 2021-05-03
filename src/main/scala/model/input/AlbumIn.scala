package model.input

case class AlbumIn(
                  mId: String,
                  mName: String,
                  mReleaseDate: String,
                  mArtisteId: String,
                  mTotalTracks: Int,
                  mTracksId: List[String]
                  )
