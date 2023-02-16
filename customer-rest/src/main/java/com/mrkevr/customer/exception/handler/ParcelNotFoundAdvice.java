package com.mrkevr.customer.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mrkevr.customer.exception.CustomerNotFoundException;
import com.mrkevr.customer.exception.ParcelNotFoundException;

@ControllerAdvice
public class ParcelNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(ParcelNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String employeeNotFoundHandler(CustomerNotFoundException ex) {
		
		return ex.getMessage();
	}

}
