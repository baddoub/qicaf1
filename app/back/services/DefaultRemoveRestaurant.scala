package back.services

import javax.inject.{Inject, Singleton}

import back.repositories.RestaurantRepository
import models.{RestaurantId, User}

@Singleton class DefaultRemoveRestaurant @Inject()(repository: RestaurantRepository) extends RemoveRestaurant{
  override def apply(user: User, restaurantId: RestaurantId): Unit = repository.deleteRestaurant(user, restaurantId)
}
