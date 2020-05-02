package com.swarawan.lib.service;

import com.swarawan.lib.model.Employee;
import com.swarawan.lib.repository.EmployeeRepository;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository repository;

	public EmployeeServiceImpl() {
		this.repository = new EmployeeRepository();
	}

	public List<Employee> findAllEmployees() {
		return repository.allEmployees();
	}

	public Employee findEmployee(Long id) {
		return repository.allEmployees()
				.stream()
				.filter(employee -> id.equals(employee.getId()))
				.findFirst()
				.orElse(null);
	}

	@Override
	public String findEmployeeToString(Long id) {
		Employee employee = findEmployee(id);
		if (null == employee) {
			return "Data tidak ditemukan";
		}
		return String.format("[%s] %s", employee.getDivision(), employee.getName());
	}
}
