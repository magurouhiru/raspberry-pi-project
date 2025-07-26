import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'toLabels',
})
export class LabelsPipe<T> implements PipeTransform {
  transform(value: T[]): unknown {
    return Object.keys(value[0] ?? {});
  }
}
