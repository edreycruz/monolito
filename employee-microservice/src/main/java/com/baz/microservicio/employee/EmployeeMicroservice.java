package com.baz.microservicio.employee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.baz.microservicio.employee.model.Employee;
import com.baz.microservicio.employee.repositry.EmployeeRepository;
import com.baz.microsrvicio.employee.utils.EmployeeUtils;

@SpringBootApplication
public class EmployeeMicroservice {

	private EmployeeRepository employeeRepository;

	public EmployeeMicroservice(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(EmployeeMicroservice.class, args);
	}

	@Bean
	public CommandLineRunner startup() {

		return (args) -> {
			Employee employee = Employee.builder().firstName("Edrey").lastName("Cruz")
					.number(EmployeeUtils.nextEmployeeNumber()).build();

			System.out.println(employee);

			employeeRepository.save(employee);

			employee = Employee.builder().firstName("Uriel").lastName("Cruz")
					.number(EmployeeUtils.nextEmployeeNumber()).build();
			System.out.println(employee);
			employeeRepository.save(employee);
			
			
		};
	}

}
