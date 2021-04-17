package ar.edu.unq.reviewitbackend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.reviewitbackend.dto.ErrorResponseBody;
import ar.edu.unq.reviewitbackend.entities.Review;
import ar.edu.unq.reviewitbackend.services.ReviewService;
import ar.edu.unq.reviewitbackend.utils.Pagination;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/review")
public class ReviewController extends CommonController<Review, ReviewService> {

	@GetMapping
	public ResponseEntity<?> getAll(Pagination pagination, @RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "description", required = false) String description) {
		log.debug("Paginación solicitada: " + pagination.toString());
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		return ResponseEntity.ok(this.service.findAll(id, description, pageRequest));
	}

	@PostMapping("/save")
	public ResponseEntity<?> createOrUpdate(@Validated @RequestBody Review entity, BindingResult result) {
		if (result.hasErrors()) {
			List<String> errors = result.getAllErrors().stream().map(e -> ((FieldError) e).getField() + " " + e.getDefaultMessage())
									.collect(Collectors.toList());
			return ResponseEntity.badRequest().body(new ErrorResponseBody(errors));
		}
		Review oEntity = this.service.save(entity);
        return oEntity == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(oEntity);
	}
}
