package com.github.gkttk.eight.xml.model;

import com.github.gkttk.eight.xml.model.enums.OperatorName;

import javax.xml.bind.annotation.*;
import java.util.Objects;

@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlType(name = "tariff-type", propOrder = {
        "name",
        "operatorName",
        "payroll",
        "smsPrice",
        "callPrice"
})
@XmlSeeAlso({
        InternetTariff.class,
        SpeakTariff.class
})
public abstract class AbstractTariff {
    @XmlAttribute(name = "tariff-id", required = true)
    @XmlSchemaType(name = "tariff-id-type")
    @XmlID
    protected String id;

    @XmlElement(name = "tariff-name", type = String.class, required = true, namespace = "tariff.schema")
    @XmlSchemaType(name = "tariff-name-type")
    protected String name;

    @XmlElement(name = "operator-name", type = OperatorName.class, required = true, namespace = "tariff.schema")
    @XmlSchemaType(name = "string")
    protected OperatorName operatorName;

    @XmlElement(name = "payroll", type = Double.class, required = true, namespace = "tariff.schema")
    @XmlSchemaType(name = "decimal-price-type")
    protected double payroll;

    @XmlSchemaType(name = "decimal-price-type")
    @XmlElement(name = "sms-price", type = Double.class, required = true, namespace = "tariff.schema")
    protected double smsPrice;

    @XmlSchemaType(name = "call-price-type")
    @XmlElement(name = "call-price", type = CallPrice.class, required = true, namespace = "tariff.schema")
    protected CallPrice callPrice;


    public AbstractTariff() {
    }

    public AbstractTariff(String id, String name, OperatorName operatorName, double payroll, double smsPrice, CallPrice callPrice) {
        this.id = id;
        this.name = name;
        this.operatorName = operatorName;
        this.payroll = payroll;
        this.smsPrice = smsPrice;
        this.callPrice = callPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OperatorName getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(OperatorName operatorName) {
        this.operatorName = operatorName;
    }

    public double getPayroll() {
        return payroll;
    }

    public void setPayroll(double payroll) {
        this.payroll = payroll;
    }

    public double getSmsPrice() {
        return smsPrice;
    }

    public void setSmsPrice(double smsPrice) {
        this.smsPrice = smsPrice;
    }

    public CallPrice getCallPrice() {
        return callPrice;
    }

    public void setCallPrice(CallPrice callPrice) {
        this.callPrice = callPrice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractTariff that = (AbstractTariff) o;
        return Double.compare(that.payroll, payroll) == 0 &&
                Double.compare(that.smsPrice, smsPrice) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                operatorName == that.operatorName &&
                Objects.equals(callPrice, that.callPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, operatorName, payroll, smsPrice, callPrice);
    }

    @Override
    public String toString() {
        return "Tariff: " + this.getClass().getName() +
                " id= " + id +
                ", name= " + name +
                ", operatorName= " + operatorName +
                ", payroll= " + payroll +
                ", smsPrice= " + smsPrice +
                ", callPrice= " + callPrice + " ";
    }
}
