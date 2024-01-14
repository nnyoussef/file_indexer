import {GenericModelService} from "../../02-services/generic-service/generic-model.service";
import {DirectoryOverviewModelData, DirectoryOverviewModelFunctions, DirectoryTree} from "./directory-overview.types";
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
    return {
      directories: {
        subTree: {},
        created: [0, 0, 0, 0, 0, 0],
        updatedAt: [0, 0, 0, 0, 0, 0],
        labels: {}
      },
      currentSelectedPath: '',
      currentSelectSubPath: {}
    }
  }

  public override defineModelAccessors(): DirectoryOverviewModelFunctions {

    const url = `${appsConfigs.backend_url}/directorymanagment/getAllWithDetails`;
    return {
      getAllDirectories: () => {
        const getCurrentDirectoryDirtyState = new BehaviorSubject<boolean>(true);
        this.appInteractions.getInputsWithId().getCurrentDirectoryDirtyState.next(getCurrentDirectoryDirtyState);
        return getCurrentDirectoryDirtyState.pipe(exhaustMap(isDirty => {
          if (isDirty) {
            return this.httpClient.get<DirectoryTree>(url)
              .pipe(tap(d => this.appInteractions.getInputsWithId().storeDirectoriesInfo.next(d)))
              .pipe(tap(d => this.state.directories = d))
              .pipe(tap(() => this.appInteractions.getInputsWithId().markDirectoriesAsClean.next()))
              .pipe(RxjsOperatorConstants.TAKE_ONE)
          } else {
            const getAllDirectories = new BehaviorSubject<DirectoryTree>(this.state.directories);
            this.appInteractions.getInputsWithId().getAllDirectories.next(getAllDirectories);
            return getAllDirectories.pipe(RxjsOperatorConstants.TAKE_ONE, tap(d => this.state.directories = d));
          }
        }));
      },
      getCurrentSelectedDirectoryTreeInfo: () => {
        return Object.entries(this.state.currentSelectSubPath)
          .filter(d => d[0] !== '')
          .map(d => {
              return {
                path: d[0],
                labels: d[1].labels,
                create: d[1].created === null ? [0, 0, 0, 0, 0, 0] : d[1].created,
                updated: d[1].updatedAt === null ? [0, 0, 0, 0, 0, 0] : d[1].updatedAt
              }
            }
          );
      }
    }
  }
}
