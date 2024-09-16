package com.innominds.todo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.innominds.todo.error.APIError;

/**
 * The Class BBResponseContainer.
 *
 * @param <T> the generic type
 */
public class ResponseContainer<T> {

	/** The data. */
	@JsonInclude(value = Include.NON_NULL)
	private T data;

	/** The bb api error. */
	@JsonInclude(value = Include.NON_NULL)
	@JsonProperty(value = "ApiError")
	private APIError apiError;

	public APIError getApiError() {
		return apiError;
	}

	public void setApiError(APIError apiError) {
		this.apiError = apiError;
	}

	/**
	 * Instantiates a new BB response container.
	 */
	public ResponseContainer() {
	}

	/**
	 * Instantiates a new BB response container.
	 *
	 * @param data the data
	 */
	public ResponseContainer(T data) {
		super();
		this.data = data;
	}

	/**
	 * Instantiates a new BB response container.
	 *
	 * @param apiError the bb api error
	 */
	public ResponseContainer(APIError bbApiError) {
		this.apiError = bbApiError;
	}

	/**
	 * Instantiates a new BB response container.
	 *
	 * @param responseData the response data
	 * @param apiError     the bb api error
	 */
	public ResponseContainer(T responseData, APIError bbApiError) {
		this(responseData);
		this.apiError = bbApiError;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(T data) {
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResponseContainer [data=" + data + ", ApiError=" + apiError + "]";
	}

}
