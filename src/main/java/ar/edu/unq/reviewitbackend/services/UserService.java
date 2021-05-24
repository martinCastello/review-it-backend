package ar.edu.unq.reviewitbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import ar.edu.unq.reviewitbackend.entities.Followers;
import ar.edu.unq.reviewitbackend.entities.User;
import javassist.NotFoundException;

public interface UserService extends CommonService<User>{

	Page<User> findAll(String inAll, String mail, String userName, Pageable pageable);

	Page<User> findAllByName(String name, Pageable pageable);

	Page<User> findAllByLastName(String lastName, Pageable pageable);
	
	Page<User> findAllByEmail(String email, Pageable pageable);
	
	Page<User> findAllByUserName(String userName, Pageable pageable);

	Page<User> findAllByNameAndLastName(String name, String lastName, Pageable pageable);

	Boolean exist(String userName, String email);

	Optional<User> findByUserNameAndEmail(String userName, String email);

	Optional<User> findByUserName(String search);

	Followers createRelationship(Followers requestFollow);

	Page<Followers> findFollowersById(Long id, PageRequest pageRequest) throws NotFoundException;
	
	List<User> findByNameContainsOrLastNameContains(String name, String lastName);

	List<User> findByNameContains(String nameOrLastName);

	List<Followers>findFollowingsById(Long id) throws NotFoundException;
}
