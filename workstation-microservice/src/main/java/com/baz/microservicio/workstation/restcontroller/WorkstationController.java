package com.baz.microservicio.workstation.restcontroller;

import java.util.Date;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.baz.microservicio.workstation.model.ApiError;
import com.baz.microservicio.workstation.model.Workstation;
import com.baz.microservicio.workstation.service.WorkstationService;

import lombok.Setter;
import lombok.SneakyThrows;

@RestController
@RequestMapping("/workstations")
public class WorkstationController {

	@Autowired
	@Setter
	private WorkstationService workstationService;

	@GetMapping
	public List<Workstation> getAllWorkstation() {
		return workstationService.findAll();
	}

	@SneakyThrows
	@GetMapping("/{id}")
	public Workstation getWorkstation(@PathVariable long id) {
		return workstationService.findById(id);
	}

	@GetMapping("/find/byId")
	public Workstation getWorkstationByEmployeeId(@RequestParam(required = true) long id) {
		return workstationService.findByEmployeeId(id);
	}

	@PostMapping
	public Workstation postWorkstation(@RequestBody Workstation workstation) {
		workstation.setId(0);
		return workstationService.saveNew(workstation);
	}

	@PutMapping("/{id}")
	public Workstation putWorkstation(@PathVariable long id, @RequestBody Workstation workstation) {
		workstation.setId(id);
		return workstationService.update(workstation);
	}

	@DeleteMapping("/{id}")
	public Workstation deleteWorkstation(@PathVariable long id) {

		return workstationService.delete(workstationService.findById(id));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiError> handle(ConstraintViolationException ex, WebRequest request) {
		ServletWebRequest servletWebRequest = (ServletWebRequest) request;
		ApiError apiError = ApiError.builder().timestamp(new Date()).status(HttpStatus.BAD_REQUEST.value())
				.error(ex.getSQLException().getMessage()).message(ex.getMessage())
				.path(servletWebRequest.getRequest().getContextPath() + servletWebRequest.getRequest().getServletPath())
				.build();
		return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
	}

}