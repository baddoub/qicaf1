package controllers


object RestaurantForm {

  import play.api.data.Form
  import play.api.data.Forms._

  /**
    * A form processing DTO that maps to the form below.
    *
    * Using a class specifically for form binding reduces the chances
    * of a parameter tampering attack and makes code clearer.
    */
  case class RestaurantsData(name: String)


  /**
    * The form definition for the "add restaurant" form.
    * It specifies the form fields and their types,
    * as well as how to convert from a Data to form data and vice versa.
    */

  val form = Form(
    mapping(
      "name" -> nonEmptyText,
    )(RestaurantsData.apply)(RestaurantsData.unapply)
  )
}
