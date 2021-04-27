package ar.edu.unq.reviewitbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.unq.reviewitbackend.entities.User;

public interface UserService extends CommonService<User>{

	Page<User> findAllByDescription(String description, Pageable pageable);

	Page<User> findAllByPoints(Integer points, Pageable pageable);

	Page<User> findAllByDescriptionAndPoints(String description, Integer points, Pageable pageable);

}
