package ar.edu.unq.reviewitbackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import ar.edu.unq.reviewitbackend.ReviewItBackendApplication;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.entities.builders.UserBuilder;
import ar.edu.unq.reviewitbackend.utils.Pagination;

@SpringBootTest(classes = ReviewItBackendApplication.class)
class ReviewServiceTest {

	@Autowired
	private ReviewService reviewService;
	
	@Test
	void givenPageSizeEqualsOneWhenIGetReviewsItsReturnOneReview() {
		Pagination pagination = new Pagination(0, 1, "id", "desc");
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		if(reviewService.findAll(pageRequest).getSize() == 0) {
			Review entity = new Review("Travis", "Para que inserte una reseña en travis", 5, UserBuilder.createUser().build());
			reviewService.save(entity);
		}
		assertEquals(1, reviewService.findAll(pageRequest).getContent().size());
		assertEquals(1, reviewService.findAll(pageRequest).getNumberOfElements());
	}
	
	@Test
	void givenPageSizeEqualsOneWhenIGetReviewsWithZeroPointsIntsReturnEmpty() {
		Pagination pagination = new Pagination(0, 1, "id", "desc");
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		
		assertEquals(0, reviewService.findAll("", "", "", 0, "", pageRequest).getContent().size());
	}
	
	@Test
	void givenPageSizeEqualsOneWhenIGetReviewsWithTwoPoinsItsReturnOneReview() {
		Pagination pagination = new Pagination(0, 1, "id", "desc");
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		if(reviewService.findAll("", "", "", 2, "", pageRequest).getSize() == 0) {
			Review entity = new Review("Travis", "Para que inserte una reseña en travis", 2, UserBuilder.createUser().build());
			reviewService.save(entity);
		}
		
		assertEquals(1, reviewService.findAll("", "", "", 2, "", pageRequest).getContent().size());
	}

}
