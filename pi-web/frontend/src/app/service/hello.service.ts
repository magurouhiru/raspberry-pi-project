import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map } from 'rxjs';

import { PageParams, toWithTotal } from './shared';

import { webSocket } from 'rxjs/webSocket';

@Injectable({
  providedIn: 'root',
})
export class HelloService {
  client = inject(HttpClient);

  read(pp: PageParams) {
    return this.client
      .get<
        Hello[]
      >('/api/hello', { params: { limit: pp.limit, offset: pp.offset }, observe: 'response' })
      .pipe(map(toWithTotal));
  }

  getTestHello() {
    return this.client.get<Hello>('/api/test/hello');
  }

  postTestHello(message: string) {
    return this.client.post<Hello>('/api/test/hello', {
      message,
    } satisfies Hello);
  }

  wsHello() {
    return webSocket<MessageEvent<string>>({
      url: '/api/hello/ws',
      deserializer: (e) => e.data,
    });
  }
}

interface Hello {
  message: string;
}
