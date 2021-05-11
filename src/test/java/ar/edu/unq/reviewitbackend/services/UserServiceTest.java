package ar.edu.unq.reviewitbackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import ar.edu.unq.reviewitbackend.ReviewItBackendApplication;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.utils.Pagination;

@SpringBootTest(classes = ReviewItBackendApplication.class)
class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Test
	void givenPageSizeEqualsOneWhenIGetUsersItsReturnOneUser() {
		Pagination pagination = new Pagination(0, 1, "id", "desc");
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		if(userService.findAll(pageRequest).getSize() == 0) {
			User entity = new User("Martin", "Castello", "mcastello@yahoo.com.ar", "tin", "123");
			userService.save(entity);
		}
		assertEquals(1, userService.findAll(pageRequest).getContent().size());
		assertEquals(1, userService.findAll(pageRequest).getNumberOfElements());
	}
	
	@Test
	void givenTheFirstUserWhenIGetexistThisUserItsReturnTrue() {
		Pagination pagination = new Pagination(0, 1, "id", "desc");
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		if(userService.findAll(pageRequest).getSize() == 0) {
			User entity = new User("Gisele", "Escobar", "gescobar@yahoo.com.ar", "gi", "123");
			userService.save(entity);
		}
		User firstUser = userService.findAll(pageRequest).getContent().get(0);
		assertTrue(userService.exist(firstUser.getUserName(), firstUser.getEmail()));
	}

}
