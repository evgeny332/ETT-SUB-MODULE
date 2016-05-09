/**
 * Transaction_InquiryResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class Transaction_InquiryResponse  implements java.io.Serializable {
    private java.lang.String transaction_InquiryResult;

    public Transaction_InquiryResponse() {
    }

    public Transaction_InquiryResponse(
           java.lang.String transaction_InquiryResult) {
           this.transaction_InquiryResult = transaction_InquiryResult;
    }


    /**
     * Gets the transaction_InquiryResult value for this Transaction_InquiryResponse.
     * 
     * @return transaction_InquiryResult
     */
    public java.lang.String getTransaction_InquiryResult() {
        return transaction_InquiryResult;
    }


    /**
     * Sets the transaction_InquiryResult value for this Transaction_InquiryResponse.
     * 
     * @param transaction_InquiryResult
     */
    public void setTransaction_InquiryResult(java.lang.String transaction_InquiryResult) {
        this.transaction_InquiryResult = transaction_InquiryResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Transaction_InquiryResponse)) return false;
        Transaction_InquiryResponse other = (Transaction_InquiryResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.transaction_InquiryResult==null && other.getTransaction_InquiryResult()==null) || 
             (this.transaction_InquiryResult!=null &&
              this.transaction_InquiryResult.equals(other.getTransaction_InquiryResult())));
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
        if (getTransaction_InquiryResult() != null) {
            _hashCode += getTransaction_InquiryResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Transaction_InquiryResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Transaction_InquiryResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transaction_InquiryResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Transaction_InquiryResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
