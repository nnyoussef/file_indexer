export type ArgsInVoidReturn = (...arg: any) => void;
export type SingleFunctionArgToVoidReturn<ARG, RETURN> = (arg: (arg1: ARG) => RETURN) => void;
