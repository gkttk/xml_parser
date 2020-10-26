package com.github.gkttk.eight.xml.model.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "operator-name-type")
@XmlEnum
public enum OperatorName {
    @XmlEnumValue("mts")
    MTS,
    @XmlEnumValue("velcom")
    VELCOM,
    @XmlEnumValue("life")
    LIFE

}
