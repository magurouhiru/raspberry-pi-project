import { AsyncPipe, DatePipe } from '@angular/common';
import { Component, input, OnDestroy } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable,
} from '@angular/material/table';
import { BehaviorSubject, Observable, of, Subject, switchMap } from 'rxjs';

import { DataSourcePipe } from '../../pipe/data-source.pipe';
import { LabelsPipe } from '../../pipe/labels.pipe';
import {
  emptyWithTotal,
  PageParams,
  ReadPage,
  WithTotal,
} from '../../service/shared';

@Component({
  selector: 'app-table',
  imports: [
    MatTable,
    MatHeaderCell,
    MatCell,
    MatHeaderRow,
    MatRow,
    MatPaginator,
    MatColumnDef,
    MatHeaderCellDef,
    MatCellDef,
    MatHeaderRowDef,
    MatRowDef,
    DatePipe,
    AsyncPipe,
    LabelsPipe,
    DataSourcePipe,
  ],
  templateUrl: './table.component.html',
  styleUrl: './table.component.scss',
})
export class TableComponent<T> implements OnDestroy {
  name = input.required<string>();
  sbj = input<Subject<PageParams>>();
  obs = input<Observable<WithTotal<T>>>(of(emptyWithTotal));

  pageSizeOptions = [5, 10, 25];
  handlePageEvent(e: PageEvent) {
    if (this.sbj()) {
      const limit = e.pageSize;
      const offset = e.pageIndex * limit;
      this.sbj()?.next({ offset, limit });
    }
  }

  ngOnDestroy() {
    this.complete();
  }
  complete() {
    this.sbj()?.complete();
    this.sbj()?.unsubscribe();
  }
}

const defaultPP = { offset: 0, limit: 5 };

export function toInput<T>(fn: ReadPage<T>) {
  const sbj = new BehaviorSubject<PageParams>(defaultPP);
  const obs = sbj.asObservable().pipe(switchMap((pp) => fn(pp)));
  return { sbj, obs };
}
