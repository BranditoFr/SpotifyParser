package model.output

case class AlbumOut(
                    mId: String,
                    mName: String,
                    mReleaseDate: String,
                    mArtiste: String,
                    mTotalTracks: Int,
                    mTracks: List[TrackOut]
                   )
