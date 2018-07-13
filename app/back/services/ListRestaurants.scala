package back.services

import javax.inject.Inject
import javax.inject.Singleton

import back.repositories.RestaurantRepository
import com.google.inject.ImplementedBy
import models.{Restaurant, User}

@ImplementedBy(classOf[DefaultListRestaurants])
trait ListRestaurants {
  def apply(user: User): Seq[Restaurant]
}

@Singleton class DefaultListRestaurants @Inject() (repository: RestaurantRepository) extends ListRestaurants {
  override def apply(user: User): Seq[Restaurant] = repository.fetchRestaurants(user)
}


