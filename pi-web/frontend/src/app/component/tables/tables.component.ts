import { Component, inject } from '@angular/core';

import { TableComponent, toInput } from '../../parts/table/table.component';
import { DBDeviceService } from '../../service/db-device.service';
import { HelloService } from '../../service/hello.service';

@Component({
  selector: 'app-tables',
  imports: [TableComponent],
  templateUrl: './tables.component.html',
  styleUrl: './tables.component.scss',
})
export class TablesComponent {
  #helloService = inject(HelloService);
  #dbDeviceService = inject(DBDeviceService);

  h = toInput(this.#helloService.read.bind(this.#helloService));

  temp = toInput(this.#dbDeviceService.readTemp.bind(this.#dbDeviceService));
  freq = toInput(this.#dbDeviceService.readFreq.bind(this.#dbDeviceService));
  cpuDetailRaw = toInput(
    this.#dbDeviceService.readCpuDetailRaw.bind(this.#dbDeviceService),
  );
  cpuRaw = toInput(
    this.#dbDeviceService.readCpuRaw.bind(this.#dbDeviceService),
  );
  mem = toInput(this.#dbDeviceService.readMem.bind(this.#dbDeviceService));
}
