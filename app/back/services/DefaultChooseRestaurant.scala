package back.services

import javax.inject.{Inject, Singleton}

import back.repositories.RestaurantRepository
import models.{Restaurant, User}

import scala.util.Random

@Singleton class DefaultChooseRestaurant @Inject()(repository: RestaurantRepository) extends ChooseRestaurant{
  val random = Random
  override def apply(user: User): Option[Restaurant] = random.shuffle(repository.fetchRestaurants(user)).headOption
}
