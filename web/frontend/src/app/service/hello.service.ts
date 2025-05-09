import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

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
}
