import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class HelloService {
  readonly #http = inject(HttpClient);
  getHello() {
    return this.#http.get('/api');
  }
}
