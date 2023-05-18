	package com.demo.flight.search.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.demo.flight.search.model.ApiErrorResponse;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * GlobalExceptionController : This Controller is used as Global Exception
 * Controller Advisor
 * 
 */
@ApiIgnore
@ApiOperation(value = "This Controller is used as Global Exception Controller Advisor.")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * Exception handler for FlightDetailsNotFoundException.
	 * 
	 * @param ex      : custom exception for Record not found
	 * @param request : WebRequest interceptors, giving access to general request
	 *                metadata,not for actual handling of the request.
	 * @return ApiErrorResponse
	 */
	@ExceptionHandler(value = FlightDetailsNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleFlightDetailsNotFoundException(FlightDetailsNotFoundException ex,
			WebRequest request) {
		log.error("In GlobalExceptionHandler.handleFlightDetailsNotFoundException. Exception is :  {}",
				ex.getMessage());
		return new ResponseEntity<>(
				createResonseBody(ex.getMessage(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value()),
				HttpStatus.NOT_FOUND);
	}

	/**
	 * Exception handler for Exception.
	 * 
	 * @param ex       : Exception as input
	 * @param request  : Provide request information for HTTP servlets
	 * @param response : Provide HTTP-specific functionality in sending a response.
	 * @return ApiErrorResponse
	 */
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ApiErrorResponse> handleException(Exception ex, HttpServletRequest request,
			HttpServletResponse response) {
		log.error("In GlobalExceptionHandler.handleException. Exception is :  {}", ex.getMessage());
		return new ResponseEntity<>(createResonseBody(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,
				HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * ConstraintViolationException – This exception reports the result of
	 * constraint violations
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		return new ResponseEntity<>(
				createResonseBody(ex.getMessage(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * handleMissingServletRequestParameter : This exception is thrown when the
	 * request is missing a parameter.
	 * 
	 * @return ApiErrorResponse
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(
				createResonseBody(ex.getMessage(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST);

	}

	/**
	 * handleMethodArgumentTypeMismatch: This exception is thrown when method
	 * argument is not the expected type.
	 * 
	 * @param ex
	 * @param request
	 * @return ApiErrorResponse
	 */
	@ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		String errorMsg = "The value of " + ex.getName();
		if (ex.getName().equals("priceSort") || ex.getName().equals("durationSort")) {
			errorMsg = errorMsg + " should be ASCE or DESC.";
		} else {
			errorMsg = errorMsg + " should be of type " + ex.getRequiredType();
		}
		return new ResponseEntity<>(createResonseBody(errorMsg, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST);

	}

	/**
	 * Create Response body depending on exception occurred.
	 * 
	 * @param message
	 * @param httpStatus
	 * @param httpStatusCode
	 * @return ApiErrorResponse
	 */
	private ApiErrorResponse createResonseBody(String message, HttpStatus httpStatus, int httpStatusCode) {
		return new ApiErrorResponse(httpStatus, httpStatusCode, LocalDateTime.now(), message);
	}

}
