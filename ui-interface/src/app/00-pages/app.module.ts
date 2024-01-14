import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AppComponent} from "./app.component";
import {BrowserModule} from "@angular/platform-browser";
import {
  HorizontalTitleImgViewComponent
} from "../01-fragement/views/horizontal-title-img-view/horizontal-title-img-view.component";
import {HttpClientModule} from "@angular/common/http";
import {MainPageNavigationRouterModule} from "../04-routers/main-page-navigation-router.module";
import {
  ContentViewWithSideMenuComponent
} from "../01-fragement/views/content-view-with-side-menu/content-view-with-side-menu.component";

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    HorizontalTitleImgViewComponent,
    HttpClientModule,
    MainPageNavigationRouterModule,
    ContentViewWithSideMenuComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
