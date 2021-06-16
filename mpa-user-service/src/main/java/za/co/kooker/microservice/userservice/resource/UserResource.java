package za.co.kooker.microservice.userservice.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import za.co.kooker.microservice.userservice.entity.User;
import za.co.kooker.microservice.userservice.exception.UserAlreadyExistException;
import za.co.kooker.microservice.userservice.exception.UserNotFoundException;
import za.co.kooker.microservice.userservice.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:4300")
@RestController
public class UserResource {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Returns a list of all users
	 * 
	 * @return
	 */
	@GetMapping("/users")
	public MappingJacksonValue retrieveAllUsers() {

		List<User> users = userRepository.findAll();

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("username");

		FilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(users);
		mapping.setFilters(filters);

		return mapping;
	}

	/**
	 * Retrieves a user
	 * 
	 * @param username
	 * @return user object
	 * @exception not found
	 */
	@GetMapping("/users/{username}")
	public MappingJacksonValue retrieveUser(@PathVariable String username) {
		Optional<User> findById = userRepository.findById(username);

		if (!findById.isPresent())
			throw new UserNotFoundException("User not found username=" + username);

		User user = findById.get();

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("username", "birthDate", "email");

		FilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(user);
		mapping.setFilters(filters);
		return mapping;
	}

	/**
	 * Creates a new user
	 * 
	 * @param user object
	 * @return created with link to created resource
	 */
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

		Optional<User> findById = userRepository.findById(user.getUsername());

		if (findById.isPresent()) {
			throw new UserAlreadyExistException("Username " + user.getUsername() + " already exist in the database");
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedString = encoder.encode(user.getPassword());
		System.out.println(encodedString);
		user.setPassword(encodedString);
		User savedUser = userRepository.save(user);

		// Need to return a 201 CREATED status with URI location of new created resource

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedUser.getUsername()).toUri();

		return ResponseEntity.created(location).build();
	}

	/**
	 * 
	 * @param username
	 * @return no content success
	 * @exception not found error
	 * 
	 */
	@DeleteMapping("/users/{username}")
	public ResponseEntity<Object> deleteUser(@PathVariable String username) {

		Optional<User> findById = userRepository.findById(username);

		if (!findById.isPresent()) {
			throw new UserNotFoundException("User not found username=" + username);
		}

		userRepository.deleteById(username);
		return ResponseEntity.noContent().build();

	}

}
