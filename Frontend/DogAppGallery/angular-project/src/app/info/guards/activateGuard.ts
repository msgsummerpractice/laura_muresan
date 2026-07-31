import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { infoService } from '../infoService';

export const infoServiceGuard: CanActivateFn = (route, state) => {
  const infoServiceInstance = inject(infoService);
  return infoServiceInstance.getInfo() === 'This is the info service';
};
