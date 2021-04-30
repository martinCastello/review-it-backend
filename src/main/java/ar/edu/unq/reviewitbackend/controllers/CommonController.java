package ar.edu.unq.reviewitbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import ar.edu.unq.reviewitbackend.services.CommonService;

public class CommonController<E, S extends CommonService<E>> {
	
	@Autowired
	protected S service;
	
	@GetMapping("dropdown")
    public ResponseEntity<?> findAllForDropdown() {
        return ResponseEntity.ok(this.service.findDropdownInfo());
    }

}
