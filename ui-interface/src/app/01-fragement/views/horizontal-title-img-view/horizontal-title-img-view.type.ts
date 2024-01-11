import {Subject} from "rxjs";


export type HorizontalTitleImgViewInteraction = {
  setImg: Subject<string | undefined>,
  setTitle: Subject<string | undefined>
}

