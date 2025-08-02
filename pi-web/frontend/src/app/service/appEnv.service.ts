import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { retry, shareReplay } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AppEnvService {
  client = inject(HttpClient);

  getAppEnv() {
    return this.#getAppEnvFetch().pipe(
      retry({ count: 100, delay: 10000 }),
      shareReplay(1),
    );
  }

  #getAppEnvFetch() {
    return this.client.get<AppEnvResponse>('/api/app/env');
  }
}

export interface ApiServerAppEnvResponse {
  APP_ENV: string;
}

export interface DeviceServerAppEnvResponse {
  APP_ENV: string;
  MOCK_MODE: boolean;
}

export interface AppEnvResponse {
  api_server: ApiServerAppEnvResponse;
  device_server?: DeviceServerAppEnvResponse;
}
