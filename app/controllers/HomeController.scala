package controllers

import javax.inject._

import back.repositories.RestaurantRepository
import models.{Restaurant, RestaurantId, User}
import org.pac4j.core.profile.{CommonProfile, ProfileManager}
import org.pac4j.play.PlayWebContext
import org.pac4j.play.scala.{Security, SecurityComponents}
import org.pac4j.play.store.PlaySessionStore
import play.api.data.Form
import play.api.i18n.I18nSupport
import play.api.mvc._

import scala.util.Random

@Singleton
class HomeController @Inject()(sessionStore: PlaySessionStore, override val controllerComponents: SecurityComponents, restaurantRepository: RestaurantRepository) extends Security[CommonProfile] with I18nSupport {
  val random = new Random
  val postCall: _root_.play.api.mvc.Call = routes.HomeController.display()
  val postAdd: _root_.play.api.mvc.Call = routes.HomeController.add()
  val postDelete: _root_.play.api.mvc.Call = routes.HomeController.delete()

  def index: Action[AnyContent] = Secure("Google2Client") { implicit request: Request[AnyContent] =>
    Ok(views.html.index(restaurantRepository.fetchRestaurants(getUser(request)), RestaurantForm.form, DeleteRestaurantForm.form, postCall, postAdd, postDelete))
  }

  def display: Action[AnyContent] = Secure("Google2Client") { implicit request: Request[AnyContent] =>
    val restaurantsList = restaurantRepository.fetchRestaurants(getUser(request))
    Redirect(routes.HomeController.index()).flashing("Info" -> "You will eat at : ".concat(restaurantsList(random.nextInt(restaurantsList.length)).name))
  }

  def add: Action[AnyContent] = Secure("Google2Client") { implicit request: Request[AnyContent] =>
    val errorFunction = { formWithErrors: Form[RestaurantForm.RestaurantsData] =>
      Redirect(routes.HomeController.index()).flashing("Info" -> ("Error: " + formWithErrors.errors.head.message))
    }

    val successFunction = { data: RestaurantForm.RestaurantsData =>
      val restaurant = new Restaurant(name = data.name, user = getUser(request))
      restaurantRepository.addRestaurant(restaurant)
      Redirect(routes.HomeController.index()).flashing("info" -> "Restaurant added!")
    }

    val formValidationResult = RestaurantForm.form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
  }

  def delete: Action[AnyContent] = Secure("Google2Client") { implicit request: Request[AnyContent] =>
    val errorFunction = { formWithErrors: Form[DeleteRestaurantForm.DeleteRestaurantData] =>
      Redirect(routes.HomeController.index()).flashing("Info" -> ("Error: " + formWithErrors.errors.head.message))
    }

    val successFunction = { data: DeleteRestaurantForm.DeleteRestaurantData =>
      restaurantRepository.deleteRestaurant(getUser(request), data.toRestaurantId)
      Redirect(routes.HomeController.index()).flashing("info" -> "Restaurant deleted")
    }

    val formValidationResult = DeleteRestaurantForm.form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
  }
  def listRestaurants: Action[AnyContent] = Secure("Google2Client") { implicit request: Request[AnyContent] =>
    // Pass an unpopulated form to the template
    Ok(views.html.index(restaurantRepository.fetchRestaurants(getUser(request)), RestaurantForm.form, DeleteRestaurantForm.form, postCall, postAdd, postDelete))
  }

  private def getUser(request: Request[AnyContent]): User = {
    val webContext = new PlayWebContext(request, sessionStore)
    val profileManager = new ProfileManager[CommonProfile](webContext)
    User(profileManager.get(true).orElseThrow(() => new RuntimeException).getTypedId)
  }
}
