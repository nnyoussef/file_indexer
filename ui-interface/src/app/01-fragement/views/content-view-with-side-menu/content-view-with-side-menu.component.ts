import {AfterViewInit, ChangeDetectionStrategy, Component, ElementRef, Input, signal, ViewChild} from '@angular/core';
import {NgTemplateOutlet} from '@angular/common';
import {ContentViewWithSideMenuModel} from "./content-view-with-side-menu.model";
import {ContentViewWithSideMenuInteractions, MenuContent} from "./content-view-with-side-menu.types";
import {BaseComponent} from "../../../03-common/base-component";
import {RouterLink, RouterOutlet} from "@angular/router";
import {RxjsOperatorConstants} from "../../../03-common/rxjs-operator-constants";

@Component({
  selector: 'app-content-view-with-side-menu',
  standalone: true,
  host: {style: 'display:contents'},
  templateUrl: './content-view-with-side-menu.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    RouterOutlet,
    RouterLink,
    NgTemplateOutlet
  ],
  providers: [ContentViewWithSideMenuModel]
})
export class ContentViewWithSideMenuComponent extends BaseComponent implements AfterViewInit {

  @ViewChild('nav_menu_container')
  private navMenu: ElementRef | undefined;

  @ViewChild('nav_control_button')
  private navControlButton: ElementRef | undefined;

  public navigationMenu = signal<MenuContent[]>([]);

  @Input()
  interactions: ContentViewWithSideMenuInteractions | undefined;

  constructor(private contentViewWithSideMenuState: ContentViewWithSideMenuModel) {
    super();
  }

  ngAfterViewInit(): void {
    this.interactions?.applyNavMenu
      .pipe(RxjsOperatorConstants.FILTER_UNDEFINED_VALUES)
      .subscribe(link => {
        this.changeMenu(link);
      });
  }

  private changeMenu(link: string): void {
    this.contentViewWithSideMenuState.getAccessors()
      .retrieveNavigationConfsFromAssets(link)
      .subscribe((menuContent: MenuContent[]) => {
        this.navigationMenu.set(menuContent);
        const route = menuContent[0].routerLink !== undefined ? menuContent[0] : menuContent[0].childElements[0];
        const queryParam = {queryParams: {routedPageTitle: route.title}};
        this.router.navigate([route.routerLink], queryParam);
      });
  }

  public toggleMenu() {
    this.contentViewWithSideMenuState.getAccessors().toggleStateAndApply(this.show, this.hide);
  }

  private hide = () => {
    this.render2.setStyle(this.navMenu?.nativeElement, 'marginLeft', '-330px');
    this.render2.setStyle(this.navControlButton?.nativeElement, "transform", 'rotate(180deg)');
  }

  private show = () => {
    this.render2.setStyle(this.navMenu?.nativeElement, 'marginLeft', '');
    this.render2.setStyle(this.navControlButton?.nativeElement, 'transform', 'rotate(0)');
  }

}
