package com.example2.webapp3;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND,reason="No such User")
public class UserNotFoundException extends RuntimeException{
	
	public UserNotFoundException(String message) {
		super(message);
	}
}
