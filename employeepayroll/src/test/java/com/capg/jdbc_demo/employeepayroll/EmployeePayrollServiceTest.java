package com.capg.jdbc_demo.employeepayroll;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.capg.jdbc_demo.employeepayroll.Service.EmployeePayrollDBService;
import com.capg.jdbc_demo.employeepayroll.exceptions.CustomMySQLException;
import com.capg.jdbc_demo.employeepayroll.exceptions.CustomMySQLException.ExceptionType;
import com.capg.jdbc_demo.employeepayroll.model.EmployeePayrollData;

public class EmployeePayrollServiceTest {

//	EmployeePayrollDBService employeePayrollDBService;

//	@BeforeEach
//	public void init() {
//		
//	}

	@Test
	public void givenEmployeePayrollInDB_When_RetrievedShouldMAtchEmployeeCount() throws SQLException {
		EmployeePayrollDBService employeePayrollDBService =  EmployeePayrollDBService.getInstance();
		employeePayrollDBService = employeePayrollDBService;
		System.out.println(employeePayrollDBService.readData());
		assertEquals(3, employeePayrollDBService.readData().size());
	}

	@Test
	public void givenNewSalaryForEmployee_WhenUpdated_ShoudlMatchWithDB() throws SQLException {
		try {
			EmployeePayrollDBService employeePayrollDBService = EmployeePayrollDBService.getInstance();
//			employeePayrollDBService = employeePayrollDBService.getInstance();
			List<EmployeePayrollData> employeePayrollData = employeePayrollDBService.readData();
			employeePayrollDBService.updateEmployeeSalary("Teressa", 500000.00,0);
			assertEquals(500000,employeePayrollData.get(1).getSalary());
//			boolean result = employeePayrollDBService.checkEmployeePayrollInSyncWithDB("Teressa");
//			assertTrue(result);
		} catch (SQLException e) {
			throw new CustomMySQLException(e.getMessage(), ExceptionType.NO_DATA_FOUND);
		}
	}
	
	@Test
	public void givenNewSalaryForEmployee_WhenUpdatedUsingPreparedStatement_ShoudlMatchWithDB() throws SQLException {
		try {
			EmployeePayrollDBService employeePayrollDBService = EmployeePayrollDBService.getInstance();
			List<EmployeePayrollData> employeePayrollData = employeePayrollDBService.readData();
			employeePayrollDBService.updateEmployeeSalary("John", 500000.00,1);
			assertEquals(500000,employeePayrollData.get(0).getSalary());
//			boolean result = employeePayrollDBService.checkEmployeePayrollInSyncWithDB("John");
//			assertTrue(result);
		} catch (SQLException e) {
			throw new CustomMySQLException(e.getMessage(), ExceptionType.NO_DATA_FOUND);
		}
	}
	
	

}
