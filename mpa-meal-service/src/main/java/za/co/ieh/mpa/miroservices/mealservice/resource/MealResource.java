package za.co.ieh.mpa.miroservices.mealservice.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import za.co.ieh.mpa.miroservices.mealservice.entity.Gallary;
import za.co.ieh.mpa.miroservices.mealservice.entity.Ingredient;
import za.co.ieh.mpa.miroservices.mealservice.entity.Meal;
import za.co.ieh.mpa.miroservices.mealservice.exception.MealNotFoundException;
import za.co.ieh.mpa.miroservices.mealservice.repository.GallaryRepository;
import za.co.ieh.mpa.miroservices.mealservice.repository.IngredientRepository;
import za.co.ieh.mpa.miroservices.mealservice.repository.MealRepository;

@RestController
@CrossOrigin(origins = { "http://localhost:4300", "http://192.168.0.110:4300/", "http://localhost:3000",
		"http://192.168.0.110:3000/" })
public class MealResource {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MealRepository mealRepository;

	@Autowired
	IngredientRepository ingredientRepository;

	@Autowired
	GallaryRepository gallaryRepository;

	/**
	 * Retrieve all Meals
	 */
	@GetMapping(path = "/users/{username}/meals")
	public List<Meal> getAllMeals(@PathVariable String username) {

		return mealRepository.findByUsername(username);

	}

	/**
	 * Retrieve a meal
	 */
	@GetMapping(path = "/users/{username}/meals/{mealId}")
	public ResponseEntity<Meal> getMeal(@PathVariable String username, @PathVariable int mealId) {
		Optional<Meal> meal = mealRepository.findById(mealId);
		if (meal.isPresent())
			return ResponseEntity.ok(meal.get());
		else
			throw new MealNotFoundException("Meal not found for mealId=" + mealId);
	}

	/**
	 * Delete a Meal
	 */
	@DeleteMapping("/users/{username}/meals/{mealId}")
	public ResponseEntity<Void> deleteMeal(@PathVariable String username, @PathVariable int mealId) {
		try {
			Optional<Meal> findById = mealRepository.findById(mealId);

			if (!findById.isPresent())
				throw new MealNotFoundException("Meal not found for mealId=" + mealId);

			List<Ingredient> ingredients = findById.get().getIngredients();
			for (Ingredient ingredient : ingredients) {
				ingredientRepository.deleteById(ingredient.getId());
			}

			List<Gallary> gallaries = findById.get().getGallaries();
			for (Gallary gallary : gallaries) {
				gallaryRepository.deleteById(gallary.getId());
			}

			mealRepository.deleteById(mealId);
			return ResponseEntity.noContent().build();
		} catch (MealNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Edit/Update a Meal
	 */
	@PutMapping("users/{username}/meals/{mealId}")
	public ResponseEntity<Meal> updateMeal(@PathVariable String username, @PathVariable int mealId,
			@RequestBody Meal meal) {

		Optional<Meal> findById = mealRepository.findById(mealId);

		if (!findById.isPresent())
			throw new MealNotFoundException("Meal not found for mealId=" + mealId);

		log.info("meal -> {}", meal);
		Meal mealUpdated = mealRepository.save(meal);
		log.info("mealUpdated -> {}", mealUpdated);
		return new ResponseEntity<Meal>(mealUpdated, HttpStatus.OK);
	}

	/**
	 * Create a new Meal
	 */
	@PostMapping("/users/{username}/meals")
	public ResponseEntity<Integer> createMeal(@RequestBody Meal meal, @PathVariable String username) {

		meal.setName(meal.getName());
		meal.setUsername(username);
		Meal savedMeal = mealRepository.save(meal);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(savedMeal.getId())
				.toUri();

		return new ResponseEntity<Integer>(savedMeal.getId(), HttpStatus.CREATED);

	}

}
