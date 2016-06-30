package org.tempuri;

public class EPin_ReloadSoapProxy implements org.tempuri.EPin_ReloadSoap {
  private String _endpoint = null;
  private org.tempuri.EPin_ReloadSoap ePin_ReloadSoap = null;
  
  public EPin_ReloadSoapProxy() {
    _initEPin_ReloadSoapProxy();
  }
  
  public EPin_ReloadSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initEPin_ReloadSoapProxy();
  }
  
  private void _initEPin_ReloadSoapProxy() {
    try {
      ePin_ReloadSoap = (new org.tempuri.EPin_ReloadLocator()).getEPin_ReloadSoap();
      if (ePin_ReloadSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)ePin_ReloadSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)ePin_ReloadSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (ePin_ReloadSoap != null)
      ((javax.xml.rpc.Stub)ePin_ReloadSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.EPin_ReloadSoap getEPin_ReloadSoap() {
    if (ePin_ReloadSoap == null)
      _initEPin_ReloadSoapProxy();
    return ePin_ReloadSoap;
  }
  
  public java.lang.String request_Reload(java.lang.String sourceNo, java.lang.String destNo, java.lang.String prodCode, int deno, java.lang.String userID, java.lang.String password, java.lang.String transID) throws java.rmi.RemoteException{
    if (ePin_ReloadSoap == null)
      _initEPin_ReloadSoapProxy();
    return ePin_ReloadSoap.request_Reload(sourceNo, destNo, prodCode, deno, userID, password, transID);
  }
  
  public org.tempuri.Request_ReloadAmount_NewResponseRequest_ReloadAmount_NewResult request_ReloadAmount_New(java.lang.String sourceNo, java.lang.String destNo, java.lang.String prodCode, java.lang.String deno, java.lang.String userID, java.lang.String password, java.lang.String transID) throws java.rmi.RemoteException{
    if (ePin_ReloadSoap == null)
      _initEPin_ReloadSoapProxy();
    return ePin_ReloadSoap.request_ReloadAmount_New(sourceNo, destNo, prodCode, deno, userID, password, transID);
  }
  
  public int ping() throws java.rmi.RemoteException{
    if (ePin_ReloadSoap == null)
      _initEPin_ReloadSoapProxy();
    return ePin_ReloadSoap.ping();
  }
  
  public java.lang.String transaction_Inquiry(java.lang.String transID, java.lang.String userID, java.lang.String password) throws java.rmi.RemoteException{
    if (ePin_ReloadSoap == null)
      _initEPin_ReloadSoapProxy();
    return ePin_ReloadSoap.transaction_Inquiry(transID, userID, password);
  }
  
  public org.tempuri.Transaction_Inquiry_DetailsResponseTransaction_Inquiry_DetailsResult transaction_Inquiry_Details(java.lang.String transID, java.lang.String userID, java.lang.String password) throws java.rmi.RemoteException{
    if (ePin_ReloadSoap == null)
      _initEPin_ReloadSoapProxy();
    return ePin_ReloadSoap.transaction_Inquiry_Details(transID, userID, password);
  }
  
  public org.tempuri.EWallet_InquiryResponseEWallet_InquiryResult EWallet_Inquiry(java.lang.String transID, java.lang.String userID, java.lang.String password) throws java.rmi.RemoteException{
    if (ePin_ReloadSoap == null)
      _initEPin_ReloadSoapProxy();
    return ePin_ReloadSoap.EWallet_Inquiry(transID, userID, password);
  }
  
  public org.tempuri.Product_Price_InquiryResponseProduct_Price_InquiryResult product_Price_Inquiry(java.lang.String transID, java.lang.String userID, java.lang.String password, java.lang.String prodCode, java.lang.String deno) throws java.rmi.RemoteException{
    if (ePin_ReloadSoap == null)
      _initEPin_ReloadSoapProxy();
    return ePin_ReloadSoap.product_Price_Inquiry(transID, userID, password, prodCode, deno);
  }
  
  public java.lang.String request_Reload_New(java.lang.String sourceNo, java.lang.String destNo, java.lang.String prodCode, java.lang.String deno, java.lang.String userID, java.lang.String password, java.lang.String transID) throws java.rmi.RemoteException{
    if (ePin_ReloadSoap == null)
      _initEPin_ReloadSoapProxy();
    return ePin_ReloadSoap.request_Reload_New(sourceNo, destNo, prodCode, deno, userID, password, transID);
  }
  
  public org.tempuri.Request_ReloadAmountResponseRequest_ReloadAmountResult request_ReloadAmount(java.lang.String sourceNo, java.lang.String destNo, java.lang.String prodCode, int deno, java.lang.String userID, java.lang.String password, java.lang.String transID) throws java.rmi.RemoteException{
    if (ePin_ReloadSoap == null)
      _initEPin_ReloadSoapProxy();
    return ePin_ReloadSoap.request_ReloadAmount(sourceNo, destNo, prodCode, deno, userID, password, transID);
  }
  
  public java.lang.String request_Reload_Whitelist(java.lang.String sourceNo, java.lang.String destNo, java.lang.String prodCode, int deno, java.lang.String userID, java.lang.String password, java.lang.String transID) throws java.rmi.RemoteException{
    if (ePin_ReloadSoap == null)
      _initEPin_ReloadSoapProxy();
    return ePin_ReloadSoap.request_Reload_Whitelist(sourceNo, destNo, prodCode, deno, userID, password, transID);
  }
  
  
}