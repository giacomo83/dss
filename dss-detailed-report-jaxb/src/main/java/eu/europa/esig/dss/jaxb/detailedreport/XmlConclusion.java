//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.07 at 08:23:16 PM CEST 
//


package eu.europa.esig.dss.jaxb.detailedreport;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import eu.europa.esig.dss.validation.policy.rules.Indication;
import eu.europa.esig.dss.validation.policy.rules.SubIndication;


/**
 * <p>Java class for Conclusion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Conclusion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Indication" type="{http://dss.esig.europa.eu/validation/detailed-report}Indication"/>
 *         &lt;element name="SubIndication" type="{http://dss.esig.europa.eu/validation/detailed-report}SubIndication" minOccurs="0"/>
 *         &lt;element name="Error" type="{http://dss.esig.europa.eu/validation/detailed-report}Error" minOccurs="0"/>
 *         &lt;element name="Info" type="{http://dss.esig.europa.eu/validation/detailed-report}Info" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Conclusion", propOrder = {
    "indication",
    "subIndication",
    "error",
    "info"
})
public class XmlConclusion {

    @XmlElement(name = "Indication", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter2 .class)
    protected Indication indication;
    @XmlElement(name = "SubIndication", type = String.class)
    @XmlJavaTypeAdapter(Adapter3 .class)
    protected SubIndication subIndication;
    @XmlElement(name = "Error")
    protected XmlError error;
    @XmlElement(name = "Info")
    protected List<XmlInfo> info;

    /**
     * Gets the value of the indication property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Indication getIndication() {
        return indication;
    }

    /**
     * Sets the value of the indication property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndication(Indication value) {
        this.indication = value;
    }

    /**
     * Gets the value of the subIndication property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public SubIndication getSubIndication() {
        return subIndication;
    }

    /**
     * Sets the value of the subIndication property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubIndication(SubIndication value) {
        this.subIndication = value;
    }

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link XmlError }
     *     
     */
    public XmlError getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlError }
     *     
     */
    public void setError(XmlError value) {
        this.error = value;
    }

    /**
     * Gets the value of the info property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the info property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XmlInfo }
     * 
     * 
     */
    public List<XmlInfo> getInfo() {
        if (info == null) {
            info = new ArrayList<XmlInfo>();
        }
        return this.info;
    }

}
