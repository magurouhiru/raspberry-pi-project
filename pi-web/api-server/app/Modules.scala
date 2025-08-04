import play.api.Configuration
import play.api.Environment
import play.api.inject.Binding

import hardware._

class Modules extends play.api.inject.Module {
  override def bindings(
      environment: Environment,
      configuration: Configuration,
  ): collection.Seq[Binding[_]] = {
    val mockOn = configuration.get[String]("device.mock") == "true"

    Seq(bind[Device].toInstance(
      if (mockOn) new DeviceMock() else new DeviceDefault(ReadFile),
    ))
  }
}
