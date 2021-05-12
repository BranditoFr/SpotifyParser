package API.endpoints


object BrowseEndpoints extends SpotifyEndpoints {
  private val browseEndpoint = baseAPIUrl + "/v1/browse/"

  def getNewRelease(iCountry: String, iLimit: Int): String = {
    callRequest(iEndpoint = browseEndpoint + s"new-releases?country=$iCountry&limit=$iLimit")
  }

}
