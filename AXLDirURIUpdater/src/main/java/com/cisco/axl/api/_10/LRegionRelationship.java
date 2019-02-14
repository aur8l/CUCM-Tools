
package com.cisco.axl.api._10;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LRegionRelationship complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LRegionRelationship">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="regionName" type="{http://www.cisco.com/AXL/API/10.5}XFkType" minOccurs="0"/>
 *         &lt;element name="bandwidth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="videoBandwidth" type="{http://www.cisco.com/AXL/API/10.5}XInteger" minOccurs="0"/>
 *         &lt;element name="lossyNetwork" type="{http://www.cisco.com/AXL/API/10.5}XLossyNetwork" minOccurs="0"/>
 *         &lt;element name="codecPreference" type="{http://www.cisco.com/AXL/API/10.5}XFkType" minOccurs="0"/>
 *         &lt;element name="immersiveVideoBandwidth" type="{http://www.cisco.com/AXL/API/10.5}XInteger" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="uuid" type="{http://www.cisco.com/AXL/API/10.5}XUUID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LRegionRelationship", propOrder = {
    "regionName",
    "bandwidth",
    "videoBandwidth",
    "lossyNetwork",
    "codecPreference",
    "immersiveVideoBandwidth"
})
public class LRegionRelationship {

    protected XFkType regionName;
    protected String bandwidth;
    protected String videoBandwidth;
    protected String lossyNetwork;
    protected XFkType codecPreference;
    protected String immersiveVideoBandwidth;
    @XmlAttribute(name = "uuid")
    protected String uuid;

    /**
     * Gets the value of the regionName property.
     * 
     * @return
     *     possible object is
     *     {@link XFkType }
     *     
     */
    public XFkType getRegionName() {
        return regionName;
    }

    /**
     * Sets the value of the regionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link XFkType }
     *     
     */
    public void setRegionName(XFkType value) {
        this.regionName = value;
    }

    /**
     * Gets the value of the bandwidth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBandwidth() {
        return bandwidth;
    }

    /**
     * Sets the value of the bandwidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBandwidth(String value) {
        this.bandwidth = value;
    }

    /**
     * Gets the value of the videoBandwidth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVideoBandwidth() {
        return videoBandwidth;
    }

    /**
     * Sets the value of the videoBandwidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVideoBandwidth(String value) {
        this.videoBandwidth = value;
    }

    /**
     * Gets the value of the lossyNetwork property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLossyNetwork() {
        return lossyNetwork;
    }

    /**
     * Sets the value of the lossyNetwork property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLossyNetwork(String value) {
        this.lossyNetwork = value;
    }

    /**
     * Gets the value of the codecPreference property.
     * 
     * @return
     *     possible object is
     *     {@link XFkType }
     *     
     */
    public XFkType getCodecPreference() {
        return codecPreference;
    }

    /**
     * Sets the value of the codecPreference property.
     * 
     * @param value
     *     allowed object is
     *     {@link XFkType }
     *     
     */
    public void setCodecPreference(XFkType value) {
        this.codecPreference = value;
    }

    /**
     * Gets the value of the immersiveVideoBandwidth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImmersiveVideoBandwidth() {
        return immersiveVideoBandwidth;
    }

    /**
     * Sets the value of the immersiveVideoBandwidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImmersiveVideoBandwidth(String value) {
        this.immersiveVideoBandwidth = value;
    }

    /**
     * Gets the value of the uuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the value of the uuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

}
