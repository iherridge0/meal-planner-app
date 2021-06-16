import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RouteGuardService } from './service/route-guard.service';
import { MealSearchComponent } from './view/browse/meal-search/meal-search.component';
import { ErrorComponent } from './view/error/error.component';
import { HomePageComponent } from './view/home-page/home-page.component';
import { LoginComponent } from './view/login/login/login.component';
import { LogoutComponent } from './view/logout/logout/logout.component';
import { MealComponent } from './view/meal/meal.component';
import { RegisterComponent } from './view/register/register/register.component';

const routes: Routes = [
  { path: '', component: MealSearchComponent  },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'meal-search', component: MealSearchComponent },
  { path: 'home', component: HomePageComponent, canActivate: [RouteGuardService]  },
  { path: 'meal/:id', component: MealComponent, canActivate: [RouteGuardService] },
  { path: 'logout', component: LogoutComponent, canActivate: [RouteGuardService] },
  { path: '**', component: ErrorComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
