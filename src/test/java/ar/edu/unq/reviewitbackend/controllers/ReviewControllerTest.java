package ar.edu.unq.reviewitbackend.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import ar.edu.unq.reviewitbackend.controllers.ReviewController;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.entities.builders.UserBuilder;
import ar.edu.unq.reviewitbackend.services.ReviewService;
import ar.edu.unq.reviewitbackend.services.UserService;
import ar.edu.unq.reviewitbackend.utils.Pagination;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private ReviewService reviewService;
	
	@MockBean
	private UserService userService;
	
	@Test
	void givenPageSizeEqualsOneWhenIGetReviewsItsReturnOneReview() {
		Pagination pagination = new Pagination(0, 1, "id", "desc");
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		Review entity = new Review("Travis", "Para que inserte una reseña en travis", 2, UserBuilder.createUser().build());
		List<Review> allReviews = Arrays.asList(entity);
	    when(reviewService.findAll(pageRequest)).thenReturn(new PageImpl<Review>(allReviews));
	    
		assertEquals(1, reviewService.findAll(pageRequest).getContent().size());
		assertEquals(1, reviewService.findAll(pageRequest).getNumberOfElements());
	}
	
	@Test
	void givenPageSizeEqualsOneWhenIGetReviewsWithZeroPointsIntsReturnEmpty() {
		Pagination pagination = new Pagination(0, 1, "id", "desc");
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		List<Review> allReviews = Arrays.asList();
	    when(reviewService.findAll(pageRequest)).thenReturn(new PageImpl<Review>(allReviews));
		
		assertEquals(0, reviewService.findAll(pageRequest).getContent().size());
	}
	
	@Test
	void givenPageSizeEqualsOneWhenIGetReviewsWithTwoPoinsItsReturnOneReview()throws Exception {
		
		Pagination pagination = new Pagination(0, 1, "id", "desc");
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		Review entity = new Review("Travis", "Para que inserte una reseña en travis", 2, UserBuilder.createUser().build());
		List<Review> allReviews = Arrays.asList(entity);
	    when(reviewService.findAll(pageRequest)).thenReturn(new PageImpl<Review>(allReviews));
		
		assertEquals(1, reviewService.findAll(pageRequest).getContent().size());
	}

}
