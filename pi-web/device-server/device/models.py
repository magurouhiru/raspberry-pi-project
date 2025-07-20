from dataclasses import dataclass


@dataclass
class TempInfo:
    timestamp: str
    temp: int


@dataclass
class FreqInfo:
    timestamp: str
    freq: int


@dataclass
class CpuDetailInfo:
    total: int
    idle: int


@dataclass
class CpuInfo:
    timestamp: str
    cpu: CpuDetailInfo
    cpu0: CpuDetailInfo
    cpu1: CpuDetailInfo
    cpu2: CpuDetailInfo
    cpu3: CpuDetailInfo


@dataclass
class MemInfo:
    timestamp: str
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
