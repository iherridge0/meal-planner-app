import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { MealDataService } from 'src/app/service/data/meal-data.service';
import { Meal } from '../../home-page/home-page.component';

@Component({
  selector: 'app-meal-search',
  templateUrl: './meal-search.component.html',
  styleUrls: ['./meal-search.component.css']
})
export class MealSearchComponent implements OnInit {

  public meals: Meal[] = [];
  private username: string = '';
  private searchString: string = '';
  private timer: number = 0;
  private timerStarted: boolean = false;

  constructor(
    private router: Router,
    private mealService: MealDataService,
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.username = this.authenticationService.getAuthenticatedUser();
    this.refreshMeals();
  }

  refreshMeals() {
    this.mealService.getAllPublicMeals(this.username).subscribe(
      response => {
        this.meals = response;
      },
      error => {
        console.log("Error while retrieving calling public/meals " + error);
      }
    )
  }

  // When a user at first starts typing a timer is set to call the 
  // api after 2 seconds, afterwhich on every keystroke the timer is
  // reset to 2 seconds and will only timout once the new timeout is reached,
  // even while running the timeout, it can be extended.
  onKey(event: any) { 
    this.searchString = event.target.value;
    var value = event.target.value;
    if(!this.timerStarted) {
      this.timerStarted = true;
      this.timer = 2000;
      this.recursiveTimeout();
     
    } else 
      this.timer = 2000;
   
  }

  // Defered API call from onKey($event)
  recursiveTimeout(){
    setTimeout(() => {
      this.timer -= 1000;
      if(this.timer > 0)
        this.recursiveTimeout();
      else {
        console.log("Do api call on [" + this.searchString +"]")
        var encodedSearchString = encodeURIComponent(this.searchString);
        this.mealService.searchAllPublicMeals(this.username, encodedSearchString).subscribe(
          data => {
            this.meals = data;
          },
          error => {
            console.log(error);
          }
        );
        this.timerStarted = false         
      }
      }, 1000)
  }

  encodeURIComponent(str:string) {
      return encodeURIComponent(str).replace(/[!'()*]/g, function (c) {
          return '%' + c.charCodeAt(0).toString(16);
      });
  } 
  

}
