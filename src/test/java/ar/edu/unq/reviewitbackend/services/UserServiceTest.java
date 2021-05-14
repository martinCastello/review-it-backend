package ar.edu.unq.reviewitbackend.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import ar.edu.unq.reviewitbackend.entities.User;

class UserServiceTest {

	UserService mockService = Mockito.mock(UserService.class);
	
	User entity;
	
	@BeforeEach
	void setUp() {
		entity = new User("Gisele", "Escobar", "gescobar@yahoo.com.ar", "gi", "123");
	}
	
	@Test
	void whenSaveUserItShouldCallSaveInUserService() {
		mockService.save(entity);
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(mockService, times(1)).save(captor.capture());
	}
	
	@Test
	void whenSaveReviewItShouldReturnReview() {
		when(mockService.save(Mockito.any(User.class))).thenReturn(entity);
		User created = mockService.save(entity);
		assertThat(created.getUserName()).isSameAs(entity.getUserName());
	}
	
	@AfterEach
    public void teardown() {
		entity = null;
    }

}
