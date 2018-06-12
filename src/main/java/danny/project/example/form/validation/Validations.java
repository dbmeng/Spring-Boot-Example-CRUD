package danny.project.example.form.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

public abstract class Validations {
  private Validations(){}

  /**
   * Defines telephone number validation for course form
   */
  @Target({FIELD})
  @Retention(RUNTIME)
  @Constraint(validatedBy = TelephoneValidator.class)
  public @interface ValidatePhoneNumber {
    String message() default "Phone number must be 11 digits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
  }
}
