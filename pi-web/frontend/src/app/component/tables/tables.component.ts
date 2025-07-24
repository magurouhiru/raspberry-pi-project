import { DatePipe } from '@angular/common';
import {Component, inject} from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatTableModule } from '@angular/material/table';

import { TablesService } from '../../service/tables.service';

@Component({
  selector: 'app-tables',
  imports: [MatTableModule, DatePipe],
  templateUrl: './tables.component.html',
  styleUrl: './tables.component.scss',
})
export class TablesComponent {
  #service = inject(TablesService);
  at = toSignal(this.#service.getAnyTables(), {
    initialValue: [],
  });
}
