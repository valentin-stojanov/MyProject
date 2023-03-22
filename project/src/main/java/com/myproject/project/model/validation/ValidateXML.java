package com.myproject.project.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates an XML file against specified XSD schema
 * (defined via local xsd file `xsdPath = "\some\local\path\gpx.xsd"` or URL `xsdURL = "https://www.topografix.com/GPX/1/1/gpx.xsd"`).
 * Error `message` is optional. Default error message is "Invalid XML file".
 * The default XSD is used from https://www.w3.org/2001/03/xml.xsd.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = XMLValidator.class)
public @interface ValidateXML {

    /**
     * @return the path to an XML Schema Definition (XSD) file that will be used to validate the XML data.
     */
    String localXsdPath() default "";

    /**
     *The default schema is taken from <a href="http://www.topografix.com/GPX/1/1/gpx.xsd">topografix.com</a> .
     * @return  the URL of an XSD file that will be used to validate the XML data.
     */
    String xsdURL()  default "https://www.w3.org/2001/03/xml.xsd";

    /**
     * @return  the error message that will be displayed if the XML data fails validation..
     */
    String message() default "Invalid XML file";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
