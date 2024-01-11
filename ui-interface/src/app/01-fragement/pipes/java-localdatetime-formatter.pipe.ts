import {Pipe, PipeTransform} from '@angular/core';

const seperator: string[] = ['/', '/', ' ', ':', ':', ''];

@Pipe({
  name: 'javaLocaldatetimeFormatter',
  standalone: true
})
export class JavaLocaldatetimeFormatterPipe implements PipeTransform {

  transform(value: number[], ...args: unknown[]): string {
    const year = value[0];
    value[0] = value[2];
    value[2] = year;
    const dateComponents = [...value];
    if (dateComponents.length < 6)
      dateComponents[5] = 0;

    return dateComponents
      .map(value1 => this.checkAndFixValue(value1))
      .reduce((accumulated: string, currentValue: string, currentIndex: number) => `${accumulated}${currentValue}${seperator[currentIndex]}`, '');
  }

  private checkAndFixValue(value: number) {
    if (value === undefined || value === 0) {
      return '00';
    } else if (value.toString().length === 1) {
      return '0'.concat(value.toString());
    } else {
      return value.toString();
    }

  }

}
