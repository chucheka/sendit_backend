package com.myapp.sendit.interfaces;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.myapp.sendit.dto.BecomeRiderDto;
import com.myapp.sendit.dto.SetNewPasswordPayload;

public class RiderPasswordValidator implements ConstraintValidator<RiderPasswordMatches, Object> {

	@Override
	public void initialize( RiderPasswordMatches constraintAnnotation) {};
	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context){
		BecomeRiderDto credentials = (BecomeRiderDto) obj;
		return credentials.getPassword().equals(credentials.getConfirmPassword());
		}
}