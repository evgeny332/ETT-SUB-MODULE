/**
 * Request_Reload_NewResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class Request_Reload_NewResponse  implements java.io.Serializable {
    private java.lang.String request_Reload_NewResult;

    public Request_Reload_NewResponse() {
    }

    public Request_Reload_NewResponse(
           java.lang.String request_Reload_NewResult) {
           this.request_Reload_NewResult = request_Reload_NewResult;
    }


    /**
     * Gets the request_Reload_NewResult value for this Request_Reload_NewResponse.
     * 
     * @return request_Reload_NewResult
     */
    public java.lang.String getRequest_Reload_NewResult() {
        return request_Reload_NewResult;
    }


    /**
     * Sets the request_Reload_NewResult value for this Request_Reload_NewResponse.
     * 
     * @param request_Reload_NewResult
     */
    public void setRequest_Reload_NewResult(java.lang.String request_Reload_NewResult) {
        this.request_Reload_NewResult = request_Reload_NewResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Request_Reload_NewResponse)) return false;
        Request_Reload_NewResponse other = (Request_Reload_NewResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.request_Reload_NewResult==null && other.getRequest_Reload_NewResult()==null) || 
             (this.request_Reload_NewResult!=null &&
              this.request_Reload_NewResult.equals(other.getRequest_Reload_NewResult())));
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
        if (getRequest_Reload_NewResult() != null) {
            _hashCode += getRequest_Reload_NewResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Request_Reload_NewResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Request_Reload_NewResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("request_Reload_NewResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Request_Reload_NewResult"));
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
