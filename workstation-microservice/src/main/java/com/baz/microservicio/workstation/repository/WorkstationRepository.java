package com.baz.microservicio.workstation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baz.microservicio.workstation.model.Workstation;

public interface WorkstationRepository extends JpaRepository<Workstation, Long> {
	List<Workstation> findByVendor(String vendor);
	List<Workstation> findByFacilitiesSerialNumber(String facilitiesSerialNumber);
	Workstation findByEmployeeId(long employeeId);
}
