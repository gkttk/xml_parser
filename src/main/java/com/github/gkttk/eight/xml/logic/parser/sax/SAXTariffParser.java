package com.github.gkttk.eight.xml.logic.parser.sax;

import com.github.gkttk.eight.xml.logic.parser.TariffParser;
import com.github.gkttk.eight.xml.logic.parser.exception.TariffParserException;
import com.github.gkttk.eight.xml.logic.parser.sax.handler.TariffHandler;
import com.github.gkttk.eight.xml.model.AbstractTariff;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.List;

public class SAXTariffParser implements TariffParser {

    private final TariffHandler tariffHandler;

    public SAXTariffParser(TariffHandler tariffHandler) {
        this.tariffHandler = tariffHandler;

    }

    @Override
    public List<AbstractTariff> parse(String fileLocation) throws TariffParserException {
        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(this.tariffHandler);
            reader.parse(fileLocation);
            return tariffHandler.getTariffs();
        } catch (SAXException | IOException e) {
            throw new TariffParserException("Can't parse the file: " + fileLocation, e);
        }


    }
}
