package com.fpt.cpdm.validators.users;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoleMustMatchValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleMustMatch {

    String message() default "This role '${validatedValue}' is not found!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
