import {GenericModelService} from "../../../02-services/generic-service/generic-model.service";
import {
  ContentViewWIthSideMenuState,
  ContentViewWIthSideMenuStateAction,
  MenuContent
} from "./content-view-with-side-menu.types";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class ContentViewWithSideMenuModel extends GenericModelService<ContentViewWIthSideMenuState, ContentViewWIthSideMenuStateAction> {

  constructor(private httpClient: HttpClient) {
    super();
  }

  public override defineDefaultState(): ContentViewWIthSideMenuState {
    return {
      menuStatus: 'opened',
      menuContent: []
    }
  }

  public override defineModelAccessors(): ContentViewWIthSideMenuStateAction {
    return {
      toggleStateAndApply: (showFunction, hideFunction) => {
        if (this.state.menuStatus === 'opened') {
          hideFunction();
          this.state.menuStatus = 'closed';
        } else {
          showFunction();
          this.state.menuStatus = 'opened';
        }
      },
      retrieveNavigationConfsFromAssets: (link: string): Observable<MenuContent[]> => this.httpClient.get<MenuContent[]>(link)
    }
  }

}
