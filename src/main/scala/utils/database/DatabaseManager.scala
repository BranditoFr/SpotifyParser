package utils.database

import model.output.{AlbumOut, ArtistOut, TrackOut}

import java.sql.{DriverManager, ResultSet}

class DatabaseManager(){
  private val lUrl = "jdbc:mysql://localhost:3306/scala"
  private val lDriver = "com.mysql.jdbc.Driver"
  private val lUsername = "root"
  private val lPassword = ""
  Class.forName(lDriver)
  val lConnection = DriverManager.getConnection(lUrl, lUsername, lPassword)

  def closeConnection(): Unit = {
    lConnection.close()
  }

  def selectAll(iTable: String) {
    val lStatement = lConnection.createStatement
    val lRs: ResultSet = lStatement.executeQuery("SELECT * FROM " + iTable)
    println(lRs)
  }

  def checkExist(iTable: String, iCol: String, iCheck: String): Boolean ={
    val lStatement = lConnection.createStatement
    val lRs: ResultSet = lStatement.executeQuery(s"""SELECT $iCol FROM $iTable WHERE $iCol = '$iCheck' """)

    if(lRs.next()) {
      true
    }else{
      false
    }
  }

  def insertAlbum(iAlbum: AlbumOut): Unit = {
    val lPreparedStmt = lConnection.prepareStatement("""INSERT INTO album (id, name, date, artist, total_tracks) VALUES (?, ?, ?, ?, ?)""")
    lPreparedStmt.setString (1, iAlbum.mId)
    lPreparedStmt.setString (2, iAlbum.mName)
    lPreparedStmt.setString (3, iAlbum.mReleaseDate)
    lPreparedStmt.setString (4, iAlbum.mArtist)
    lPreparedStmt.setInt (5, iAlbum.mTotalTracks)
    lPreparedStmt.execute
    lPreparedStmt.close()
  }

  def insertArtist(iArtist: ArtistOut): Unit = {
    val lPreparedStmt = lConnection.prepareStatement("""INSERT INTO artist (id, name, followers, popularity) VALUES ( ?, ?, ?, ?)""")
    lPreparedStmt.setString (1, iArtist.mId)
    lPreparedStmt.setString (2, iArtist.mName)
    lPreparedStmt.setLong (3, iArtist.mNbFollowers)
    lPreparedStmt.setInt (4, iArtist.mPopularity)
    lPreparedStmt.execute
    lPreparedStmt.close()
  }

  def insertTrack(iTrack: TrackOut): Unit = {
    val lPreparedStmt = lConnection.prepareStatement("""INSERT INTO track (id, name, artist) VALUES (?, ?, ? )""")
    lPreparedStmt.setString (1, iTrack.mId)
    lPreparedStmt.setString (2, iTrack.mName)
    lPreparedStmt.setString (3, iTrack.mArtist)
    lPreparedStmt.execute
    lPreparedStmt.close()
  }
}
