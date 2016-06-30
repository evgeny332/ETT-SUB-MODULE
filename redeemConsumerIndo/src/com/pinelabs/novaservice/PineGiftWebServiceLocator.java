/**
 * PineGiftWebServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.pinelabs.novaservice;

public class PineGiftWebServiceLocator extends org.apache.axis.client.Service implements com.pinelabs.novaservice.PineGiftWebService {

    public PineGiftWebServiceLocator() {
    }


    public PineGiftWebServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PineGiftWebServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IPineGiftService
    private java.lang.String BasicHttpBinding_IPineGiftService_address = "https://novaserviceuat.pinelabs.com/Nova/PineGiftWebService.svc";

    public java.lang.String getBasicHttpBinding_IPineGiftServiceAddress() {
        return BasicHttpBinding_IPineGiftService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_IPineGiftServiceWSDDServiceName = "BasicHttpBinding_IPineGiftService";

    public java.lang.String getBasicHttpBinding_IPineGiftServiceWSDDServiceName() {
        return BasicHttpBinding_IPineGiftServiceWSDDServiceName;
    }

    public void setBasicHttpBinding_IPineGiftServiceWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_IPineGiftServiceWSDDServiceName = name;
    }

    public com.pinelabs.novaservice.IPineGiftService getBasicHttpBinding_IPineGiftService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IPineGiftService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IPineGiftService(endpoint);
    }

    public com.pinelabs.novaservice.IPineGiftService getBasicHttpBinding_IPineGiftService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.pinelabs.novaservice.BasicHttpBinding_IPineGiftServiceStub _stub = new com.pinelabs.novaservice.BasicHttpBinding_IPineGiftServiceStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IPineGiftServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IPineGiftServiceEndpointAddress(java.lang.String address) {
        BasicHttpBinding_IPineGiftService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.pinelabs.novaservice.IPineGiftService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.pinelabs.novaservice.BasicHttpBinding_IPineGiftServiceStub _stub = new com.pinelabs.novaservice.BasicHttpBinding_IPineGiftServiceStub(new java.net.URL(BasicHttpBinding_IPineGiftService_address), this);
                _stub.setPortName(getBasicHttpBinding_IPineGiftServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BasicHttpBinding_IPineGiftService".equals(inputPortName)) {
            return getBasicHttpBinding_IPineGiftService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://novaservice.pinelabs.com", "PineGiftWebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://novaservice.pinelabs.com", "BasicHttpBinding_IPineGiftService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IPineGiftService".equals(portName)) {
            setBasicHttpBinding_IPineGiftServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
