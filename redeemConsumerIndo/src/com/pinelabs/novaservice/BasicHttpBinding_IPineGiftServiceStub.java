/**
 * BasicHttpBinding_IPineGiftServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.pinelabs.novaservice;

public class BasicHttpBinding_IPineGiftServiceStub extends org.apache.axis.client.Stub implements com.pinelabs.novaservice.IPineGiftService {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[3];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GenerateVoucher");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("https://novaservice.pinelabs.com", "generateVoucherRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GenerateVoucherRequest"), org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GenerateVoucherRequest.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GenerateVoucherResponse"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GenerateVoucherResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("https://novaservice.pinelabs.com", "GenerateVoucherResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetBrandConfiguration");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("https://novaservice.pinelabs.com", "brandConfigurationRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GetBrandConfigurationRequest"), org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetBrandConfigurationRequest.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GetBrandConfigurationResponse"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetBrandConfigurationResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("https://novaservice.pinelabs.com", "GetBrandConfigurationResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetStatusOfGeneratedVouchers");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("https://novaservice.pinelabs.com", "getStatusOfGeneratedVouchersRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GetStatusOfGeneratedVouchersRequest"), org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetStatusOfGeneratedVouchersRequest.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GetStatusOfGeneratedVouchersResponse"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetStatusOfGeneratedVouchersResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("https://novaservice.pinelabs.com", "GetStatusOfGeneratedVouchersResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

    }

    public BasicHttpBinding_IPineGiftServiceStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public BasicHttpBinding_IPineGiftServiceStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public BasicHttpBinding_IPineGiftServiceStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "ArrayOfBrand");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.Brand[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Brand");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Brand");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "ArrayOfDenomination");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.Denomination[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Denomination");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Denomination");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "ArrayOfGeneratedVoucherDetails");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GeneratedVoucherDetails[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GeneratedVoucherDetails");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GeneratedVoucherDetails");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "ArrayOfScheme");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.Scheme[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Scheme");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Scheme");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "ArrayOfVoucherRequest");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.VoucherRequest[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "VoucherRequest");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "VoucherRequest");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Brand");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.Brand.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Denomination");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.Denomination.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GeneratedVoucherDetails");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GeneratedVoucherDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GenerateVoucherRequest");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GenerateVoucherRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GenerateVoucherResponse");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GenerateVoucherResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GetBrandConfigurationRequest");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetBrandConfigurationRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GetBrandConfigurationResponse");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetBrandConfigurationResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GetStatusOfGeneratedVouchersRequest");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetStatusOfGeneratedVouchersRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GetStatusOfGeneratedVouchersResponse");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetStatusOfGeneratedVouchersResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Scheme");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.Scheme.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "VoucherRequest");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.VoucherRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GenerateVoucherResponse generateVoucher(org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GenerateVoucherRequest generateVoucherRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("https://novaservice.pinelabs.com/IPineGiftService/GenerateVoucher");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://novaservice.pinelabs.com", "GenerateVoucher"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {generateVoucherRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GenerateVoucherResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GenerateVoucherResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GenerateVoucherResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetBrandConfigurationResponse getBrandConfiguration(org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetBrandConfigurationRequest brandConfigurationRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("https://novaservice.pinelabs.com/IPineGiftService/GetBrandConfiguration");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://novaservice.pinelabs.com", "GetBrandConfiguration"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {brandConfigurationRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetBrandConfigurationResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetBrandConfigurationResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetBrandConfigurationResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetStatusOfGeneratedVouchersResponse getStatusOfGeneratedVouchers(org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetStatusOfGeneratedVouchersRequest getStatusOfGeneratedVouchersRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("https://novaservice.pinelabs.com/IPineGiftService/GetStatusOfGeneratedVouchers");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("https://novaservice.pinelabs.com", "GetStatusOfGeneratedVouchers"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {getStatusOfGeneratedVouchersRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetStatusOfGeneratedVouchersResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetStatusOfGeneratedVouchersResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GetStatusOfGeneratedVouchersResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
