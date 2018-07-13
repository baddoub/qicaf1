package back.services

import javax.inject.{Inject, Singleton}

import back.repositories.RestaurantRepository
import com.google.inject.ImplementedBy
import models.{Restaurant, RestaurantId, User}

@ImplementedBy(classOf[DefaultChooseRestaurant])
trait ChooseRestaurant{
  def apply(user: User): Option[Restaurant]
}


