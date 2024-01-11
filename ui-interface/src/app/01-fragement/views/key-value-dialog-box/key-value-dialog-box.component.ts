import {AfterViewInit, ChangeDetectionStrategy, Component, ElementRef, Input, signal, ViewChild} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BaseComponent} from "../../../03-common/base-component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RxFor} from "@rx-angular/template/for";
import {RxLet} from "@rx-angular/template/let";
import {KeyValueDialogBoxInteractor} from "./key-value-dialog-box.types";
import {RxjsOperatorConstants} from "../../../03-common/rxjs-operator-constants";

@Component({
  selector: 'app-key-value-dialog-box',
  standalone: true,
  host: {style: 'display:contents'},
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RxFor, RxLet],
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
        const keyValueDataList: { key: string, value: any }[] = Object.entries(data).map(data => ({
          key: data[0],
          value: data[1]
        }));
        this.title.set(title);
        this.data.set(keyValueDataList);
        this.dialogBox?.nativeElement.show();
      });
  }
}
