import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MEAL_SERVICE_URL, IMAGE_SERVICE_URL } from 'src/app/app.constants';
import { Gallary, Ingredient, Meal } from 'src/app/view/home-page/home-page.component';

@Injectable({
  providedIn: 'root'
})
export class MealDataService {

  constructor(
    private http: HttpClient
  ) { }

  getAllMeals(username: string) {
    return this.http.get<Meal[]>(`${MEAL_SERVICE_URL}/users/${username}/meals`);
  }

  getAllPublicMeals(username: string) {
    if(!username)
    username = 'anonymous';
    return this.http.get<Meal[]>(`${MEAL_SERVICE_URL}/users/${username}/public/meals`);
  }

  searchAllPublicMeals(username: string, searchString: string) {
    if(!username)
    username = 'anonymous';
    return this.http.get<Meal[]>(`${MEAL_SERVICE_URL}/users/${username}/public/meals/${searchString}`);
  }

  deleteMeal(username: string, id: number) {

    return this.http.delete(`${MEAL_SERVICE_URL}/users/${username}/meals/${id}`);
  }

  retrieveMeal(username: string, id: number) {

    return this.http.get<Meal>(`${MEAL_SERVICE_URL}/users/${username}/meals/${id}`);
  }

  saveMeal(username: string, meal: Meal) {
    return this.http.put(`${MEAL_SERVICE_URL}/users/${username}/meals/${meal.id}`, meal);
  }

  createMeal(username: string, meal: Meal) {
    return this.http.post(`${MEAL_SERVICE_URL}/users/${username}/meals`, meal);
  }

  addIngredient(username: string, ingredient: Ingredient, id: number) {
    return this.http.post(`${MEAL_SERVICE_URL}/users/${username}/meals/${id}/ingredients`, ingredient);
  }

  deleteIngredient(username: string, mealId: number, ingredientId: number) {
    return this.http.delete(`${MEAL_SERVICE_URL}/users/${username}/meals/${mealId}/ingredients/${ingredientId}`);
  }

  updateIngredient(username: string, mealId: number, ingredients: Ingredient[]) {
    return this.http.put(`${MEAL_SERVICE_URL}/users/${username}/meals/${mealId}/ingredients`, ingredients);
  }

  public baseUrl = 'http://localhost:8220/api/images';
  uploadMealImage(username: string, mealId: number, uploadImageData: FormData) {
    //Make a call to the Spring Boot Application to save the image 
    const file = uploadImageData.get('file') as File;
    const url =  `${IMAGE_SERVICE_URL}/meals/${mealId}/upload?file=${file.name}`;
    return this.http.post(url, uploadImageData , {responseType: 'text'});
    
  }

}
