import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class RouteGuardService {

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService
    ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    if (this.authenticationService.isUserLoggedIn())
      return true;
    else {
      this.router.navigate(['login']);
      return false;
    }
  }
}
