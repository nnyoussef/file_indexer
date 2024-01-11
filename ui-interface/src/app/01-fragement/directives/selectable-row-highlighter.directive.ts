import {AfterViewInit, Directive, Input, ViewContainerRef} from '@angular/core';
import {BaseComponent} from "../../03-common/base-component";

@Directive({
  selector: '[appSelectableRowHighlighter]',
  standalone: true
})
export class SelectableRowHighlighterDirective extends BaseComponent implements AfterViewInit {

  @Input()
  highlighterCss: string = '';

  private currentSelectedTr: Element | null | undefined;

  constructor(viewContainerRef: ViewContainerRef) {
    super();
    this.render2.listen(viewContainerRef.element.nativeElement, 'click', (event: MouseEvent) => {
      if (this.currentSelectedTr)
        this.render2.setAttribute(this.currentSelectedTr, 'class', '');

      this.currentSelectedTr = document.elementFromPoint(event.x, event.y)?.parentElement;
      while (this.currentSelectedTr?.localName !== 'tr') {
        this.currentSelectedTr = this.currentSelectedTr?.parentElement;
      }
      this.render2.setAttribute(this.currentSelectedTr, 'class', this.highlighterCss);

    })
  }

  ngAfterViewInit(): void {
  }
}
