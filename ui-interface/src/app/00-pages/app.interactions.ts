import {GenericsComponentInteraction} from "../02-services/generic-service/generics-component-interaction";
import {AppInputs} from "./app.types";
import {BehaviorSubject, Subject} from "rxjs";
import {Injectable} from "@angular/core";
import {DirectoryTree} from "./01-directory-overview/directory-overview.types";

@Injectable({providedIn: 'root'})
export class AppInteractions extends GenericsComponentInteraction<AppInputs> {

  constructor() {
    super();
  }

  override getDefaultInputs(): AppInputs {
    return {
      getAllDirectories: new Subject<BehaviorSubject<DirectoryTree>>(),
      storeDirectoriesInfo: new Subject<DirectoryTree>(),
      markDirectoriesAsClean: new Subject<void>(),
      markDirectoriesAsDirty: new Subject<void>(),
      getCurrentDirectoryDirtyState: new Subject<Subject<boolean>>()
    }
  }

}
