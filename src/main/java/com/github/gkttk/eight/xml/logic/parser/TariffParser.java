package com.github.gkttk.eight.xml.logic.parser;

import com.github.gkttk.eight.xml.logic.parser.exception.TariffParserException;
import com.github.gkttk.eight.xml.model.AbstractTariff;

import java.util.List;

public interface TariffParser {

    List<AbstractTariff> parse(String fileLocation) throws TariffParserException;
}
