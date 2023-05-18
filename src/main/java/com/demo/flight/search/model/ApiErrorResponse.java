package com.demo.flight.search.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author nabochak
 *
 */


/*
 * @AllArgsConstructor
 * 
 * @NoArgsConstructor
 * 
 * @Getter
 * 
 * @Setter
 * 
 * @ToString
 */
 
public class ApiErrorResponse {

	private HttpStatus httpStatus;
	private int httpStatusCode;
	private LocalDateTime timestamp;
	private String message;

	public ApiErrorResponse(HttpStatus httpStatus, int httpStatusCode, LocalDateTime timestamp, String message) {
		this.httpStatus = httpStatus;
		this.setHttpStatusCode(httpStatusCode);
		this.timestamp = timestamp;
		this.message = message;

	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
