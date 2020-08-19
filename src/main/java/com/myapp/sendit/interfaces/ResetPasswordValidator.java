package com.myapp.sendit.interfaces;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.myapp.sendit.dto.SetNewPasswordPayload;


public class ResetPasswordValidator implements ConstraintValidator<ResetPasswordMatches, Object> {

	@Override
	public void initialize( ResetPasswordMatches constraintAnnotation) {};
	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context){
		SetNewPasswordPayload credentials = (SetNewPasswordPayload) obj;
		return credentials.getNewPassword().equals(credentials.getConfirmNewPassword());
		}
}