package ar.edu.unq.reviewitbackend.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unq.reviewitbackend.entities.builders.UserBuilder;

class UserTest {

	private User defaultUser;
	private UserBuilder defaultUserBuilder = UserBuilder.createUser();
	
	@BeforeEach
	void setUp() {
		defaultUser = UserBuilder.createUser().build();
	}
	
	
	@Test
	void givenANewUserWhenIGetUserNameItsReturnDefaultUserName() throws Exception {
		assertEquals("gi021", defaultUser.getUserName());
	}
	
	@Test
	void givenANewUserWhenIGetNameItsReturnDefaultName() throws Exception {
		assertEquals("Giselle", defaultUser.getName());
	}
	
	@Test
	void givenANewUserWhenIGetLastNameItsReturnDefaultLastName() throws Exception {
		assertEquals("Escobar", defaultUser.getLastName());
	}

	@Test
	void givenANewUserWithUserNameWhenIGetNameItsReturnTheValueSetted() throws Exception {
		User newUser = defaultUserBuilder.withUserName("Giselle2021").build();
		assertEquals("Giselle2021", newUser.getUserName());
	}
	
	@AfterEach
    public void teardown() {
		defaultUser = null;
    }

}
