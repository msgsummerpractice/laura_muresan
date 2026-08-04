import { Component, inject, OnInit } from '@angular/core';
import {
  FormControl,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-mfa-verify',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './mfa-verify.html',
})
export class MfaVerify implements OnInit {
  private fb = inject(NonNullableFormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  mfaError = '';

  otpControl: FormControl<string> = this.fb.control('', [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(6),
  ]);

  ngOnInit(): void {
    if (!this.authService.pendingChallengeToken()) {
      this.router.navigate(['/login']);
    }
  }

  onSubmit(): void {
    if (this.otpControl.invalid) return;

    this.authService.verifyMfa(this.otpControl.value).subscribe({
      next: () => this.router.navigate(['/info']),
      error: (err) => {
        this.mfaError =
          err.status === 400
            ? 'Invalid or expired code.'
            : 'Something went wrong. Please try again.';
      },
    });
  }
}
