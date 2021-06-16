import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { MealDataService } from 'src/app/service/data/meal-data.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  public meals: Meal[] = [];
  public message: string = '';
  private username: string = '';

  constructor(
    private router: Router,
    private service: MealDataService,
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.username = this.authenticationService.getAuthenticatedUser();
    this.refreshMeals();
  }

  refreshMeals() {
    this.service.getAllMeals(this.username).subscribe(
      response => {
        console.log(response);
        this.meals = response;
      },
      error => {
        console.log("Error while retrieving calling /meals " + error);
      }
    )
  }

  updateMeal(id: number) {

    this.router.navigate(['meal/', id]);
  }

  deleteMeal(id: number) {
    this.service.deleteMeal(this.username, id).subscribe(
      response => {
        console.log(response)
        this.message = `Meal item of ${id} deleted`
        setTimeout(() => {
          this.message = '';
          this.refreshMeals();
        }, 2000);
      },
      error => {
        console.log("ERROR: deleteMeal(" + id + ")" + error);
      }
    )
    console.log("Deleting #" + id);
  }

  createMeal() {
    this.router.navigate(['meal/', -1]);
  }

}
export class Gallary {

  constructor(
    public url: string
  ) { }
}

export class Ingredient {
  constructor(
    public id: number,
    public name: string,
    public quantity: string
  ) { }
}

export class Meal {
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public status: boolean,
    public ingredients: Ingredient[],
    public gallaries: Gallary[]
  ) { }
}


