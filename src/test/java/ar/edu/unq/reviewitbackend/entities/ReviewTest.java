package ar.edu.unq.reviewitbackend.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ar.edu.unq.reviewitbackend.entities.builders.ReviewBuilder;

class ReviewTest {

	@Test
	void givenANewReviewWhenIGetDescriptionItsReturnDefault() throws Exception {
		Review newReview = ReviewBuilder.createReview().build();
		assertEquals(4, newReview.getPoints());
	}

}
