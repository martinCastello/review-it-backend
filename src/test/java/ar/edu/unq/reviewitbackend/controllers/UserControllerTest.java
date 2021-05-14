package ar.edu.unq.reviewitbackend.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.services.UserService;
import ar.edu.unq.reviewitbackend.utils.Pagination;

@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private UserService userService;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	
	/*@Test
	void itShouldReturnCreatedUser() throws Exception {

		User entity = new User("Gisele", "Escobar", "gescobar@yahoo.com.ar", "gi", "123");
		when(userService.findByUserName(entity.getUserName())).thenReturn(Optional.ofNullable(null));
		entity.setCreatedDate(new Date());
		entity.setLastModifiedDate(new Date());
	    when(userService.save(entity)).thenReturn(entity);
	    System.out.println(mapper.writeValueAsString(entity));
	    mvc.perform(post("/users/signUp")
	    		.content(mapper.writeValueAsString(entity))
	    	    .contentType(MediaType.APPLICATION_JSON))
	    	    .andExpect(status().isOk())
	    	    .andExpect(jsonPath("$.name").value(entity.getName()));
	}*/
	
	
	@Test
	void testPublicEndpointItShouldReturnExistingUser() throws Exception {
		User entity = new User("Gisele", "Escobar", "gescobar@yahoo.com.ar", "gi", "123");
		when(userService.findByUserName(entity.getUserName())).thenReturn(Optional.of(entity));
	    
	    mvc.perform(post("/users/signUp")
	    		.content(mapper.writeValueAsString(entity))
	    	    .contentType(MediaType.APPLICATION_JSON))
	    	    .andExpect(status().isAccepted())
	    	    .andExpect(jsonPath("$.name").value(entity.getName()));
	}
	
	@Test
	void testPrivateEndpointWhenNoUserReturnsUnauthorized() throws Exception {
	    mvc.perform(get("/users"))
	    		  .andDo(print())
	    	      .andExpect(status().isUnauthorized())
	    	      .andReturn();
	}
	
	@Test
	void testPrivateEndpointGivenPageSizeEqualsOneWhenNoUserAndIGetUsersItsReturnsUnauthorized() throws Exception {
	    mvc.perform(get("/users")
	    	      .contentType(MediaType.APPLICATION_JSON))
	    	      .andExpect(status().isUnauthorized());
	}
	
	@Test
    @WithMockUser(username = "testUser")
    public void testPrivateEndpointReturnsOkWhenAuthorized() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult.getResponse().getContentAsString());
    }
	
	@Test
	@WithMockUser(username = "testUser")
	void testPrivateEndpointGivenPageSizeEqualsOneWhenAuthorizedAndIGetUsersItsReturnsOneUser() throws Exception {
		
		Pagination pagination = new Pagination(0, 1, "id", "desc");
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		User entity = new User("Gisele", "Escobar", "gescobar@yahoo.com.ar", "testUser", "123");
	    List<User> allUsers = Arrays.asList(entity);
	    when(userService.findAll(pageRequest)).thenReturn(new PageImpl<User>(allUsers));
	    
	    MvcResult mvcResult = mvc.perform(get("/users")
	    	      .contentType(MediaType.APPLICATION_JSON))
	    		  .andDo(print())
	    	      .andExpect(status().isOk())
//	    	      .andExpect(jsonPath("$.content[0].name", is(entity.getName())))
	    	      .andReturn();
	    System.out.println("Inciio");
	    System.out.println(mvcResult.getResponse());
	    //assertNotNull(mvcResult.getResponse().getOutputStream());
	    
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
