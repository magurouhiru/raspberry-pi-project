import { DatePipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatTableModule } from '@angular/material/table';

import { AnyDBService } from '../../service/any-db.service';

@Component({
  selector: 'app-show-db',
  imports: [MatTableModule, DatePipe],
  templateUrl: './show-db.component.html',
  styleUrl: './show-db.component.scss',
})
export class ShowDbComponent {
  #anyDb = inject(AnyDBService);
  db = toSignal(this.#anyDb.getAnyDB(), {
    initialValue: [],
  });
}
