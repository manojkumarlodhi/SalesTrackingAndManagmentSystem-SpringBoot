package com.company.salestracker.exception;

public class MethodArgumentNotValidException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MethodArgumentNotValidException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MethodArgumentNotValidException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public MethodArgumentNotValidException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MethodArgumentNotValidException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MethodArgumentNotValidException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
