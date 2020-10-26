package com.github.gkttk.eight.xml.logic.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class TariffValidator implements XmlValidator {

    private final static String LANGUAGE = XMLConstants.W3C_XML_SCHEMA_NS_URI;
    private final SchemaFactory FACTORY = SchemaFactory.newInstance(LANGUAGE);
    private final static Logger LOGGER = LogManager.getLogger(TariffValidator.class);

    @Override
    public boolean validate(String fileLocation, String schemaLocation) {
        File schemaFile = new File(schemaLocation);
        try {
            Schema schema = FACTORY.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(fileLocation);
            validator.validate(source);
            return true;
        } catch (SAXException | IOException exception) {
            LOGGER.warn("Can't validate the file:{}, with given schema:{}\nException:{}",
                    fileLocation, schemaLocation, exception);
            return false;
        }

    }


}
