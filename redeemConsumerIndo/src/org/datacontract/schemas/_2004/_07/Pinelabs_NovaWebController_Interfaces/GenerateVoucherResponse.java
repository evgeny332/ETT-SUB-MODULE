/**
 * GenerateVoucherResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces;

public class GenerateVoucherResponse  implements java.io.Serializable {
    private java.lang.String responseCode;

    private java.lang.String responseMessage;

    private org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GeneratedVoucherDetails[] responseVoucherList;

    public GenerateVoucherResponse() {
    }

    public GenerateVoucherResponse(
           java.lang.String responseCode,
           java.lang.String responseMessage,
           org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GeneratedVoucherDetails[] responseVoucherList) {
           this.responseCode = responseCode;
           this.responseMessage = responseMessage;
           this.responseVoucherList = responseVoucherList;
    }


    /**
     * Gets the responseCode value for this GenerateVoucherResponse.
     * 
     * @return responseCode
     */
    public java.lang.String getResponseCode() {
        return responseCode;
    }


    /**
     * Sets the responseCode value for this GenerateVoucherResponse.
     * 
     * @param responseCode
     */
    public void setResponseCode(java.lang.String responseCode) {
        this.responseCode = responseCode;
    }


    /**
     * Gets the responseMessage value for this GenerateVoucherResponse.
     * 
     * @return responseMessage
     */
    public java.lang.String getResponseMessage() {
        return responseMessage;
    }


    /**
     * Sets the responseMessage value for this GenerateVoucherResponse.
     * 
     * @param responseMessage
     */
    public void setResponseMessage(java.lang.String responseMessage) {
        this.responseMessage = responseMessage;
    }


    /**
     * Gets the responseVoucherList value for this GenerateVoucherResponse.
     * 
     * @return responseVoucherList
     */
    public org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GeneratedVoucherDetails[] getResponseVoucherList() {
        return responseVoucherList;
    }


    /**
     * Sets the responseVoucherList value for this GenerateVoucherResponse.
     * 
     * @param responseVoucherList
     */
    public void setResponseVoucherList(org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GeneratedVoucherDetails[] responseVoucherList) {
        this.responseVoucherList = responseVoucherList;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GenerateVoucherResponse)) return false;
        GenerateVoucherResponse other = (GenerateVoucherResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.responseCode==null && other.getResponseCode()==null) || 
             (this.responseCode!=null &&
              this.responseCode.equals(other.getResponseCode()))) &&
            ((this.responseMessage==null && other.getResponseMessage()==null) || 
             (this.responseMessage!=null &&
              this.responseMessage.equals(other.getResponseMessage()))) &&
            ((this.responseVoucherList==null && other.getResponseVoucherList()==null) || 
             (this.responseVoucherList!=null &&
              java.util.Arrays.equals(this.responseVoucherList, other.getResponseVoucherList())));
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
        if (getResponseCode() != null) {
            _hashCode += getResponseCode().hashCode();
        }
        if (getResponseMessage() != null) {
            _hashCode += getResponseMessage().hashCode();
        }
        if (getResponseVoucherList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getResponseVoucherList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getResponseVoucherList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GenerateVoucherResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GenerateVoucherResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseVoucherList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "ResponseVoucherList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GeneratedVoucherDetails"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GeneratedVoucherDetails"));
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
