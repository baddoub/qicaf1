package controllers

import models.RestaurantId

/**
  * Form to delete a restaurant.
  */
object DeleteRestaurantForm {

  import play.api.data.Form
  import play.api.data.Forms._

  case class DeleteRestaurantData(id: Long) {
    def toRestaurantId: RestaurantId = RestaurantId(id)
  }

  val form = Form(
    mapping(
      "id" -> longNumber,
    )(DeleteRestaurantData.apply)(DeleteRestaurantData.unapply)
  )
}
