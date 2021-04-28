package ar.edu.unq.reviewitbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.unq.reviewitbackend.entities.User;

public interface UserService extends CommonService<User>{

	Page<User> findAllByName(String name, Pageable pageable);

	Page<User> findAllByLastName(String lastName, Pageable pageable);
	
	Page<User> findAllByEmail(String email, Pageable pageable);
	
	Page<User> findAllByUserName(String userName, Pageable pageable);

	Page<User> findAllByNameAndLastName(String name, String lastName, Pageable pageable);

}
