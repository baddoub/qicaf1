package back.repositories

import models.Restaurant

import scala.collection.mutable

object RestaurantRepository {
  private val restaurantMap: mutable.Map[String, Seq[Restaurant]] = mutable.Map.empty

  def fetchRestaurants(userId: String) = restaurantMap.getOrElse(userId, Seq())

  def addRestaurant(userId: String, restaurant: Restaurant): Unit = {
    restaurantMap += ((userId, restaurantMap.getOrElse(userId, Seq()) :+ restaurant))
  }
}
