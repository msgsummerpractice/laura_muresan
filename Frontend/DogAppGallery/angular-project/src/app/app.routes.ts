import { provideRouter, Routes } from '@angular/router';
import { Home } from './home/home';
import { NotFoundComponent } from './not-found-component/not-found-component';
import { App } from './app';
import { bootstrapApplication } from '@angular/platform-browser';
import { infoServiceGuard } from './info/guards/activateGuard';
import { confirmExitGuard } from './info/guards/deactivateGuard';

export const routes: Routes = [
  { path: '', component: Home },
  {
    path: 'login',
    loadComponent: () => import('./login/login').then((m) => m.Login),
  },
  {
    path: 'mfa-verify',
    loadComponent: () => import('./mfa-verify/mfa-verify').then((m) => m.MfaVerify),
  },
  {
    path: 'info',
    canActivate: [infoServiceGuard],
    canDeactivate: [confirmExitGuard],
    loadComponent: () => import('./info/info').then((m) => m.Info),
  },
  { path: 'not-found', component: NotFoundComponent },
];
