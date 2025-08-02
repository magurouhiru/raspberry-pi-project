package controllers

import javax.inject._

import play.api.libs.json._
import play.api.mvc._

import repositories.DeviceRepository
import repositories.HelloRepository

@Singleton
class HealthController @Inject() (
    cc: ControllerComponents,
    helloRepository: HelloRepository,
    deviceRepository: DeviceRepository,
) extends AbstractController(cc) {

  case class Hello(message: String)

  implicit val helloFormat: OFormat[Hello] = Json.format[Hello]

  def health(): Action[AnyContent] = Action(Ok(""))

  def ready(): Action[AnyContent] = Action {
    var sum = 0L
    sum += helloRepository.readSize()
    sum += deviceRepository.readSizeTemp()
    sum += deviceRepository.readSizeFreq()
    sum += deviceRepository.readSizeCpuRaw()
    sum += deviceRepository.readSizeCpuDetailRaw()
    sum += deviceRepository.readSizeMem()
    Ok(sum.toString)
  }

}
