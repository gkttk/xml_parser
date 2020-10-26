package com.github.gkttk.eight.xml.logic.parser.sax;

import com.github.gkttk.eight.xml.logic.parser.AbstractParserTest;
import com.github.gkttk.eight.xml.logic.parser.TariffParser;
import com.github.gkttk.eight.xml.logic.parser.sax.handler.TariffHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class SAXTariffParserTest extends AbstractParserTest {

    private static final TariffHandler TARIFF_HANDLER_MOCK = Mockito.mock(TariffHandler.class);

    @BeforeEach
    void init() {
        when(TARIFF_HANDLER_MOCK.getTariffs()).thenReturn(MAIN_TARIFFS_LIST);
    }

    @Test
    @Override
    public void testParseShouldThrowExceptionWhenXmlFileIsIncorrect() {
        //given
        when(TARIFF_HANDLER_MOCK.getTariffs()).thenThrow(IllegalArgumentException.class);
        //when
        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> tariffParser.parse(INCORRECT_XML_LOCATION));
    }

    @Override
    protected TariffParser getTariffParser() {
        return new SAXTariffParser(TARIFF_HANDLER_MOCK);
    }
}
