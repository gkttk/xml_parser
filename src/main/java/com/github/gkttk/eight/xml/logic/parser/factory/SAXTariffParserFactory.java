package com.github.gkttk.eight.xml.logic.parser.factory;

import com.github.gkttk.eight.xml.logic.parser.TariffParser;
import com.github.gkttk.eight.xml.logic.parser.sax.SAXTariffParser;
import com.github.gkttk.eight.xml.logic.parser.sax.handler.TariffHandler;

public class SAXTariffParserFactory implements TariffParserFactory {
    public final static TariffHandler DEFAULT_TARIFF_HANDLER = new TariffHandler();

    @Override
    public TariffParser createTariffParser() {
        return new SAXTariffParser(DEFAULT_TARIFF_HANDLER);
    }
}
