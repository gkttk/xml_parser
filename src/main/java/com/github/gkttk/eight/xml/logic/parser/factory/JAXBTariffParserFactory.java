package com.github.gkttk.eight.xml.logic.parser.factory;

import com.github.gkttk.eight.xml.logic.parser.TariffParser;
import com.github.gkttk.eight.xml.logic.parser.jaxb.JAXBTariffParser;

public class JAXBTariffParserFactory implements TariffParserFactory {
    public final static String DEFAULT_SCHEMA_LOCATION = "xml/tariffsSchema.xsd";

    @Override
    public TariffParser createTariffParser() {
        return new JAXBTariffParser(DEFAULT_SCHEMA_LOCATION);
    }
}
