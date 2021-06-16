package za.co.kooker.microservice.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 982357509535486169L;

	public UserNotFoundException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
