import {BehaviorSubject, Subject} from "rxjs";
import {DirectoryInfo} from "./01-directory-overview/directory-overview.types";
export type AppDataModel = {
  globalData: {},
  availableDirectories: {
    values: DirectoryInfo[],
    dirty: boolean
  }
}

export type AppDataModelAccessor = {
  storeDirectoriesInfo: (data: DirectoryInfo[]) => void,
  getAllDirectories: () => DirectoryInfo[],
  setDirectoriesInfoDirtyState: (state: boolean) => void,
  getDirectoriesInfoDirtyState: () => boolean
}

export type AppInputs = {
  storeDirectoriesInfo: Subject<DirectoryInfo[]>,
  getAllDirectories: Subject<BehaviorSubject<DirectoryInfo[]>>,
  markDirectoriesAsDirty: Subject<void>,
  markDirectoriesAsClean: Subject<void>,
  getCurrentDirectoryDirtyState: Subject<Subject<boolean>>
}
