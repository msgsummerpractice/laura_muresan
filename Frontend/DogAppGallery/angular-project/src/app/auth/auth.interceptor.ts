import { HttpHandlerFn, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { AuthService } from './auth.service';
import { inject } from '@angular/core';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const authToken = authService.getAuthToken();

  if (!authToken) {
    return next(req);
  }

  return next(req.clone({ setHeaders: { Authorization: `Bearer ${authToken}` } }));
};
