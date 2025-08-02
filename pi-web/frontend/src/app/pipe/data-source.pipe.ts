import { Pipe, PipeTransform } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';

@Pipe({
  name: 'toDataSource',
})
export class DataSourcePipe<T> implements PipeTransform {
  transform(value: T[]): unknown {
    return new MatTableDataSource(value);
  }
}
