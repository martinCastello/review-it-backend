package ar.edu.unq.reviewitbackend.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import ar.edu.unq.reviewitbackend.services.FollowerService;
import ar.edu.unq.reviewitbackend.services.UserService;
import ar.edu.unq.reviewitbackend.utils.Pagination;
import ar.edu.unq.viewmodel.FollowerViewModel;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/users")
public class UserController extends CommonController<User, UserService> {

	@Autowired
	private FollowerService followerService;
	
	@GetMapping
	public ResponseEntity<?> getAll(Pagination pagination, 
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "userName", required = false) String userName) {
		// log.debug("Paginación solicitada: " + pagination.toString());
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		return ResponseEntity.ok(this.service.findAll(pageRequest));
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
	public ResponseEntity<?> follow(@RequestBody FollowerViewModel requestFollow){
		Long idFrom = Long.valueOf(requestFollow.getIdFrom());
		Long idTo = Long.valueOf(requestFollow.getIdTo());
		Optional<User> from = this.service.findById(idFrom);
		Optional<User> to = this.service.findById(idTo);

		if((!from.isPresent() || !to.isPresent()) && (from.isEmpty() || to.isEmpty())){
			return ResponseEntity.badRequest().build();
		}
		User userTo = to.get();
		User userFrom = from.get();
		
		Followers followRelation = new Followers(userFrom, userTo);

		followRelation.setIdFrom(userFrom.getId());
		followRelation.setIdTo(userTo.getId());

		Followers persistentFollowRelation = this.followerService.save(followRelation);

		System.out.println("todo ok");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(requestFollow);
	}
}