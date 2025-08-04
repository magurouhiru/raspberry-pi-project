package hardware

import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

import scala.util.Random
import scala.util.Success
import scala.util.Try

import api._

@Singleton
class DeviceMock @Inject() extends Device {

  private val rnd = new Random()

  def getTemp: Try[DeviceTempInfoResponse] =
    Success(DeviceTempInfoResponse(Instant.now, rnd.between(60000, 80000)))

  def getFreq: Try[DeviceFreqInfoResponse] =
    Success(DeviceFreqInfoResponse(Instant.now, rnd.between(600000, 1200000)))

  private var cnt = 1
  private val idle_buf = Array(0, 0, 0, 0, 0)
  def getCpu: Try[DeviceCpuInfoResponse] = {
    cnt += 1
    idle_buf(0) += rnd.between(0, 100)
    idle_buf(1) += rnd.between(0, 100)
    idle_buf(2) += rnd.between(0, 100)
    idle_buf(3) += rnd.between(0, 100)
    idle_buf(4) += rnd.between(0, 100)
    val cpu = DeviceCpuDetailInfoResponse(100 * cnt, idle_buf(0))
    val cpu0 = DeviceCpuDetailInfoResponse(100 * cnt, idle_buf(1))
    val cpu1 = DeviceCpuDetailInfoResponse(100 * cnt, idle_buf(2))
    val cpu2 = DeviceCpuDetailInfoResponse(100 * cnt, idle_buf(3))
    val cpu3 = DeviceCpuDetailInfoResponse(100 * cnt, idle_buf(4))
    Success(DeviceCpuInfoResponse(Instant.now, cpu, cpu0, cpu1, cpu2, cpu3))
  }

  def getMem: Try[DeviceMemInfoResponse] = Success(DeviceMemInfoResponse(
    Instant.now,
    mem_total = 927976,
    mem_free = 703692 + rnd.between(-200000, 100000),
    buffers = 19072,
    cached = 101068,
    active = 112968,
    inactive = 32764,
  ))

}
