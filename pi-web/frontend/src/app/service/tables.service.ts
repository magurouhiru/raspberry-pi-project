import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TablesService {
  client = inject(HttpClient);
  getAnyTables() {
    return this.client
      .get<AnyTables>('/api/tables')
      .pipe(map((at) => toDataSource(at)));
  }
}

function toDataSource(at: AnyTables) {
  return at.tables.map((table) => {
    return {
      name: table.name,
      label: table.label,
      data: table.data.map((row) => {
        return toRecord(table.label, row);
      }),
    };
  });
}

function toRecord(label: string[], value: string[]): Record<string, string> {
  return Object.fromEntries(label.map((l, i) => [l, value[i]]));
}

export interface AnyTables {
  tables: AnyTable[];
}

export interface AnyTable {
  name: string;
  label: string[];
  data: string[][];
}
