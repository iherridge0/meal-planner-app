package za.co.kooker.microservice.userservice.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("UserFilter")
@Entity
public class User {

	@Id
	@Size(min = 2, message = "Name should have atleast 2 characters.")
	private String username;

	private String password;

	@Past
	private Date birthDate;

	@Email
	private String email;

	@Enumerated(EnumType.STRING)
	private UserType userType;

	protected User() {

	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User(String username, Date birthDate, String email, UserType userType) {
		super();
		this.username = username;
		this.birthDate = birthDate;
		this.email = email;
		this.userType = userType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

}