package za.co.kooker.microservices.imageservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Gallary {

	@Id
	@GeneratedValue
	private int id;

	private String url;

	private int mealId;

	protected Gallary() {

	}

	public Gallary(String url, int mealId) {
		super();
		this.url = url;
		this.mealId = mealId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getMealId() {
		return mealId;
	}

	public void setMealId(int mealId) {
		this.mealId = mealId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Gallary [id=" + id + ", url=" + url + ", mealId=" + mealId + "]";
	}

}
