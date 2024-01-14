import {ChangeDetectionStrategy, Component, Input, signal} from '@angular/core';
import {BaseComponent} from "../../03-common/base-component";
import {
  AbstractControl,
  FormArray,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  ValidationErrors,
  Validators
} from "@angular/forms";
import {NgForOf, NgTemplateOutlet} from "@angular/common";
import {RxFor} from "@rx-angular/template/for";
import {DirectoryCreateModel} from "./directory-create.model";

const validators = Validators.compose([Validators.minLength(1), Validators.maxLength(100)]);

@Component({
  standalone: true,
  templateUrl: './directory-create.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    ReactiveFormsModule,
    NgForOf,
    NgTemplateOutlet,
    RxFor
  ],
  providers: [
    {provide: DirectoryCreateModel}
  ]
})
export class DirectoryCreateComponent extends BaseComponent {

  @Input()
  public routedPageTitle: string = '';

  formGroup = signal(new FormGroup<any>({
    path: new FormControl('', validators),
    labels: new FormArray<FormGroup>([], (labels) => {
      return this.validatedLabelsForm(labels);
    })
  }));
  labelsList = signal<any[]>([]);

  constructor(private directoryCreateModel: DirectoryCreateModel) {
    super();
  }

  public post(): void {
    if (this.formGroup().valid) {
      this.directoryCreateModel.getAccessors().createDir(this.formGroup().value).subscribe(response => {
      });
    }
  }

  getLabelId( control: any) {
    return control.get('id').value;
  }

  addLabel(): void {
    const newKeyValueFormGroup = new FormGroup({
      key: new FormControl('', validators),
      value: new FormControl('', validators),
      id: new FormControl(this.directoryCreateModel.getAccessors().getAndIncrementLabelsCount())
    });
    (<FormArray>this.formGroup().get('labels')).push(newKeyValueFormGroup);

    this.updateLabelControlList();
  }

  resetLabelList() {
    (<FormArray>this.formGroup().get('labels')).clear();
    this.updateLabelControlList();
  }

  deleteLabelFromList(event: MouseEvent) {
    const id = document.elementFromPoint(event.x, event.y)?.id;
    if (id?.startsWith('del')) {
      const index: number = Number(id.split('-')[2]);
      const indexInFormArray = (<FormArray>this.formGroup().get('labels')).controls.findIndex(s => s.get('id')?.value === index);
      (<FormArray>this.formGroup().get('labels')).removeAt(indexInFormArray);
      this.updateLabelControlList();
    }
  }

  updateLabelControlList() {
    this.labelsList.set((<FormArray>this.formGroup().get('labels')).controls);
  }

  validatedLabelsForm(labels: AbstractControl): ValidationErrors | any {
    let errors: any = {};
    let keys = new Map<string, undefined>();
    const formArray = <FormArray<FormGroup>>labels;

    formArray.controls.forEach(group => {
      if (keys.has(group.value.key))
        errors[group.value.key] = `Key : ${group.value.key} is duplicated`;
      else
        keys.set(group.value.key, undefined);
    });
    return Object.keys(errors).length === 0 ? null : errors;
  }
}
export class X {}
