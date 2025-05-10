import { DatePipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatTableModule } from '@angular/material/table';

import { AnyDBService } from '../../service/any-db.service';

@Component({
  selector: 'app-any-db',
  imports: [MatTableModule, DatePipe],
  templateUrl: './any-db.component.html',
  styleUrl: './any-db.component.scss',
})
export class AnyDbComponent {
  #anyDb = inject(AnyDBService);
  db = toSignal(this.#anyDb.getAnyDB(), {
    initialValue: [],
  });
}
