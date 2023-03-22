package com.myproject.project.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates a gpx file according to a specified xsd schema
 * (defined via local xsd file `xsdPath = "\some\local\path\gpx.xsd"` or URL `xsdURL = "http://www.topografix.com/GPX/1/1/gpx.xsd"`).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = XMLValidator.class)
public @interface ValidateXML {

    /**
     * @return the path to an XML Schema Definition (XSD) file that will be used to validate the XML data.
     */
    String xsdPath() default "";

    /**
     *The default schema is taken from <a href="http://www.topografix.com/GPX/1/1/gpx.xsd">topografix.com</a> .
     * @return  the URL of an XSD file that will be used to validate the XML data.
     */
    String xsdURL()  default "http://www.topografix.com/GPX/1/1/gpx.xsd";

    /**
     * @return  the error message that will be displayed if the XML data fails validation..
     */
    String message() default "Invalid gpx file";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
