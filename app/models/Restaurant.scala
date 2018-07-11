package models

/**
  * Presentation object used for displaying data in a template */
case class Restaurant private (id: Option[RestaurantId], name: String, user: User) {
  def this(id: RestaurantId, name: String, user: User) = this(Option(id), name, user)
  def this(name: String, user: User) = this(Option.empty, name, user)
}

case class RestaurantId (id: Long)
