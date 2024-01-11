import {Subject} from "rxjs";

export type KeyValueDialogBoxInteractor = {
  renderData: Subject<KeyValueDialogBoxParams | undefined>
}
export type KeyValueDialogBoxParams = {
  data: { key: string, value: any }[],
  title: string
}
