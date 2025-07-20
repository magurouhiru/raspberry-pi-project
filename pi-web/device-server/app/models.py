from dataclasses import dataclass


@dataclass
class TempInfo:
    temp: int


@dataclass
class FreqInfo:
    freq: int


@dataclass
class CpuDetailInfo:
    total: int
    idle: int


@dataclass
class CpuInfo:
    cpu: CpuDetailInfo
    cpu0: CpuDetailInfo
    cpu1: CpuDetailInfo
    cpu2: CpuDetailInfo
    cpu3: CpuDetailInfo


@dataclass
class MemInfo:
    mem_total: int
    mem_free: int
    buffers: int
    cached: int
    active: int
    inactive: int


mem_key_map = {
    'mem_total': 'MemTotal',
    'mem_free': 'MemFree',
    'buffers': 'Buffers',
    'cached': 'Cached',
    'active': 'Active',
    'inactive': 'Inactive',
}
