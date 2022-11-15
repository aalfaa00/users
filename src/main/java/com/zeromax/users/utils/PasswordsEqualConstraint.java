package com.zeromax.users.utils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsEqualConstraintValidator.class)
@Documented
public @interface PasswordsEqualConstraint {

  String message() default "Password and confirmation does not match";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}

class PasswordsEqualConstraintValidator
    implements ConstraintValidator<PasswordsEqualConstraint, Object> {

  @Override
  public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
    var passwordConfirmableObject = (ConfirmablePassword) o;
    return passwordConfirmableObject.getPassword()
        .equals(passwordConfirmableObject.getConfirmPassword());
  }

  @Override
  public void initialize(PasswordsEqualConstraint constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

}
