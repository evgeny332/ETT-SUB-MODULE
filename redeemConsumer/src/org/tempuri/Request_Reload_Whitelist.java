/**
 * Request_Reload_Whitelist.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class Request_Reload_Whitelist  implements java.io.Serializable {
    private java.lang.String sourceNo;

    private java.lang.String destNo;

    private java.lang.String prodCode;

    private int deno;

    private java.lang.String userID;

    private java.lang.String password;

    private java.lang.String transID;

    public Request_Reload_Whitelist() {
    }

    public Request_Reload_Whitelist(
           java.lang.String sourceNo,
           java.lang.String destNo,
           java.lang.String prodCode,
           int deno,
           java.lang.String userID,
           java.lang.String password,
           java.lang.String transID) {
           this.sourceNo = sourceNo;
           this.destNo = destNo;
           this.prodCode = prodCode;
           this.deno = deno;
           this.userID = userID;
           this.password = password;
           this.transID = transID;
    }


    /**
     * Gets the sourceNo value for this Request_Reload_Whitelist.
     * 
     * @return sourceNo
     */
    public java.lang.String getSourceNo() {
        return sourceNo;
    }


    /**
     * Sets the sourceNo value for this Request_Reload_Whitelist.
     * 
     * @param sourceNo
     */
    public void setSourceNo(java.lang.String sourceNo) {
        this.sourceNo = sourceNo;
    }


    /**
     * Gets the destNo value for this Request_Reload_Whitelist.
     * 
     * @return destNo
     */
    public java.lang.String getDestNo() {
        return destNo;
    }


    /**
     * Sets the destNo value for this Request_Reload_Whitelist.
     * 
     * @param destNo
     */
    public void setDestNo(java.lang.String destNo) {
        this.destNo = destNo;
    }


    /**
     * Gets the prodCode value for this Request_Reload_Whitelist.
     * 
     * @return prodCode
     */
    public java.lang.String getProdCode() {
        return prodCode;
    }


    /**
     * Sets the prodCode value for this Request_Reload_Whitelist.
     * 
     * @param prodCode
     */
    public void setProdCode(java.lang.String prodCode) {
        this.prodCode = prodCode;
    }


    /**
     * Gets the deno value for this Request_Reload_Whitelist.
     * 
     * @return deno
     */
    public int getDeno() {
        return deno;
    }


    /**
     * Sets the deno value for this Request_Reload_Whitelist.
     * 
     * @param deno
     */
    public void setDeno(int deno) {
        this.deno = deno;
    }


    /**
     * Gets the userID value for this Request_Reload_Whitelist.
     * 
     * @return userID
     */
    public java.lang.String getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this Request_Reload_Whitelist.
     * 
     * @param userID
     */
    public void setUserID(java.lang.String userID) {
        this.userID = userID;
    }


    /**
     * Gets the password value for this Request_Reload_Whitelist.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this Request_Reload_Whitelist.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the transID value for this Request_Reload_Whitelist.
     * 
     * @return transID
     */
    public java.lang.String getTransID() {
        return transID;
    }


    /**
     * Sets the transID value for this Request_Reload_Whitelist.
     * 
     * @param transID
     */
    public void setTransID(java.lang.String transID) {
        this.transID = transID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Request_Reload_Whitelist)) return false;
        Request_Reload_Whitelist other = (Request_Reload_Whitelist) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sourceNo==null && other.getSourceNo()==null) || 
             (this.sourceNo!=null &&
              this.sourceNo.equals(other.getSourceNo()))) &&
            ((this.destNo==null && other.getDestNo()==null) || 
             (this.destNo!=null &&
              this.destNo.equals(other.getDestNo()))) &&
            ((this.prodCode==null && other.getProdCode()==null) || 
             (this.prodCode!=null &&
              this.prodCode.equals(other.getProdCode()))) &&
            this.deno == other.getDeno() &&
            ((this.userID==null && other.getUserID()==null) || 
             (this.userID!=null &&
              this.userID.equals(other.getUserID()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.transID==null && other.getTransID()==null) || 
             (this.transID!=null &&
              this.transID.equals(other.getTransID())));
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
        if (getSourceNo() != null) {
            _hashCode += getSourceNo().hashCode();
        }
        if (getDestNo() != null) {
            _hashCode += getDestNo().hashCode();
        }
        if (getProdCode() != null) {
            _hashCode += getProdCode().hashCode();
        }
        _hashCode += getDeno();
        if (getUserID() != null) {
            _hashCode += getUserID().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getTransID() != null) {
            _hashCode += getTransID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Request_Reload_Whitelist.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Request_Reload_Whitelist"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sourceNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "destNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "prodCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deno");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "deno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "UserID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "transID"));
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
