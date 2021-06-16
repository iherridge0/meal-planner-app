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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import za.co.ieh.mpa.miroservices.mealservice.entity.Ingredient;
import za.co.ieh.mpa.miroservices.mealservice.entity.Meal;
import za.co.ieh.mpa.miroservices.mealservice.exception.MealNotFoundException;
import za.co.ieh.mpa.miroservices.mealservice.repository.IngredientRepository;
import za.co.ieh.mpa.miroservices.mealservice.repository.MealRepository;

@RestController
@CrossOrigin(origins = { "http://localhost:4300", "http://192.168.0.110:4300/", "http://localhost:3000",
		"http://192.168.0.110:3000/" })
public class IngredientsResource {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MealRepository mealRepository;

	@Autowired
	IngredientRepository ingredientRepository;

	@PostMapping("/users/{username}/meals/{mealId}/ingredients")
	public ResponseEntity<Void> addIngredientToMeal(@PathVariable String username, @PathVariable int mealId,
			@RequestBody Ingredient ingredient) {

		Optional<Meal> findById = mealRepository.findById(mealId);

		if (!findById.isPresent())
			throw new MealNotFoundException("Meal not found for mealId=" + mealId);

		Meal meal = findById.get();
		Ingredient newIngredient = new Ingredient(ingredient.getName(), ingredient.getQuantity());
		newIngredient.setMeal(meal);

		ingredientRepository.save(newIngredient);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(ingredient.getId())
				.toUri();

		return ResponseEntity.created(uri).build();

	}

	/**
	 * Delete an ingredient from a meal
	 */
	@DeleteMapping("/users/{username}/meals/{mealId}/ingredients/{ingredientId}")
	public ResponseEntity<Void> removeIngredientFromMeal(@PathVariable String username, @PathVariable int mealId,
			@PathVariable int ingredientId) {
		try {
			ingredientRepository.deleteById(ingredientId);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Edit/Update an Ingredient
	 */
	@PutMapping("/users/{username}/meals/{mealId}/ingredients/{ingredientId}")
	public ResponseEntity<Ingredient> updateIngredient(@PathVariable String username, @PathVariable int mealId,
			@PathVariable int ingredientId, @RequestBody Ingredient ingredient) {

		Optional<Meal> findById = mealRepository.findById(mealId);

		if (!findById.isPresent())
			throw new MealNotFoundException("Meal not found for mealId=" + mealId);

		Meal meal = findById.get();
		ingredient.setMeal(meal);

		Ingredient ingredientUpdated = ingredientRepository.save(ingredient);
		log.info("Ingredient Updated -> {}", ingredientUpdated);

		return new ResponseEntity<Ingredient>(ingredientUpdated, HttpStatus.OK);
	}

	/**
	 * Edit/Update an Ingredient
	 */
	@PutMapping("/users/{username}/meals/{mealId}/ingredients")
	public ResponseEntity<List<Ingredient>> updateIngredients(@PathVariable String username, @PathVariable int mealId,
			@RequestBody List<Ingredient> ingredients) {

		Optional<Meal> findById = mealRepository.findById(mealId);

		if (!findById.isPresent())
			throw new MealNotFoundException("Meal not found for mealId=" + mealId);

		Meal meal = findById.get();

		for (Ingredient ingredient : ingredients) {
			ingredient.setMeal(meal);
			Ingredient ingredientUpdated = ingredientRepository.save(ingredient);
			log.info("Ingredient Updated -> {}", ingredientUpdated);
		}

		return new ResponseEntity<List<Ingredient>>(ingredients, HttpStatus.OK);
	}

}
