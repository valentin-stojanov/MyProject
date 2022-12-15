package com.myproject.project.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = XMLValidator.class)
public @interface ValidateXML {

    String xsdPath();

    String message() default "Invalid gpx file";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
