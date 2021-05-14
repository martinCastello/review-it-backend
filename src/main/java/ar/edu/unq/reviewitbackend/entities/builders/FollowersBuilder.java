package ar.edu.unq.reviewitbackend.entities.builders;

import ar.edu.unq.reviewitbackend.entities.Followers;
import ar.edu.unq.reviewitbackend.entities.User;

public class FollowersBuilder {

	private User to = UserBuilder.createUser().build();
	private User from = UserBuilder.createUser().withUserName("Mar").build();
	
	public static FollowersBuilder createFollowers() {
		return new FollowersBuilder();
	}

	public Followers build() {
		Followers followers = new Followers(this.from, this.to);
		return followers;
	}
	
	public FollowersBuilder withFrom(User aUser) {
		this.from = aUser;
		return this;	
	}
	
	public FollowersBuilder withTo(User aUser) {
		this.to = aUser;
		return this;	
	}
	
}
