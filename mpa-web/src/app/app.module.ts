import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './view/home-page/home-page.component';
import { MealComponent } from './view/meal/meal.component';
import { FormsModule } from '@angular/forms';
import { ErrorComponent } from './view/error/error.component';
import { LoginComponent } from './view/login/login/login.component';
import { LogoutComponent } from './view/logout/logout/logout.component';
import { HttpIntercepterAuthService } from './service/http/http-intercepter-auth.service';
import { RegisterComponent } from './view/register/register/register.component';
import { MenuComponent } from './view/menu/menu.component';
import { MealSearchComponent } from './view/browse/meal-search/meal-search.component';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    MealComponent,
    ErrorComponent,
    LoginComponent,
    LogoutComponent,
    RegisterComponent,
    MenuComponent,
    MealSearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HttpIntercepterAuthService, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
