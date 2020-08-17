package com.myapp.sendit.exceptions;




import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.SendFailedException;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.myapp.sendit.payload.ErrorResponse;





@ControllerAdvice
public class CustomGlobalExceptionHandler{
	
	@ExceptionHandler({AccessDeniedException.class})
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(Exception ex,WebRequest req){
		ErrorResponse error = new ErrorResponse();
				error.setStatus(HttpStatus.FORBIDDEN.value());
				error.setMessage(ex.getMessage());
				return new ResponseEntity<ErrorResponse>(error,HttpStatus.FORBIDDEN);	
						
	}
	

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
			}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getDefaultMessage());
			}
		ErrorResponse ErrorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), errors);
		return new ResponseEntity<>(ErrorResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> constraintViolationException(ConstraintViolationException ex) {
	
		ErrorResponse error = new ErrorResponse();
		
		List<String> errors = new ArrayList<>();
		
		errors.add(ex.getMessage());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		error.setErrors(errors);
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exc){
	ErrorResponse error = new ErrorResponse();
		
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException exc){
	ErrorResponse error = new ErrorResponse();
		
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exc){
		ErrorResponse error = new ErrorResponse();
		
		
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(AppException.class)
	public ResponseEntity<ErrorResponse> handleAppException(AppException exc){
		ErrorResponse error = new ErrorResponse();
		
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage(exc.getLocalizedMessage());
		return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ActionNotAllowedException.class)
	public ResponseEntity<ErrorResponse> handleActionNotAllowedException(ActionNotAllowedException exc){
		ErrorResponse error = new ErrorResponse();
		
		
		error.setStatus(HttpStatus.EXPECTATION_FAILED.value());
		error.setMessage(exc.getMessage());
		return new ResponseEntity<>(error,HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler(SendFailedException.class)
	public ResponseEntity<ErrorResponse> handleSendFailedException(SendFailedException exc){
		ErrorResponse error = new ErrorResponse();
		
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(FileUploadException.class)
	public ResponseEntity<ErrorResponse> handleFileUploadException(FileUploadException ex) {
	
		ErrorResponse error = new ErrorResponse();
		
		List<String> errors = new ArrayList<>();
		
		errors.add(ex.getMessage());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		error.setErrors(errors);
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
}
