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


    private final static String TARIFF = "tariff";
    private final static String INTERNET = "internet";
    private final static String SPEAK = "speak";
    private final static String TARIFFS = "tariffs";
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
    private final static String CALL_PRICE = "call-price";


    @Override
    public void startDocument() {
        LOGGER.info("Starting parsing a file");
        this.tariffs = new ArrayList<>();
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (localName != null && localName.endsWith(TARIFF)) {
            initTariff(localName, attributes);
            return;
        } else if ("call-price".equals(localName)) {
            currentCallPrice = new CallPrice();
            return;
        }
        currentParameter = localName;

    }


    private void initTariff(String tariffName, Attributes attributes) {
        if (tariffName.startsWith(INTERNET)) {
            currentTariff = new InternetTariff();
        } else if (tariffName.startsWith(SPEAK)) {
            currentTariff = new SpeakTariff();
        }
        String tariffId = attributes.getValue(TARIFF_ID);
        currentTariff.setId(tariffId);
    }


    @Override
    public void characters(char[] ch, int start, int length) {
        if (currentParameter == null || currentParameter.equals(TARIFFS)) {
            return;
        }
        String parameterValue = new String(ch, start, length).trim();

        switch (currentParameter) {
            case TARIFF_NAME: {
                currentTariff.setName(parameterValue);
                break;
            }
            case OPERATOR_NAME: {
                String operatorName = parameterValue.toUpperCase().trim();
                OperatorName operatorNameEnum = OperatorName.valueOf(operatorName);
                currentTariff.setOperatorName(operatorNameEnum);
                break;
            }
            case PAYROLL: {
                double payroll = Double.parseDouble(parameterValue);
                currentTariff.setPayroll(payroll);
                break;
            }
            case SMS_PRICE: {
                double smsPrice = Double.parseDouble(parameterValue);
                currentTariff.setSmsPrice(smsPrice);
                break;
            }
            case FREE_INTERNET_MB: {
                int freeInternetMb = Integer.parseInt(parameterValue);
                ((InternetTariff) currentTariff).setFreeInternetMb(freeInternetMb);
                break;
            }
            case FREE_SOCIAL_MEDIA: {
                String socialMediaName = parameterValue.toUpperCase().trim();
                SocialMediaName socialMedia = SocialMediaName.valueOf(socialMediaName);
                ((InternetTariff) currentTariff).addFreeSocialMedia(socialMedia);
                break;
            }
            case FREE_MINUTES_ABROAD: {
                int freeMinutesAbroad = Integer.parseInt(parameterValue);
                ((SpeakTariff) currentTariff).setFreeMinutesAbroad(freeMinutesAbroad);
                break;
            }
            case FAVOURITE_NUMBER: {
                Long favoriteNumber = Long.parseLong(parameterValue);
                ((SpeakTariff) currentTariff).addFavoriteNumber(favoriteNumber);
                break;
            }
            case INTERNAL_CALLS: {
                double internalCallsPrice = Double.parseDouble(parameterValue);
                currentCallPrice.setInternalPrice(internalCallsPrice);
                break;
            }
            case EXTERNAL_CALLS: {
                double externalCallsPrice = Double.parseDouble(parameterValue);
                currentCallPrice.setExternalPrice(externalCallsPrice);
                break;
            }
            case LAND_LINE_CALLS: {
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
        if (INTERNET_TARIFF.equals(localName) || SPEAK_TARIFF.equals(localName)) {
            tariffs.add(currentTariff);
            currentTariff = null;
        }
        if (CALL_PRICE.equals(localName)) {
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
