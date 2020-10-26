package com.github.gkttk.eight.xml.logic.parser.jaxb;

import com.github.gkttk.eight.xml.logic.parser.AbstractParserTest;
import com.github.gkttk.eight.xml.logic.parser.TariffParser;

public class JAXBTariffParserTest extends AbstractParserTest {


    private final static String SCHEMA_LOCATION = "src/test/resources/schema.xsd";


    @Override
    protected TariffParser getTariffParser() {
        return new JAXBTariffParser(SCHEMA_LOCATION);
    }
}
