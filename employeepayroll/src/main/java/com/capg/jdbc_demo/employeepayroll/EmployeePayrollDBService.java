package com.capg.jdbc_demo.employeepayroll;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Hello world!
 *
 */
public class EmployeePayrollDBService {

	private static Connection getConnection() {
		final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
		final String DB_URL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";

		// Database credentials
		final String USER = "root";
		final String PASS = "hardik@#123";
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Driver Loaded!");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Cant find another classpath!", e);
		}

		try {
			System.out.println("Conecting to dbs : " + DB_URL);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connection is Successfull" + conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static List<EmployeePayrollData> readData() throws SQLException {
		String sql = "Select * from temp_payroll_table";
		List<EmployeePayrollData> employeePayrollList = new ArrayList<EmployeePayrollData>();
		Connection conn = getConnection();
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		while (result.next()) {
			int id = result.getInt("emp_id");
			String name = result.getString("name");
			double salary = result.getDouble("salary");
			LocalDate startDate = result.getDate("start_date").toLocalDate();
			employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
		}

		conn.close();
		return employeePayrollList;
	}

}