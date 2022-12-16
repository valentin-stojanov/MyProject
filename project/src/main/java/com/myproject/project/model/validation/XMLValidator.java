package com.myproject.project.model.validation;

import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XMLValidator implements ConstraintValidator<ValidateXML, MultipartFile> {

    private String xsdPath;
    private String message;

    @Override
    public void initialize(ValidateXML constraintAnnotation) {
        this.xsdPath  = constraintAnnotation.xsdPath();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(MultipartFile gpxCoordinates, ConstraintValidatorContext context) {

        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(this.xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(gpxCoordinates.getInputStream()));
        } catch (IOException | SAXException e) {
//            System.out.println("Exception: "+e.getMessage());
            context
                    .buildConstraintViolationWithTemplate(e.getMessage())
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }
        return true;
    }
}
