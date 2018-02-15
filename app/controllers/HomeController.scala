package controllers

import javax.inject._

import models.Restaurant
import play.api.data.Form
import play.api.mvc._

import scala.util.Random

@Singleton
class HomeController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc)  {


  var restaurants: _root_.models.Restaurant = Restaurant("Italian")

  private val restaurantsList = scala.collection.mutable.ArrayBuffer(
    Restaurant("Restaurant 1"),
    Restaurant("Restaurant 2"),
    Restaurant("Restaurant 3")
  )

  val postCall: _root_.play.api.mvc.Call = routes.HomeController.display()
  val postAdd : _root_.play.api.mvc.Call = routes.HomeController.add()

  def index = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.index(restaurantsList, RestaurantForm.form, postCall, postAdd))
  }

  def display = Action { implicit request: MessagesRequest[AnyContent] =>
    val random = new Random
    Redirect(routes.HomeController.index()).flashing("Info" -> "You will eat at : ".concat(restaurantsList(random.nextInt(restaurantsList.length)).name))
  }

  def add = Action { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[RestaurantForm.RestaurantsData] =>
      Redirect(routes.HomeController.index()).flashing("Info" -> "Error")
    }

    val successFunction = { data: RestaurantForm.RestaurantsData =>
      val restaurant = Restaurant(name = data.name)
      restaurantsList.append(restaurant)
      Redirect(routes.HomeController.index()).flashing("info" -> "Restaurant added!")
    }

    val formValidationResult = RestaurantForm.form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
  }

  def listRestaurants = Action { implicit request: MessagesRequest[AnyContent] =>
    // Pass an unpopulated form to the template
    Ok(views.html.index(restaurantsList, RestaurantForm.form, postCall, postAdd))
  }
}
