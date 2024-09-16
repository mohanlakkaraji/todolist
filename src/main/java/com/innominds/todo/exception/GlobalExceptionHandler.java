package com.innominds.todo.exception;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.innominds.todo.dto.ResponseContainer;
import com.innominds.todo.error.APIError;

/**
 * The Class GlobalExceptionHandler. Acts as advisor to all controllers in this
 * service. Primarily used for exception handlers
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/** The Constant logger. */
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/*
	 * * Handle unknown errors.
	 *
	 * @param ex the java.lang.Exception
	 * 
	 * @return the response entity
	 */
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex) {
		logger.error("Unexpected exception", ex);
		APIError apiError = null;
		if (ex instanceof MissingServletRequestParameterException) {
			apiError = new APIError(HttpStatus.BAD_REQUEST, "E00", ex.getLocalizedMessage(), null);
		} else {
			apiError = new APIError(HttpStatus.INTERNAL_SERVER_ERROR, "E11", "Please contact support", null);
		}
		ResponseContainer<?> response = new ResponseContainer<>(apiError);
		return new ResponseEntity<>(response, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * Handle all.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler({ LockException.class })
	@ResponseStatus(HttpStatus.LOCKED)
	public ResponseEntity<Object> handleAll(LockException ex) {
		logger.error("Optimistic Lock Exception ", ex);
		APIError apiError = new APIError(HttpStatus.LOCKED, ex.getMessage(), ex.getCode(), null);
		ResponseContainer<?> response = new ResponseContainer<>(apiError);
		return new ResponseEntity<>(response, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * Handle all.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler({ TodoException.class })
	public ResponseEntity<Object> handleAll(TodoException ex) {
		logger.error("Exception while saving and fetching the entities", ex);
		APIError apiError = new APIError(HttpStatus.CONFLICT, ex);
		ResponseContainer<?> response = new ResponseContainer<>(apiError);
		return new ResponseEntity<>(response, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * Handle http message not readable.
	 *
	 * @param ex the HttpMessageNotReadableException
	 * @return the response entity
	 */
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
		logger.error(ex.getClass().getName(), ex);
		APIError apiError = new APIError(HttpStatus.BAD_REQUEST, "Request parameter and/or body is invalid",
				"Parameter not correct", null);
		ResponseContainer<?> response = new ResponseContainer<>(apiError);
		return new ResponseEntity<>(response, new HttpHeaders(), apiError.getStatus());
	}

}
