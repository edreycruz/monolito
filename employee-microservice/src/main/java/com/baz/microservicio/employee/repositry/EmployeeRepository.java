package com.baz.microservicio.employee.repositry;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.baz.microservicio.employee.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	List<Employee> findAllByOrderByIdAsc();
	List<Employee> findByFirstNameIgnoreCase(String firstName);
	List<Employee> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
	Employee findByNumber(String number);

}
