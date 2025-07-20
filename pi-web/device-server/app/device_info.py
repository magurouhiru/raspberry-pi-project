import random
import re
from abc import abstractmethod, ABC

from app.models import TempInfo, FreqInfo, mem_key_map, MemInfo, CpuDetailInfo, CpuInfo


class DeviceInfoBase(ABC):

    @abstractmethod
    def get_temp(self) -> TempInfo:
        pass

    @abstractmethod
    def get_freq(self) -> FreqInfo:
        pass

    @abstractmethod
    def get_cpu(self) -> CpuInfo:
        pass

    @abstractmethod
    def get_mem(self) -> MemInfo:
        pass


def get_cpu_detail(target: str, data: {str: [str]}) -> CpuDetailInfo:
    for k, v in data.items():
        if k == target:
            idle = v[2] + v[3]
            total = sum(v)
            return CpuDetailInfo(total, idle)
    raise RuntimeError("can't find cpu detail for " + target)


class RealDeviceInfo(DeviceInfoBase):

    def get_temp(self) -> TempInfo:
        with open("/sys/class/thermal/thermal_zone0/temp", "r") as f:
            temp_str = f.read()
        return TempInfo(temp=int(temp_str))

    def get_freq(self) -> FreqInfo:
        with open("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq", "r") as f:
            freq_str = f.read()
        return FreqInfo(freq=int(freq_str))

    def get_cpu(self):
        with open("/proc/stat", "r") as f:
            cpu_str = f.read()

        buf: {str: [str]} = {}
        for line in cpu_str.split("\n"):
            tmp = line.split()
            buf.update({tmp[0]: tmp[1:]})

        cpu = get_cpu_detail("cpu", buf)
        cpu0 = get_cpu_detail("cpu0", buf)
        cpu1 = get_cpu_detail("cpu1", buf)
        cpu2 = get_cpu_detail("cpu2", buf)
        cpu3 = get_cpu_detail("cpu3", buf)
        return CpuInfo(cpu, cpu0, cpu1, cpu2, cpu3)

    def get_mem(self) -> MemInfo:
        with open("/proc/meminfo", "r") as f:
            mem_str = f.read()

        buff = {}
        for line in mem_str.strip().split("\n"):
            # 行の先頭の「キー」と「数値」を抽出
            # 例: "MemTotal:" と "927976"
            m = re.match(r"(\S+):\s+(\d+)", line)
            if m:
                key = m.group(1)
                value = int(m.group(2))
                buff[key] = value

        data = {field: buff[mem_key_map[field]] for field in mem_key_map}
        return MemInfo(**data)


class MockDeviceInfo(DeviceInfoBase):

    def get_temp(self) -> TempInfo:
        return TempInfo(temp=random.randint(60000, 100000))

    def get_freq(self) -> FreqInfo:
        return FreqInfo(freq=random.randint(600000000, 1200000000))

    def get_cpu(self) -> CpuInfo:
        cpu = CpuDetailInfo(total=100, idle=50)
        cpu0 = CpuDetailInfo(total=100, idle=0)
        cpu1 = CpuDetailInfo(total=100, idle=10)
        cpu2 = CpuDetailInfo(total=100, idle=20)
        cpu3 = CpuDetailInfo(total=100, idle=30)
        return CpuInfo(cpu, cpu0, cpu1, cpu2, cpu3)

    def get_mem(self) -> MemInfo:
        return MemInfo(mem_total=927976, mem_free=900000, buffers=800000, cached=700000, active=600000, inactive=500000)


def provide_device_info(mock_mode: bool) -> DeviceInfoBase:
    if mock_mode:
        return MockDeviceInfo()
    else:
        return RealDeviceInfo()
