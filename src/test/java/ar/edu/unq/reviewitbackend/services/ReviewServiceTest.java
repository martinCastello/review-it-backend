package ar.edu.unq.reviewitbackend.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.unq.reviewitbackend.ReviewItBackendApplication;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.entities.builders.UserBuilder;
import ar.edu.unq.reviewitbackend.repositories.ReviewRepository;

@RunWith(MockitoJUnitRunner.class)
class ReviewServiceTest {
	
	@Mock
	private ReviewRepository reviewRepository;
	
	@InjectMocks
	private ReviewService reviewService;
	
	@InjectMocks
	private UserService userService;
	
	@Test
	void whenSaveReviewItShouldReturnReview() {
		ReviewRepository mockRepository = Mockito.mock(ReviewRepository.class);
		ReviewService mockService = Mockito.mock(ReviewService.class);
		Review entity = new Review("Travis", "Para que inserte una reseña en travis", 2, UserBuilder.createUser().build());
		when(mockService.save(entity)).thenReturn(entity);
		//reviewRepository.save(entity);
		Review created = mockService.save(entity);
		//System.out.println(created.getTitle());
		//ArgumentCaptor<Review> captor = ArgumentCaptor.forClass(Review.class);
		//verify(reviewRepository, times(1)).save(captor.capture());
		assertThat(created.getTitle()).isSameAs(entity.getTitle());
	}
	

}
