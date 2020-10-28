package com.capg.jdbc_demo.employeepayroll;

import java.time.LocalDate;

public class EmployeePayrollData {

	private int id;
	private String name;
	private Double salary;
	private LocalDate startDate;
	public EmployeePayrollData(int id, String name, Double salary, LocalDate startDate) {
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.startDate = startDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	@Override
	public String toString() {
		return "EmployeePayrollData [id=" + id + ", name=" + name + ", salary=" + salary + ", startDate=" + startDate
				+ "]";
	}
	
	
	
}
