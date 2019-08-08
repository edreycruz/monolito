package com.baz.microservicio.workstation.service;

import java.util.List;

import com.baz.microservicio.workstation.model.Workstation;

public interface WorkstationService {
	List<Workstation> findAll();
	Workstation findById(Long id);
	List<Workstation> findByNumber(String number);
	List<Workstation> findByVendor(String vendor);
	Workstation findByEmployeeId(long employeeId);
	Workstation saveNew(Workstation workstation);
	Workstation update(Workstation workstation);
	Workstation delete(Workstation workstation);
}
