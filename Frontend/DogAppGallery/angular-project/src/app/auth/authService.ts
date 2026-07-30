import { Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthService {
  isAuthentificated = signal(false);
  public login() {
    this.isAuthentificated.set(true);
  }

  public logout() {
    this.isAuthentificated.set(false);
  }

  getAuthToken(): string {
    return 'your-auth-token';
  }
}
