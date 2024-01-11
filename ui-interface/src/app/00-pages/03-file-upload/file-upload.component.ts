import {ChangeDetectionStrategy, Component, Input, signal} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule} from "@angular/forms";
import {PageWithTitleComponent} from "../../01-fragement/views/page-with-title/page-with-title.component";

@Component({
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PageWithTitleComponent],
  templateUrl: './file-upload.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FileUploadComponent {
  @Input()
  public routedPageTitle: string = '';
  protected readonly signal = signal;
}
