package controllers

import javax.inject._

import scala.concurrent.ExecutionContext

import play.api.libs.json._
import play.api.mvc._

import cats.data.EitherT

import api.DeviceAllInfoResponse
import services.DeviceApiService
import services.DeviceDBService
import services.WithTotal

@Singleton
class DeviceController @Inject() (
    cc: ControllerComponents,
    apiService: DeviceApiService,
    dbService: DeviceDBService,
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def getDBTemp(offset: Option[Int], limit: Option[Int]): Action[AnyContent] =
    Action.async(
      dbService
        .readTemp(offset.getOrElse(0), limit.getOrElse(5))
        .map {
          case Right(WithTotal(size, items)) => Ok(Json.toJson(items))
              .withHeaders("X-Total-Count" -> size.toString)
          case Left(error) => Status(error.status)(error.error)
        },
    )

  def getTemp: Action[AnyContent] = Action.async(
    apiService
      .getTempInfo
      .map {
        case Right(value) => Ok(Json.toJson(value))
        case Left(error) => Status(error.status)(error.error)
      },
  )

  def getDBFreq(offset: Option[Int], limit: Option[Int]): Action[AnyContent] =
    Action.async(
      dbService
        .readFreq(offset.getOrElse(0), limit.getOrElse(5))
        .map {
          case Right(WithTotal(size, items)) => Ok(Json.toJson(items))
              .withHeaders("X-Total-Count" -> size.toString)
          case Left(error) => Status(error.status)(error.error)
        },
    )

  def getFreq: Action[AnyContent] = Action.async(
    apiService
      .getFreqInfo
      .map {
        case Right(value) => Ok(Json.toJson(value))
        case Left(error) => Status(error.status)(error.error)
      },
  )

  def getDBCpuDetailRaw(
      offset: Option[Int],
      limit: Option[Int],
  ): Action[AnyContent] = Action.async(
    dbService
      .readCpuDetailRaw(offset.getOrElse(0), limit.getOrElse(5))
      .map {
        case Right(WithTotal(size, items)) =>
          Ok(Json.toJson(items)).withHeaders("X-Total-Count" -> size.toString)
        case Left(error) => Status(error.status)(error.error)
      },
  )

  def getDBCpuRaw(offset: Option[Int], limit: Option[Int]): Action[AnyContent] =
    Action.async(
      dbService
        .readCpuRaw(offset.getOrElse(0), limit.getOrElse(5))
        .map {
          case Right(WithTotal(size, items)) => Ok(Json.toJson(items))
              .withHeaders("X-Total-Count" -> size.toString)
          case Left(error) => Status(error.status)(error.error)
        },
    )

  def getDBCpu(offset: Option[Int], limit: Option[Int]): Action[AnyContent] =
    Action.async(
      dbService
        .readCpu(offset.getOrElse(0), limit.getOrElse(5))
        .map {
          case Right(WithTotal(size, items)) => Ok(Json.toJson(items))
              .withHeaders("X-Total-Count" -> size.toString)
          case Left(error) => Status(error.status)(error.error)
        },
    )

  def getCpu: Action[AnyContent] = Action.async(
    apiService
      .getCpuInfo
      .map {
        case Right(value) => Ok(Json.toJson(value))
        case Left(error) => Status(error.status)(error.error)
      },
  )

  def getDBMem(offset: Option[Int], limit: Option[Int]): Action[AnyContent] =
    Action.async(
      dbService
        .readMem(offset.getOrElse(0), limit.getOrElse(5))
        .map {
          case Right(WithTotal(size, items)) => Ok(Json.toJson(items))
              .withHeaders("X-Total-Count" -> size.toString)
          case Left(error) => Status(error.status)(error.error)
        },
    )

  def getMem: Action[AnyContent] = Action.async(
    apiService
      .getMemInfo
      .map {
        case Right(value) => Ok(Json.toJson(value))
        case Left(error) => Status(error.status)(error.error)
      },
  )

  def getAll: Action[AnyContent] = Action.async {
    val res = for {
      temp <- EitherT(apiService.getTempInfo)
      freq <- EitherT(apiService.getFreqInfo)
      cpu <- EitherT(apiService.getCpuInfo)
      mem <- EitherT(apiService.getMemInfo)
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
