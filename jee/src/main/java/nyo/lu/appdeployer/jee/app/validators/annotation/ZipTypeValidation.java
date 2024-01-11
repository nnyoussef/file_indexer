package nyo.lu.appdeployer.jee.app.validators.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nyo.lu.appdeployer.jee.app.validators.impl.ZipTypeValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ZipTypeValidator.class)
@Target({TYPE, PARAMETER})
@Retention(RUNTIME)
public @interface ZipTypeValidation {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
