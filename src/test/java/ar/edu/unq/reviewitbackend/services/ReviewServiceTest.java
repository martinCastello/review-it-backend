package ar.edu.unq.reviewitbackend.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.entities.builders.UserBuilder;
import ar.edu.unq.reviewitbackend.repositories.ReviewRepository;

class ReviewServiceTest {
	
	@Mock
	private ReviewRepository reviewRepository;
	
	@InjectMocks
	private ReviewService reviewService;
	
	@InjectMocks
	private UserService userService;
	
	@Test
	void whenSaveReviewItShouldCallSaveInReviewService() {
		ReviewService mockService = Mockito.mock(ReviewService.class);
		Review entity = new Review("Travis", "Para que inserte una reseña en travis", 2, UserBuilder.createUser().build());
		mockService.save(entity);
		ArgumentCaptor<Review> captor = ArgumentCaptor.forClass(Review.class);
		verify(mockService, times(1)).save(captor.capture());
	}
	
	@Test
	void whenSaveReviewItShouldReturnReview() {
		ReviewService mockService = Mockito.mock(ReviewService.class);
		Review entity = new Review("Travis", "Para que inserte una reseña en travis", 2, UserBuilder.createUser().build());
		when(mockService.save(Mockito.any(Review.class))).thenReturn(entity);
		Review created = mockService.save(entity);
		assertThat(created.getTitle()).isSameAs(entity.getTitle());
	}
	

}
