package controllers

import javax.inject._

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import play.api.Environment
import play.api.http._
import play.api.mvc._

@Singleton
class HomeController @Inject() (
    errorHandler: HttpErrorHandler,
    meta: AssetsMetadata,
    env: Environment,
)(implicit ec: ExecutionContext)
    extends Assets(errorHandler, meta, env) {

  def index(path: String, indexFile: String): Action[AnyContent] =
    versioned(path, Assets.Asset(name = indexFile))

  // メインのAction
  def serveOrFallback( 
      path: String,
      indexFile: String,
      file: String,
  ): Action[AnyContent] = Action.async { request =>
    val assetAction = versioned(path, file)

    // Assets.versioned は Action を返すので、一度実行して結果を見る
    assetAction
      .apply(request)
      .flatMap {
        case notFound if notFound.header.status == NOT_FOUND =>
          // index.html を代わりに返す（HTMLアプリのSPA対応）
          versioned(path, indexFile).apply(request)
        case other => Future.successful(other)
      }
  }

}
