package services

import javax.inject.Inject
import javax.inject.Singleton

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import api._
import hardware.Device

@Singleton
class DeviceApiService @Inject() (device: Device)(implicit ec: ExecutionContext) {

  def getTempInfo: Future[Either[ServiceError, DeviceTempInfoResponse]] = Future(
    device.getTemp.toEither.left.map(e => ServiceError.throwableToServiceError(e)),
  )

  def getFreqInfo: Future[Either[ServiceError, DeviceFreqInfoResponse]] = Future(
    device.getFreq.toEither.left.map(e => ServiceError.throwableToServiceError(e)),
  )

  def getCpuInfo: Future[Either[ServiceError, DeviceCpuInfoResponse]] = Future(
    device.getCpu.toEither.left.map(e => ServiceError.throwableToServiceError(e)),
  )

  def getMemInfo: Future[Either[ServiceError, DeviceMemInfoResponse]] = Future(
    device.getMem.toEither.left.map(e => ServiceError.throwableToServiceError(e)),
  )

}
