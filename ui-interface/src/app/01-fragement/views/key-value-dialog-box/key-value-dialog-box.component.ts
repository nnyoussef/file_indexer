import {AfterViewInit, ChangeDetectionStrategy, Component, ElementRef, Input, signal, ViewChild} from '@angular/core';
import {BaseComponent} from "../../../03-common/base-component";
import {KeyValueDialogBoxInteractor} from "./key-value-dialog-box.types";
import {RxjsOperatorConstants} from "../../../03-common/rxjs-operator-constants";

@Component({
  selector: 'app-key-value-dialog-box',
  standalone: true,
  host: {style: 'display:contents'},
  imports: [],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './key-value-dialog-box.component.html',
})
export class KeyValueDialogBoxComponent extends BaseComponent implements AfterViewInit {
  data = signal<{ key: string; value: any }[]>([])
  title = signal('');

  @ViewChild('dialog')
  dialogBox: ElementRef | undefined;

  @Input()
  id = '';

  @Input()
  interactor: KeyValueDialogBoxInteractor | undefined;

  constructor() {
    super();
  }

  ngAfterViewInit(): void {

    this.interactor?.renderData
      .pipe(RxjsOperatorConstants.FILTER_UNDEFINED_VALUES)
      .subscribe(arg => {

        const data = arg.data;
        const title = arg.title;
        this.title.set(title);
        this.data.set(data);
        if (this.dialogBox?.nativeElement.hasAttribute('open'))
          return;
        requestIdleCallback(() => this.dialogBox?.nativeElement.show());
      });
  }
}
