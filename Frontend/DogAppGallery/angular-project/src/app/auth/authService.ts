import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthResponse } from './AuthResponse';
import { HttpClient } from '@angular/common/http';
interface User {
  email: string;
  password: string;
}
@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  isAuthentificated = signal(false);
  private currentUser = signal<User | null>(null);
  user = this.currentUser.asReadonly();
  private apiUrl = 'http://localhost:8080/api/auth';
  login(email: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, { email, password });
  }

  public logout() {
    this.isAuthentificated.set(false);
  }

  getAuthToken(): string {
    return 'your-auth-token';
  }

  get email(): string | null {
    return this.currentUser()?.email || null;
  }

  get password(): string | null {
    return this.currentUser()?.password || null;
  }
}
