package com.innominds.todo.error;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * The Class BBAPISubError.
 */
@JsonRootName(value = "errors")
public class APISubError {

	/** The code. */
	private String code;

	/** The message. */
	private String message;

	/**
	 * This field can hold a reference to an entity/a record that the error refers
	 * to.
	 */
	private String referenceId;

	/**
	 * Instantiates a new BBAPI sub error.
	 */
	public APISubError() {
	}

	/**
	 * Instantiates a new BBAPI sub error.
	 *
	 * @param code    the code
	 * @param message the message
	 */
	public APISubError(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * Instantiates a new BBAPI sub error.
	 *
	 * @param code        the code
	 * @param message     the message
	 * @param referenceId the reference id
	 */
	public APISubError(String code, String message, String referenceId) {
		this.code = code;
		this.message = message;
		this.referenceId = referenceId;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the reference id.
	 *
	 * @return the reference id
	 */
	public String getReferenceId() {
		return referenceId;
	}

	/**
	 * Sets the reference id.
	 *
	 * @param referenceId the new reference id
	 */
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

}
