/**
 * @note the objective of this class is to be a template for implementing components communications with component
 * rendered dynamically , the following will be a service created by a parent component and inject in the child
 *   component, and it's useful when component are created dynamically and also when deeper children in the component
 *   tree need to communicate with a parent many level above them.
 *
 * P.S : if the component is a directly initialized by the parent or its know to be static for the parent please use
 *   the
 * <b>@Input()</b> to inject an implementation of this class, its preferred it inject an object of subtype
 *   <b>INPUTS</b> as stated in the generic of this class with keys pointing to Subject Values
 * */
export abstract class GenericsComponentInteraction<INPUTS extends { [name: string]: any }> {

  private inputRegistry: Map<string, INPUTS> = new Map<string, INPUTS>();

  public getInputsWithId(id: string = ''): INPUTS {
    let inputs: INPUTS;
    if (!this.inputRegistry.has(id)) {
      inputs = this.getDefaultInputs();
      this.inputRegistry.set(id, inputs);
    } else {
      // @ts-ignore
      inputs = this.inputRegistry.get(id);
    }
    return inputs;
  }

  abstract getDefaultInputs(): INPUTS ;
}
