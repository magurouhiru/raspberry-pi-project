package controllers

import javax.inject._

import scala.concurrent.ExecutionContext

import play.api.mvc._

import cats.data.EitherT

import services.DeviceApiService
import services.DeviceDBService

@Singleton
class BatchController @Inject() (
    cc: ControllerComponents,
    deviceApiService: DeviceApiService,
    deviceDBService: DeviceDBService,
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def deviceAll(): Action[AnyContent] = Action.async {

    val res = for {
      temp <- EitherT(deviceApiService.getTempInfo)
      freq <- EitherT(deviceApiService.getFreqInfo)
      cpu <- EitherT(deviceApiService.getCpuInfo)
      mem <- EitherT(deviceApiService.getMemInfo)
      _ <- EitherT(deviceDBService.createTemp(temp))
      _ <- EitherT(deviceDBService.createFreq(freq))
      _ <- EitherT(deviceDBService.createCpu(cpu))
      _ <- EitherT(deviceDBService.createMem(mem))
    } yield ()

    res
      .value
      .map {
        case Right(_) => Ok("OK")
        case Left(error) => Status(error.status)(error.error)
      }
  }

}
