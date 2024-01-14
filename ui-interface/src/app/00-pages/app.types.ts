import {BehaviorSubject, Subject} from "rxjs";
import {DirectoryTree} from "./01-directory-overview/directory-overview.types";

export declare  type AppDataModel = {
  globalData: {},
  availableDirectories: {
    values: DirectoryTree,
    dirty: boolean
  }
}

export type AppDataModelAccessor = {
  storeDirectoriesInfo: (data: DirectoryTree) => void,
  getAllDirectories: () => DirectoryTree,
  setDirectoriesInfoDirtyState: (state: boolean) => void,
  getDirectoriesInfoDirtyState: () => boolean
}

export type AppInputs = {
  storeDirectoriesInfo: Subject<DirectoryTree>,
  getAllDirectories: Subject<BehaviorSubject<DirectoryTree>>,
  markDirectoriesAsDirty: Subject<void>,
  markDirectoriesAsClean: Subject<void>,
  getCurrentDirectoryDirtyState: Subject<Subject<boolean>>
}
