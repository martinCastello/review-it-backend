package ar.edu.unq.reviewitbackend.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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

import ar.edu.unq.reviewitbackend.entities.Followers;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.services.UserService;
import ar.edu.unq.reviewitbackend.utils.Pagination;
import javassist.NotFoundException;

@RestController
@RequestMapping("/users")
public class UserController extends CommonController<User, UserService> {
	
	@GetMapping
	public ResponseEntity<?> getAll(Pagination pagination,
			@RequestParam(value = "search", required = false) String inAll,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "userName", required = false) String userName) {
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		return ResponseEntity.ok(this.service.findAll(inAll, email, userName, pageRequest));
	}
	
	@PostMapping("/signUp")
	public ResponseEntity<?> singUp(@Valid @RequestBody User user) {

		Optional<User> oUser = this.service.findByUserName(user.getUserName());

		if (oUser.isPresent()) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(oUser.get());
		} else {
			User oEntity = this.service.save(user);
			return oEntity == null ? ResponseEntity.badRequest().build() :ResponseEntity.status(HttpStatus.CREATED).body(oEntity);
		}
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> update(@Valid @RequestBody User entity, BindingResult result) {
		if(result.hasErrors()) {
			return this.validar(result);
		}
		User oEntity = service.save(entity);
		return oEntity == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(oEntity);
	}
	
	protected ResponseEntity<?> validar(BindingResult result){
		Map<String, Object> errores = new HashMap<>();
		result.getAllErrors().forEach(err -> {
				errores.put(((FieldError) err).getField(), "El campo " + ((FieldError) err).getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}
	
	@PostMapping("/info")
	public ResponseEntity<?> get(@RequestBody Long id) {
		Optional<User> oUser = this.service.findById(id);
		return oUser.isPresent() ? ResponseEntity.status(HttpStatus.ACCEPTED).body(oUser.get()) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/profile/{id}")
	public ResponseEntity<?> getProfile(@PathVariable Long id) {
		Optional<User> oUser = this.service.findById(id);
		return oUser.isPresent() ? ResponseEntity.ok(oUser.get()) : ResponseEntity.badRequest().build();
	}

	@PostMapping("/follow")
	public ResponseEntity<?> follow(@RequestBody Followers requestFollow){
		Followers relationship = this.service.createRelationship(requestFollow);
		return relationship == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(relationship);
	}

	@GetMapping("/followers/{id}")
	public ResponseEntity<?> getFollowers(Pagination pagination, @PathVariable Long id) throws NotFoundException {
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		return ResponseEntity.ok(this.service.findFollowersById(id, pageRequest));
	}

	@GetMapping("/followings/{id}")
	public ResponseEntity<?> getFollowings(@PathVariable Long id) throws NotFoundException {
		return ResponseEntity.ok(this.service.findFollowingsById(id));
	}
}