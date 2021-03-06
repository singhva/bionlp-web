//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.30 at 11:20:27 AM EST 
//


package com.bionlp.ctgov;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for agency_class_enum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="agency_class_enum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NIH"/>
 *     &lt;enumeration value="U.S. Fed"/>
 *     &lt;enumeration value="Industry"/>
 *     &lt;enumeration value="Other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "agency_class_enum")
@XmlEnum
public enum AgencyClassEnum {

    NIH("NIH"),
    @XmlEnumValue("U.S. Fed")
    U_S_FED("U.S. Fed"),
    @XmlEnumValue("Industry")
    INDUSTRY("Industry"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    AgencyClassEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AgencyClassEnum fromValue(String v) {
        for (AgencyClassEnum c: AgencyClassEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
