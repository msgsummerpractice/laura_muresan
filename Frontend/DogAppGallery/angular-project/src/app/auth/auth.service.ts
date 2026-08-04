import { inject, Injectable, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { LoginResponse, isMfaChallengeResponse } from './sign-in-response';
import { SignInResponse } from './response.interfaces';
import { HttpClient } from '@angular/common/http';
interface User {
  email: string;
  password: string;
}
@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);

  isAuthentificated = signal(false);
  private token = signal<string | null>(localStorage.getItem('authToken'));
  private email = signal<string | null>(localStorage.getItem('email'));
  private roles = signal<string[] | null>(JSON.parse(localStorage.getItem('roles') ?? '[]'));

  pendingChallengeToken = signal<string | null>(null);
  constructor() {
    this.isAuthentificated.set(!!this.token());
  }
  private apiUrl = 'http://localhost:8080/api/auth';

  login(email: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>('${this.apiUrl}/login', { email, password }).pipe(
      tap((response) => {
        if (isMfaChallengeResponse(response)) {
          this.pendingChallengeToken.set(response.challengeToken);
        } else {
          this.setSession(response);
        }
      }),
    );
  }

  verifyMfa(otpCode: string): Observable<SignInResponse> {
    const challengeToken = this.pendingChallengeToken();
    if (!challengeToken) {
      throw new Error('No pending MFA challenge token found.');
    }
    return this.http
      .post<SignInResponse>(`${this.apiUrl}/verify-mfa`, { challengeToken, otpCode })
      .pipe(
        tap((response) => {
          this.setSession(response);
        }),
      );
  }

  private setSession(response: SignInResponse): void {
    this.token.set(response.token);
    this.email.set(response.email);
    this.roles.set(response.roles);
    this.isAuthentificated.set(true);
    this.pendingChallengeToken.set(null);
    localStorage.setItem('authToken', response.token);
    localStorage.setItem('email', response.email);
    localStorage.setItem('roles', JSON.stringify(response.roles));
  }

  public logout(): void {
    this.token.set(null);
    this.email.set(null);
    this.roles.set(null);
    this.isAuthentificated.set(false);
    localStorage.removeItem('authToken');
    localStorage.removeItem('email');
    localStorage.removeItem('roles');
  }

  getAuthToken(): string | null {
    return this.token();
  }

  getEmail(): string | null {
    return this.email();
  }

  getRoles(): string[] | null {
    return this.roles();
  }
}
