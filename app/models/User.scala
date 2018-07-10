package models

/**
  * Class representing a user of the application
  * @param userTypeId a string representing the id of the user, including the authentication method, as returned by pac4j.
  */
case class User (userTypeId: String)

