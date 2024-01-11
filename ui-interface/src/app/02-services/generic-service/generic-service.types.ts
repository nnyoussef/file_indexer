export interface KeyFunctionMap {
  [p: string]: (...arg0: any) => any | void
}

export type GenericModelAccessors<T> = {
  getState: () => T,
  setState: (newState: T) => void,
  updateState: (callback: ((current: T) => T)) => void
}
