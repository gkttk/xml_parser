package com.github.gkttk.eight.xml.logic.parser;

import com.github.gkttk.eight.xml.logic.parser.exception.TariffParserException;
import com.github.gkttk.eight.xml.model.AbstractTariff;
import com.github.gkttk.eight.xml.model.CallPrice;
import com.github.gkttk.eight.xml.model.InternetTariff;
import com.github.gkttk.eight.xml.model.SpeakTariff;
import com.github.gkttk.eight.xml.model.enums.OperatorName;
import com.github.gkttk.eight.xml.model.enums.SocialMediaName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractParserTest {

    protected static final String CORRECT_XML_LOCATION = "src/test/resources/correct.xml";
    protected static final String INCORRECT_XML_LOCATION = "src/test/resources/incorrect.xml";

    protected final TariffParser tariffParser;

    protected final static List<AbstractTariff> MAIN_TARIFFS_LIST = Arrays.asList(
            new InternetTariff("I10001", "InternetFirst", OperatorName.VELCOM, 25, 0.2,
                    new CallPrice(0.3, 0.8, 1.5),
                    300, Arrays.asList(
                    SocialMediaName.VK,
                    SocialMediaName.INSTAGRAM
            )),
            new InternetTariff("I10002", "InternetSecond", OperatorName.LIFE, 15, 0.5,
                    new CallPrice(0.5, 1.5, 2.5),
                    300, Collections.singletonList(
                    SocialMediaName.TWITTER
            )),
            new SpeakTariff("S10001", "SpeakFirst", OperatorName.MTS, 30, 0.1,
                    new CallPrice(0.1, 0.3, 1),
                    150, Arrays.asList(
                    375291012908L, 291122341L, 375444563210L
            )),
            new SpeakTariff("S10002", "SpeakSecond", OperatorName.VELCOM, 10, 0.2,
                    new CallPrice(0.4, 0.9, 1),
                    150, Collections.singletonList(
                    375291111111L
            ))

    );

    public AbstractParserTest() {
        this.tariffParser = getTariffParser();
    }


    @Test
    public void testParseShouldReturnListOfTariffsIfXmlFileIsCorrect() throws TariffParserException {
        //given
        int expectedValue = 4;
        //when
        List<AbstractTariff> resultList = tariffParser.parse(CORRECT_XML_LOCATION);
        //then
        Assertions.assertEquals(expectedValue, resultList.size());
        Assertions.assertEquals(MAIN_TARIFFS_LIST, resultList);
    }

    @Test
    public void testParseShouldThrowExceptionWhenXmlFileLocationIsIncorrect() {
        //given
        //when
        //then
        Assertions.assertThrows(TariffParserException.class, () -> tariffParser.parse(""));
    }

    @Test
    public void testParseShouldThrowExceptionWhenXmlFileIsIncorrect() {
        //given
        //when
        //then
        Assertions.assertThrows(TariffParserException.class, () -> tariffParser.parse(INCORRECT_XML_LOCATION));
    }


    protected abstract TariffParser getTariffParser();


}
