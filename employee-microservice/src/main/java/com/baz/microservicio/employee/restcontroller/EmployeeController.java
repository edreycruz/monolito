package com.baz.microservicio.employee.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baz.microservicio.employee.model.Employee;
import com.baz.microservicio.employee.service.EmployeeServiceInterface;

import lombok.Setter;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	@Setter
	private EmployeeServiceInterface employeeService;

	@GetMapping
	public List<Employee> getAllEmployees() {
		return employeeService.findAll();
	}

	@GetMapping("/{id}")
	public Employee getEmployee(@PathVariable long id) {
		return employeeService.findById(id);
	}

	@GetMapping("/find/byFirstName")
	public List<Employee> getEmployeeByFirstName(@RequestParam(required = true) String firstName) {
		return employeeService.findByFirstName(firstName);
	}

	@GetMapping("/find/byFullName")
	public List<Employee> getEmployeeByFirstNameAndLastName(@RequestParam(required = true) String firstName,
			@RequestParam(required = true) String lastName) {
		return employeeService.findByFirstNameAndLastName(firstName, lastName);
	}

	@GetMapping("/find/byNumber")
	public Employee getEmployeeByEmployeeNumber(@RequestParam(required = true) String number) {
		return employeeService.findByNumber(number);
	}

	@PostMapping
	public Employee postEmployee(@RequestBody Employee employee) {
		return employeeService.saveNew(employee);
	}

	@PutMapping("/{id}")
	public Employee putEmployee(@PathVariable long id, @RequestBody Employee employee) {

		employee.setId(id);

		return employeeService.update(employee);
	}

	@DeleteMapping("/{id}")
	public Employee deleteEmployee(@PathVariable long id) {
		return employeeService.delete(employeeService.findById(id));
	}

}