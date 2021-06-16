import { browser, element, by, WebElement } from "protractor";

export class HomePage {

    addBtn: WebElement = element(by.id('createMealBtn'));
    viewBtn: WebElement = element(by.id('updateMealBtn'));
    deleteBtn: WebElement = element(by.id('deleteMealBtn'));


    async navigateTo(): Promise<unknown> {
        //Navigate to the home page of the app
        return browser.get("/");
    }

    async getHeadingText() {
        return element(by.css('app-root h1')).getText();
    }

}