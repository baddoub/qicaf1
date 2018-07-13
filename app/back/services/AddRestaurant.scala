package back.services

import javax.inject.Inject

import back.repositories.RestaurantRepository
import com.google.inject.ImplementedBy
import models.Restaurant

@ImplementedBy(classOf[DefaultAddRestaurant])
trait AddRestaurant {
  def apply(restaurant: Restaurant): Unit
}


