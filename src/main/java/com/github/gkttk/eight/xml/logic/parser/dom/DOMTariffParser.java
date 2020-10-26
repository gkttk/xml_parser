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
            buildTariffs(root, "internet-tariff");
            buildTariffs(root, "speak-tariff");

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
                case "internet-tariff": {
                    this.currentTariff = new InternetTariff();
                    buildCurrentInternetTariff(tariffElement);
                    break;

                }
                case "speak-tariff": {
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
        String attributeId = tariffElement.getAttribute("tariff-id");
        currentTariff.setId(attributeId);
        //name
        String name = getElementTextContent(tariffElement, "tariff-name");
        currentTariff.setName(name);
        //operator-name
        String operatorNameString = getElementTextContent(tariffElement, "operator-name").toUpperCase().trim();
        OperatorName operatorName = OperatorName.valueOf(operatorNameString);
        currentTariff.setOperatorName(operatorName);
        //payroll
        String payrollString = getElementTextContent(tariffElement, "payroll");
        double payroll = Double.parseDouble(payrollString);
        currentTariff.setPayroll(payroll);
        //sms-price
        String smsPriceString = getElementTextContent(tariffElement, "sms-price");
        double smsPrice = Double.parseDouble(smsPriceString);
        currentTariff.setSmsPrice(smsPrice);
        //call-price
        buildCurrentCallPrice(tariffElement);
    }

    private void buildCurrentCallPrice(Element element) throws TariffParserException {
        CallPrice callPrice = new CallPrice();
        //internal-calls
        String internalCallsString = getElementTextContent(element, "internal-calls");
        double internalCallsPrice = Double.parseDouble(internalCallsString);
        callPrice.setInternalPrice(internalCallsPrice);

        //external-calls
        String externalCallsString = getElementTextContent(element, "external-calls");
        double externalCallsPrice = Double.parseDouble(externalCallsString);
        callPrice.setExternalPrice(externalCallsPrice);

        //landline-calls
        String landLineCallsString = getElementTextContent(element, "landline-calls");
        double landLineCallsPrice = Double.parseDouble(landLineCallsString);
        callPrice.setLandLinePrice(landLineCallsPrice);

        this.currentTariff.setCallPrice(callPrice);
    }

    private void buildCurrentInternetTariff(Element tariffElement) throws TariffParserException {
        //free-internet-mb
        String freeInternetMbString = getElementTextContent(tariffElement, "free-internet-mb");
        int freeInternetMb = Integer.parseInt(freeInternetMbString);
        ((InternetTariff) this.currentTariff).setFreeInternetMb(freeInternetMb);
        //free-social-media
        NodeList freeSocialMediaNodes = tariffElement.getElementsByTagName("free-social-media");
        for (int i = 0; i < freeSocialMediaNodes.getLength(); i++) {
            Node currentNode = freeSocialMediaNodes.item(i);
            String socialMediaString = currentNode.getTextContent().toUpperCase().trim();
            SocialMediaName socialMedia = SocialMediaName.valueOf(socialMediaString);
            ((InternetTariff) this.currentTariff).addFreeSocialMedia(socialMedia);
        }
    }

    private void buildCurrentSpeakTariff(Element tariffElement) throws TariffParserException {
        //free-minutes-abroad
        String freeMinutesAbroadString = getElementTextContent(tariffElement, "free-minutes-abroad");
        int freeMinutesAbroad = Integer.parseInt(freeMinutesAbroadString);
        ((SpeakTariff) this.currentTariff).setFreeMinutesAbroad(freeMinutesAbroad);

        //favourite-number
        NodeList favoriteNumbersNodes = tariffElement.getElementsByTagName("favourite-number");
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
