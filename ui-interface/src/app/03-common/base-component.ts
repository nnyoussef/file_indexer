import {inject, Renderer2} from "@angular/core";
import {Router} from "@angular/router";

export class BaseComponent {
  protected render2: Renderer2 = inject(Renderer2);
  protected router: Router = inject(Router);


}
