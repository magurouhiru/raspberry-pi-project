import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface WithTotal<T> {
  total: number;
  items: T[];
}

export const emptyWithTotal = { total: 0, items: [] };

export function toWithTotal<T>(res: HttpResponse<T[]>) {
  return {
    total: Number(res.headers.get('X-Total-Count') ?? 0),
    items: res.body ?? [],
  } satisfies WithTotal<T>;
}

export interface PageParams {
  offset: number;
  limit: number;
}

export type ReadPage<T> = (pp: PageParams) => Observable<WithTotal<T>>;
