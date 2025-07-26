import { Component, inject } from '@angular/core';

import { TableComponent, toInput } from '../../parts/table/table.component';
import { HelloService } from '../../service/hello.service';

@Component({
  selector: 'app-tables',
  imports: [TableComponent],
  templateUrl: './tables.component.html',
  styleUrl: './tables.component.scss',
})
export class TablesComponent {
  #helloService = inject(HelloService);

  h = toInput(this.#helloService.read.bind(this.#helloService));
}
