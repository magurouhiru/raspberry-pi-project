import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';

import { AppEnvService } from './service/appEnv.service';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatChip } from '@angular/material/chips';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    MatButtonModule,
    MatIcon,
    MatChip,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  readonly #appEnvService = inject(AppEnvService);
  readonly appEnv = toSignal(this.#appEnvService.getAppEnv(), {
    initialValue: null,
  });
}
