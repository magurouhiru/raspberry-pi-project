package hardware

import scala.util.Try

import api._

trait Device {

  def getTemp: Try[DeviceTempInfoResponse]
  def getFreq: Try[DeviceFreqInfoResponse]
  def getCpu: Try[DeviceCpuInfoResponse]
  def getMem: Try[DeviceMemInfoResponse]

}
