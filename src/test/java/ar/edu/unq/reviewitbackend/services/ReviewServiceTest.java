package ar.edu.unq.reviewitbackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import ar.edu.unq.reviewitbackend.ReviewItBackendApplication;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.utils.Pagination;

@SpringBootTest(classes = ReviewItBackendApplication.class)
class ReviewServiceTest {

	@Autowired
	private ReviewService reviewService;
	
	@Test
	void test() {
		Pagination pagination = new Pagination(0, 1, "id", "desc");
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		if(reviewService.findAll(pageRequest).getSize() == 0) {
			Review entity = new Review("Travis", "Para que inserte una reseña en travis", 5);
			reviewService.save(entity);
		}
		assertEquals(1, reviewService.findAll(pageRequest).getSize());
	}

}
