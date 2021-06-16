package za.co.iherridge0.microservices.netflixzuulapigatewayserver.jwt.resource;

public class AuthenticationException extends RuntimeException {
	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}
