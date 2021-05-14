package ar.edu.unq.reviewitbackend.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unq.reviewitbackend.entities.builders.FollowersBuilder;
import ar.edu.unq.reviewitbackend.entities.builders.UserBuilder;

class FollowersTest {

	private User defaultUser;
	private Followers defaultFollowers;
	
	@BeforeEach
	void setUp() {
		defaultUser = UserBuilder.createUser().withUserName("Juan").build();
		defaultFollowers = FollowersBuilder.createFollowers().build();
	}
	
	
	@Test
	void givenANewFollowersWhenIGetFollowersItsReturnOne() throws Exception {
		defaultFollowers.setFrom(UserBuilder.createUser().withUserName("Mar").build());
		defaultFollowers.setTo(defaultUser);
		defaultUser.addFollower(defaultFollowers);
		assertEquals(1, defaultUser.getFollowers().size());
	}
	
	@Test
	void givenANewFollowWhenIGetFollowsItsReturnOne() throws Exception {
		defaultFollowers.setTo(UserBuilder.createUser().withUserName("Mar").build());
		defaultFollowers.setFrom(defaultUser);
		defaultUser.addFollow(defaultFollowers);
		assertEquals(1, defaultUser.getFollows().size());
	}
	
	@AfterEach
    public void teardown() {
		defaultFollowers = null;
    }

}
