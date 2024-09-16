package com.innominds.todo.exception;

public class LockException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public static final String RECORD_ALREADY_MODIFIED = "E04";
	public static final String UPDATE_FAILED_IN_DB = "E05";
	private final String code;

	/**
	 * Instantiates a new covid 19 batch exception.
	 *
	 * @param message the message
	 */
	public LockException(String code, String message) {
		super(message);
		this.code = code;
	}

	/**
	 * Instantiates a new covid 19 batch exception.
	 *
	 * @param message the message
	 * @param t       the t
	 */
	public LockException(String code, String message, Throwable t) {
		super(message, t);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
