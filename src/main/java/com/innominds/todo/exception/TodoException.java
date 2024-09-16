package com.innominds.todo.exception;

public class TodoException extends Throwable {

	private static final long serialVersionUID = 1L;
	public static final String CREATED_FAILED_IN_DB = "E01";
	public static final String READ_FAILED_IN_DB = "E02";
	public static final String UPDATE_FAILED_IN_DB = "E03";
	public static final String DELETE_FAILED_IN_DB = "E08";

	/**
	 * Instantiates a new covid 19 batch exception.
	 *
	 * @param message the message
	 */
	public TodoException(String code, String message) {
		super(message);
	}

	/**
	 * Instantiates a new covid 19 batch exception.
	 *
	 * @param message the message
	 * @param t       the t
	 */
	public TodoException(String code, String message, Throwable t) {
		super(message, t);
	}

}
