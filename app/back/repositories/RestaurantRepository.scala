package back.repositories

import models.Restaurant

object RestaurantRepository {
  private var restaurantList: Seq[Restaurant] = Seq()

  def fetchRestaurants() = restaurantList

  def addRestaurant(restaurant: Restaurant): Unit = {
    restaurantList :+= restaurant
  }
}
