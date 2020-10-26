package com.github.gkttk.eight.xml.logic.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TariffValidatorTest {

    private static final String SCHEMA_LOCATION = "src/test/resources/schema.xsd";
    private static final String CORRECT_XML_LOCATION = "src/test/resources/correct.xml";
    private static final String INCORRECT_XML_LOCATION = "src/test/resources/incorrect.xml";

    private final XmlValidator tariffValidator = new TariffValidator();

    @Test
    public void testValidateShouldReturnTrueIfXmlCorrespondsSchema() {
        //given
        //when
        boolean validateResult = tariffValidator.validate(CORRECT_XML_LOCATION, SCHEMA_LOCATION);
        //then
        Assertions.assertTrue(validateResult);
    }

    @Test
    public void testValidateShouldReturnFalseIfXmlDoesNotCorrespondSchema() {
        //given
        //when
        boolean validateResult = tariffValidator.validate(INCORRECT_XML_LOCATION, SCHEMA_LOCATION);
        //then
        Assertions.assertFalse(validateResult);
    }

    @Test
    public void testValidateShouldReturnFalseIfXmlLocationOrSchemaLocationIncorrect() {
        //given
        String xmlWrongLocation = "";
        String schemaWrongLocation = "";
        //when
        boolean validateResult = tariffValidator.validate(xmlWrongLocation, schemaWrongLocation);
        //then
        Assertions.assertFalse(validateResult);
    }


}
