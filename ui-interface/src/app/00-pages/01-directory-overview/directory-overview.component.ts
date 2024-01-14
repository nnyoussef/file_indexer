import {AfterViewInit, ChangeDetectionStrategy, Component, Input, signal} from '@angular/core';
import {BaseComponent} from "../../03-common/base-component";
import {DirectoryOverviewModel} from "./directory-overview.model";
import {JavaLocaldatetimeFormatterPipe} from "../../01-fragement/pipes/java-localdatetime-formatter.pipe";
import {KeyValueDialogBoxComponent} from "../../01-fragement/views/key-value-dialog-box/key-value-dialog-box.component";

import {SelectableRowHighlighterDirective} from "../../01-fragement/directives/selectable-row-highlighter.directive";
import {
  KeyValueDialogBoxInteractor,
  KeyValueDialogBoxParams
} from "../../01-fragement/views/key-value-dialog-box/key-value-dialog-box.types";
import {BehaviorSubject} from "rxjs";

@Component({
  standalone: true,
  imports: [JavaLocaldatetimeFormatterPipe, KeyValueDialogBoxComponent, SelectableRowHighlighterDirective],
  templateUrl: './directory-overview.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [
    {provide: DirectoryOverviewModel, useClass: DirectoryOverviewModel}]
})
export class DirectoryOverviewComponent extends BaseComponent implements AfterViewInit {

  @Input()
  public routedPageTitle: string = '';

  directoriesInfo = signal<{ path: string, labels: { [p: string]: string }, create: number[], updated: number[] }[]>([
    {
      path: 'Not Found',
      labels: {},
      create: [0, 0, 0, 0, 0, 0],
      updated: [0, 0, 0, 0, 0, 0]
    }
  ]);

  currentDirectory = signal('');
  readonly keyValueDialogBoxInteractor: KeyValueDialogBoxInteractor = {
    renderData: new BehaviorSubject<KeyValueDialogBoxParams | undefined>(undefined)
  };

  constructor(private directoryViewModel: DirectoryOverviewModel) {
    super();
  }

  ngAfterViewInit(): void {
    this.directoryViewModel.getAccessors().getAllDirectories().subscribe(x => {
      this.directoryViewModel.getAccessors().getState().currentSelectSubPath = x.subTree;
      const dirInfos = this.directoryViewModel.getAccessors().getCurrentSelectedDirectoryTreeInfo();
      this.directoriesInfo.set(dirInfos);
    });
  }

  public showDetails(index: number) {
    this.keyValueDialogBoxInteractor.renderData.next({
      title: `${this.directoryViewModel.getAccessors().getState().currentSelectedPath}/${this.directoriesInfo()[index].path}`,
      data: Object.entries(this.directoriesInfo()[index].labels).map(v => ({
        key: v[0],
        value: v[1]
      }))
    });
    Object.entries(this.directoriesInfo()[index].labels).forEach(d => console.log(d[1]))
  }

  public onSubDirClicked(subDir: string) {
    this.directoryViewModel.getAccessors().updateState(current => {
      current.currentSelectedPath = `${current.currentSelectedPath}/${subDir}`;
      current.currentSelectSubPath = current.currentSelectSubPath[subDir].subTree
      return current;
    });
    this.currentDirectory.set(this.directoryViewModel.getAccessors().getState().currentSelectedPath);
    this.directoriesInfo.set(this.directoryViewModel.getAccessors().getCurrentSelectedDirectoryTreeInfo());
  }

  public goToRoot() {
    const currentDirPath = this.directoryViewModel.getAccessors().getState().currentSelectedPath.split('/');
    let previousDirectoryTree = this.directoryViewModel.getAccessors().getState().directories.subTree;
    let previousDir = '';
    for (let i = 1; i < currentDirPath.length - 1; i += 1) {
      previousDirectoryTree = previousDirectoryTree[currentDirPath[i]].subTree;
      previousDir = `${previousDir}/${currentDirPath[i]}`;
    }
    this.directoryViewModel.getAccessors().updateState(current => {
      current.currentSelectedPath = previousDir;
      current.currentSelectSubPath = previousDirectoryTree
      return current;
    });
    this.currentDirectory.set(this.directoryViewModel.getAccessors().getState().currentSelectedPath);
    this.directoriesInfo.set(this.directoryViewModel.getAccessors().getCurrentSelectedDirectoryTreeInfo());
  }
}
