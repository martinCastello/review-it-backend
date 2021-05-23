package ar.edu.unq.reviewitbackend.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
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

import ar.edu.unq.reviewitbackend.entities.Commentary;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.services.ReviewService;
import ar.edu.unq.reviewitbackend.utils.Pagination;
import javassist.NotFoundException;

@RestController
@RequestMapping(path="/reviews")
public class ReviewController extends CommonController<Review, ReviewService> {
	
	@GetMapping
	public ResponseEntity<?> getAllBy(Pagination pagination,
			@RequestParam(value = "search", required = false) String inAll,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "points", required = false) Integer points,
			@RequestParam(value = "userName", required = false) String userName) {
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		return ResponseEntity.ok(this.service.findAll(inAll, title, description, points, userName, pageRequest));
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Review entity, BindingResult result) {
		if(result.hasErrors()) {
			return this.validar(result);
		}
		Review oEntity = service.create(entity);
		return oEntity == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(oEntity);
	}
	
	protected ResponseEntity<?> validar(BindingResult result){
		Map<String, Object> errores = new HashMap<>();
		result.getAllErrors().forEach(err -> {
				errores.put(((FieldError) err).getField(), err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}
	
	@PostMapping("/createCommentary")
	public ResponseEntity<?> createCommentary(@Valid @RequestBody Commentary entity, BindingResult result) throws NotFoundException {
		if(result.hasErrors()) {
			return this.validar(result);
		}
		Commentary oEntity = service.createCommentary(entity);
		return oEntity == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(oEntity);
	}
	
	@GetMapping("/{id}/comments")
	public ResponseEntity<?> getComments(Pagination pagination, @PathVariable Long id) throws NotFoundException {
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		return ResponseEntity.ok(this.service.findAllCommetariesById(id, pageRequest));
	}
	
	
}