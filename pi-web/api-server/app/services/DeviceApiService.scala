package services

import javax.inject.Inject
import javax.inject.Singleton

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import play.api.Configuration
import play.libs.ws._

import api._

@Singleton
class DeviceApiService @Inject() (config: Configuration, ws: WSClient)(implicit
    ec: ExecutionContext,
) extends ApiServiceBase(ws) {

  private val deviceUrl: String = config.get[String]("device.server.url")

  def getAppEnv: Future[Either[ServiceError, DeviceServerAppEnvResponse]] =
    get[DeviceServerAppEnvResponse](s"$deviceUrl/device/app/env")

  def getTempInfo: Future[Either[ServiceError, DeviceTempInfoResponse]] =
    get[DeviceTempInfoResponse](s"$deviceUrl/device/temp")

  def getFreqInfo: Future[Either[ServiceError, DeviceFreqInfoResponse]] =
    get[DeviceFreqInfoResponse](s"$deviceUrl/device/freq")

  def getCpuInfo: Future[Either[ServiceError, DeviceCpuInfoResponse]] =
    get[DeviceCpuInfoResponse](s"$deviceUrl/device/cpu")

  def getMemInfo: Future[Either[ServiceError, DeviceMemInfoResponse]] =
    get[DeviceMemInfoResponse](s"$deviceUrl/device/mem")

}
