import { provideRouter, Routes } from '@angular/router';
import { Home } from './home/home';
import { NotFoundComponent } from './not-found-component/not-found-component';
import { App } from './app';
import { bootstrapApplication } from '@angular/platform-browser';
import { authGuard } from './auth/activateGuard';
import { confirmExitGuard } from './auth/deactivateGuard';

export const routes: Routes = [
  { path: '', component: Home, canActivate: [authGuard], canDeactivate: [confirmExitGuard] },
  {
    path: 'login',
    loadComponent: () => import('./login/login').then((m) => m.Login),
  },
  { path: 'not-found', component: NotFoundComponent },
];
bootstrapApplication(App, {
  providers: [provideRouter(routes)],
});
