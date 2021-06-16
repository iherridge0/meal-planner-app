package za.co.ieh.mpa.miroservices.mealservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.co.ieh.mpa.miroservices.mealservice.entity.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

}
