import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AnyDBService {
  client = inject(HttpClient);
  getAnyDB() {
    return this.client
      .get<AnyDB>('/api/db')
      .pipe(map((db) => toDataSource(db)));
  }
}

function toDataSource(db: AnyDB) {
  return db.tables.map((table) => {
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

export interface AnyDB {
  tables: AnyTable[];
}

export interface AnyTable {
  name: string;
  label: string[];
  data: string[][];
}
