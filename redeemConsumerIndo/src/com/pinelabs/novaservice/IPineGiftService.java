/**
 * IPineGiftService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.pinelabs.novaservice;

public interface IPineGiftService extends java.rmi.Remote {
    public org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GenerateVoucherResponse generateVoucher(org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GenerateVoucherRequest generateVoucherRequest) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetBrandConfigurationResponse getBrandConfiguration(org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetBrandConfigurationRequest brandConfigurationRequest) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetStatusOfGeneratedVouchersResponse getStatusOfGeneratedVouchers(org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetStatusOfGeneratedVouchersRequest getStatusOfGeneratedVouchersRequest) throws java.rmi.RemoteException;
}
