import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class DeviceService {
  client = inject(HttpClient);
  getDevice() {
    return this.client.get<Device>('/api/device');
  }
}

export interface Device {
  temp: number;
  freq: number;
  cpu: {
    cpu0: CpuDetail;
    cpu1: CpuDetail;
    cpu2: CpuDetail;
    cpu3: CpuDetail;
  };
  mem: {
    mem_total: number;
    mem_free: number;
    buffers: number;
    cached: number;
    active: number;
    inactive: number;
  };
}

export interface CpuDetail {
  total: number;
  idle: number;
}
