/**
 * Transaction_Inquiry_DetailsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class Transaction_Inquiry_DetailsResponse  implements java.io.Serializable {
    private org.tempuri.Transaction_Inquiry_DetailsResponseTransaction_Inquiry_DetailsResult transaction_Inquiry_DetailsResult;

    public Transaction_Inquiry_DetailsResponse() {
    }

    public Transaction_Inquiry_DetailsResponse(
           org.tempuri.Transaction_Inquiry_DetailsResponseTransaction_Inquiry_DetailsResult transaction_Inquiry_DetailsResult) {
           this.transaction_Inquiry_DetailsResult = transaction_Inquiry_DetailsResult;
    }


    /**
     * Gets the transaction_Inquiry_DetailsResult value for this Transaction_Inquiry_DetailsResponse.
     * 
     * @return transaction_Inquiry_DetailsResult
     */
    public org.tempuri.Transaction_Inquiry_DetailsResponseTransaction_Inquiry_DetailsResult getTransaction_Inquiry_DetailsResult() {
        return transaction_Inquiry_DetailsResult;
    }


    /**
     * Sets the transaction_Inquiry_DetailsResult value for this Transaction_Inquiry_DetailsResponse.
     * 
     * @param transaction_Inquiry_DetailsResult
     */
    public void setTransaction_Inquiry_DetailsResult(org.tempuri.Transaction_Inquiry_DetailsResponseTransaction_Inquiry_DetailsResult transaction_Inquiry_DetailsResult) {
        this.transaction_Inquiry_DetailsResult = transaction_Inquiry_DetailsResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Transaction_Inquiry_DetailsResponse)) return false;
        Transaction_Inquiry_DetailsResponse other = (Transaction_Inquiry_DetailsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.transaction_Inquiry_DetailsResult==null && other.getTransaction_Inquiry_DetailsResult()==null) || 
             (this.transaction_Inquiry_DetailsResult!=null &&
              this.transaction_Inquiry_DetailsResult.equals(other.getTransaction_Inquiry_DetailsResult())));
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
        if (getTransaction_Inquiry_DetailsResult() != null) {
            _hashCode += getTransaction_Inquiry_DetailsResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Transaction_Inquiry_DetailsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Transaction_Inquiry_DetailsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transaction_Inquiry_DetailsResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Transaction_Inquiry_DetailsResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">>Transaction_Inquiry_DetailsResponse>Transaction_Inquiry_DetailsResult"));
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
