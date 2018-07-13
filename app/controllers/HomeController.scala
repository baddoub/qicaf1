package controllers

import javax.inject._

import back.services.{AddRestaurant, ChooseRestaurant, ListRestaurants, RemoveRestaurant}
import models.{Restaurant, User}
import org.pac4j.core.profile.{CommonProfile, ProfileManager}
import org.pac4j.play.PlayWebContext
import org.pac4j.play.scala.{Security, SecurityComponents}
import play.api.data.Form
import play.api.i18n.I18nSupport
import play.api.mvc._

import scala.util.Random

@Singleton
class HomeController @Inject()(
                                override val controllerComponents: SecurityComponents,
                                listRestaurants: ListRestaurants,
                                chooseRestaurant: ChooseRestaurant,
                                addRestaurant: AddRestaurant,
                                removeRestaurant: RemoveRestaurant
                              ) extends Security[CommonProfile] with I18nSupport {
  val random = new Random
  val postCall: _root_.play.api.mvc.Call = routes.HomeController.display()
  val postAdd: _root_.play.api.mvc.Call = routes.HomeController.add()
  val postDelete: _root_.play.api.mvc.Call = routes.HomeController.delete()

  def index: Action[AnyContent] = Secure("Google2Client") { implicit request: Request[AnyContent] =>
    Ok(views.html.index(listRestaurants(getUser(request)), RestaurantForm.form, DeleteRestaurantForm.form, postCall, postAdd, postDelete))
  }

  def display: Action[AnyContent] = Secure("Google2Client") { implicit request: Request[AnyContent] =>
    val message = chooseRestaurant(getUser(request)).map("You will eat at: " + _.name) getOrElse "Please add some restaurants"
    Redirect(routes.HomeController.index()).flashing("Info" -> message)
  }

  def add: Action[AnyContent] = Secure("Google2Client") { implicit request: Request[AnyContent] =>
    val errorFunction = { formWithErrors: Form[RestaurantForm.RestaurantsData] =>
      Redirect(routes.HomeController.index()).flashing("Info" -> ("Error: " + formWithErrors.errors.head.message))
    }

    val successFunction = { data: RestaurantForm.RestaurantsData =>
      val restaurant = new Restaurant(name = data.name, user = getUser(request))
      addRestaurant(restaurant)
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
      removeRestaurant(getUser(request), data.toRestaurantId)
      Redirect(routes.HomeController.index()).flashing("info" -> "Restaurant deleted")
    }

    val formValidationResult = DeleteRestaurantForm.form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
  }

  private def getUser(request: Request[AnyContent]): User = {
    val webContext = new PlayWebContext(request, controllerComponents.playSessionStore)
    val profileManager = new ProfileManager[CommonProfile](webContext)
    User(profileManager.get(true).orElseThrow(() => new RuntimeException).getTypedId)
  }
}
