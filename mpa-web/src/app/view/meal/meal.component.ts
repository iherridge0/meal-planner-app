import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { MealDataService } from 'src/app/service/data/meal-data.service';
import { Gallary, Ingredient, Meal } from '../home-page/home-page.component';

@Component({
  selector: 'app-meal',
  templateUrl: './meal.component.html',
  styleUrls: ['./meal.component.css']
})
export class MealComponent implements OnInit {

  mealId = this.route.snapshot.params['id'];
  meal: Meal = new Meal(this.mealId, '', '', false, [], []);
  newIngredient: Ingredient = new Ingredient(-1, '', '');
  username: string = '';
  edit: boolean = false;
  message: string = '';
  selectedFile!: File;
  mealImageUrl: string = '';
  

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private mealService: MealDataService,
    private authenticationService: AuthenticationService
  ) { }

   ngOnInit(): void {
    if(!this.authenticationService.isUserLoggedIn){
      this.router.navigate(['login/']);
    } else {
      this.username = this.authenticationService.getAuthenticatedUser();
      this.refreshMeal();
    }
  }

  //Gets called when the user selects an image
  onFileChanged(event: any) {
    //Select File
    this.selectedFile = event.target.files[0];
    this.onUpload();
  }

  //Gets called when the user clicks on submit to upload the image
  onUpload() {
    console.log(this.selectedFile);
    //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    const uploadImageData = new FormData();
    uploadImageData.append('file', this.selectedFile, this.selectedFile.name);
    //Make a call to the Spring Boot Application to save the image
    this.mealService.uploadMealImage(this.username, this.mealId, uploadImageData).subscribe(
      res => {
        this.mealImageUrl = res;
        this.refreshMeal();
    },
    error => {
      console.log("An error occured while uploading a gallary image to a meal {}", error);

    });;
    
  }   

  refreshMeal() {
    if (!this.newMeal()) {
      this.mealService.retrieveMeal(this.username, this.meal.id).subscribe(
        response => {
          console.log(response);
          this.meal = response;
  
        },
        error => {
          console.log(error);
          this.router.navigate(['/error']);
        }
      );
    }
  }

  newMeal() {
    if (this.route.snapshot.params['id'] < 0)
      return true;
    else
      return false;
  }

  saveMeal() {
    //Create Meal
    this.mealService.createMeal(this.username, this.meal).subscribe(
      response => {
        console.log(response);
        this.mealId = response;
        this.meal.id = this.mealId;
    this.edit = false;
        this.router.navigate(['/meal/',  response]);

      }
    );
    
  }

  updateMeal() {
    //Update Meal
    console.log('Update Meal' + this.meal.name);
    this.mealService.saveMeal(this.username, this.meal).subscribe(
      response => {
        console.log(response);

      }
    );
    this.edit = false;
  }

  changePrivacyStatus() {
    if(this.meal.status == true)
      this.meal.status = false;
    else 
      this.meal.status = true;
    
    this.updateMeal();
  }

  toggleEdit() {

    if (this.edit) {
      this.edit = false;
    } else {
      this.edit = true;
    }
  }

  saveIngredient() {
    
    this.mealService.addIngredient(this.username, this.newIngredient, this.mealId).subscribe(
      response => {
        console.log(this.newIngredient);
        this.newIngredient = new Ingredient(-1, '', '');
        this.meal.id = this.mealId;
        this.refreshMeal();
      }
    );
  }

  updateIngredient(id: number, name: string, quantity: string){
    this.mealService.updateIngredient(this.username, this.meal.id, this.meal.ingredients).subscribe(
      response => {
        console.log(response);
      }
    );
    this.edit = false;
  }

  back() {
    this.router.navigate(['home']);
  }

  deleteIngredient(ingredientId: number) {
    this.mealService.deleteIngredient(this.username, this.meal.id, ingredientId).subscribe(
      response => {
        console.log(response)
        this.message = `Ingredient item of ${ingredientId} deleted from ${this.meal.id}`
        setTimeout(() => {
          this.message = '';
          this.refreshMeal();
        }, 2000);

      },
      error => {
        console.log(error);
      }
    );

  }
}
