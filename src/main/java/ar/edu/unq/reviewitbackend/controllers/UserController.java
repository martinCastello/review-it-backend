package ar.edu.unq.reviewitbackend.controllers;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.services.UserService;
import ar.edu.unq.reviewitbackend.utils.Pagination;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/users")
public class UserController extends CommonController<User, UserService> {

	@GetMapping
	public ResponseEntity<?> getAll(Pagination pagination, 
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "userName", required = false) String userName) {
		log.debug("Paginación solicitada: " + pagination.toString());
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		return ResponseEntity.ok(this.service.findAll(pageRequest));
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> createOrUpdateReview(@RequestBody User entity) {
		User oEntity = service.save(entity);
		return oEntity == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(oEntity);
	}
	
	@PostMapping("/signUp")
	public ResponseEntity<?> singUp(@Valid @RequestBody User user) {

		var alreadyExist = this.service.exist(user.getUserName(), user.getEmail());

		if (alreadyExist) {
			return ResponseEntity.status(HttpStatus.FOUND).build();
		} else {
			User oEntity = this.service.save(user);
			return oEntity == null ? ResponseEntity.badRequest().build() :ResponseEntity.status(HttpStatus.CREATED).body(oEntity);
		}
	}
}