package controllers

import javax.inject._

import models.RestaurantList
import play.api.data.Form
import play.api.mvc._

import scala.util.Random

@Singleton
class HomeController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc)  {


  var restaurants: _root_.models.RestaurantList = RestaurantList("Italian", "Moroccan", "French", "Spanish")


  val postCall: _root_.play.api.mvc.Call = routes.HomeController.display()

  def index = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.index(restaurants, RestaurantForm.form, postCall))
  }

  def display = Action { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[RestaurantForm.RestaurantsData] =>
      Redirect(routes.HomeController.index()).flashing("Info" -> "Please fill in all fields.")
    }
    val successFunction = { data : RestaurantForm.RestaurantsData =>
      val answer = List.newBuilder.+=(data.rest1).+=(data.rest2).+=(data.rest3).+=(data.rest4).result()
      val random = new Random
      Redirect(routes.HomeController.index()).flashing("Info" -> "You will eat at : ".concat(answer(random.nextInt(answer.length))))
    }
    val formValidationResult = RestaurantForm.form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
  }

}
