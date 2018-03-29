package modules

import com.google.inject.{AbstractModule, Provides}
import org.pac4j.core.client.Clients
import org.pac4j.core.config.Config
import org.pac4j.oauth.client.Google2Client
import org.pac4j.play.scala.{DefaultSecurityComponents, SecurityComponents}
import org.pac4j.play.store.{PlayCacheSessionStore, PlaySessionStore}
import org.pac4j.play.{CallbackController, LogoutController}
import play.api.{Configuration, Environment}

class SecurityModule(environment: Environment, configuration: Configuration) extends AbstractModule {
  val baseUrl = configuration.get[String]("baseUrl")

    override def configure(): Unit = {
      bind(classOf[PlaySessionStore]).to(classOf[PlayCacheSessionStore])
      bind(classOf[SecurityComponents]).to(classOf[DefaultSecurityComponents])

      // callback
      val callbackController = new CallbackController()
      callbackController.setDefaultUrl("/")
      callbackController.setMultiProfile(true)
      bind(classOf[CallbackController]).toInstance(callbackController)

      // logout
      val logoutController = new LogoutController()
      logoutController.setDefaultUrl("/")
      bind(classOf[LogoutController]).toInstance(logoutController)
    }

  @Provides
  def provideGoogleClient: Google2Client = new Google2Client("", "")

  @Provides
  def provideConfig(googleClient: Google2Client): Config = {
    val clients = new Clients(baseUrl + "/callback", googleClient)
    new Config(clients)
  }
}