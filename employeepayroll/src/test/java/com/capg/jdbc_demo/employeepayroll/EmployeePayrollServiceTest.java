package com.capg.jdbc_demo.employeepayroll;

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
		EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
		employeePayrollDBService = employeePayrollDBService.getInstance();
		System.out.println(employeePayrollDBService.readData());
		assertEquals(3, employeePayrollDBService.readData().size());
	}

	@Test
	public void givenNewSalaryForEmployee_WhenUpdated_ShoudlMatchWithDB() throws SQLException {
		try {
			EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
			employeePayrollDBService = employeePayrollDBService.getInstance();
			List<EmployeePayrollData> employeePayrollData = employeePayrollDBService.readData();
			employeePayrollDBService.updateEmployeeSalary("Teressa", 500000.00);
			assertEquals(500000,employeePayrollData.get(1).getSalary());
//			boolean result = employeePayrollDBService.checkEmployeePayrollInSyncWithDB("Teressa");
//			assertTrue(result);
		} catch (SQLException e) {
			throw new CustomMySQLException(e.getMessage(), ExceptionType.NO_DATA_FOUND);
		}
	}

}
