import {Injectable} from "@angular/core";
import {GenericModelService} from "../../02-services/generic-service/generic-model.service";
import {HttpClient} from "@angular/common/http";
import {DirectoryCreateDataFunctions} from "./directory-create.types";
import {appsConfigs} from "../../../configs/config";
import {tap} from "rxjs";
import {AppInteractions} from "../app.interactions";

@Injectable()
export class DirectoryCreateModel extends GenericModelService<any, DirectoryCreateDataFunctions> {


  constructor(private httpClient: HttpClient,
              private appInteractions: AppInteractions) {
    super();
  }

  public override defineDefaultState() {
    return {
      labelsCreated: 0
    }
  }

  public override defineModelAccessors(): DirectoryCreateDataFunctions {
    return {
      createDir: params => this.httpClient
        .post(`${appsConfigs.backend_url}/directorymanagment/create/dir`, params)
        .pipe(tap(_ => this.appInteractions.getInputsWithId().markDirectoriesAsDirty.next())),
      getAndIncrementLabelsCount: () => {
        this.state.labelsCreated += 1;
        return this.state.labelsCreated;
      },
    }
  }


}
