import {ChangeDetectionStrategy, Component, ElementRef, Input, signal, ViewChild} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BaseComponent} from "../../../03-common/base-component";

@Component({
  selector: 'app-page-with-title',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './page-with-title.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  host: {style: 'display:contents'}
})
export class PageWithTitleComponent extends BaseComponent {

  @Input()
  title = signal('');

  @Input()
  width = signal('fit-content');

  @ViewChild('fieldSetElement', {read: ElementRef})
  fieldSetElement: ElementRef | undefined;


}
