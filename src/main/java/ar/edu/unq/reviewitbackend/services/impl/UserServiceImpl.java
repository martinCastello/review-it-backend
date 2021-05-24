package ar.edu.unq.reviewitbackend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.unq.reviewitbackend.dto.DropdownInfo;
import ar.edu.unq.reviewitbackend.entities.Followers;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.repositories.UserRepository;
import ar.edu.unq.reviewitbackend.services.FollowerService;
import ar.edu.unq.reviewitbackend.services.UserService;
import javassist.NotFoundException;

@Service
public class UserServiceImpl extends CommonServiceImpl<User, UserRepository> implements UserService{

	@Autowired
	private FollowerService followerService;
	
	public Page<User> findAll(Pageable pageable) {
		return this.repository.findAll(pageable);
	}

	public Page<User> findAllByName(String name, Pageable pageable) {
		return this.repository.findAllByName(name, pageable);
	}
	
	public Page<User> findAllByLastName(String lastName, Pageable pageable) {
		return this.repository.findAllByLastName(lastName, pageable);
	}
	
	public Page<User> findAllByEmail(String email, Pageable pageable) {
		return this.repository.findAllByEmail(email, pageable);
	}
	
	public Page<User> findAllByUserName(String userName, Pageable pageable) {
		return this.repository.findAllByUserName(userName, pageable);
	}

	public Page<User> findAllByNameAndLastName(String name, String lastName, Pageable pageable) {
		return this.repository.findAllByNameAndLastName(name, lastName, pageable);
	}
	
	public List<DropdownInfo> findDropdownInfo() {
        return this.repository.findDropdownInfo();
    }

	public Boolean exist(String userName, String email) {
		return this.repository.findByUserNameOrEmail(userName, email).isPresent();
	}

	public Optional<User> findByUserNameAndEmail(String userName, String email) {
		return this.repository.findByUserNameAndEmail(userName, email);
	}

	public Optional<User> findByUserName(String userName) {
		return this.repository.findByUserName(userName);
	}

	@Override
	public Followers createRelationship(Followers requestFollow) {
		User from = this.findById(requestFollow.getIdFrom()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		User to = this.findById(requestFollow.getIdTo()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		Followers followRelation = new Followers(from, to);
		return this.followerService.save(followRelation);
	}

	@Override
	public Page<Followers> findFollowersById(Long id, PageRequest pageRequest) throws NotFoundException {
		User user = this.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
		return this.followerService.findAllByTo(user, pageRequest);
	}

	@Override
	public List<User> findByNameContainsOrLastNameContains(String name, String lastName) {
		return this.repository.findByNameContainsOrLastNameContains(name, lastName);
	}

	@Override
	public List<User> findByNameContains(String nameOrLastName) {
		return this.repository.findByNameContains(nameOrLastName);
	}
	
}
