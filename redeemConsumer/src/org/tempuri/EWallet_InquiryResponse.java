/**
 * EWallet_InquiryResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class EWallet_InquiryResponse  implements java.io.Serializable {
    private org.tempuri.EWallet_InquiryResponseEWallet_InquiryResult EWallet_InquiryResult;

    public EWallet_InquiryResponse() {
    }

    public EWallet_InquiryResponse(
           org.tempuri.EWallet_InquiryResponseEWallet_InquiryResult EWallet_InquiryResult) {
           this.EWallet_InquiryResult = EWallet_InquiryResult;
    }


    /**
     * Gets the EWallet_InquiryResult value for this EWallet_InquiryResponse.
     * 
     * @return EWallet_InquiryResult
     */
    public org.tempuri.EWallet_InquiryResponseEWallet_InquiryResult getEWallet_InquiryResult() {
        return EWallet_InquiryResult;
    }


    /**
     * Sets the EWallet_InquiryResult value for this EWallet_InquiryResponse.
     * 
     * @param EWallet_InquiryResult
     */
    public void setEWallet_InquiryResult(org.tempuri.EWallet_InquiryResponseEWallet_InquiryResult EWallet_InquiryResult) {
        this.EWallet_InquiryResult = EWallet_InquiryResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EWallet_InquiryResponse)) return false;
        EWallet_InquiryResponse other = (EWallet_InquiryResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.EWallet_InquiryResult==null && other.getEWallet_InquiryResult()==null) || 
             (this.EWallet_InquiryResult!=null &&
              this.EWallet_InquiryResult.equals(other.getEWallet_InquiryResult())));
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
        if (getEWallet_InquiryResult() != null) {
            _hashCode += getEWallet_InquiryResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EWallet_InquiryResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">EWallet_InquiryResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EWallet_InquiryResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "EWallet_InquiryResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">>EWallet_InquiryResponse>EWallet_InquiryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
