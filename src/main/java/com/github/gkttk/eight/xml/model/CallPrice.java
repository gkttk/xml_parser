package com.github.gkttk.eight.xml.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "call-price-type", propOrder = {
        "internalPrice",
        "externalPrice",
        "landLinePrice"
})
public class CallPrice {
    @XmlElement(name = "internal-calls", type = Double.class, required = true, namespace = "tariff.schema")
    private double internalPrice;
    @XmlElement(name = "external-calls", type = Double.class, required = true, namespace = "tariff.schema")
    private double externalPrice;
    @XmlElement(name = "landline-calls", type = Double.class, required = true, namespace = "tariff.schema")
    private double landLinePrice;


    public CallPrice() {
    }

    public CallPrice(double internalPrice, double externalPrice, double landLinePrice) {
        this.internalPrice = internalPrice;
        this.externalPrice = externalPrice;
        this.landLinePrice = landLinePrice;
    }


    public void setInternalPrice(double internalPrice) {
        this.internalPrice = internalPrice;
    }

    public void setExternalPrice(double externalPrice) {
        this.externalPrice = externalPrice;
    }

    public void setLandLinePrice(double landLinePrice) {
        this.landLinePrice = landLinePrice;
    }

    public double getInternalPrice() {
        return internalPrice;
    }

    public double getExternalPrice() {
        return externalPrice;
    }

    public double getLandLinePrice() {
        return landLinePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CallPrice callPrice = (CallPrice) o;
        return Double.compare(callPrice.internalPrice, internalPrice) == 0 &&
                Double.compare(callPrice.externalPrice, externalPrice) == 0 &&
                Double.compare(callPrice.landLinePrice, landLinePrice) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalPrice, externalPrice, landLinePrice);
    }

    @Override
    public String toString() {
        return "internalPrice=" + internalPrice +
                ", externalPrice=" + externalPrice +
                ", landLinePrice=" + landLinePrice;
    }
}
