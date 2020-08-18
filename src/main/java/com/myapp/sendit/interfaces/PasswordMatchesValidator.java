package com.myapp.sendit.interfaces;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

  private String password;
  private String confirmPassword;

  public void initialize(PasswordMatches constraintAnnotation) {
      this.password = constraintAnnotation.password();
      this.confirmPassword = constraintAnnotation.confirmPassword();
  }

  public boolean isValid(Object value, 
    ConstraintValidatorContext context) {

      Object fieldValue = new BeanWrapperImpl(value)
        .getPropertyValue(password);
      Object fieldMatchValue = new BeanWrapperImpl(value)
        .getPropertyValue(confirmPassword);
      
      if (fieldValue != null) {
          return fieldValue.equals(fieldMatchValue);
      } else {
          return fieldMatchValue == null;
      }
  }
}
