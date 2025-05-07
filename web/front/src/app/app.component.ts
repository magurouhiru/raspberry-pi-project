import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HelloService } from './hello.service';
import { toSignal } from '@angular/core/rxjs-interop';
import { map } from 'rxjs';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'front';
  s = inject(HelloService);
  hello = toSignal(this.s.getHello().pipe(map((x) => JSON.stringify(x))));
}
