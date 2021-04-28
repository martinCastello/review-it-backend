package ar.edu.unq.reviewitbackend.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ar.edu.unq.reviewitbackend.entities.builders.UserBuilder;

class UserTest {

	@Test
	void givenANewUserWhenIGetNameItsReturnDefaultName() throws Exception {
		User newUser = UserBuilder.createUser().build();
		assertEquals("Giselle", newUser.getName());
	}

	@Test
	void givenANewUserWithUserNameWhenIGetNameItsReturnTheValueSetted() throws Exception {
		User newUser = UserBuilder.createUser().withUserName("Giselle2021").build();
		assertEquals("Giselle2021", newUser.getUserName());
	}

}
