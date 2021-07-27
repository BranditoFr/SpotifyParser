package model.output

case class AlbumOut(
                     mId: String,
                     mName: String,
                     mReleaseDate: String,
                     mArtist: String,
                     mTotalTracks: Int,
                     mTracks: List[TrackOut]
                   )
