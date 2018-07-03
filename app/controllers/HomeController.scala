package controllers

import javax.inject._

import models.Restaurant
import back.repositories.RestaurantRepository
import org.pac4j.core.profile.{CommonProfile, ProfileManager}
import org.pac4j.play.PlayWebContext
import org.pac4j.play.scala.{Security, SecurityComponents}
import org.pac4j.play.store.PlaySessionStore
import play.api.data.Form
import play.api.i18n.I18nSupport
import play.api.mvc._

import scala.util.Random

@Singleton
class HomeController @Inject()(sessionStore: PlaySessionStore, override val controllerComponents: SecurityComponents) extends Security[CommonProfile] with I18nSupport {
  val postCall: _root_.play.api.mvc.Call = routes.HomeController.display()
  val postAdd : _root_.play.api.mvc.Call = routes.HomeController.add()

  def index: Action[AnyContent] = Secure("Google2Client") { implicit request: Request[AnyContent] =>
    Ok(views.html.index(RestaurantRepository.fetchRestaurants(getUserId(request)), RestaurantForm.form, postCall, postAdd))
  }

  def display: Action[AnyContent] = Secure("Google2Client") { implicit request: Request[AnyContent] =>
    val random = new Random
    val restaurantsList = RestaurantRepository.fetchRestaurants(getUserId(request))
    Redirect(routes.HomeController.index()).flashing("Info" -> "You will eat at : ".concat(restaurantsList(random.nextInt(restaurantsList.length)).name))
  }

  def add: Action[AnyContent] = Secure("Google2Client") { implicit request: Request[AnyContent] =>
    val errorFunction = { formWithErrors: Form[RestaurantForm.RestaurantsData] =>
      Redirect(routes.HomeController.index()).flashing("Info" -> "Error")
    }

    val successFunction = { data: RestaurantForm.RestaurantsData =>
      val restaurant = Restaurant(name = data.name)
      RestaurantRepository.addRestaurant(getUserId(request), restaurant)
      Redirect(routes.HomeController.index()).flashing("info" -> "Restaurant added!")
    }

    val formValidationResult = RestaurantForm.form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
  }

  def listRestaurants: Action[AnyContent] = Secure("Google2Client") { implicit request: Request[AnyContent] =>
    // Pass an unpopulated form to the template
    Ok(views.html.index(RestaurantRepository.fetchRestaurants(getUserId(request)), RestaurantForm.form, postCall, postAdd))
  }

  private def getUserId(request: Request[AnyContent]): String = {
    val webContext = new PlayWebContext(request, sessionStore)
    val profileManager = new ProfileManager[CommonProfile](webContext)
    profileManager.get(true).orElseThrow(() => new RuntimeException).getTypedId
  }
}
