import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'repeat' })
export class RepeatPipe implements PipeTransform {
  transform(value: string, times: number): string {
    return value.repeat(times);
  }
}
