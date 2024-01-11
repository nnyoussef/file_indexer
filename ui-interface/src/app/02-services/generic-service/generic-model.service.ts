import {GenericModelAccessors, KeyFunctionMap} from "./generic-service.types";

export abstract class GenericModelService<MODEL_TYPE, MODEL_ACCESSORS extends KeyFunctionMap> {

  private owner: any;
  protected state: MODEL_TYPE;
  protected modelAccessors: MODEL_ACCESSORS;

  protected constructor() {
    this.state = this.defineDefaultState();
    this.modelAccessors = this.defineModelAccessors();
    this.owner = this.getOwner();
  }

  public abstract defineDefaultState(): MODEL_TYPE ;

  public abstract defineModelAccessors(): MODEL_ACCESSORS;

  public getOwner(): string {
    return typeof this;
  }

  public getAccessors(): MODEL_ACCESSORS & GenericModelAccessors<MODEL_TYPE> {
    return {
      ...this.modelAccessors,
      getState: () => this.state,
      setState: (newState: MODEL_TYPE) => this.state = newState,
      updateState: (updateFunction: (arg: MODEL_TYPE) => MODEL_TYPE) => this.state = updateFunction(this.state)
    }
  }
}
