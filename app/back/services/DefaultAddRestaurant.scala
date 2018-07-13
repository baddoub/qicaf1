package back.services

import javax.inject.Inject
import javax.inject.Singleton

import back.repositories.RestaurantRepository
import models.Restaurant

@Singleton class DefaultAddRestaurant @Inject() (repository: RestaurantRepository) extends AddRestaurant {
  override def apply(restaurant: Restaurant): Unit = repository.addRestaurant(restaurant)
}
