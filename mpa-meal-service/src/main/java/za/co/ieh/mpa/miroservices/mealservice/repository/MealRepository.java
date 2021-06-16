package za.co.ieh.mpa.miroservices.mealservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import za.co.ieh.mpa.miroservices.mealservice.entity.Meal;

@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {

	public List<Meal> findByUsername(String username);

	public List<Meal> findByStatus(boolean status);

	@Query(value = "Select * From Meal where (name like :searchString or description like :searchString ) and status = 1", nativeQuery = true)
	List<Meal> searchMealNames(String searchString);

}
