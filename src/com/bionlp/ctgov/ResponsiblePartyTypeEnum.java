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
 * <p>Java class for responsible_party_type_enum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="responsible_party_type_enum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Sponsor"/>
 *     &lt;enumeration value="Principal Investigator"/>
 *     &lt;enumeration value="Sponsor-Investigator"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "responsible_party_type_enum")
@XmlEnum
public enum ResponsiblePartyTypeEnum {

    @XmlEnumValue("Sponsor")
    SPONSOR("Sponsor"),
    @XmlEnumValue("Principal Investigator")
    PRINCIPAL_INVESTIGATOR("Principal Investigator"),
    @XmlEnumValue("Sponsor-Investigator")
    SPONSOR_INVESTIGATOR("Sponsor-Investigator");
    private final String value;

    ResponsiblePartyTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ResponsiblePartyTypeEnum fromValue(String v) {
        for (ResponsiblePartyTypeEnum c: ResponsiblePartyTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
