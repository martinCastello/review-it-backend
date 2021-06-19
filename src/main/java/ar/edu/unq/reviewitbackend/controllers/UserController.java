package ar.edu.unq.reviewitbackend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.reviewitbackend.entities.Follower;
import ar.edu.unq.reviewitbackend.entities.Message;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.services.MessageService;
import ar.edu.unq.reviewitbackend.services.UserService;
import ar.edu.unq.reviewitbackend.utils.Pagination;
import javassist.NotFoundException;

@RestController
@RequestMapping("/users")
public class UserController extends CommonController<User, UserService> {
	
	@Autowired
	protected MessageService messageService;
	
	@GetMapping
	public ResponseEntity<?> getAll(Pagination pagination,
			@RequestParam(value = "search", required = false) String inAll,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "userName", required = false) String userName) {
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		return ResponseEntity.ok(this.service.findAll(inAll, email, userName, pageRequest));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody User user) {
		User oEntity = this.service.create(user);
		return oEntity == null ? ResponseEntity.badRequest().build() :ResponseEntity.ok(oEntity);
	}
	
	@PostMapping
	public ResponseEntity<?> modify(@Valid User entity, BindingResult result) {
		if(result.hasErrors()) {
			return this.validar(result);
		}
		User oEntity;
		try {
			oEntity = service.modify(entity);
			return ResponseEntity.ok(oEntity);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
	}
	
	protected ResponseEntity<?> validar(BindingResult result){
		Map<String, Object> errores = new HashMap<>();
		result.getAllErrors().forEach(err -> {
			errores.put(((FieldError) err).getField(), err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}
	
	@PostMapping("/info")
	public ResponseEntity<?> get(@RequestBody Long id) {
		Optional<User> oUser = this.service.findById(id);
		return oUser.isPresent() ? ResponseEntity.status(HttpStatus.ACCEPTED).body(oUser.get()) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/profile/{username}")
	public ResponseEntity<?> getProfile(@PathVariable String username) {
		Optional<User> oUser = this.service.findByUserName(username);
		return oUser.isPresent() ? ResponseEntity.ok(oUser.get()) : ResponseEntity.badRequest().build();
	}

	@PostMapping("/follow")
	public ResponseEntity<?> follow(@RequestBody Follower requestFollow){
		Follower relationship = this.service.createRelationship(requestFollow);
		return relationship == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(relationship);
	}
	
	@PostMapping("/unfollow")
	public ResponseEntity<?> unfollow(@RequestBody Follower requestFollow){
		try {
			this.service.deleteRelationship(requestFollow);
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/followers/{username}")
	public ResponseEntity<?> getFollowers(Pagination pagination, @PathVariable String username) throws NotFoundException {
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		return ResponseEntity.ok(this.service.findFollowersByUserName(username, pageRequest));
	}

	@GetMapping("/followings/{username}")
	public ResponseEntity<?> getFollowings(Pagination pagination, @PathVariable String username) throws NotFoundException {
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		return ResponseEntity.ok(this.service.findFollowingsByUserName(username, pageRequest));
	}
	
	@GetMapping("/followingsAll/{username}")
	public ResponseEntity<?> getFollowings(@PathVariable String username) {
		try {
			return ResponseEntity.ok(this.service.findFollowingsByUserName(username));
		}catch(NotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@GetMapping("/avatar/{username}")
    public ResponseEntity<?> getAvatar(@PathVariable String username) {
		Optional<User> oUser = this.service.findByUserName(username);
		byte [] avatarFile = new byte[0];
		if(oUser.get().getAvatarFile() != null){
			avatarFile = oUser.get().getAvatarFile();
		}
		return ResponseEntity
				.ok()
				.contentType(MediaType.IMAGE_PNG)
				.body(new ByteArrayResource(avatarFile));
    }
	
	@GetMapping("/likes/to/{username}")
	public ResponseEntity<?> getLikes(@PathVariable String username) {
		try{
			return ResponseEntity.ok(this.service.findLikesToUserName(username));
		} catch (NotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/messages/{usernameTo}/{usernameFrom}")
	public ResponseEntity<?> getLikes(@PathVariable String usernameTo, @PathVariable String usernameFrom ) {
		List<Message> messages= this.messageService.findAll(usernameFrom, usernameTo);

		return ResponseEntity.ok(messages);
	}

	
	@GetMapping("/{username}/reviews")
	public ResponseEntity<?> getReviews(@PathVariable String username) {
		try {
			return ResponseEntity.ok(this.service.findReviewsByUserName(username));
		}catch(NotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
}