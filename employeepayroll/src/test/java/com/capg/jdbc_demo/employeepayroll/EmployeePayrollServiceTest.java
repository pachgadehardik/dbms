package com.capg.jdbc_demo.employeepayroll;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

public class EmployeePayrollServiceTest {

	@Test
	public void givenEmployeePayrollInDB_When_RetrievedShouldMAtchEmployeeCount() throws SQLException {
		
		EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
		System.out.println(employeePayrollDBService.readData());
		assertEquals(3,employeePayrollDBService.readData().size());
		
	}
	
}
