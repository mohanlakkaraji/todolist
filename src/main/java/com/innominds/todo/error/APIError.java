package com.innominds.todo.error;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * The Class BBAPIError.
 */
@JsonRootName(value = "ApiError")
public class APIError {

	/** The status. */
	private HttpStatus status;

	/** The timestamp. */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date timestamp;

	/** The code. */
	private String code;

	/** The message. */
	private String message;

	/** The errors. */
	@JsonInclude(value = Include.NON_NULL)
	private List<APISubError> errors;

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Instantiates a new BBAPI error.
	 */
	private APIError() {
		timestamp = new Date();
	}

	/**
	 * Instantiates a new BBAPI error.
	 *
	 * @param status the status
	 * @param ex     the exception
	 */
	public APIError(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		if (ex != null) {
			this.message = ex.getLocalizedMessage();
		}
	}

	/**
	 * Instantiates a new BBAPI error.
	 *
	 * @param status the status
	 * @param ex     the exception
	 * @param errors the errors
	 */
	public APIError(HttpStatus status, Throwable ex, List<APISubError> errors) {
		this(status, ex);
		this.errors = errors;
	}

	/**
	 * Instantiates a new BBAPI error.
	 *
	 * @param status the status
	 * @param ex     the exception
	 * @param title  the title
	 */
	public APIError(HttpStatus status, Throwable ex, String code) {
		this(status, ex);
		this.code = code;
	}

	/**
	 * Instantiates a new BBAPI error.
	 *
	 * @param status the status
	 * @param ex     the exception
	 * @param errors the errors
	 * @param title  the title
	 */
	public APIError(HttpStatus status, Throwable ex, List<APISubError> errors, String code) {
		this(status, ex, errors);
		this.code = code;
	}

	/**
	 * Instantiates a new BBAPI error.
	 *
	 * @param status  the status
	 * @param message the message
	 * @param title   the title
	 * @param errors  the errors
	 */
	public APIError(HttpStatus status, String message, String code, List<APISubError> errors) {
		this();
		this.status = status;
		this.errors = errors;
		this.code = code;
		this.message = message;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Gets the errors.
	 *
	 * @return the errors
	 */
	public List<APISubError> getErrors() {
		return errors;
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
	 * @param code the code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "APIError [status=" + status + ", timestamp=" + timestamp + ", code=" + code + ", message=" + message
				+ ", errors=" + errors + "]";
	}

}