package controllers

import play.api.data.Form
import play.api.data.Forms._

case class RestaurantData(restaurant1: String, restaurant2: String, restaurant3: String, restaurant4: String) {


  val restaurantsForm = Form(
    mapping(
      "restaurant1" -> text,
      "restaurant2" -> text,
      "restaurant3" -> text,
      "restaurant4" -> text,
    )(RestaurantData.apply)(RestaurantData.unapply)
  )


  val anyData = Map("restaurant1" -> "bob", "restaurant2" -> "21")
//
//  val ResData = restaurantsForm.bind(anyData).get
//  val ResData1 = restaurantsForm.bindFromRequest.get


//  restaurantsForm.bindFromRequest.fold(
//    formWithErrors => {
//      // binding failure, you retrieve the form containing errors:
//      BadRequest(views.html.restaurants(formWithErrors))
//    },
//    userData => {
//      /* binding success, you get the actual value. */
//      val newUser = models.(ResData1.rest, userData.age)
//      val id = models.RestaurantList.create(newUser)
//      Redirect(routes..home(id))
//    }
//  )
}