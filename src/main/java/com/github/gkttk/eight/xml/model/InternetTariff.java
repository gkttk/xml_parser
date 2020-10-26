package com.github.gkttk.eight.xml.model;

import com.github.gkttk.eight.xml.model.enums.OperatorName;
import com.github.gkttk.eight.xml.model.enums.SocialMediaName;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "internet-tariff-type", propOrder = {
        "freeInternetMb",
        "freeSocialMedia"
})
public class InternetTariff extends AbstractTariff {
    @XmlElement(name = "free-internet-mb", type = Integer.class, required = true, defaultValue = "300", namespace = "tariff.schema")
    @XmlSchemaType(name = "positiveInteger")
    private int freeInternetMb;
    @XmlElement(name = "free-social-media", type = SocialMediaName.class, namespace = "tariff.schema")
    @XmlSchemaType(name = "string")
    private List<SocialMediaName> freeSocialMedia = new ArrayList<>();

    public InternetTariff() {

    }

    public InternetTariff(String id, String name, OperatorName operatorName, double payroll, double smsPrice, CallPrice callPrice, int freeInternetMb, List<SocialMediaName> freeSocialMedia) {
        super(id, name, operatorName, payroll, smsPrice, callPrice);
        this.freeInternetMb = freeInternetMb;
        this.freeSocialMedia = freeSocialMedia;
    }

    public int getFreeInternetMb() {
        return freeInternetMb;
    }

    public void setFreeInternetMb(int freeInternetMb) {
        this.freeInternetMb = freeInternetMb;
    }

    public List<SocialMediaName> getFreeSocialMedia() {
        return freeSocialMedia;
    }

    public void setFreeSocialMedia(List<SocialMediaName> freeSocialMedia) {
        this.freeSocialMedia = freeSocialMedia;
    }


    public void addFreeSocialMedia(SocialMediaName socialMediaName) {
        this.freeSocialMedia.add(socialMediaName);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        InternetTariff tariff = (InternetTariff) o;
        return freeInternetMb == tariff.freeInternetMb &&
                Objects.equals(freeSocialMedia, tariff.freeSocialMedia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), freeInternetMb, freeSocialMedia);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", freeInternetMb= " + freeInternetMb +
                ", freeSocialMedia= " + freeSocialMedia + " ";
    }
}
