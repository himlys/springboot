package com.spring.exception;

import com.spring.controller.ExceptionHandlerController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "it's UserServiceException not found" ,code = HttpStatus.OK)
public class UserServiceException extends RuntimeException{
}
