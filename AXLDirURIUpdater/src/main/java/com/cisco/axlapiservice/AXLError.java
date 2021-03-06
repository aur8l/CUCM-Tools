
package com.cisco.axlapiservice;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "axlError", targetNamespace = "http://www.cisco.com/AXL/API/10.5")
public class AXLError
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private com.cisco.axl.api._10.AXLError faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public AXLError(String message, com.cisco.axl.api._10.AXLError faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public AXLError(String message, com.cisco.axl.api._10.AXLError faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.cisco.axl.api._10.AXLError
     */
    public com.cisco.axl.api._10.AXLError getFaultInfo() {
        return faultInfo;
    }

}
