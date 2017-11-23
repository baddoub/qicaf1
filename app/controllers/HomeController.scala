package controllers

import javax.inject._

import models.RestaurantList
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(messagesAction: MessagesActionBuilder, cc: ControllerComponents) extends AbstractController(cc) {


  val restaurants: _root_.models.RestaurantList = RestaurantList("Italian", "Moroccan", "French", "Spanish")


  val postCall: _root_.play.api.mvc.Call = routes.HomeController.index()

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.index(restaurants, RestaurantForm.form, postCall))
  }

  //  def restaurantList = Action {  implicit request: MessagesRequest[AnyContent] =>
  //      Ok(views.html.index(restaurants, RestaurantForm.form, postUrl))
  //  }
}
