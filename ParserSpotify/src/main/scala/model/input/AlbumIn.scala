package model.input

case class AlbumIn(
                  mName: String,
                  mReleaseDate: String,
                  mPopularity: Int,
                  mArtisteId: String,
                  mTracksId: List[String]
                  )
