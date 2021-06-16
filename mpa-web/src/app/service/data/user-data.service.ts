import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { USER_SERVICE_URL } from 'src/app/app.constants';
import { Register } from 'src/app/view/register/register/register.component';

@Injectable({
  providedIn: 'root'
})
export class UserDataService {

  constructor(
    private http: HttpClient
  ) { }

  saveUser(newUser: Register) {
    return this.http.post(`${USER_SERVICE_URL}/users`, newUser);
  }
}
