package controllers


object RestaurantForm {

  import play.api.data.Form
  import play.api.data.Forms._

  case class RestaurantsData(rest1: String, rest2: String, rest3: String, rest4: String, rest5: String)


  val form = Form(
    mapping(
      "rest1" -> nonEmptyText,
      "rest2" -> nonEmptyText,
      "rest3" -> nonEmptyText,
      "rest4" -> nonEmptyText,
      "rest5" -> nonEmptyText
    )(RestaurantsData.apply)(RestaurantsData.unapply)
  )
}
