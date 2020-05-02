package com.swarawan.lib.service;

import com.swarawan.lib.model.Employee;

import java.util.List;

public interface EmployeeService {

	List<Employee> findAllEmployees();

	Employee findEmployee(Long id);

	String findEmployeeToString(Long id);
}
