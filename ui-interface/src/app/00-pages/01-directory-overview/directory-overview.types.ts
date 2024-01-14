import {Observable} from "rxjs";

export type DirectoryOverviewModelFunctions = {
  getAllDirectories: () => Observable<DirectoryTree>,
  getCurrentSelectedDirectoryTreeInfo: () => { path: string, labels: { [p: string]: string }, create: number[], updated: number[] }[]
}

export type DirectoryOverviewModelData = {
  directories: DirectoryTree,
  currentSelectedPath: string,
  currentSelectSubPath: { [p: string]: DirectoryTree }
}

export type DirectoryTree = {
  labels: { [p: string]: string },
  subTree: { [p: string]: DirectoryTree },
  created: number[],
  updatedAt: number[]
}

