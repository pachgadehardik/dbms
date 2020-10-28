package com.capg.jdbc_demo.employeepayroll;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Enumeration;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
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
		
		listDrivers();
		try {
			System.out.println("Conecting to dbs : "+DB_URL);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connection is Successfull"+conn);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void listDrivers() {
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while(driverList.hasMoreElements()) {
			Driver driverClass = driverList.nextElement();
			System.out.println(" "+driverClass.getClass().getName());
		}
	}
}