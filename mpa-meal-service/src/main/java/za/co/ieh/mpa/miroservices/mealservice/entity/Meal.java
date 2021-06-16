package za.co.ieh.mpa.miroservices.mealservice.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Meal")
public class Meal {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empSeqGen")
	@SequenceGenerator(name = "empSeqGen", sequenceName = "EMP_SEQ_GEN")
	private int id;

	private String name;

	private String description;

	@OneToMany(mappedBy = "meal")
	private List<Ingredient> ingredients = new ArrayList<>();

	@OneToMany(mappedBy = "meal")
	private List<Gallary> gallaries = new ArrayList<>();

	@Column(nullable = false, unique = true)
	private String username;

	private boolean status;

	protected Meal() {

	}

	public Meal(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public Meal(String name, String description, boolean status) {
		super();
		this.name = name;
		this.description = description;
		this.status = status;
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

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}

	public void removeIngredient(Ingredient ingredient) {
		this.ingredients.remove(ingredient);
	}

	public List<Gallary> getGallaries() {
		return gallaries;
	}

	public void addGallary(Gallary gallary) {
		this.gallaries.add(gallary);
	}

	public void removeGallary(Gallary gallary) {
		this.gallaries.remove(gallary);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Meal [id=" + id + ", name=" + name + ", description=" + description + ", username=" + username
				+ ", status=" + status + "]";
	}

}
