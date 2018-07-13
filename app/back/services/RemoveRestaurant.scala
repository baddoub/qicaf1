package back.services

import com.google.inject.ImplementedBy
import models.{RestaurantId, User}

@ImplementedBy(classOf[DefaultRemoveRestaurant])
trait RemoveRestaurant {
  def apply(user: User, restaurantId: RestaurantId): Unit
}

