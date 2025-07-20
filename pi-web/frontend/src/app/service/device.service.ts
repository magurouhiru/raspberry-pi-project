import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class DeviceService {
  client = inject(HttpClient);
  getDevice() {
    return this.client.get<DeviceAllInfoResponse>('/api/device/all');
  }
}

export interface DeviceTempInfoResponse {
  timestamp: string;
  temp: number;
}

export interface DeviceFreqInfoResponse {
  timestamp: string;
  freq: number;
}

export interface CpuDetail {
  total: number;
  idle: number;
}

export interface DeviceCpuInfoResponse {
  timestamp: string;
  cpu: {
    cpu: CpuDetail;
    cpu0: CpuDetail;
    cpu1: CpuDetail;
    cpu2: CpuDetail;
    cpu3: CpuDetail;
  };
}

export interface DeviceMemInfoResponse {
  timestamp: string;
  mem: {
    mem_total: number;
    mem_free: number;
    buffers: number;
    cached: number;
    active: number;
    inactive: number;
  };
}

export interface DeviceAllInfoResponse {
  temp: DeviceTempInfoResponse;
  freq: DeviceFreqInfoResponse;
  cpu: DeviceCpuInfoResponse;
  mem: DeviceMemInfoResponse;
}
