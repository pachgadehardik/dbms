package com.capg.jdbc_demo.employeepayroll.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.capg.jdbc_demo.employeepayroll.exceptions.CustomMySQLException;
import com.capg.jdbc_demo.employeepayroll.exceptions.CustomMySQLException.ExceptionType;
import com.capg.jdbc_demo.employeepayroll.model.EmployeePayrollData;

/**
 * Hello world!
 *
 */
public class EmployeePayrollDBService {

	private PreparedStatement employeePayrollDataStatement;
	private static EmployeePayrollDBService employeePayrollDBService;
	
	static List<EmployeePayrollData> employeePayrollList;

	private EmployeePayrollDBService() {
	}
//
	public static EmployeePayrollDBService getInstance() {
		if (employeePayrollDBService == null)
			employeePayrollDBService = new EmployeePayrollDBService();
		return employeePayrollDBService;
	}

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
		employeePayrollList = new ArrayList<EmployeePayrollData>();
		Connection conn = getConnection();
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		employeePayrollList = getEmployeePayrollData(result);
		conn.close();
		return employeePayrollList;
	}

	public boolean checkEmployeePayrollInSyncWithDB(String name) throws CustomMySQLException {
		List<EmployeePayrollData> employeePayrollDataList = this.getEmployeePayrollDataInList(name);
		 return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
	}

	private List<EmployeePayrollData> getEmployeePayrollDataInList(String name) throws CustomMySQLException {
		List<EmployeePayrollData> employeePayrollDataList = null;
		if (this.employeePayrollDataStatement == null)
			this.preparedStatementForEmployeeData();
		try {
			employeePayrollDataStatement.setString(1, name);
			ResultSet resultSet = employeePayrollDataStatement.executeQuery();
			employeePayrollDataList = this.getEmployeePayrollData(resultSet);
		} catch (SQLException e) {
			throw new CustomMySQLException(e.getMessage(), ExceptionType.OTHER_TYPE);
		}
		return null;
	}

	public static List<EmployeePayrollData> getEmployeePayrollData(ResultSet result) {
		List<EmployeePayrollData> employeeList = new ArrayList<EmployeePayrollData>();
		try {
			while (result.next()) {
				int id = result.getInt("emp_id");
				String name = result.getString("name");
				double salary = result.getDouble("salary");
				LocalDate startDate = result.getDate("start_date").toLocalDate();
				employeeList.add(new EmployeePayrollData(id, name, salary, startDate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeeList;
	}

	private void preparedStatementForEmployeeData() throws CustomMySQLException {
		try {
			Connection conn = this.getConnection();
			String sql = "Select * from temp_payroll_table where name = ?";
			employeePayrollDataStatement = conn.prepareStatement(sql);
		} catch (SQLException e) {
			throw new CustomMySQLException(e.getMessage(), ExceptionType.OTHER_TYPE);
		}
	}

	public void updateEmployeeSalary(String name, double salary,int flag) throws SQLException {
		int result = updateEmployeeData(name, salary,flag);
		if (result == 0)
			return;
		EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
		if (employeePayrollData != null)
			employeePayrollData.setSalary(salary);
	}

	private EmployeePayrollData getEmployeePayrollData(String name) {
		return this.employeePayrollList.stream()
				.filter(EmployeePayrollDataItem -> EmployeePayrollDataItem.getName().equals(name)).findFirst()
				.orElse(null);
	}

	private int updateEmployeeData(String name, double salary,int flag) throws SQLException {
		if(flag==0)
			return this.updateEmployeeDataUsingSQLQuery(name, salary);
		return this.updateEmployeeDataUsingPreparedStatement(name,salary);
	}

	private int updateEmployeeDataUsingPreparedStatement(String name, double salary) throws SQLException {
		int result = 0;
		try(Connection conn = this.getConnection()){
			String sql = "update temp_payroll_table set salary =? where name = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, salary);
			stmt.setString(2, name);
			result = stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	private int updateEmployeeDataUsingSQLQuery(String name, double salary) throws SQLException {
		try (Connection conn = getConnection()) {
			String sql = String.format("update temp_payroll_table set salary = %.2f where name = '%s';", salary, name);
			Statement stmt = conn.createStatement();
			return stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new CustomMySQLException(e.getMessage(), ExceptionType.NO_DATA_FOUND);
		}
	}

}