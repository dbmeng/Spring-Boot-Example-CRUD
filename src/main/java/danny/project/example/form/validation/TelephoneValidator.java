package danny.project.example.form.validation;

import danny.project.example.form.validation.Validations.ValidatePhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TelephoneValidator implements ConstraintValidator<ValidatePhoneNumber, String>{

  @Override
  public void initialize(ValidatePhoneNumber validatePhoneNumber){

  }

  @Override
  public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext){
    return phoneNumber != null && phoneNumber.matches("[0-9]+") && phoneNumber.length() == 11;
  }
}
