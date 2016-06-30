package com.pinelabs.novaservice;

public class IPineGiftServiceProxy implements com.pinelabs.novaservice.IPineGiftService {
  private String _endpoint = null;
  private com.pinelabs.novaservice.IPineGiftService iPineGiftService = null;
  
  public IPineGiftServiceProxy() {
    _initIPineGiftServiceProxy();
  }
  
  public IPineGiftServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIPineGiftServiceProxy();
  }
  
  private void _initIPineGiftServiceProxy() {
    try {
      iPineGiftService = (new com.pinelabs.novaservice.PineGiftWebServiceLocator()).getBasicHttpBinding_IPineGiftService();
      if (iPineGiftService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iPineGiftService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iPineGiftService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iPineGiftService != null)
      ((javax.xml.rpc.Stub)iPineGiftService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.pinelabs.novaservice.IPineGiftService getIPineGiftService() {
    if (iPineGiftService == null)
      _initIPineGiftServiceProxy();
    return iPineGiftService;
  }
  
  public org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GenerateVoucherResponse generateVoucher(org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GenerateVoucherRequest generateVoucherRequest) throws java.rmi.RemoteException{
    if (iPineGiftService == null)
      _initIPineGiftServiceProxy();
    return iPineGiftService.generateVoucher(generateVoucherRequest);
  }
  
  public org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetBrandConfigurationResponse getBrandConfiguration(org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetBrandConfigurationRequest brandConfigurationRequest) throws java.rmi.RemoteException{
    if (iPineGiftService == null)
      _initIPineGiftServiceProxy();
    return iPineGiftService.getBrandConfiguration(brandConfigurationRequest);
  }
  
  public org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetStatusOfGeneratedVouchersResponse getStatusOfGeneratedVouchers(org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetStatusOfGeneratedVouchersRequest getStatusOfGeneratedVouchersRequest) throws java.rmi.RemoteException{
    if (iPineGiftService == null)
      _initIPineGiftServiceProxy();
    return iPineGiftService.getStatusOfGeneratedVouchers(getStatusOfGeneratedVouchersRequest);
  }
  
  
}