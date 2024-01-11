import {AfterViewInit, ChangeDetectionStrategy, Component, Input, signal} from '@angular/core';
import {CommonModule, NgForOf} from '@angular/common';
import {BaseComponent} from "../../03-common/base-component";
import {DirectoryOverviewModel} from "./directory-overview.model";
import {JavaLocaldatetimeFormatterPipe} from "../../01-fragement/pipes/java-localdatetime-formatter.pipe";
import {KeyValueDialogBoxComponent} from "../../01-fragement/views/key-value-dialog-box/key-value-dialog-box.component";

import {SelectableRowHighlighterDirective} from "../../01-fragement/directives/selectable-row-highlighter.directive";
import {DirectoryInfo} from "./directory-overview.types";
import {
  KeyValueDialogBoxInteractor,
  KeyValueDialogBoxParams
} from "../../01-fragement/views/key-value-dialog-box/key-value-dialog-box.types";
import {BehaviorSubject} from "rxjs";

@Component({
  standalone: true,
  imports: [JavaLocaldatetimeFormatterPipe, KeyValueDialogBoxComponent, SelectableRowHighlighterDirective, NgForOf, CommonModule],
  templateUrl: './directory-overview.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [
    {provide: DirectoryOverviewModel, useClass: DirectoryOverviewModel}]
})
export class DirectoryOverviewComponent extends BaseComponent implements AfterViewInit {

  @Input()
  public routedPageTitle: string = '';

  directoriesInfo = signal<DirectoryInfo[]>([
    {
      path: 'Not Available',
      createdAt: [],
      updatedAt: [],
      labels: [{key: 'Not Available', value: 'Not Available'}]
    }
  ]);

  readonly keyValueDialogBoxInteractor: KeyValueDialogBoxInteractor = {
    renderData: new BehaviorSubject<KeyValueDialogBoxParams | undefined>(undefined)
  };

  constructor(private directoryViewModel: DirectoryOverviewModel) {
    super();

  }

  ngAfterViewInit(): void {
    this.directoryViewModel.getAccessors().getAllDirectories().subscribe(x => {
      this.directoriesInfo.set(x);
    });
  }

  public showDetails(index: number) {
    let directory;
    directory = this.directoriesInfo().at(index);
    // @ts-ignore
    this.keyValueDialogBoxInteractor.renderData.next(directory.labels, directory.path);
  }

}
