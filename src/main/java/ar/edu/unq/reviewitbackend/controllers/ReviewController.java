package ar.edu.unq.reviewitbackend.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.services.ReviewService;
import ar.edu.unq.reviewitbackend.services.UserService;
import ar.edu.unq.reviewitbackend.utils.Pagination;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(path="/reviews")
public class ReviewController extends CommonController<Review, ReviewService> {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<?> getAllBy(Pagination pagination,
			@RequestParam(value = "search", required = false) String inAll,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "points", required = false) Integer points,
			@RequestParam(value = "userName", required = false) String userName) {
		log.debug("Paginación solicitada: " + pagination.toString());
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		return ResponseEntity.ok(this.service.findAll(inAll, title, description, points, userName, pageRequest));
	}

	@PostMapping("/save")
	public ResponseEntity<?> createOrUpdate(@Valid @RequestBody Review entity, BindingResult result) {
		if(result.hasErrors()) {
			return this.validar(result);
		}
		Optional<User> oUser = this.userService.findById(entity.getUserId());
		if(oUser.isEmpty())
			return ResponseEntity.notFound().build();
		entity.setUser(oUser.get());
		Review oEntity = service.save(entity);
		return oEntity == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(oEntity);
	}
	
	protected ResponseEntity<?> validar(BindingResult result){
		Map<String, Object> errores = new HashMap<>();
		result.getAllErrors().forEach(err -> {
				errores.put(((FieldError) err).getField(), "El campo " + ((FieldError) err).getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}
	
	
}