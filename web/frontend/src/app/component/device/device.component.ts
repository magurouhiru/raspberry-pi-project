import { Component, effect, inject, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { concatMap, tap, timer } from 'rxjs';

import { Device, DeviceService } from '../../service/device.service';
import { BaseComponent } from '../base/base.component';

import { ChartConfiguration } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-device',
  imports: [BaseChartDirective],
  templateUrl: './device.component.html',
  styleUrl: './device.component.scss',
})
export class DeviceComponent extends BaseComponent {
  readonly #device = inject(DeviceService);

  readonly cash = signal<Device[]>([]);
  tempData = signal<ChartConfiguration['data']>({
    datasets: [
      {
        data: [],
        label: 'temp',
      },
    ],
    labels: [],
  });
  constructor() {
    super();
    timer(0, 3000)
      .pipe(
        tap(() => console.log(Date.now())),
        takeUntilDestroyed(),
        concatMap(() => this.#device.getDevice()),
      )
      .subscribe({
        next: (v) =>
          this.cash.update((c) =>
            c.length === 0 ? [v] : [c[c.length - 1], v],
          ),
        error: (e) => console.error(e),
        complete: () => console.log('complete'),
      });
    effect(() => {
      const c = this.cash();
      if (c.length !== 0) {
        const lastItem = c[c.length - 1];
        this.tempData.update((t) => {
          t.datasets.forEach((dataset) => {
            switch (dataset.label) {
              case 'temp': {
                dataset.data.push(lastItem.temp);
              }
            }
          });

          t.labels?.push(Date.now());
          return { ...t };
        });
      }
    });
  }
}
