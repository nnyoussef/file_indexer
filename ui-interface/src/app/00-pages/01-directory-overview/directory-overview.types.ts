import {Observable} from "rxjs";

export type DirectoryOverviewModelFunctions = {
  getAllDirectories: () => Observable<DirectoryInfo[]>,
}

export type DirectoryOverviewModelData = {}

export type DirectoryInfo = {
  path: string,
  updatedAt: [],
  createdAt: [],
  labels: [{ key: string, value: string }]
}

export type DirectoryOverviewCoordinationFunctions = {}
