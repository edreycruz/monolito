package com.baz.microservicio.employee.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.microservicio.employee.model.Employee;
import com.baz.microservicio.employee.model.Workstation;
import com.baz.microservicio.employee.repositry.EmployeeRepository;
import com.baz.microservicio.employee.service.EmployeeServiceInterface;
import com.baz.microservicio.employee.workstation.webclient.WorkstationClient;
import com.baz.microsrvicio.employee.utils.EmployeeUtils;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeServiceInterface {

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private WorkstationClient workstationClient;

	@Override
	public Employee findById(Long id) {
		Employee employee = employeeRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("No se encuentra el empleado"));

		Workstation ws = workstationClient.findByEmployeeId(employee.getId()).orElse(null);
		if (ws != null) {
			employee.setWorkstation(ws);
		}
		return employee;
	}

	@Override
	public List<Employee> findAll() {
		List<Employee> listemployee = employeeRepo.findAll();
		for (Employee employee : listemployee) {
			Workstation ws = workstationClient.findByEmployeeId(employee.getId()).orElse(null);
			if (ws != null) {
				employee.setWorkstation(ws);
			}
		}

		return listemployee;
	}

	@Override
	public Employee saveNew(Employee employee) {
		employee.setId(0);
		Workstation ws = employee.getWorkstation();
		if (ws != null && ws.getId() > 0) {

			ws = workstationClient.findById(ws.getId())
					.orElseThrow(() -> new RuntimeException("No se encuentra la workstation"));

			if (ws.getEmployeeId() != null) {
				Employee assignedEmlpoyee = employeeRepo.findById(ws.getEmployeeId()).orElse(null);
				if (assignedEmlpoyee == null) {
					employee.setWorkstation(ws);
				} else {
					throw new RuntimeException("Ya ocupada");
				}
			} else {
				employee.setWorkstation(ws);
			}
			employee.setNumber(EmployeeUtils.nextEmployeeNumber());
			employee = employeeRepo.save(employee);
			ws.setEmployeeId(employee.getId());
			ws = workstationClient.update(ws);
		} else if (ws != null && ws.getId() == 0) {

			employee.setNumber(EmployeeUtils.nextEmployeeNumber());
			employee = employeeRepo.save(employee);
			ws.setFacilitiesSerialNumber(EmployeeUtils.nextFacilitiesSerialNumber());
			ws.setEmployeeId(employee.getId());
			ws = workstationClient.save(ws);
			employee.setWorkstation(ws);
		} else if (ws == null) {
			employee.setNumber(EmployeeUtils.nextEmployeeNumber());
			employee = employeeRepo.save(employee);
		}
		return employee;
	}

	@Override
	public Employee update(Employee actualizado) {

		Employee registrado = employeeRepo.findById(actualizado.getId())
				.orElseThrow(() -> new RuntimeException("No se encuentra el empleado"));

		registrado.setFirstName(actualizado.getFirstName());
		registrado.setLastName(actualizado.getLastName());

		Workstation workstation = actualizado.getWorkstation();
		Workstation workstationExistente = null;

		if (workstation != null && workstation.getId() > 0) {

			workstationExistente = workstationClient.findById(workstation.getId())
					.orElseThrow(() -> new RuntimeException("No se encuentra la workstation"));

			if (workstation.getEmployeeId() != null) {
				Employee empleadoLigado = employeeRepo.findById(workstation.getEmployeeId()).get();
				if (empleadoLigado == null) {
					registrado.setWorkstation(workstationExistente);
					workstationExistente.setEmployeeId(registrado.getId());
					registrado = employeeRepo.save(registrado);
					workstationExistente = workstationClient.update(workstationExistente);

				} else if (empleadoLigado != null && empleadoLigado.getId() == registrado.getId()) {
					registrado = employeeRepo.save(registrado);
					workstationExistente = workstationClient.save(workstationExistente);
				} else {
					throw new RuntimeException("Ya ocupada");
				}
			} else {
				registrado.setWorkstation(workstationExistente);
				workstationExistente.setEmployeeId(registrado.getId());
				registrado = employeeRepo.save(registrado);
				workstationExistente = workstationClient.update(workstationExistente);
			}
		} else if (workstation != null && workstation.getId() == 0) {
			workstation.setFacilitiesSerialNumber(EmployeeUtils.nextFacilitiesSerialNumber());
			workstation.setEmployeeId(registrado.getId());
			workstation = workstationClient.save(workstation);
			registrado.setWorkstation(workstation);
			registrado = employeeRepo.save(registrado);
		} else if (workstation == null) {
			workstationExistente = registrado.getWorkstation();
			System.out.println("Se registró " + workstationExistente);
			if (workstationExistente != null) {
				workstationExistente.setEmployeeId(null);
				workstationExistente = workstationClient.save(workstationExistente);
			}
			registrado.setWorkstation(null);
			registrado = employeeRepo.save(registrado);
		}
		return registrado;
	}

	@Override
	public Employee findByNumber(String employeeNumber) {
		return employeeRepo.findByNumber(employeeNumber);
	}
	
	@Override
	public List<Employee> findByFirstNameAndLastName(String firstName, String lastName) {
		return employeeRepo.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
	}

	@Override
	public Employee delete(Employee employee) {

		Workstation ws = employee.getWorkstation();
		if (ws != null) {
			ws.setEmployeeId(null);
			workstationClient.save(ws);
		}
		employeeRepo.delete(employee);
		return employee;
	}


	@Override
	public List<Employee> findByFirstName(String firstName) {
		return employeeRepo.findByFirstNameIgnoreCase(firstName);
	}
}
