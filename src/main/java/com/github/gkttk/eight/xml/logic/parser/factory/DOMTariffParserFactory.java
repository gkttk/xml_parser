package com.github.gkttk.eight.xml.logic.parser.factory;

import com.github.gkttk.eight.xml.logic.parser.TariffParser;
import com.github.gkttk.eight.xml.logic.parser.dom.DOMTariffParser;

public class DOMTariffParserFactory implements TariffParserFactory {

    @Override
    public TariffParser createTariffParser() {
        return new DOMTariffParser();
    }
}
