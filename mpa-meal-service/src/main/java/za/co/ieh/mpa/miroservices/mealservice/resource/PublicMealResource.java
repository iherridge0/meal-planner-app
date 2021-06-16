package za.co.ieh.mpa.miroservices.mealservice.resource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import za.co.ieh.mpa.miroservices.mealservice.entity.Meal;
import za.co.ieh.mpa.miroservices.mealservice.repository.GallaryRepository;
import za.co.ieh.mpa.miroservices.mealservice.repository.IngredientRepository;
import za.co.ieh.mpa.miroservices.mealservice.repository.MealRepository;

@RestController
@CrossOrigin(origins = { "http://localhost:4300", "http://192.168.0.110:4300/", "http://localhost:3000",
		"http://192.168.0.110:3000/" })
public class PublicMealResource {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MealRepository mealRepository;

	@Autowired
	IngredientRepository ingredientRepository;

	@Autowired
	GallaryRepository gallaryRepository;

	/**
	 * Retrieve all Public Meals
	 */
	@GetMapping(path = "/users/{username}/public/meals")
	public Set<Meal> getAllPublicMeals(@PathVariable String username) {

		Set<Meal> foundMeals = new HashSet<>();
		List<Meal> findByStatus = mealRepository.findByStatus(true);
		for (Meal meal : findByStatus) {
			if (meal.getGallaries().size() > 0)
				foundMeals.add(meal);
		}

		return foundMeals;

	}

	@GetMapping(path = "/users/{username}/public/meals/{search}")
	public Set<Meal> searchAllPublicMeals(@PathVariable String username, @PathVariable String search) {

		Set<Meal> foundMeals = new HashSet<>();
		String[] tags = search.split(" ");
		for (String string : tags) {
			List<Meal> meals = mealRepository.searchMealNames("%" + string + "%");
			for (Meal meal : meals) {
				if (meal.getGallaries().size() > 0)
					foundMeals.add(meal);
			}
		}

		log.info("Searching for meals with the following search keywords -> {}", search);

		return foundMeals;

	}
}
