package ar.edu.unq.reviewitbackend.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.entities.User;
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
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	void testPrivateEndpointWhenNoUserAndIGetReviewsItsReturnsUnauthorized() throws Exception {
	    mvc.perform(get("/reviews"))
	    		  .andDo(print())
	    	      .andExpect(status().isUnauthorized())
	    	      .andReturn();
	}
	
	@Test
    @WithMockUser(username = "testUser")
    public void testPrivateEndpointReturnsOkWhenAuthorized() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/reviews"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult.getResponse().getContentAsString());
    }
	
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
	    when(reviewService.findAllByPoints(2, pageRequest)).thenReturn(new PageImpl<Review>(allReviews));
		
		assertEquals(1, reviewService.findAllByPoints(2, pageRequest).getContent().size());
	}
	
	@Test
	@WithMockUser(username = "gi021")
	void testPrivateEndpointItShouldReturnCreatedReview() throws Exception {
		User gi021 = UserBuilder.createUser().build();
		Review entity = new Review("Travis", "Para que inserte una reseña en travis", 2, gi021);
		when(userService.findById(entity.getUser().getId())).thenReturn(Optional.of(gi021));
		when(reviewService.save(Mockito.any(Review.class))).thenReturn(entity);
	    
	    mvc.perform(post("/reviews/save")
	    		.content(mapper.writeValueAsString(entity))
	    	    .contentType(MediaType.APPLICATION_JSON))
	    	    .andExpect(status().isOk())
	    	    .andExpect(jsonPath("$.title").value(entity.getTitle()));
	}
	
	@Test
	@WithMockUser(username = "gi021")
	void testPrivateEndpointItSdhouldReturnCreatedReview() throws Exception {
		Pagination pagination = new Pagination(0, 1, "id", "desc");
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		Review entity = new Review("Travis", "Para que inserte una reseña en travis", 2, UserBuilder.createUser().build());
		List<Review> allReviews = Arrays.asList(entity);
		Page<Review> page = new PageImpl<Review>(allReviews);
		when(reviewService.findAll("","","",2,"",pageRequest)).thenReturn(page);
		
	    mvc.perform(get("/reviews?points=2")
	    	    .contentType(MediaType.APPLICATION_JSON))
	    	    .andExpect(status().isOk());
	}

}
