package com.github.gkttk.eight.xml.model;

import com.github.gkttk.eight.xml.model.enums.OperatorName;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "speak-tariff-type", propOrder = {
        "freeMinutesAbroad",
        "favoriteNumbers"
})
public class SpeakTariff extends AbstractTariff {
    @XmlElement(name = "free-minutes-abroad", type = Integer.class, required = true,
            defaultValue = "150", namespace = "tariff.schema")
    @XmlSchemaType(name = "positiveInteger")
    private int freeMinutesAbroad;
    @XmlElement(name = "favourite-number", type = Long.class, namespace = "tariff.schema")
    @XmlSchemaType(name = "phone-number-type")
    private List<Long> favoriteNumbers = new ArrayList<>();


    public SpeakTariff() {
    }


    public SpeakTariff(String id, String name, OperatorName operatorName, double payroll, double smsPrice,
                       CallPrice callPrice, int freeMinutesAbroad, List<Long> favoriteNumbers) {
        super(id, name, operatorName, payroll, smsPrice, callPrice);
        this.freeMinutesAbroad = freeMinutesAbroad;
        this.favoriteNumbers = favoriteNumbers;
    }

    public int getFreeMinutesAbroad() {
        return freeMinutesAbroad;
    }

    public void setFreeMinutesAbroad(int freeMinutesAbroad) {
        this.freeMinutesAbroad = freeMinutesAbroad;
    }

    public List<Long> getFavoriteNumbers() {
        return favoriteNumbers;
    }

    public void setFavoriteNumbers(List<Long> favoriteNumbers) {
        this.favoriteNumbers = favoriteNumbers;
    }

    public void addFavoriteNumber(Long number) {
        this.favoriteNumbers.add(number);
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
        SpeakTariff that = (SpeakTariff) o;
        return freeMinutesAbroad == that.freeMinutesAbroad &&
                Objects.equals(favoriteNumbers, that.favoriteNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), freeMinutesAbroad, favoriteNumbers);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", freeMinutesAbroad= " + freeMinutesAbroad +
                ", favoriteNumbers= " + favoriteNumbers + " ";
    }
}
