package com.swarawan.lib.repository;

import com.swarawan.lib.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

	public List<Employee> allEmployees() {
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(new Employee(1L, "Nobita", "Tech"));
		employees.add(new Employee(2L, "Doraemon", "Tech"));
		employees.add(new Employee(3L, "Jayen", "Finance"));
		employees.add(new Employee(4L, "Suneo", "Finance"));
		employees.add(new Employee(5L, "Sisuka", "Product"));
		employees.add(new Employee(6L, "Pak Guru", "Product"));
		return employees;
	}
}
