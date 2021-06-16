import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { ExceptionHandlingService } from 'src/app/service/exception-handling.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = '';
  password = '';
  errorMessage = 'Invalid Login';
 // invalidLogin = false;

  constructor(
    public router: Router,
    public authenticationService: AuthenticationService,
    public exceceptionHandlingService: ExceptionHandlingService
  ) { }

  ngOnInit(): void {
    this.errorMessage = this.exceceptionHandlingService.errorMessage;
  }

  register() {
    this.router.navigate(['register']);
  }

  handleAuthLogin() {
    let encryptedPassword = window.btoa(`${this.username}:${this.password}`);
    this.authenticationService.executeAuthenticationService(this.username, encryptedPassword).subscribe(
      data => {
        this.router.navigate(['home']);
        //this.invalidLogin = false;
        this.username = '';
        this.password = '';
      },
      error => {
        console.log(error);
        //this.invalidLogin = true;
        if(error.status == 0){
          this.exceceptionHandlingService.errorMessage = 'Die agterkant is tans af, probeer asseblief later weer.';
          this.errorMessage = this.exceceptionHandlingService.errorMessage;
        } else if(error.status == 401) {
          this.exceceptionHandlingService.errorMessage = 'Verkeerde gebruikersnaam of wagwoord, probeer weer.';
          this.errorMessage = this.exceceptionHandlingService.errorMessage;
          this.router.navigate(['login'])
        }
        
      }
    )
  }

}
