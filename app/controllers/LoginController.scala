package controllers

import javax.inject._

import org.pac4j.core.profile.{CommonProfile, ProfileManager}
import org.pac4j.play.PlayWebContext
import org.pac4j.play.scala.{Security, SecurityComponents}
import org.pac4j.play.store.PlaySessionStore
import play.api.mvc.{Action, AnyContent}

@Singleton
class LoginController @Inject()(sessionStore: PlaySessionStore, override val controllerComponents: SecurityComponents) extends Security[CommonProfile] {
  def login: Action[AnyContent] = Secure("Google2Client") { implicit request =>
    val webContext = new PlayWebContext(request, playSessionStore)
    val profileManager = new ProfileManager[CommonProfile](webContext)
    val profile = profileManager.get(true).orElseThrow(() => new RuntimeException)
    Redirect(routes.HomeController.index()).flashing("info" -> "Welcome ".concat(profile.getDisplayName))
  }
}
