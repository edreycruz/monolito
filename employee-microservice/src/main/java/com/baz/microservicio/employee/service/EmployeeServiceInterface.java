package com.baz.microservicio.employee.service;

import java.util.List;

import com.baz.microservicio.employee.model.Employee;

public interface EmployeeServiceInterface {
	List<Employee> findAll(); 
	Employee findById(Long id);
	Employee findByNumber(String number);
	List<Employee> findByFirstName(String firstName);
	List<Employee> findByFirstNameAndLastName(String firstName, String lastName);
	Employee saveNew(Employee employee);
	Employee update(Employee employee);
	Employee delete(Employee employee);


}
