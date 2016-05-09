/**
 * Product_Price_InquiryResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class Product_Price_InquiryResponse  implements java.io.Serializable {
    private org.tempuri.Product_Price_InquiryResponseProduct_Price_InquiryResult product_Price_InquiryResult;

    public Product_Price_InquiryResponse() {
    }

    public Product_Price_InquiryResponse(
           org.tempuri.Product_Price_InquiryResponseProduct_Price_InquiryResult product_Price_InquiryResult) {
           this.product_Price_InquiryResult = product_Price_InquiryResult;
    }


    /**
     * Gets the product_Price_InquiryResult value for this Product_Price_InquiryResponse.
     * 
     * @return product_Price_InquiryResult
     */
    public org.tempuri.Product_Price_InquiryResponseProduct_Price_InquiryResult getProduct_Price_InquiryResult() {
        return product_Price_InquiryResult;
    }


    /**
     * Sets the product_Price_InquiryResult value for this Product_Price_InquiryResponse.
     * 
     * @param product_Price_InquiryResult
     */
    public void setProduct_Price_InquiryResult(org.tempuri.Product_Price_InquiryResponseProduct_Price_InquiryResult product_Price_InquiryResult) {
        this.product_Price_InquiryResult = product_Price_InquiryResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Product_Price_InquiryResponse)) return false;
        Product_Price_InquiryResponse other = (Product_Price_InquiryResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.product_Price_InquiryResult==null && other.getProduct_Price_InquiryResult()==null) || 
             (this.product_Price_InquiryResult!=null &&
              this.product_Price_InquiryResult.equals(other.getProduct_Price_InquiryResult())));
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
        if (getProduct_Price_InquiryResult() != null) {
            _hashCode += getProduct_Price_InquiryResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Product_Price_InquiryResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Product_Price_InquiryResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("product_Price_InquiryResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Product_Price_InquiryResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">>Product_Price_InquiryResponse>Product_Price_InquiryResult"));
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
