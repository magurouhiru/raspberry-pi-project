package hardware

import java.time.Instant

import scala.util.Success

import org.mockito.Mockito._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.mockito.MockitoSugar

import api.DeviceCpuDetailInfoResponse
import api.DeviceCpuInfoResponse
import api.DeviceMemInfoResponse

class DeviceSuite extends AnyFunSuite with MockitoSugar {

  test("getTemp") {
    val readFileMock = mock[ReadFile]
    val mockValue = Success("""558441""".split("\\n"))
    when(readFileMock.readLines("/sys/class/thermal/thermal_zone0/temp"))
      .thenReturn(mockValue)
    val device = new DeviceDefault(readFileMock)

    val result = device.getTemp

    assert(result.map(_.temp) == Success(55844))
  }

  test("getFreq") {
    val readFileMock = mock[ReadFile]
    val mockValue = Success("""600000""".split("\\n"))
    when(readFileMock.readLines(
      "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq",
    )).thenReturn(mockValue)
    val device = new DeviceDefault(readFileMock)

    val result = device.getFreq

    assert(result.map(_.freq) == Success(600000))
  }

  test("getCpu") {
    val readFileMock = mock[ReadFile]
    val mockValue = Success {
      """cpu  1548 0 1672 6791246 1246 0 24 0 0 0
cpu0 313 0 344 1697956 337 0 6 0 0 0
cpu1 397 0 428 1697698 416 0 7 0 0 0
cpu2 462 0 478 1697742 199 0 5 0 0 0
cpu3 374 0 421 1697849 292 0 5 0 0 0
intr 142692816 0 0 22102 72 0 1711 67433 0 0 0 114594 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 137045146 0 0
 0 0 0 0 0 0 218 0 4747 0 0 0 0 0 0 0 0 0 0 0 4442889 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1156 4203 0 0 0 0 5290 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 983255 0 0 0 1
ctxt 725784
btime 1753004573
processes 1036
procs_running 1
procs_blocked 0
softirq 1451022 25789 657457 2 24354 0 0 35270 649216 0 58934""".split("\\n")
    }
    when(readFileMock.readLines("/proc/stat")).thenReturn(mockValue)
    val now = Instant.now
    val device = new DeviceDefault(readFileMock)

    val result = device.getCpu
    val resultNow = result.map(r =>
      DeviceCpuInfoResponse(now, r.cpu, r.cpu0, r.cpu1, r.cpu2, r.cpu3),
    )

    val expected = DeviceCpuInfoResponse(
      now,
      DeviceCpuDetailInfoResponse(total = 6795736, idle = 6792918),
      DeviceCpuDetailInfoResponse(total = 1698956, idle = 1698300),
      DeviceCpuDetailInfoResponse(total = 1698946, idle = 1698126),
      DeviceCpuDetailInfoResponse(total = 1698886, idle = 1698220),
      DeviceCpuDetailInfoResponse(total = 1698941, idle = 1698270),
    )

    assert(resultNow == Success(expected))
  }

  test("getMem") {
    val readFileMock = mock[ReadFile]
    val mockValue = Success {
      """MemTotal:         927976 kB
MemFree:          703692 kB
MemAvailable:     783100 kB
Buffers:           19072 kB
Cached:           101068 kB
SwapCached:            0 kB
Active:           112968 kB
Inactive:          32764 kB
Active(anon):      28112 kB
Inactive(anon):        0 kB
Active(file):      84856 kB
Inactive(file):    32764 kB
Unevictable:           0 kB
Mlocked:               0 kB
SwapTotal:       2097148 kB
SwapFree:        2097148 kB
Zswap:                 0 kB
Zswapped:              0 kB
Dirty:                 4 kB
Writeback:             0 kB
AnonPages:         25608 kB
Mapped:            37692 kB
Shmem:              2504 kB
KReclaimable:      13944 kB
Slab:              36924 kB
SReclaimable:      13944 kB
SUnreclaim:        22980 kB
KernelStack:        2368 kB
PageTables:         1544 kB
SecPageTables:         0 kB
NFS_Unstable:          0 kB
Bounce:                0 kB
WritebackTmp:          0 kB
CommitLimit:     2561136 kB
Committed_AS:     169640 kB
VmallocTotal:   261087232 kB
VmallocUsed:       12828 kB
VmallocChunk:          0 kB
Percpu:             1104 kB
CmaTotal:         262144 kB
CmaFree:          256744 kB""".split("\\n")
    }
    when(readFileMock.readLines("/proc/meminfo")).thenReturn(mockValue)
    val device = new DeviceDefault(readFileMock)
    val now = Instant.now

    val result = device.getMem
    val resultNow = result.map(r =>
      DeviceMemInfoResponse(
        now,
        r.mem_total,
        r.mem_free,
        r.buffers,
        r.cached,
        r.active,
        r.inactive,
      ),
    )

    val expected = DeviceMemInfoResponse(
      now,
      mem_total = 927976,
      mem_free = 703692,
      buffers = 19072,
      cached = 101068,
      active = 112968,
      inactive = 32764,
    )

    assert(resultNow == Success(expected))
  }
}
