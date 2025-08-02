import unittest
from unittest.mock import mock_open, patch

from device.device_info import RealDeviceInfo, now_utc
from device.models import TempInfo, FreqInfo, CpuDetailInfo, CpuInfo, MemInfo


class MyTestCase(unittest.TestCase):

    def test_get_temp(self):
        d = RealDeviceInfo()
        m = mock_open(read_data="""55844""")
        with patch("builtins.open", m):
            now = now_utc()
            result = d.get_temp()
            result.timestamp = now
            self.assertEqual(result, TempInfo(now, 55844))
            m.assert_called_once_with("/sys/class/thermal/thermal_zone0/temp", "r")

    def test_get_freq(self):
        d = RealDeviceInfo()
        m = mock_open(read_data="""600000""")
        with patch("builtins.open", m):
            now = now_utc()
            result = d.get_freq()
            result.timestamp = now
            self.assertEqual(result, FreqInfo(now, 600000))
            m.assert_called_once_with("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq", "r")

    def test_get_cpu(self):
        d = RealDeviceInfo()
        m = mock_open(read_data="""cpu  1548 0 1672 6791246 1246 0 24 0 0 0
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
softirq 1451022 25789 657457 2 24354 0 0 35270 649216 0 58934
""")
        with patch("builtins.open", m):
            now = now_utc()
            result = d.get_cpu()
            result.timestamp = now
            cpu = CpuDetailInfo(total=6795736, idle=6792918)
            cpu0 = CpuDetailInfo(total=1698956, idle=1698300)
            cpu1 = CpuDetailInfo(total=1698946, idle=1698126)
            cpu2 = CpuDetailInfo(total=1698886, idle=1698220)
            cpu3 = CpuDetailInfo(total=1698941, idle=1698270)
            self.assertEqual(result, CpuInfo(now, cpu, cpu0, cpu1, cpu2, cpu3))
            m.assert_called_once_with("/proc/stat", "r")

    def test_get_mem(self):
        d = RealDeviceInfo()
        m = mock_open(read_data="""MemTotal:         927976 kB
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
CmaFree:          256744 kB
""")
        with patch("builtins.open", m):
            now = now_utc()
            result = d.get_mem()
            result.timestamp = now
            self.assertEqual(
                result,
                MemInfo(
                    now,
                    mem_total=927976,
                    mem_free=703692,
                    buffers=19072,
                    cached=101068,
                    active=112968,
                    inactive=32764,
                ))
            m.assert_called_once_with("/proc/meminfo", "r")


if __name__ == '__main__':
    unittest.main()
