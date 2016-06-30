/**
 * GetStatusOfGeneratedVouchersResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces;

public class GetStatusOfGeneratedVouchersResponse  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GeneratedVoucherDetails[] reponseVoucherList;

    private java.lang.String responseCode;

    private java.lang.String responseMessage;

    public GetStatusOfGeneratedVouchersResponse() {
    }

    public GetStatusOfGeneratedVouchersResponse(
           org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GeneratedVoucherDetails[] reponseVoucherList,
           java.lang.String responseCode,
           java.lang.String responseMessage) {
           this.reponseVoucherList = reponseVoucherList;
           this.responseCode = responseCode;
           this.responseMessage = responseMessage;
    }


    /**
     * Gets the reponseVoucherList value for this GetStatusOfGeneratedVouchersResponse.
     * 
     * @return reponseVoucherList
     */
    public org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GeneratedVoucherDetails[] getReponseVoucherList() {
        return reponseVoucherList;
    }


    /**
     * Sets the reponseVoucherList value for this GetStatusOfGeneratedVouchersResponse.
     * 
     * @param reponseVoucherList
     */
    public void setReponseVoucherList(org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GeneratedVoucherDetails[] reponseVoucherList) {
        this.reponseVoucherList = reponseVoucherList;
    }


    /**
     * Gets the responseCode value for this GetStatusOfGeneratedVouchersResponse.
     * 
     * @return responseCode
     */
    public java.lang.String getResponseCode() {
        return responseCode;
    }


    /**
     * Sets the responseCode value for this GetStatusOfGeneratedVouchersResponse.
     * 
     * @param responseCode
     */
    public void setResponseCode(java.lang.String responseCode) {
        this.responseCode = responseCode;
    }


    /**
     * Gets the responseMessage value for this GetStatusOfGeneratedVouchersResponse.
     * 
     * @return responseMessage
     */
    public java.lang.String getResponseMessage() {
        return responseMessage;
    }


    /**
     * Sets the responseMessage value for this GetStatusOfGeneratedVouchersResponse.
     * 
     * @param responseMessage
     */
    public void setResponseMessage(java.lang.String responseMessage) {
        this.responseMessage = responseMessage;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetStatusOfGeneratedVouchersResponse)) return false;
        GetStatusOfGeneratedVouchersResponse other = (GetStatusOfGeneratedVouchersResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reponseVoucherList==null && other.getReponseVoucherList()==null) || 
             (this.reponseVoucherList!=null &&
              java.util.Arrays.equals(this.reponseVoucherList, other.getReponseVoucherList()))) &&
            ((this.responseCode==null && other.getResponseCode()==null) || 
             (this.responseCode!=null &&
              this.responseCode.equals(other.getResponseCode()))) &&
            ((this.responseMessage==null && other.getResponseMessage()==null) || 
             (this.responseMessage!=null &&
              this.responseMessage.equals(other.getResponseMessage())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getReponseVoucherList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getReponseVoucherList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getReponseVoucherList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getResponseCode() != null) {
            _hashCode += getResponseCode().hashCode();
        }
        if (getResponseMessage() != null) {
            _hashCode += getResponseMessage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetStatusOfGeneratedVouchersResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GetStatusOfGeneratedVouchersResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reponseVoucherList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "ReponseVoucherList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GeneratedVoucherDetails"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GeneratedVoucherDetails"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "ResponseCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "ResponseMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
