package com.app.exception.handler;

import java.util.Date;
import java.util.NoSuchElementException;

import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.app.exception.AuthenticatedException;
import com.app.exception.ValidateTokenException;

@RestControllerAdvice
public class AppExceptionHandler {
	@ExceptionHandler(AuthenticatedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public Message getBadCredentialsException(Exception exception, WebRequest request) {
		return new Message(exception.getMessage(), request.getContextPath(), new Date());
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public Message getAccessDeniedException(Exception exception, WebRequest request) {
		return new Message(exception.getMessage(), request.getContextPath(), new Date());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Message getHttpMessageNotReadableException(Exception exception, WebRequest request) {
		return new Message("Missing input", request.getContextPath(), new Date());
	}

	@ExceptionHandler(ValidateTokenException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Message getRegistrationException(Exception exception, WebRequest request) {
		return new Message(exception.getMessage(), request.getContextPath(), new Date());
	}

	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Message getNoSuchElementException(Exception exception, WebRequest request) {
		return new Message(exception.getMessage(), request.getContextPath(), new Date());
	}

	@ExceptionHandler(HibernateException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Message getHibernateException(Exception exception, WebRequest request) {
		return new Message("could not execute statement", request.getContextPath(), new Date());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public Message getHttpRequestMethodNotSupportedException(Exception exception, WebRequest request) {
		return new Message(exception.getMessage(), request.getContextPath(), new Date());
	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Message getNullPointerException(Exception exception, WebRequest request) {
		return new Message(exception.getMessage(), request.getContextPath(), new Date());
	}

//	@ExceptionHandler(Exception.class)
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	public Message getOthesrException(Exception exception, WebRequest request) {
//		return new Message(exception.getMessage(), request.getContextPath(), new Date());
//	}
//	@ExceptionHandler(ExpiredJwtException.class)
//	@ResponseStatus(HttpStatus.UNAUTHORIZED)
//	public Message getExpiredJwtException(Exception exception, WebRequest request) {
//		return new Message(exception.getMessage(), request.getContextPath(), new Date());
//	}

}
