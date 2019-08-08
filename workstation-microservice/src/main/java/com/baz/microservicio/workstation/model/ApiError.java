package com.baz.microservicio.workstation.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {

	private Date timestamp;

	private int status;

	private String error;

	private String message;

	private String path;
}
