package ar.edu.unq.reviewitbackend.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unq.reviewitbackend.entities.builders.ReviewBuilder;

class ReviewTest {

	private Review dafaultReview;
	
	@BeforeEach
	void setUp() {
		dafaultReview = ReviewBuilder.createReview().build();
	}
	
	@Test
	void givenANewReviewWhenIGetTitleItsReturnDefault() throws Exception {
		assertEquals("Toy Story", dafaultReview.getTitle());
	}
	
	@Test
	void givenANewReviewWhenIGetDescriptionItsReturnDefault() throws Exception {
		assertEquals("Muy bueno!", dafaultReview.getDescription());
	}
	
	@Test
	void givenANewReviewWhenIGetPointsItsReturnDefault() throws Exception {
		assertEquals(4, dafaultReview.getPoints());
	}
	
	@Test
	void givenANewReviewWhenIGetOwnerItsReturnDefault() throws Exception {
		assertEquals("gi021", dafaultReview.getUser().getUserName());
	}
	
	@AfterEach
    public void teardown() {
		dafaultReview = null;
    }

}
