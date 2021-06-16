package za.co.ieh.mpa.miroservices.mealservice.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Gallary {

	@Id
	@GeneratedValue
	private int id;

	private String url;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Meal meal;

	protected Gallary() {

	}

	public Gallary(String url) {
		super();
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Gallary [id=" + id + ", url=" + url + ", meal=" + meal + "]";
	}

}
