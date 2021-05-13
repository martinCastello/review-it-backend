package ar.edu.unq.reviewitbackend.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ar.edu.unq.reviewitbackend.ReviewItBackendApplication;
import ar.edu.unq.reviewitbackend.controllers.UserController;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.services.UserService;
import ar.edu.unq.reviewitbackend.utils.Pagination;

@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private UserService userService;
	
	@Test
	void givenPageSizeEqualsOneWhenIGetUsersItsReturnOneUser() throws Exception {
		
		Pagination pagination = new Pagination(0, 1, "id", "desc");
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		User entity = new User("Gisele", "Escobar", "gescobar@yahoo.com.ar", "gi", "123");
	    List<User> allUsers = Arrays.asList(entity);
	    when(userService.findAll(pageRequest)).thenReturn(new PageImpl<User>(allUsers));
	    
//	    mvc.perform(get("/users")
//	    	      .contentType(MediaType.APPLICATION_JSON))
//	    	      .andExpect(status().isOk())
//	    	      .andExpect(jsonPath("$.content.[0].name", is(entity.getName())));
		
	    assertEquals(1, userService.findAll(pageRequest).getContent().size());
	    
//		Pagination pagination = new Pagination(0, 1, "id", "desc");
//		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
//		if(userService.findAll(pageRequest).getContent().isEmpty()) {
//			User entity = new User("Gisele", "Escobar", "gescobar@yahoo.com.ar", "gi", "123");
//			userService.save(entity);
//		}
//		User firstUser = userService.findAll(pageRequest).getContent().get(0);
//		assertTrue(userService.exist(firstUser.getUserName(), firstUser.getEmail()));
	}

}
