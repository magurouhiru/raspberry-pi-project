import { Component, inject, OnInit } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { map } from 'rxjs';

import { HelloService } from './service/hello.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ReactiveFormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  hello = inject(HelloService);
  fb = inject(FormBuilder);

  getHello = toSignal(
    this.hello.getHello().pipe(map((x) => JSON.stringify(x))),
  );
  form = this.fb.nonNullable.control('');
  submit() {
    this.hello.postHello(this.form.getRawValue()).subscribe({
      next: (x) => console.log(JSON.stringify(x)),
      error: (err) => console.error(err),
    });
  }
  subject = this.hello.wsHello();
  conect() {
    this.subject.subscribe({
      next: (message: MessageEvent) => console.log(JSON.stringify(message)),
      error: (e) => console.error(e),
      complete: () => console.log("complete"),
    })
  }
  send() {
    this.subject.next(new MessageEvent<string>("hello"))
  }
}
