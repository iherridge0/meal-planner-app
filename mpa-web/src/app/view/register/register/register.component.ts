import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserDataService } from 'src/app/service/data/user-data.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public errorMessage: string = '';

  constructor(
    private router: Router,
    private userDataService: UserDataService
  ) { }

  newUser: Register = new Register("", "", new Date(), "");


  ngOnInit(): void {
  }

  back() {
    this.router.navigate(['/']);
  }

  saveUser() {
    console.log("saving user");
    let encryptedPassword = window.btoa(`${this.newUser.username}:${this.newUser.password}`);
    this.newUser.password = encryptedPassword;
    this.userDataService.saveUser(this.newUser).subscribe(
      data => {
        console.log(data);
        this.router.navigate(['/']);
      },
      error => {
        console.log(error);
        if(error.status == 0){
          this.errorMessage = 'Die agterkant is tans af, probeer asseblief later weer';
        } else if(error.status == 422) {
          this.errorMessage = this.newUser.username + ' bestaan reeds, probeer \'n ander gebruikersnaam';
        } else {
          //Validation errors
          this.errorMessage = 'Jou username en password mag nie minder as 2 karakters wees nie, en jy moet \'n regte e-posadres insit; jou geboorte datum mag ook nie in die toekoms wees nie.';
        }
      }
    );
  }

}

export class Register {
  constructor(
    public username: string,
    public email: string,
    public birthDate: Date,
    public password: string
  ) { };
}
