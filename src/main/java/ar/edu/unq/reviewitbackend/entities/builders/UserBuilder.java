package ar.edu.unq.reviewitbackend.entities.builders;

import ar.edu.unq.reviewitbackend.entities.User;

public class UserBuilder {

	private String name = "Giselle";
	private String lastName = "Escobar";
	private String email = "giselle@gmail.com";
	private String userName = "gi021";
	private String password = "giselle1234";
	
	public static UserBuilder createUser() {
		return new UserBuilder();
	}

	public User build() {
		User User = new User(this.name, this.lastName, this.email, this.userName, this.password);
		return User;
	}
	public UserBuilder withName(String aName) {
		this.name = aName;
		return this;
	}
	public UserBuilder withLastName(String aLastName) {
		this.lastName = aLastName;
		return this;
	}
	public UserBuilder withEmail(String aEmail) {
		this.email = aEmail;
		return this;
	}
	public UserBuilder withPassword(String aPassword) {
		this.password = aPassword;
		return this;
	}
	public UserBuilder withUserName(String aUsername) {
		this.userName = aUsername;
		return this;
	}
}
