import {GenericModelService} from "../../02-services/generic-service/generic-model.service";
import {DirectoryInfo, DirectoryOverviewModelData, DirectoryOverviewModelFunctions} from "./directory-overview.types";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {appsConfigs} from "../../../configs/config";
import {AppInteractions} from "../app.interactions";
import {BehaviorSubject, exhaustMap, tap} from "rxjs";
import {RxjsOperatorConstants} from "../../03-common/rxjs-operator-constants";

@Injectable()
export class DirectoryOverviewModel extends GenericModelService<DirectoryOverviewModelData, DirectoryOverviewModelFunctions> {

  constructor(private httpClient: HttpClient,
              private appInteractions: AppInteractions) {
    super();
  }

  public override defineDefaultState(): DirectoryOverviewModelData {
    return {}
  }

  public override defineModelAccessors(): DirectoryOverviewModelFunctions {
    const url = `${appsConfigs.backend_url}/directorymanagment/getAllWithDetails`;
    return {
      getAllDirectories: () => {
        const getCurrentDirectoryDirtyState = new BehaviorSubject<boolean>(true);
        this.appInteractions.getInputsWithId().getCurrentDirectoryDirtyState.next(getCurrentDirectoryDirtyState);
        return getCurrentDirectoryDirtyState
          .pipe(exhaustMap(isDirty => {
            if (isDirty) {
              return this.httpClient.get<DirectoryInfo[]>(url)
                .pipe(tap(d => this.appInteractions.getInputsWithId().storeDirectoriesInfo.next(d)))
                .pipe(tap(() => this.appInteractions.getInputsWithId().markDirectoriesAsClean.next()))
            } else {
              const getAllDirectories = new BehaviorSubject<DirectoryInfo[]>([]);
              this.appInteractions.getInputsWithId().getAllDirectories.next(getAllDirectories);
              return getAllDirectories.pipe(RxjsOperatorConstants.TAKE_ONE);
            }
          }));
      }
    }

  }
}
