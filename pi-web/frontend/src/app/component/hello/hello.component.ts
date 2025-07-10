import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject, signal } from '@angular/core';
import { ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIcon } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatSnackBar } from '@angular/material/snack-bar';

import { HelloService } from '../../service/hello.service';
import { BaseComponent } from '../base/base.component';

@Component({
  selector: 'app-hello',
  imports: [
    MatButtonModule,
    MatIcon,
    MatListModule,
    MatInputModule,
    MatFormFieldModule,
    ReactiveFormsModule,
  ],
  templateUrl: './hello.component.html',
  styleUrl: './hello.component.scss',
})
export class HelloComponent extends BaseComponent {
  #hello = inject(HelloService);
  #fb = inject(FormBuilder);
  #snackBar = inject(MatSnackBar);

  getRequestList = signal<string[]>([]);
  sendGetRequest() {
    this.#hello.getHello().subscribe({
      next: (v) => {
        this.getRequestList.update((list) => {
          list.push(JSON.stringify(v));
          return [...list];
        });
      },
      error: (err) => console.error(err),
    });
  }

  postForm = this.#fb.nonNullable.control('');
  postRequestList = signal<string[]>([]);
  sendPostRequest() {
    this.#hello.postHello(this.postForm.getRawValue()).subscribe({
      next: (v) => {
        this.postRequestList.update((list) => {
          list.push(JSON.stringify(v));
          return [...list];
        });
      },
      error: (err: HttpErrorResponse) => {
        this.postRequestList.update((list) => {
          list.push(JSON.stringify(err));
          return [...list];
        });
        console.error(err);
        this.#snackBar.open(
          `status: ${err.status}, message: ${err.error.message}`,
          'OK',
          {
            duration: 5000,
            horizontalPosition: 'right',
            verticalPosition: 'top',
          },
        );
      },
    });
  }

  wsRequestList = signal<string[]>([]);
  #ws = this.#hello.wsHello();
  sendwsRequest() {
    this.#ws.subscribe({
      next: (v) => {
        console.log(v);
        this.wsRequestList.update((list) => {
          list.push(JSON.stringify(v));
          return [...list];
        });
      },
      error: (err) => console.error(err),
    });
  }
}
