package com.github.gkttk.eight.xml.logic.parser.sax.handler;

import com.github.gkttk.eight.xml.model.AbstractTariff;
import com.github.gkttk.eight.xml.model.CallPrice;
import com.github.gkttk.eight.xml.model.InternetTariff;
import com.github.gkttk.eight.xml.model.SpeakTariff;
import com.github.gkttk.eight.xml.model.enums.OperatorName;
import com.github.gkttk.eight.xml.model.enums.SocialMediaName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class TariffHandler extends DefaultHandler {
    private final static Logger LOGGER = LogManager.getLogger(TariffHandler.class);
    private List<AbstractTariff> tariffs;

    private AbstractTariff currentTariff;
    private CallPrice currentCallPrice;
    private String currentParameter;


    @Override
    public void startDocument() {
        LOGGER.info("Starting parsing a file");
        this.tariffs = new ArrayList<>();
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (localName != null && localName.endsWith("tariff")) {
            initTariff(localName, attributes);
            return;
        } else if ("call-price".equals(localName)) {
            currentCallPrice = new CallPrice();
            return;
        }
        currentParameter = localName;

    }


    private void initTariff(String tariffName, Attributes attributes) {
        if (tariffName.startsWith("internet")) {
            currentTariff = new InternetTariff();
        } else if (tariffName.startsWith("speak")) {
            currentTariff = new SpeakTariff();
        }
        String tariffId = attributes.getValue("tariff-id");
        currentTariff.setId(tariffId);
    }


    @Override
    public void characters(char[] ch, int start, int length) {
        if (currentParameter == null || currentParameter.equals("tariffs")) {
            return;
        }
        String parameterValue = new String(ch, start, length).trim();

        switch (currentParameter) {
            case "tariff-name": {
                currentTariff.setName(parameterValue);
                break;
            }
            case "operator-name": {
                String operatorName = parameterValue.toUpperCase().trim();
                OperatorName operatorNameEnum = OperatorName.valueOf(operatorName);
                currentTariff.setOperatorName(operatorNameEnum);
                break;
            }
            case "payroll": {
                double payroll = Double.parseDouble(parameterValue);
                currentTariff.setPayroll(payroll);
                break;
            }
            case "sms-price": {
                double smsPrice = Double.parseDouble(parameterValue);
                currentTariff.setSmsPrice(smsPrice);
                break;
            }
            case "free-internet-mb": {
                int freeInternetMb = Integer.parseInt(parameterValue);
                ((InternetTariff) currentTariff).setFreeInternetMb(freeInternetMb);
                break;
            }
            case "free-social-media": {
                String socialMediaName = parameterValue.toUpperCase().trim();
                SocialMediaName socialMedia = SocialMediaName.valueOf(socialMediaName);
                ((InternetTariff) currentTariff).addFreeSocialMedia(socialMedia);
                break;
            }
            case "free-minutes-abroad": {
                int freeMinutesAbroad = Integer.parseInt(parameterValue);
                ((SpeakTariff) currentTariff).setFreeMinutesAbroad(freeMinutesAbroad);
                break;
            }
            case "favourite-number": {
                Long favoriteNumber = Long.parseLong(parameterValue);
                ((SpeakTariff) currentTariff).addFavoriteNumber(favoriteNumber);
                break;
            }
            case "internal-calls": {
                double internalCallsPrice = Double.parseDouble(parameterValue);
                currentCallPrice.setInternalPrice(internalCallsPrice);
                break;
            }
            case "external-calls": {
                double externalCallsPrice = Double.parseDouble(parameterValue);
                currentCallPrice.setExternalPrice(externalCallsPrice);
                break;
            }
            case "landline-calls": {
                double landLineCalls = Double.parseDouble(parameterValue);
                currentCallPrice.setLandLinePrice(landLineCalls);
                break;
            }
            default:
                LOGGER.error("Wrong parameter in xml:{}, value:{}", currentParameter, parameterValue);
                throw new IllegalArgumentException(String.format("Wrong parameter in xml: %s, value, %s)",
                        currentParameter, parameterValue));
        }
        currentParameter = null;
    }


    @Override
    public void endElement(String uri, String localName, String qName) {
        if ("internet-tariff".equals(localName) || "speak-tariff".equals(localName)) {
            tariffs.add(currentTariff);
            currentTariff = null;
        }
        if ("call-price".equals(localName)) {
            currentTariff.setCallPrice(currentCallPrice);
            currentCallPrice = null;
        }
    }


    @Override
    public void endDocument() {
        LOGGER.info("Finishing parsing a file");
    }

    public List<AbstractTariff> getTariffs() {
        List<AbstractTariff> resultTariffs = this.tariffs;
        this.tariffs = null;
        return resultTariffs;
    }


}
