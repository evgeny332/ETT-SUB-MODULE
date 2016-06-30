/**
 * VoucherRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces;

public class VoucherRequest  implements java.io.Serializable {
    private java.lang.String denomination;

    private java.lang.String faceValue;

    private java.lang.Long quantity;

    private java.lang.String schemeId;

    public VoucherRequest() {
    }

    public VoucherRequest(
           java.lang.String denomination,
           java.lang.String faceValue,
           java.lang.Long quantity,
           java.lang.String schemeId) {
           this.denomination = denomination;
           this.faceValue = faceValue;
           this.quantity = quantity;
           this.schemeId = schemeId;
    }


    /**
     * Gets the denomination value for this VoucherRequest.
     * 
     * @return denomination
     */
    public java.lang.String getDenomination() {
        return denomination;
    }


    /**
     * Sets the denomination value for this VoucherRequest.
     * 
     * @param denomination
     */
    public void setDenomination(java.lang.String denomination) {
        this.denomination = denomination;
    }


    /**
     * Gets the faceValue value for this VoucherRequest.
     * 
     * @return faceValue
     */
    public java.lang.String getFaceValue() {
        return faceValue;
    }


    /**
     * Sets the faceValue value for this VoucherRequest.
     * 
     * @param faceValue
     */
    public void setFaceValue(java.lang.String faceValue) {
        this.faceValue = faceValue;
    }


    /**
     * Gets the quantity value for this VoucherRequest.
     * 
     * @return quantity
     */
    public java.lang.Long getQuantity() {
        return quantity;
    }


    /**
     * Sets the quantity value for this VoucherRequest.
     * 
     * @param quantity
     */
    public void setQuantity(java.lang.Long quantity) {
        this.quantity = quantity;
    }


    /**
     * Gets the schemeId value for this VoucherRequest.
     * 
     * @return schemeId
     */
    public java.lang.String getSchemeId() {
        return schemeId;
    }


    /**
     * Sets the schemeId value for this VoucherRequest.
     * 
     * @param schemeId
     */
    public void setSchemeId(java.lang.String schemeId) {
        this.schemeId = schemeId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VoucherRequest)) return false;
        VoucherRequest other = (VoucherRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.denomination==null && other.getDenomination()==null) || 
             (this.denomination!=null &&
              this.denomination.equals(other.getDenomination()))) &&
            ((this.faceValue==null && other.getFaceValue()==null) || 
             (this.faceValue!=null &&
              this.faceValue.equals(other.getFaceValue()))) &&
            ((this.quantity==null && other.getQuantity()==null) || 
             (this.quantity!=null &&
              this.quantity.equals(other.getQuantity()))) &&
            ((this.schemeId==null && other.getSchemeId()==null) || 
             (this.schemeId!=null &&
              this.schemeId.equals(other.getSchemeId())));
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
        if (getDenomination() != null) {
            _hashCode += getDenomination().hashCode();
        }
        if (getFaceValue() != null) {
            _hashCode += getFaceValue().hashCode();
        }
        if (getQuantity() != null) {
            _hashCode += getQuantity().hashCode();
        }
        if (getSchemeId() != null) {
            _hashCode += getSchemeId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VoucherRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "VoucherRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denomination");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Denomination"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faceValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "FaceValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Quantity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schemeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "SchemeId"));
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
