package ar.edu.unq.reviewitbackend.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "userName", required = false) String userName) {
		log.debug("Paginación solicitada: " + pagination.toString());
		final PageRequest pageRequest = Pagination.buildPageRequest(pagination);
		return ResponseEntity.ok(this.service.findAll(pageRequest));
	}
}