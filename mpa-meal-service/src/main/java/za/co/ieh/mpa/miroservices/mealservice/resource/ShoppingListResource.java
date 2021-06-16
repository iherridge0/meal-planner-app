package za.co.ieh.mpa.miroservices.mealservice.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import za.co.ieh.mpa.miroservices.mealservice.entity.Ingredient;
import za.co.ieh.mpa.miroservices.mealservice.repository.IngredientRepository;

@RestController
@CrossOrigin(origins = { "http://localhost:4300", "http://192.168.0.110:4300/", "http://localhost:3000",
		"http://192.168.0.110:3000/" })
public class ShoppingListResource {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IngredientRepository ingredientRepository;

	/**
	 * Retrieve all Ingredients
	 */
	@GetMapping(path = "/users/{username}/shoppinglist")
	public List<Ingredient> getAllIngredients(@PathVariable String username) {

		List<Ingredient> ingredients = ingredientRepository.findAll();

		log.info("Ingrediets -> {}", ingredients);
		return ingredientRepository.findAll();

	}

}
