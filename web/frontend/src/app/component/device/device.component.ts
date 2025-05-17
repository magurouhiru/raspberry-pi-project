import { formatDate } from '@angular/common';
import { Component, effect, inject, LOCALE_ID, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { concatMap, timer } from 'rxjs';

import { ChartConfiguration } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

import { Device, DeviceService } from '../../service/device.service';
import { BaseComponent } from '../base/base.component';

@Component({
  selector: 'app-device',
  imports: [BaseChartDirective],
  templateUrl: './device.component.html',
  styleUrl: './device.component.scss',
})
export class DeviceComponent extends BaseComponent {
  readonly #device = inject(DeviceService);
  readonly #locale = inject(LOCALE_ID);

  readonly #interval = 3000;
  readonly #xScale = 60;
  readonly #lengh = (this.#xScale * 1000) / this.#interval;
  readonly cash = signal<Device[]>([]);
  defaultScalesX = {
    x: {
      ticks: {
        autoSkip: true,
        maxRotation: 45,
        minRotation: 30,
      },
      grid: {
        display: false,
      },
    },
  };

  // 温度
  tempData = signal<ChartConfiguration['data']>({
    datasets: [
      {
        data: createArray(this.#lengh),
        label: 'temp',
        borderColor: BORDER_COLORS[4],
      },
    ],
    labels: createArray(this.#lengh),
  });
  tempOption: ChartConfiguration['options'] = {
    scales: {
      ...this.defaultScalesX,
      y: {
        max: 100,
        min: 0,
        title: {
          text: '℃',
          display: true,
        },
      },
    },
    plugins: {
      annotation: {
        annotations: [
          {
            type: 'line',
            scaleID: 'y',
            value: 0,
            display: true,
            borderColor: BORDER_COLORS[0],
            borderWidth: 2,
            label: {
              content: 'Minimum Operating Temperature',
            },
          },
          {
            type: 'line',
            scaleID: 'y',
            value: 70,
            display: true,
            borderColor: BORDER_COLORS[2],
            borderWidth: 2,
            label: {
              content: 'Maximum Operating Temperature',
            },
          },
        ],
      },
    },
  };

  // クロック
  freqData = signal<ChartConfiguration['data']>({
    datasets: [
      {
        data: createArray(this.#lengh),
        label: 'freq',
        borderColor: BORDER_COLORS[4],
      },
    ],
    labels: createArray(this.#lengh),
  });
  freqOption: ChartConfiguration['options'] = {
    scales: {
      ...this.defaultScalesX,
      y: {
        max: 1500,
        min: 500,
        title: {
          text: 'MHz',
          display: true,
        },
      },
    },
    plugins: {
      annotation: {
        annotations: [
          {
            type: 'line',
            scaleID: 'y',
            value: 1200,
            display: true,
            borderColor: BORDER_COLORS[2],
            borderWidth: 2,
            label: {
              content: 'Rated Clock Speed',
            },
          },
        ],
      },
    },
  };

  // CPU
  cpuData = signal<ChartConfiguration['data']>({
    datasets: [
      {
        data: createArray(this.#lengh),
        label: 'cpu',
        borderColor: BORDER_COLORS[4],
      },
      {
        data: createArray(this.#lengh),
        label: 'cpu0',
        borderColor: BORDER_COLORS[0],
      },
      {
        data: createArray(this.#lengh),
        label: 'cpu1',
        borderColor: BORDER_COLORS[1],
      },
      {
        data: createArray(this.#lengh),
        label: 'cpu2',
        borderColor: BORDER_COLORS[3],
      },
      {
        data: createArray(this.#lengh),
        label: 'cpu3',
        borderColor: BORDER_COLORS[5],
      },
    ],
    labels: createArray(this.#lengh),
  });
  cpuOption: ChartConfiguration['options'] = {
    scales: {
      ...this.defaultScalesX,
      y: {
        max: 100,
        min: 0,
        title: {
          text: '%',
          display: true,
        },
      },
    },
  };

  // メモリ
  memData = signal<ChartConfiguration['data']>({
    datasets: [
      {
        data: createArray(this.#lengh),
        label: 'mem',
        borderColor: BORDER_COLORS[4],
      },
    ],
    labels: createArray(this.#lengh),
  });
  memOption: ChartConfiguration['options'] = {
    scales: {
      ...this.defaultScalesX,
      y: {
        max: 100,
        min: 0,
        title: {
          text: '%',
          display: true,
        },
      },
    },
  };

  constructor() {
    super();
    timer(0, this.#interval)
      .pipe(
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
        const timestamp = formatDate(
          lastItem.timestamp,
          'mediumTime',
          this.#locale,
        );

        this.tempData.update((t) => {
          t.datasets.forEach((dataset) => {
            switch (dataset.label) {
              case 'temp': {
                dataset.data.push(lastItem.temp / 1000);
                dataset.data.shift();
                break;
              }
            }
          });
          t.labels?.push(timestamp);
          t.labels?.shift();
          return { ...t };
        });

        this.freqData.update((t) => {
          t.datasets.forEach((dataset) => {
            switch (dataset.label) {
              case 'freq': {
                dataset.data.push(lastItem.freq / 1000);
                dataset.data.shift();
                break;
              }
            }
          });
          t.labels?.push(timestamp);
          t.labels?.shift();
          return { ...t };
        });

        if (c.length !== 1) {
          const beforeItem = c[c.length - 2];

          this.cpuData.update((t) => {
            t.datasets.forEach((dataset) => {
              switch (dataset.label) {
                case 'cpu': {
                  const dt = lastItem.cpu.cpu.total - beforeItem.cpu.cpu.total;
                  const di = lastItem.cpu.cpu.idle - beforeItem.cpu.cpu.idle;
                  dataset.data.push(((dt - di) / dt) * 100);
                  dataset.data.shift();
                  break;
                }
                case 'cpu0': {
                  const dt =
                    lastItem.cpu.cpu0.total - beforeItem.cpu.cpu0.total;
                  const di = lastItem.cpu.cpu0.idle - beforeItem.cpu.cpu0.idle;
                  dataset.data.push(((dt - di) / dt) * 100);
                  dataset.data.shift();
                  break;
                }
                case 'cpu1': {
                  const dt =
                    lastItem.cpu.cpu1.total - beforeItem.cpu.cpu1.total;
                  const di = lastItem.cpu.cpu1.idle - beforeItem.cpu.cpu1.idle;
                  dataset.data.push(((dt - di) / dt) * 100);
                  dataset.data.shift();
                  break;
                }
                case 'cpu2': {
                  const dt =
                    lastItem.cpu.cpu2.total - beforeItem.cpu.cpu2.total;
                  const di = lastItem.cpu.cpu2.idle - beforeItem.cpu.cpu2.idle;
                  dataset.data.push(((dt - di) / dt) * 100);
                  dataset.data.shift();
                  break;
                }
                case 'cpu3': {
                  const dt =
                    lastItem.cpu.cpu3.total - beforeItem.cpu.cpu3.total;
                  const di = lastItem.cpu.cpu3.idle - beforeItem.cpu.cpu3.idle;
                  dataset.data.push(((dt - di) / dt) * 100);
                  dataset.data.shift();
                  break;
                }
              }
            });
            t.labels?.push(timestamp);
            t.labels?.shift();
            return { ...t };
          });
        }

        this.memData.update((t) => {
          t.datasets.forEach((dataset) => {
            switch (dataset.label) {
              case 'mem': {
                const m =
                  ((lastItem.mem.mem_total -
                    lastItem.mem.mem_free -
                    lastItem.mem.buffers -
                    lastItem.mem.cached) /
                    lastItem.mem.mem_total) *
                  100;
                dataset.data.push(m);
                dataset.data.shift();
                break;
              }
            }
          });
          t.labels?.push(timestamp);
          t.labels?.shift();
          return { ...t };
        });
      }
    });
  }
}

function createArray(n: number, obj = null) {
  return [...Array(n)].map(() => obj);
}

// https://github.com/chartjs/Chart.js/blob/master/src/plugins/plugin.colors.ts
const BORDER_COLORS = [
  'rgb(54, 162, 235)', // blue
  'rgb(255, 99, 132)', // red
  'rgb(255, 159, 64)', // orange
  'rgb(255, 205, 86)', // yellow
  'rgb(75, 192, 192)', // green
  'rgb(153, 102, 255)', // purple
  'rgb(201, 203, 207)', // grey
];
