/**
 * Request_ReloadAmountResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class Request_ReloadAmountResponse  implements java.io.Serializable {
    private org.tempuri.Request_ReloadAmountResponseRequest_ReloadAmountResult request_ReloadAmountResult;

    public Request_ReloadAmountResponse() {
    }

    public Request_ReloadAmountResponse(
           org.tempuri.Request_ReloadAmountResponseRequest_ReloadAmountResult request_ReloadAmountResult) {
           this.request_ReloadAmountResult = request_ReloadAmountResult;
    }


    /**
     * Gets the request_ReloadAmountResult value for this Request_ReloadAmountResponse.
     * 
     * @return request_ReloadAmountResult
     */
    public org.tempuri.Request_ReloadAmountResponseRequest_ReloadAmountResult getRequest_ReloadAmountResult() {
        return request_ReloadAmountResult;
    }


    /**
     * Sets the request_ReloadAmountResult value for this Request_ReloadAmountResponse.
     * 
     * @param request_ReloadAmountResult
     */
    public void setRequest_ReloadAmountResult(org.tempuri.Request_ReloadAmountResponseRequest_ReloadAmountResult request_ReloadAmountResult) {
        this.request_ReloadAmountResult = request_ReloadAmountResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Request_ReloadAmountResponse)) return false;
        Request_ReloadAmountResponse other = (Request_ReloadAmountResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.request_ReloadAmountResult==null && other.getRequest_ReloadAmountResult()==null) || 
             (this.request_ReloadAmountResult!=null &&
              this.request_ReloadAmountResult.equals(other.getRequest_ReloadAmountResult())));
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
        if (getRequest_ReloadAmountResult() != null) {
            _hashCode += getRequest_ReloadAmountResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Request_ReloadAmountResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Request_ReloadAmountResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("request_ReloadAmountResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Request_ReloadAmountResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">>Request_ReloadAmountResponse>Request_ReloadAmountResult"));
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
