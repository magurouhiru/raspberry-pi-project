import { Component, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { map } from 'rxjs';

import { DeviceService } from '../../service/device.service';
import { BaseComponent } from '../base/base.component';

@Component({
  selector: 'app-device',
  imports: [],
  templateUrl: './device.component.html',
  styleUrl: './device.component.scss',
})
export class DeviceComponent extends BaseComponent {
  readonly #device = inject(DeviceService);
  d = toSignal(this.#device.getDevice().pipe(map((v) => JSON.stringify(v))));
}
