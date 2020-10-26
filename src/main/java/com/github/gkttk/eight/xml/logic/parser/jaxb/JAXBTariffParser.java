package com.github.gkttk.eight.xml.logic.parser.jaxb;

import com.github.gkttk.eight.xml.logic.parser.TariffParser;
import com.github.gkttk.eight.xml.logic.parser.exception.TariffParserException;
import com.github.gkttk.eight.xml.model.AbstractTariff;
import com.github.gkttk.eight.xml.model.tariffs.Tariffs;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.List;

public class JAXBTariffParser implements TariffParser {

    private String schemaLocation;

    public JAXBTariffParser(String schemaLocation) {
        this.schemaLocation = schemaLocation;

    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }


    @Override
    public List<AbstractTariff> parse(String fileLocation) throws TariffParserException {
        try {
            JAXBContext content = JAXBContext.newInstance(Tariffs.class);
            Unmarshaller unmarshaller = content.createUnmarshaller();
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            File schemaFile = new File(schemaLocation);
            Schema schema = factory.newSchema(schemaFile);
            unmarshaller.setSchema(schema);
            File fileLocationFile = new File(fileLocation);
            Tariffs tariffs = (Tariffs) unmarshaller.unmarshal(fileLocationFile);
            return tariffs.getTariffs();
        } catch (JAXBException | SAXException exception) {
            throw new TariffParserException(this.getClass().getName() + " can't parse the file: "
                    + fileLocation + "\nWith schemaLocation: " + schemaLocation, exception);
        }
    }
}
