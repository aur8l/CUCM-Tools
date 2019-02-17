
package com.cisco.axlapiservice;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "AXLAPIService", targetNamespace = "http://www.cisco.com/AXLAPIService/", wsdlLocation = "classpath:schema/current/AXLAPI.wsdl")
public class AXLAPIService
    extends Service
{

    private final static URL AXLAPISERVICE_WSDL_LOCATION;
    private final static WebServiceException AXLAPISERVICE_EXCEPTION;
    private final static QName AXLAPISERVICE_QNAME = new QName("http://www.cisco.com/AXLAPIService/", "AXLAPIService");
    static final Logger logger = LoggerFactory.getLogger(AXLAPIService.class);
    static {
        URL url = null;
        WebServiceException e = null;
        url = AXLAPIService.class.getClassLoader().getResource("schema/current/AXLAPI.wsdl");
//        try {
//            //url = new URL("classpath:schema/current/AXLAPI.wsdl");
//        	
//        } catch (MalformedURLException ex) {
//            e = new WebServiceException(ex);
//        }
        
        if (url == null) {
        	logger.error("Could not initialize service.");
        }
        
        AXLAPISERVICE_WSDL_LOCATION = url;
        AXLAPISERVICE_EXCEPTION = e;
    }

    public AXLAPIService() {
        super(__getWsdlLocation(), AXLAPISERVICE_QNAME);
    }

    public AXLAPIService(WebServiceFeature... features) {
        super(__getWsdlLocation(), AXLAPISERVICE_QNAME, features);
    }

    public AXLAPIService(URL wsdlLocation) {
        super(wsdlLocation, AXLAPISERVICE_QNAME);
    }

    public AXLAPIService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, AXLAPISERVICE_QNAME, features);
    }

    public AXLAPIService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AXLAPIService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns AXLPort
     */
    @WebEndpoint(name = "AXLPort")
    public AXLPort getAXLPort() {
        return super.getPort(new QName("http://www.cisco.com/AXLAPIService/", "AXLPort"), AXLPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AXLPort
     */
    @WebEndpoint(name = "AXLPort")
    public AXLPort getAXLPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.cisco.com/AXLAPIService/", "AXLPort"), AXLPort.class, features);
    }

    private static URL __getWsdlLocation() {
        if (AXLAPISERVICE_EXCEPTION!= null) {
            throw AXLAPISERVICE_EXCEPTION;
        }
        return AXLAPISERVICE_WSDL_LOCATION;
    }

}
