package API.endpoints

import scalaj.http.{Http, HttpRequest, HttpResponse}
import parser.Parser.mToken
abstract class SpotifyEndpoints {

  protected val baseAPIUrl = "https://api.spotify.com"

  protected def callRequest(iEndpoint: String): String = {
    Http(iEndpoint)
      .header("Authorization", "Bearer " + mToken)
      .asString.body
  }

  protected def callRequest(iEndpoint: String, iParams: List[(String, String)]): String = {
    Http(iEndpoint)
      .params(iParams)
      .header("Authorization", "Bearer " + mToken)
      .asString.body
  }
}

