import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { LoginComponent } from '../login/login/login.component';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent extends LoginComponent {

  ngOnInit(): void {
    // if(!this.authenticationService.isUserLoggedIn()){
    //   this.router.navigate(['meal-search/']);
    // } 
  }


}
