/**
 * EPin_ReloadLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class EPin_ReloadLocator extends org.apache.axis.client.Service implements org.tempuri.EPin_Reload {

    public EPin_ReloadLocator() {
    }


    public EPin_ReloadLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EPin_ReloadLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EPin_ReloadSoap
    private java.lang.String EPin_ReloadSoap_address = "http://175.139.151.69:8099/IAT/EPin_Reload.asmx";

    public java.lang.String getEPin_ReloadSoapAddress() {
        return EPin_ReloadSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EPin_ReloadSoapWSDDServiceName = "EPin_ReloadSoap";

    public java.lang.String getEPin_ReloadSoapWSDDServiceName() {
        return EPin_ReloadSoapWSDDServiceName;
    }

    public void setEPin_ReloadSoapWSDDServiceName(java.lang.String name) {
        EPin_ReloadSoapWSDDServiceName = name;
    }

    public org.tempuri.EPin_ReloadSoap getEPin_ReloadSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EPin_ReloadSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEPin_ReloadSoap(endpoint);
    }

    public org.tempuri.EPin_ReloadSoap getEPin_ReloadSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.EPin_ReloadSoapStub _stub = new org.tempuri.EPin_ReloadSoapStub(portAddress, this);
            _stub.setPortName(getEPin_ReloadSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEPin_ReloadSoapEndpointAddress(java.lang.String address) {
        EPin_ReloadSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.EPin_ReloadSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.EPin_ReloadSoapStub _stub = new org.tempuri.EPin_ReloadSoapStub(new java.net.URL(EPin_ReloadSoap_address), this);
                _stub.setPortName(getEPin_ReloadSoapWSDDServiceName());
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
        if ("EPin_ReloadSoap".equals(inputPortName)) {
            return getEPin_ReloadSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "EPin_Reload");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "EPin_ReloadSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EPin_ReloadSoap".equals(portName)) {
            setEPin_ReloadSoapEndpointAddress(address);
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
