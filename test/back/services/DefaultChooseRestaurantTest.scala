package back.services

import back.repositories.RestaurantRepository
import models.{Restaurant, User}
import org.mockito.{ArgumentMatchers, Matchers}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec

class DefaultChooseRestaurantTest extends PlaySpec with MockitoSugar {
  val user = User("user")
  val repository = mock[RestaurantRepository]
  val chooseRestaurant = new DefaultChooseRestaurant(repository)

  "ChooseRestaurant" should {
    "Choose one restaurant among the different available" in {
      val restaurants = Seq(new Restaurant("restaurant1", user), new Restaurant("restaurant2", user))
      when(repository.fetchRestaurants(user)).thenReturn(restaurants)

      restaurants must contain(chooseRestaurant(user).get)
    }

    "Choose the only restaurant available" in {
      val restaurants = Seq(new Restaurant("restaurant1", user))
      when(repository.fetchRestaurants(user)).thenReturn(restaurants)

      chooseRestaurant(user) mustBe Option(restaurants.head)
    }

    "Return empty when no restaurants are available" in {
      val restaurants = Seq()
      when(repository.fetchRestaurants(user)).thenReturn(restaurants)

      chooseRestaurant(user) mustBe empty
    }
  }
}
