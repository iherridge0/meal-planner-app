import { browser, element, by, WebElement } from "protractor";

export class MealPage {

    async navigateToHome() {
        //Navigate to the home page of the app
        return browser.get("/");
    }

    async getHeadingText() {
        //Get the Ingredient label text
        return element(by.css('h2')).getText();
    }
}