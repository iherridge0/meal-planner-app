import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ExceptionHandlingService {

  public errorMessage: string = '';

  constructor() { }

}
