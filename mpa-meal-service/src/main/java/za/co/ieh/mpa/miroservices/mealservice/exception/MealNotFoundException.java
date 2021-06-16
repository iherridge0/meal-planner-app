package za.co.ieh.mpa.miroservices.mealservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MealNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 982357509535486169L;

	public MealNotFoundException(String message) {
		super(message);
	}

}
