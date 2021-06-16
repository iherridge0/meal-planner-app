package za.co.kooker.microservice.userservice.entity;

public enum UserType {

	/**
	 * A basic user type can create their own recipes and browser other users
	 * recipes
	 */
	BASIC,

	/**
	 * A premium user type has more features than the basic user
	 */
	PREMIUM;
}
