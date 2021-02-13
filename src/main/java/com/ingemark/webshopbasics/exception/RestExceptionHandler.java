package com.ingemark.webshopbasics.exception;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource iMessageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ErrorResponse tErrorResponse = ErrorResponse.builder()
				.errors(ex.getBindingResult().getFieldErrors().stream().map(tError -> {
					String[] tMessage = tError.getDefaultMessage().split(";");
					return new RestError(Integer.parseInt(tMessage[0]), tMessage[1], LocalDateTime.now());
				}).sorted(Comparator.comparingInt(RestError::getErrorCode)).collect(Collectors.toList())).build();

		return handleExceptionInternal(ex, tErrorResponse, headers, status, request);

	}

	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException pEx) {

		return new ResponseEntity<ErrorResponse>(ErrorResponse.builder()
				.error(RestError.builder().errorMessage(iMessageSource.getMessage(pEx.getMessage(), null, null))
						.timestamp(LocalDateTime.now()).build())
				.build(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(SystemErrorException.class)
	protected ResponseEntity<ErrorResponse> handleSystemErrorException(SystemErrorException pEx) {
		String[] tMessage = iMessageSource.getMessage(pEx.getMessage(), null, null).split(";");
		return new ResponseEntity<ErrorResponse>(
				ErrorResponse.builder().error(RestError.builder().errorCode(Integer.parseInt(tMessage[0]))
						.errorMessage(tMessage[1]).timestamp(LocalDateTime.now()).build()).build(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
