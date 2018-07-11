package back.repositories

import javax.inject.Inject
import javax.inject.Singleton

import models.{Restaurant, RestaurantId, User}
import play.api.db.Database

@Singleton
class RestaurantRepository @Inject() (db: Database) {
  def fetchRestaurants(user: User): Seq[Restaurant] = {
    db.withTransaction(connection => {
      val statement = connection.prepareStatement("SELECT id, restaurant_name, user_type_id FROM RESTAURANT WHERE USER_TYPE_ID = ?")
      statement.setString(1, user.userTypeId)
      val resultSet = statement.executeQuery()
      val resultBuilder = Seq.newBuilder[Restaurant]
      while(resultSet.next()) {
        resultBuilder += new Restaurant(
          RestaurantId(resultSet.getLong("id")),
          resultSet.getString("restaurant_name"),
          User(resultSet.getString("user_type_id"))
        )
      }
      resultBuilder.result()
    })
  }

  def addRestaurant(restaurant: Restaurant): Unit = {
    db.withTransaction(connection => {
      val statement = connection.prepareStatement("INSERT INTO RESTAURANT (USER_TYPE_ID, RESTAURANT_NAME) VALUES (?, ?)")
      statement.setString(1, restaurant.user.userTypeId)
      statement.setString(2, restaurant.name)
      statement.executeUpdate()
    })
  }

  def deleteRestaurant(user: User, restaurantId: RestaurantId): Unit = {
    db.withTransaction(connection => {
      val statement = connection.prepareStatement("DELETE FROM RESTAURANT WHERE USER_TYPE_ID = ? AND ID = ?")
      statement.setString(1, user.userTypeId)
      statement.setLong(2, restaurantId.id)
      val deletedRowsNumber = statement.executeUpdate()
      if(deletedRowsNumber == 0) {
        throw new IllegalArgumentException("Could not delete restaurant " + restaurantId)
      }
    })
  }
}
