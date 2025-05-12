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
    cpu0: number;
    cpu1: number;
    cpu2: number;
    cpu3: number;
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
