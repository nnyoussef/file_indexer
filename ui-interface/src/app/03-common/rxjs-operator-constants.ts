import {filter, MonoTypeOperatorFunction, take} from "rxjs";

export class RxjsOperatorConstants {
  public static readonly FILTER_UNDEFINED_VALUES: MonoTypeOperatorFunction<any> = filter(value => value !== undefined);
  public static readonly TAKE_ONE: MonoTypeOperatorFunction<any> = take(1);
}
