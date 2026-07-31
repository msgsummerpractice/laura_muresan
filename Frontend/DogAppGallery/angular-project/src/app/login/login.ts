import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  NonNullableFormBuilder,
  Validators,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';

type LoginForm = {
  email: FormControl<string>;
  password: FormControl<string>;
};
@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, MatButtonModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  private readonly _formBuilder = inject(NonNullableFormBuilder);

  protected readonly loginFormGroup = this._formBuilder.group<LoginForm>({
    email: this._formBuilder.control('', [Validators.required, Validators.email]),
    password: this._formBuilder.control('', [Validators.required, Validators.minLength(5)]),
  });

  onFormSubmit(): void {
    if (this.loginFormGroup.valid) {
      console.log('getRawValue():', this.loginFormGroup.getRawValue());
      console.log('value:', this.loginFormGroup.value);
    }
  }
}
