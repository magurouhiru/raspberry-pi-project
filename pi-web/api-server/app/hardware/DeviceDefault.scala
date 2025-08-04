package hardware

import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

import scala.util.Try

import api._

@Singleton
class DeviceDefault @Inject() (readFile: ReadFile) extends Device {

  def getTemp: Try[DeviceTempInfoResponse] = readFile
    .readLines("/sys/class/thermal/thermal_zone0/temp")
    .map(lines => DeviceTempInfoResponse(Instant.now, lines.head.trim.toInt))

  def getFreq: Try[DeviceFreqInfoResponse] = readFile
    .readLines("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq")
    .map(lines => DeviceFreqInfoResponse(Instant.now, lines.head.trim.toInt))

  private val DELIMITER_REGEX = "\\s+"

  def getCpu: Try[DeviceCpuInfoResponse] = readFile
    .readLines("/proc/stat")
    .map { lines =>
      val map: Map[String, Array[Int]] = lines
        .map(_.split(DELIMITER_REGEX))
        .collect { case Array(key, values @ _*) =>
          key -> values.map(_.toInt).toArray
        }
        .toMap
      DeviceCpuInfoResponse(
        Instant.now,
        getCpuDetail(map("cpu")),
        getCpuDetail(map("cpu0")),
        getCpuDetail(map("cpu1")),
        getCpuDetail(map("cpu2")),
        getCpuDetail(map("cpu3")),
      )

    }

  private def getCpuDetail(detail: Array[Int]): DeviceCpuDetailInfoResponse =
    DeviceCpuDetailInfoResponse(detail.sum, detail(2) + detail(3))

  def getMem: Try[DeviceMemInfoResponse] = readFile
    .readLines("/proc/meminfo")
    .map { lines =>
      val map: Map[String, Int] = lines
        .map(_.split(DELIMITER_REGEX))
        .collect { case Array(key, values, _) => key -> values.toInt }
        .toMap
      DeviceMemInfoResponse(
        Instant.now,
        mem_total = map("MemTotal:"),
        mem_free = map("MemFree:"),
        buffers = map("Buffers:"),
        cached = map("Cached:"),
        active = map("Active:"),
        inactive = map("Inactive:"),
      )
    }

}
