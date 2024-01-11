import {AfterViewInit, ChangeDetectionStrategy, Component, Input, signal} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RxLet} from "@rx-angular/template/let";
import {BaseComponent} from "../../../03-common/base-component";
import {HorizontalTitleImgViewInteraction} from "./horizontal-title-img-view.type";
import {RxjsOperatorConstants} from "../../../03-common/rxjs-operator-constants";

@Component({
  selector: 'app-horizontal-title-img-view',
  standalone: true,
  imports: [CommonModule, RxLet],
  host: {style: 'display:contents'},
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './horizontal-title-img-view.component.html',
})
export class HorizontalTitleImgViewComponent extends BaseComponent implements AfterViewInit {

  img = signal('');

  title = signal('');

  @Input()
  interaction: HorizontalTitleImgViewInteraction | undefined;

  constructor() {
    super();
  }

  ngAfterViewInit(): void {
    this.interaction?.setImg
      .pipe(RxjsOperatorConstants.FILTER_UNDEFINED_VALUES, RxjsOperatorConstants.TAKE_ONE)
      .subscribe(src => {
        this.img.set(src);
      });
    this.interaction?.setTitle
      .pipe(RxjsOperatorConstants.FILTER_UNDEFINED_VALUES, RxjsOperatorConstants.FILTER_UNDEFINED_VALUES)
      .subscribe(title => {
        this.title.set(title);
      });
  }


}
