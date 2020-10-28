package com.capg.jdbc_demo.employeepayroll.exceptions;

import java.sql.SQLException;

public class CustomMySQLException extends SQLException {

	
	private static final long serialVersionUID = -6015737653052400220L;

	public enum ExceptionType{
		NO_DATA_FOUND, OTHER_TYPE
	};
	
	ExceptionType exceptionType;
	
	public CustomMySQLException(String msg, ExceptionType exceptionType) {
		super(msg);
		this.exceptionType = exceptionType;
	}
	
	
}
