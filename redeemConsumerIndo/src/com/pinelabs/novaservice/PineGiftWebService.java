/**
 * PineGiftWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.pinelabs.novaservice;

public interface PineGiftWebService extends javax.xml.rpc.Service {
    public java.lang.String getBasicHttpBinding_IPineGiftServiceAddress();

    public com.pinelabs.novaservice.IPineGiftService getBasicHttpBinding_IPineGiftService() throws javax.xml.rpc.ServiceException;

    public com.pinelabs.novaservice.IPineGiftService getBasicHttpBinding_IPineGiftService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
