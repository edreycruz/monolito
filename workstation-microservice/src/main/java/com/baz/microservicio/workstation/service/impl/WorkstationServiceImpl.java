package com.baz.microservicio.workstation.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.microservicio.workstation.model.Workstation;
import com.baz.microservicio.workstation.repository.WorkstationRepository;
import com.baz.microservicio.workstation.service.WorkstationService;
import com.baz.microservicio.workstation.utils.WorkstationUtils;

@Service
@Transactional
public class WorkstationServiceImpl implements WorkstationService {

	@Autowired
	private WorkstationRepository workstationRepository;

	@Override
	public Workstation findById(Long id) {
		return workstationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No se encontró la workstation"));
	}

	@Override
	public List<Workstation> findAll() {
		return workstationRepository.findAll();
	}

	@Override
	public Workstation saveNew(Workstation workstation) {
		workstation.setFacilitiesSerialNumber(WorkstationUtils.nextFacilitiesSerialNumber());
		workstation = workstationRepository.save(workstation);
		return workstation;
	}

	@Override
	public Workstation update(Workstation workstation) {

		Workstation ws = workstationRepository.findByEmployeeId(workstation.getEmployeeId());
		if (ws != null) {
			ws.setEmployeeId(null);
			workstationRepository.save(ws);
		}
		Workstation registeredWorkstation = workstationRepository.findById(workstation.getId())
				.orElseThrow(() -> new RuntimeException("No se encuentra la workstation"));

		registeredWorkstation.setVendor(workstation.getVendor());
		registeredWorkstation.setModel(workstation.getModel());
		registeredWorkstation.setEmployeeId(workstation.getEmployeeId());
		registeredWorkstation = workstationRepository.save(registeredWorkstation);
		return registeredWorkstation;
	}

	@Override
	public Workstation delete(Workstation workstation) {
		workstationRepository.delete(workstation);
		return workstation;
	}

	@Override
	public List<Workstation> findByVendor(String vendor) {
		return workstationRepository.findByVendor(vendor);
	}

	@Override
	public List<Workstation> findByNumber(String facilitiesSerialNumber) {
		return workstationRepository.findByFacilitiesSerialNumber(facilitiesSerialNumber);
	}

	@Override
	public Workstation findByEmployeeId(long employeeId) {
		return workstationRepository.findByEmployeeId(employeeId);
	}

}
