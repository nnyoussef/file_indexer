import {AfterViewInit, ChangeDetectionStrategy, Component} from '@angular/core';

import {BaseComponent} from "../03-common/base-component";
import {AppData} from "./app.data";

import {AppInteractions} from "./app.interactions";
import {
  ContentViewWithSideMenuInteractions
} from "../01-fragement/views/content-view-with-side-menu/content-view-with-side-menu.types";
import {BehaviorSubject} from "rxjs";
import {
  HorizontalTitleImgViewInteraction
} from "../01-fragement/views/horizontal-title-img-view/horizontal-title-img-view.type";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  host: {
    style: 'display:contents'
  },
  providers: [
    {provide: AppData, useClass: AppData},
  ]
})
export class AppComponent extends BaseComponent implements AfterViewInit {

  readonly contentViewWithSideMenuInteractions: ContentViewWithSideMenuInteractions = {
    applyNavMenu: new BehaviorSubject<string | undefined>(undefined)
  };

  readonly horizontalTitleImgViewInteraction: HorizontalTitleImgViewInteraction = {
    setTitle: new BehaviorSubject<string | undefined>(undefined),
    setImg: new BehaviorSubject<string | undefined>(undefined)
  };

  constructor(private appInteractions: AppInteractions,
              appData: AppData) {
    super();
    this.appInteractions.getInputsWithId().getAllDirectories
      .subscribe((s) => {
        s.next(appData.getAccessors().getAllDirectories());
      });
    this.appInteractions.getInputsWithId().markDirectoriesAsDirty.subscribe(() => {
      appData.getAccessors().setDirectoriesInfoDirtyState(true);
    });
    this.appInteractions.getInputsWithId().markDirectoriesAsClean.subscribe(() => {
      appData.getAccessors().setDirectoriesInfoDirtyState(false);
    });
    this.appInteractions.getInputsWithId().storeDirectoriesInfo.subscribe(data => appData.getAccessors().storeDirectoriesInfo(data));
    this.appInteractions.getInputsWithId().getCurrentDirectoryDirtyState.subscribe(s => {
      s.next(appData.getAccessors().getDirectoriesInfoDirtyState());
    });
  }


  ngAfterViewInit(): void {
    this.contentViewWithSideMenuInteractions.applyNavMenu.next('/assets/home-page-slide-menu-conf.json');
    this.horizontalTitleImgViewInteraction.setTitle.next('File Indexer');
    this.horizontalTitleImgViewInteraction.setImg.next('/assets/logo.webp');
  }
}
