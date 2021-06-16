import { browser, logging, element, by } from "protractor";
import { HomePage } from "./home-page.po";
import { MealPage } from "./meal-page.po";

describe('mpa-web', function () {

    let homePage: HomePage;
    let mealPage: MealPage;

    beforeEach(() => {
        homePage = new HomePage();
        mealPage = new MealPage();
    });

    it('a meal should be created', async () => {
        await homePage.navigateTo();
        expect(await homePage.addBtn.getText()).toEqual('Add');
    });

    afterEach(async () => {
        // Assert that there are no errors emitted from the browser
        const logs = await browser.manage().logs().get(logging.Type.BROWSER);
        expect(logs).not.toContain(jasmine.objectContaining({
            level: logging.Level.SEVERE,
        } as logging.Entry));
    });
});