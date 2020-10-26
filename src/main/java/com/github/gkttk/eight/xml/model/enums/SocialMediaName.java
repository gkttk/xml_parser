package com.github.gkttk.eight.xml.model.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "free-social-media-type")
@XmlEnum
public enum SocialMediaName {
    @XmlEnumValue("vk")
    VK,
    @XmlEnumValue("instagram")
    INSTAGRAM,
    @XmlEnumValue("twitter")
    TWITTER

}
