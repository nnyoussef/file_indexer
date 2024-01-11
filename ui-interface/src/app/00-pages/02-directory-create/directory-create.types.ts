import {Observable} from "rxjs";

export type DirectoryCreationParameters = {
  path: string,
  labels: [{
    key: string,
    value: string
  }]
}

export type DirectoryCreateDataFunctions = {
  createDir: (params: DirectoryCreationParameters) => Observable<any>,
  getAndIncrementLabelsCount: () => number
}
