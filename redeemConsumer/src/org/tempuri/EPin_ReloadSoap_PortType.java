/**
 * EPin_ReloadSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface EPin_ReloadSoap_PortType extends java.rmi.Remote {
    public java.lang.String request_Reload(java.lang.String sourceNo, java.lang.String destNo, java.lang.String prodCode, int deno, java.lang.String userID, java.lang.String password, java.lang.String transID) throws java.rmi.RemoteException;
    public Request_ReloadAmount_NewResponseRequest_ReloadAmount_NewResult request_ReloadAmount_New(java.lang.String sourceNo, java.lang.String destNo, java.lang.String prodCode, java.lang.String deno, java.lang.String userID, java.lang.String password, java.lang.String transID) throws java.rmi.RemoteException;
    public int ping() throws java.rmi.RemoteException;
    public java.lang.String transaction_Inquiry(java.lang.String transID, java.lang.String userID, java.lang.String password) throws java.rmi.RemoteException;
    public Transaction_Inquiry_DetailsResponseTransaction_Inquiry_DetailsResult transaction_Inquiry_Details(java.lang.String transID, java.lang.String userID, java.lang.String password) throws java.rmi.RemoteException;
    public EWallet_InquiryResponseEWallet_InquiryResult EWallet_Inquiry(java.lang.String transID, java.lang.String userID, java.lang.String password) throws java.rmi.RemoteException;
    public Product_Price_InquiryResponseProduct_Price_InquiryResult product_Price_Inquiry(java.lang.String transID, java.lang.String userID, java.lang.String password, java.lang.String prodCode, java.lang.String deno) throws java.rmi.RemoteException;
    public java.lang.String request_Reload_New(java.lang.String sourceNo, java.lang.String destNo, java.lang.String prodCode, java.lang.String deno, java.lang.String userID, java.lang.String password, java.lang.String transID) throws java.rmi.RemoteException;
    public Request_ReloadAmountResponseRequest_ReloadAmountResult request_ReloadAmount(java.lang.String sourceNo, java.lang.String destNo, java.lang.String prodCode, int deno, java.lang.String userID, java.lang.String password, java.lang.String transID) throws java.rmi.RemoteException;
    public java.lang.String request_Reload_Whitelist(java.lang.String sourceNo, java.lang.String destNo, java.lang.String prodCode, int deno, java.lang.String userID, java.lang.String password, java.lang.String transID) throws java.rmi.RemoteException;
}
