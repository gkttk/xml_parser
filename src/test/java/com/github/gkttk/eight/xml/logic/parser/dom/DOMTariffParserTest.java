package com.github.gkttk.eight.xml.logic.parser.dom;

import com.github.gkttk.eight.xml.logic.parser.AbstractParserTest;
import com.github.gkttk.eight.xml.logic.parser.TariffParser;

public class DOMTariffParserTest extends AbstractParserTest {


    @Override
    protected TariffParser getTariffParser() {
        return new DOMTariffParser();
    }
}
