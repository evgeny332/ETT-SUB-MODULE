/**
 * Request_Reload_WhitelistResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class Request_Reload_WhitelistResponse  implements java.io.Serializable {
    private java.lang.String request_Reload_WhitelistResult;

    public Request_Reload_WhitelistResponse() {
    }

    public Request_Reload_WhitelistResponse(
           java.lang.String request_Reload_WhitelistResult) {
           this.request_Reload_WhitelistResult = request_Reload_WhitelistResult;
    }


    /**
     * Gets the request_Reload_WhitelistResult value for this Request_Reload_WhitelistResponse.
     * 
     * @return request_Reload_WhitelistResult
     */
    public java.lang.String getRequest_Reload_WhitelistResult() {
        return request_Reload_WhitelistResult;
    }


    /**
     * Sets the request_Reload_WhitelistResult value for this Request_Reload_WhitelistResponse.
     * 
     * @param request_Reload_WhitelistResult
     */
    public void setRequest_Reload_WhitelistResult(java.lang.String request_Reload_WhitelistResult) {
        this.request_Reload_WhitelistResult = request_Reload_WhitelistResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Request_Reload_WhitelistResponse)) return false;
        Request_Reload_WhitelistResponse other = (Request_Reload_WhitelistResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.request_Reload_WhitelistResult==null && other.getRequest_Reload_WhitelistResult()==null) || 
             (this.request_Reload_WhitelistResult!=null &&
              this.request_Reload_WhitelistResult.equals(other.getRequest_Reload_WhitelistResult())));
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
        if (getRequest_Reload_WhitelistResult() != null) {
            _hashCode += getRequest_Reload_WhitelistResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Request_Reload_WhitelistResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Request_Reload_WhitelistResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("request_Reload_WhitelistResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Request_Reload_WhitelistResult"));
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
