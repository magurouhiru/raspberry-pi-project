import { Component, inject, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { createTitle, CustomData } from '../../routerData';

@Component({
  selector: 'app-base',
  imports: [],
  template: '',
})
export class BaseComponent implements OnInit {
  #route = inject(ActivatedRoute);
  #title = inject(Title);
  #data$ = this.#route.data as Observable<CustomData>;
  ngOnInit(): void {
    this.#data$.subscribe({
      next: (data: CustomData) => {
        if (data) {
          // title を変更
          this.#title.setTitle(createTitle(data));
        }
      },
      error: (err) => console.error(err),
    });
  }
}
