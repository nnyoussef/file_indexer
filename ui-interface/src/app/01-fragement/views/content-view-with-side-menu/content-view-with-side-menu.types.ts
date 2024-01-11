import {Observable, Subject} from "rxjs";

export type ContentViewWIthSideMenuState = {
  menuStatus: ContentViewWIthSideMenuStateEnum,
  menuContent: MenuContent[]
};

export type ContentViewWIthSideMenuStateEnum = 'opened' | 'closed';

export type MenuContent = {
  id: string | number,
  title: string,
  isClickable?: boolean,
  icon?: string,
  routerLink?: string,
  childElements: MenuContent[]
};

export type ContentViewWIthSideMenuStateAction = {
  toggleStateAndApply: (showFunction: () => void, hideFunction: () => void) => void,
  retrieveNavigationConfsFromAssets: (link: string) => Observable<MenuContent[]>
}

export type ContentViewSideMenuCoordinator = {
  applyNavMenu: (link: string) => void
}

export type ContentViewSideMenuFunctions = {
  changeMenu: (link: string) => void
}

export type ContentViewWithSideMenuInteractions = {
  applyNavMenu: Subject<string | undefined>
}
