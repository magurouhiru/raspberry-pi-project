package controllers

import javax.inject._

import scala.concurrent.ExecutionContext

import play.api.libs.json._
import play.api.mvc._

import api.DeviceAllInfoResponse
import cats.data.EitherT
import services.DeviceApiService

@Singleton
class DeviceController @Inject() (
    cc: ControllerComponents,
    service: DeviceApiService,
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def getTemp: Action[AnyContent] = Action.async(
    service
      .getTempInfo
      .map {
        case Right(value) => Ok(Json.toJson(value))
        case Left(error) => Status(error.status)(error.error)
      },
  )

  def getFreq: Action[AnyContent] = Action.async(
    service
      .getFreqInfo
      .map {
        case Right(value) => Ok(Json.toJson(value))
        case Left(error) => Status(error.status)(error.error)
      },
  )

  def getCpu: Action[AnyContent] = Action.async(
    service
      .getCpuInfo
      .map {
        case Right(value) => Ok(Json.toJson(value))
        case Left(error) => Status(error.status)(error.error)
      },
  )

  def getMem: Action[AnyContent] = Action.async(
    service
      .getMemInfo
      .map {
        case Right(value) => Ok(Json.toJson(value))
        case Left(error) => Status(error.status)(error.error)
      },
  )

  def getAll: Action[AnyContent] = Action.async {
    val res = for {
      temp <- EitherT(service.getTempInfo)
      freq <- EitherT(service.getFreqInfo)
      cpu <- EitherT(service.getCpuInfo)
      mem <- EitherT(service.getMemInfo)
    } yield (temp, freq, cpu, mem)

    res
      .value
      .map {
        case Right((temp, freq, cpu, mem)) =>
          Ok(Json.toJson(DeviceAllInfoResponse(temp, freq, cpu, mem)))
        case Left(error) => Status(error.status)(error.error)
      }
  }
}
