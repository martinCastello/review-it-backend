package ar.edu.unq.reviewitbackend.entities.builders;

import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.entities.User;

public class ReviewBuilder {

	private String title = "Toy Story"; 
	private String description = "Muy bueno!";
	private Integer points = 4;
	private User user = UserBuilder.createUser().build();
	
	public static ReviewBuilder createReview() {
		return new ReviewBuilder();
	}

	public Review build() {
		Review review = new Review(this.title, this.description, this.points, this.user);
		return review;
	}
	public ReviewBuilder withTitle(String aTitle) {
		this.title = aTitle;
		return this;
		
	}
	public ReviewBuilder withDescription(String aDescription) {
		this.description = aDescription;
		return this;
		
	}
	public ReviewBuilder withPoints(Integer aPoints) {
		this.points = aPoints;
		return this;
	}
	public ReviewBuilder withUser(User aUser) {
		this.user = aUser;
		return this;
	}
}
