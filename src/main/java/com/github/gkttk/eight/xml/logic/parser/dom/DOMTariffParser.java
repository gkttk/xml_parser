package com.github.gkttk.eight.xml.logic.parser.dom;

import com.github.gkttk.eight.xml.logic.parser.TariffParser;
import com.github.gkttk.eight.xml.logic.parser.exception.TariffParserException;
import com.github.gkttk.eight.xml.model.AbstractTariff;
import com.github.gkttk.eight.xml.model.CallPrice;
import com.github.gkttk.eight.xml.model.InternetTariff;
import com.github.gkttk.eight.xml.model.SpeakTariff;
import com.github.gkttk.eight.xml.model.enums.OperatorName;
import com.github.gkttk.eight.xml.model.enums.SocialMediaName;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DOMTariffParser implements TariffParser {


    private List<AbstractTariff> tariffs;
    private AbstractTariff currentTariff;

    private final static String INTERNET_TARIFF = "internet-tariff";
    private final static String SPEAK_TARIFF = "speak-tariff";
    private static final String TARIFF_ID = "tariff-id";
    private static final String TARIFF_NAME = "tariff-name";
    private static final String OPERATOR_NAME = "operator-name";
    private static final String PAYROLL = "payroll";
    private static final String SMS_PRICE = "sms-price";
    private static final String INTERNAL_CALLS = "internal-calls";
    private static final String EXTERNAL_CALLS = "external-calls";
    private static final String LAND_LINE_CALLS = "landline-calls";
    private final static String FREE_MINUTES_ABROAD = "free-minutes-abroad";
    private final static String FAVOURITE_NUMBER = "favourite-number";
    private final static String FREE_INTERNET_MB = "free-internet-mb";
    private final static String FREE_SOCIAL_MEDIA = "free-social-media";

    public DOMTariffParser() {
        this.tariffs = new ArrayList<>();
    }

    @Override
    public List<AbstractTariff> parse(String fileLocation) throws TariffParserException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(fileLocation);
            Element root = document.getDocumentElement();
            buildTariffs(root, INTERNET_TARIFF);
            buildTariffs(root, SPEAK_TARIFF);

        } catch (SAXException exception) {
            throw new TariffParserException("Can't parse the file: " + fileLocation, exception);
        } catch (ParserConfigurationException exception) {
            throw new TariffParserException("Can't init DOMTariffParser in parse() method", exception);
        } catch (IOException exception) {
            throw new TariffParserException("File:{} not found" + fileLocation, exception);
        }
        return tariffs;
    }

    private void buildTariffs(Element rootElement, String tariffName) throws TariffParserException {
        NodeList tariffsNodeList = rootElement.getElementsByTagName(tariffName);
        for (int i = 0; i < tariffsNodeList.getLength(); i++) {
            Element tariffElement = (Element) tariffsNodeList.item(i);
            switch (tariffName) {
                case INTERNET_TARIFF: {
                    this.currentTariff = new InternetTariff();
                    buildCurrentInternetTariff(tariffElement);
                    break;

                }
                case SPEAK_TARIFF: {
                    this.currentTariff = new SpeakTariff();
                    buildCurrentSpeakTariff(tariffElement);
                    break;
                }
            }
            buildCurrentTariff(tariffElement);
            this.tariffs.add(this.currentTariff);
            this.currentTariff = null;
        }
    }


    private void buildCurrentTariff(Element tariffElement) throws TariffParserException {
        //id attribute
        String attributeId = tariffElement.getAttribute(TARIFF_ID);
        currentTariff.setId(attributeId);
        //name
        String name = getElementTextContent(tariffElement, TARIFF_NAME);
        currentTariff.setName(name);
        //operator-name
        String operatorNameString = getElementTextContent(tariffElement, OPERATOR_NAME).toUpperCase().trim();
        OperatorName operatorName = OperatorName.valueOf(operatorNameString);
        currentTariff.setOperatorName(operatorName);
        //payroll
        String payrollString = getElementTextContent(tariffElement, PAYROLL);
        double payroll = Double.parseDouble(payrollString);
        currentTariff.setPayroll(payroll);
        //sms-price
        String smsPriceString = getElementTextContent(tariffElement, SMS_PRICE);
        double smsPrice = Double.parseDouble(smsPriceString);
        currentTariff.setSmsPrice(smsPrice);
        //call-price
        buildCurrentCallPrice(tariffElement);
    }


    private void buildCurrentCallPrice(Element element) throws TariffParserException {
        CallPrice callPrice = new CallPrice();
        //internal-calls
        String internalCallsString = getElementTextContent(element, INTERNAL_CALLS);
        double internalCallsPrice = Double.parseDouble(internalCallsString);
        callPrice.setInternalPrice(internalCallsPrice);

        //external-calls
        String externalCallsString = getElementTextContent(element, EXTERNAL_CALLS);
        double externalCallsPrice = Double.parseDouble(externalCallsString);
        callPrice.setExternalPrice(externalCallsPrice);

        //landline-calls
        String landLineCallsString = getElementTextContent(element, LAND_LINE_CALLS);
        double landLineCallsPrice = Double.parseDouble(landLineCallsString);
        callPrice.setLandLinePrice(landLineCallsPrice);

        this.currentTariff.setCallPrice(callPrice);
    }


    private void buildCurrentInternetTariff(Element tariffElement) throws TariffParserException {
        //free-internet-mb
        String freeInternetMbString = getElementTextContent(tariffElement, FREE_INTERNET_MB);
        int freeInternetMb = Integer.parseInt(freeInternetMbString);
        ((InternetTariff) this.currentTariff).setFreeInternetMb(freeInternetMb);
        //free-social-media
        NodeList freeSocialMediaNodes = tariffElement.getElementsByTagName(FREE_SOCIAL_MEDIA);
        for (int i = 0; i < freeSocialMediaNodes.getLength(); i++) {
            Node currentNode = freeSocialMediaNodes.item(i);
            String socialMediaString = currentNode.getTextContent().toUpperCase().trim();
            SocialMediaName socialMedia = SocialMediaName.valueOf(socialMediaString);
            ((InternetTariff) this.currentTariff).addFreeSocialMedia(socialMedia);
        }
    }


    private void buildCurrentSpeakTariff(Element tariffElement) throws TariffParserException {
        //free-minutes-abroad
        String freeMinutesAbroadString = getElementTextContent(tariffElement, FREE_MINUTES_ABROAD);
        int freeMinutesAbroad = Integer.parseInt(freeMinutesAbroadString);
        ((SpeakTariff) this.currentTariff).setFreeMinutesAbroad(freeMinutesAbroad);

        //favourite-number
        NodeList favoriteNumbersNodes = tariffElement.getElementsByTagName(FAVOURITE_NUMBER);
        for (int i = 0; i < favoriteNumbersNodes.getLength(); i++) {
            Node currentNode = favoriteNumbersNodes.item(i);
            String numberString = currentNode.getTextContent();
            long number = Long.parseLong(numberString);
            ((SpeakTariff) this.currentTariff).addFavoriteNumber(number);
        }
    }

    private String getElementTextContent(Element element, String elementName) throws TariffParserException {
        NodeList elements = element.getElementsByTagName(elementName);
        if (elements.getLength() == 0) {
            throw new TariffParserException("There is no such element in the xml: " + elementName +
                    " for element " + element);
        }
        Node item = elements.item(0);
        return item.getTextContent();
    }


}
