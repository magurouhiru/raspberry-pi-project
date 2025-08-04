import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map } from 'rxjs';

import { PageParams, toWithTotal } from './shared';

@Injectable({
  providedIn: 'root',
})
export class DBDeviceService {
  client = inject(HttpClient);

  readTemp(pp: PageParams) {
    return this.client
      .get<DBDeviceTempInfoResponse[]>('/api/device/db/temp', {
        params: { offset: pp.offset, limit: pp.limit },
        observe: 'response',
      })
      .pipe(map(toWithTotal));
  }

  readFreq(pp: PageParams) {
    return this.client
      .get<DBDeviceFreqInfoResponse[]>('/api/device/db/freq', {
        params: { offset: pp.offset, limit: pp.limit },
        observe: 'response',
      })
      .pipe(map(toWithTotal));
  }

  readCpuDetailRaw(pp: PageParams) {
    return this.client
      .get<DBDeviceCpuDetailRaw[]>('/api/device/db/cpu-detail/raw', {
        params: { offset: pp.offset, limit: pp.limit },
        observe: 'response',
      })
      .pipe(map(toWithTotal));
  }

  readCpuRaw(pp: PageParams) {
    return this.client
      .get<DBDeviceCpuRaw[]>('/api/device/db/cpu/raw', {
        params: { offset: pp.offset, limit: pp.limit },
        observe: 'response',
      })
      .pipe(map(toWithTotal));
  }

  readCpu(pp: PageParams) {
    return this.client
      .get<DBDeviceCpuInfoResponse[]>('/api/device/db/cpu', {
        params: { offset: pp.offset, limit: pp.limit },
        observe: 'response',
      })
      .pipe(map(toWithTotal));
  }

  readMem(pp: PageParams) {
    return this.client
      .get<DBDeviceMemInfoResponse[]>('/api/device/db/mem', {
        params: { offset: pp.offset, limit: pp.limit },
        observe: 'response',
      })
      .pipe(map(toWithTotal));
  }
}

export interface DBDeviceTempInfoResponse {
  id: number;
  timestamp: string;
  temp: number;
}

export interface DBDeviceFreqInfoResponse {
  id: number;
  timestamp: string;
  freq: number;
}

export interface DBDeviceCpuDetailRaw {
  id: number;
  cpu_info_id: number;
  cpu_number: number;
  total: number;
  idle: number;
}

export interface DBDeviceCpuRaw {
  id: number;
  timestamp: string;
}

export interface DBDeviceCpuDetail {
  total: number;
  idle: number;
}

export interface DBDeviceCpuInfoResponse {
  id: number;
  timestamp: string;
  cpu: DBDeviceCpuDetail;
  cpu0: DBDeviceCpuDetail;
  cpu1: DBDeviceCpuDetail;
  cpu2: DBDeviceCpuDetail;
  cpu3: DBDeviceCpuDetail;
}

export interface DBDeviceMemInfoResponse {
  id: number;
  timestamp: string;
  mem_total: number;
  mem_free: number;
  buffers: number;
  cached: number;
  active: number;
  inactive: number;
}
