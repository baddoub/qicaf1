package back.repositories

import javax.inject.Inject
import javax.inject.Singleton

import models.Restaurant
import play.api.db.Database

@Singleton
class RestaurantRepository @Inject() (db: Database) {
  def fetchRestaurants(userId: String): Seq[Restaurant] = {
    db.withTransaction(connection => {
      val statement = connection.prepareStatement("SELECT restaurant_name FROM RESTAURANT WHERE USER_TYPE_ID = ?")
      statement.setString(1, userId)
      val resultSet = statement.executeQuery()
      val resultBuilder = Seq.newBuilder[Restaurant]
      while(resultSet.next()) {
        resultBuilder += Restaurant(resultSet.getString("restaurant_name"))
      }
      resultBuilder.result()
    })
  }

  def addRestaurant(userId: String, restaurant: Restaurant): Unit = {
    db.withTransaction(connection => {
      val statement = connection.prepareStatement("INSERT INTO RESTAURANT (USER_TYPE_ID, RESTAURANT_NAME) VALUES (?, ?)")
      statement.setString(1, userId)
      statement.setString(2, restaurant.name)
      statement.executeUpdate()
    })
  }
}
