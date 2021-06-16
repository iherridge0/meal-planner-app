package za.co.ieh.mpa.miroservices.mealservice.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Ingredient")
public class Ingredient {

	@Id
	@GeneratedValue
	private int id;

	private String name;

	private String quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Meal meal;

	protected Ingredient() {

	}

	public Ingredient(String name, String quantity) {
		this.name = name;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	@Override
	public String toString() {
		return "Ingredient [name=" + name + ", quantity=" + quantity + "]";
	}

}
