package za.co.ieh.mpa.miroservices.mealservice;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import za.co.ieh.mpa.miroservices.mealservice.entity.Ingredient;
import za.co.ieh.mpa.miroservices.mealservice.entity.Meal;
import za.co.ieh.mpa.miroservices.mealservice.repository.IngredientRepository;
import za.co.ieh.mpa.miroservices.mealservice.repository.MealRepository;

@SpringBootTest
class MealServiceApplicationTests {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MealRepository mealRepository;

	@Autowired
	IngredientRepository ingredientService;

	@Test
	@Transactional
	@DirtiesContext
	void test_add_ingredients() {

		Meal meal = new Meal("Peanut Butter Zamie", "Lekker Zamie bra");
		mealRepository.save(meal);

		Ingredient ingredient = new Ingredient("Peanut Butter", "1 tsp");
		meal.addIngredient(ingredient);
		ingredientService.save(ingredient);
		ingredient.setMeal(meal);
		meal.addIngredient(ingredient);

		Optional<Meal> optional = mealRepository.findById(meal.getId());

		log.info("Adding Ingredients Test {}", optional.get());
	}

}
