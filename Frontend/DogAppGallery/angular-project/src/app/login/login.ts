import { Component, inject } from '@angular/core';
import {
  NonNullableFormBuilder,
  Validators,
  FormControl,
  ReactiveFormsModule,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../auth/auth.service';
import { Router } from '@angular/router';

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
  private authService = inject(AuthService);
  private router = inject(Router);
  loginError = '';

  protected readonly loginFormGroup = this._formBuilder.group<LoginForm>({
    email: this._formBuilder.control('', [Validators.required, Validators.email]),
    password: this._formBuilder.control('', [Validators.required, Validators.minLength(5)]),
  });

  onFormSubmit(): void {
    if (this.loginFormGroup.valid) {
      const { email, password } = this.loginFormGroup.getRawValue();
      this.authService.login(email, password).subscribe({
        next: (response) => {
          if ('challengeToken' in response) {
            this.router.navigate(['/mfa-verify']);
          } else {
            this.router.navigate(['/info']);
          }
        },
        error: (err) => {
          if (err.status === 401) {
            this.loginError = 'Invalid email or password.';
          } else {
            this.loginError = 'Something went wrong. Please try again.';
          }
        },
      });
      this.router.navigate(['/info']);
    }
  }
}
