package com.baz.microservicio.workstation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.baz.microservicio.workstation.model.Workstation;
import com.baz.microservicio.workstation.repository.WorkstationRepository;
import com.baz.microservicio.workstation.utils.WorkstationUtils;

@SpringBootApplication
public class WorkstationMicroservice {

	public static void main(String[] args) {
		SpringApplication.run(WorkstationMicroservice.class, args);
	}

	private WorkstationRepository workstationRepository;

	public WorkstationMicroservice(WorkstationRepository workstationRepository) {
		this.workstationRepository = workstationRepository;
	}

	@Bean
	public CommandLineRunner startup() {

		return (args) -> {

			Workstation workstation = Workstation.builder().vendor("Lanix").model("OPT F5")
					.facilitiesSerialNumber(WorkstationUtils.nextFacilitiesSerialNumber()).build();

			workstationRepository.save(workstation);

			workstation = Workstation.builder().vendor("Dataflux").model("AQUA")
					.facilitiesSerialNumber(WorkstationUtils.nextFacilitiesSerialNumber()).build();

			workstationRepository.save(workstation);

			workstation = Workstation.builder().vendor("Steren").model("Bitpower RZY")
					.facilitiesSerialNumber(WorkstationUtils.nextFacilitiesSerialNumber()).build();

			workstationRepository.save(workstation);
			
			workstation = Workstation.builder().vendor("Mitzu").model("Razor 90")
					.facilitiesSerialNumber(WorkstationUtils.nextFacilitiesSerialNumber()).build();
			
			workstationRepository.save(workstation);

			Workstation ws = workstationRepository.findById((long)Math.ceil((Math.random() * 4))).get();
			ws.setEmployeeId((long)Math.ceil((Math.random() * 2)));
			workstationRepository.save(ws);
		};
	}

}
