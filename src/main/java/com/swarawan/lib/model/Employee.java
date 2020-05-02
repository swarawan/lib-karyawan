package com.swarawan.lib.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {

	private Long id;
	private String name;
	private String division;
}
