import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

import { webSocket } from 'rxjs/webSocket';

@Injectable({
  providedIn: 'root',
})
export class HelloService {
  client = inject(HttpClient);
  getHello() {
    return this.client.get('/api/hello');
  }
  postHello(message: string) {
    return this.client.post('/api/hello', { message });
  }
  wsHello() {
    return webSocket<MessageEvent<string>>({
      url: '/api/hello/ws',
      deserializer: (e) => e.data,
    });
  }
}
