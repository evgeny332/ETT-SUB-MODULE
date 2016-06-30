/**
 * GenerateVoucherRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces;

public class GenerateVoucherRequest  implements java.io.Serializable {
    private java.lang.String accessCode;

    private java.lang.String customerEmailId;

    private java.lang.String customerFirstName;

    private java.lang.String customerLastName;

    private java.lang.String customerMobileNumber;

    private java.lang.String dateOfBirth;

    private java.lang.String gender;

    private java.lang.Long requestId;

    private org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.VoucherRequest[] requestVoucherList;

    private java.lang.Long serverId;

    public GenerateVoucherRequest() {
    }

    public GenerateVoucherRequest(
           java.lang.String accessCode,
           java.lang.String customerEmailId,
           java.lang.String customerFirstName,
           java.lang.String customerLastName,
           java.lang.String customerMobileNumber,
           java.lang.String dateOfBirth,
           java.lang.String gender,
           java.lang.Long requestId,
           org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.VoucherRequest[] requestVoucherList,
           java.lang.Long serverId) {
           this.accessCode = accessCode;
           this.customerEmailId = customerEmailId;
           this.customerFirstName = customerFirstName;
           this.customerLastName = customerLastName;
           this.customerMobileNumber = customerMobileNumber;
           this.dateOfBirth = dateOfBirth;
           this.gender = gender;
           this.requestId = requestId;
           this.requestVoucherList = requestVoucherList;
           this.serverId = serverId;
    }


    /**
     * Gets the accessCode value for this GenerateVoucherRequest.
     * 
     * @return accessCode
     */
    public java.lang.String getAccessCode() {
        return accessCode;
    }


    /**
     * Sets the accessCode value for this GenerateVoucherRequest.
     * 
     * @param accessCode
     */
    public void setAccessCode(java.lang.String accessCode) {
        this.accessCode = accessCode;
    }


    /**
     * Gets the customerEmailId value for this GenerateVoucherRequest.
     * 
     * @return customerEmailId
     */
    public java.lang.String getCustomerEmailId() {
        return customerEmailId;
    }


    /**
     * Sets the customerEmailId value for this GenerateVoucherRequest.
     * 
     * @param customerEmailId
     */
    public void setCustomerEmailId(java.lang.String customerEmailId) {
        this.customerEmailId = customerEmailId;
    }


    /**
     * Gets the customerFirstName value for this GenerateVoucherRequest.
     * 
     * @return customerFirstName
     */
    public java.lang.String getCustomerFirstName() {
        return customerFirstName;
    }


    /**
     * Sets the customerFirstName value for this GenerateVoucherRequest.
     * 
     * @param customerFirstName
     */
    public void setCustomerFirstName(java.lang.String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }


    /**
     * Gets the customerLastName value for this GenerateVoucherRequest.
     * 
     * @return customerLastName
     */
    public java.lang.String getCustomerLastName() {
        return customerLastName;
    }


    /**
     * Sets the customerLastName value for this GenerateVoucherRequest.
     * 
     * @param customerLastName
     */
    public void setCustomerLastName(java.lang.String customerLastName) {
        this.customerLastName = customerLastName;
    }


    /**
     * Gets the customerMobileNumber value for this GenerateVoucherRequest.
     * 
     * @return customerMobileNumber
     */
    public java.lang.String getCustomerMobileNumber() {
        return customerMobileNumber;
    }


    /**
     * Sets the customerMobileNumber value for this GenerateVoucherRequest.
     * 
     * @param customerMobileNumber
     */
    public void setCustomerMobileNumber(java.lang.String customerMobileNumber) {
        this.customerMobileNumber = customerMobileNumber;
    }


    /**
     * Gets the dateOfBirth value for this GenerateVoucherRequest.
     * 
     * @return dateOfBirth
     */
    public java.lang.String getDateOfBirth() {
        return dateOfBirth;
    }


    /**
     * Sets the dateOfBirth value for this GenerateVoucherRequest.
     * 
     * @param dateOfBirth
     */
    public void setDateOfBirth(java.lang.String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    /**
     * Gets the gender value for this GenerateVoucherRequest.
     * 
     * @return gender
     */
    public java.lang.String getGender() {
        return gender;
    }


    /**
     * Sets the gender value for this GenerateVoucherRequest.
     * 
     * @param gender
     */
    public void setGender(java.lang.String gender) {
        this.gender = gender;
    }


    /**
     * Gets the requestId value for this GenerateVoucherRequest.
     * 
     * @return requestId
     */
    public java.lang.Long getRequestId() {
        return requestId;
    }


    /**
     * Sets the requestId value for this GenerateVoucherRequest.
     * 
     * @param requestId
     */
    public void setRequestId(java.lang.Long requestId) {
        this.requestId = requestId;
    }


    /**
     * Gets the requestVoucherList value for this GenerateVoucherRequest.
     * 
     * @return requestVoucherList
     */
    public org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.VoucherRequest[] getRequestVoucherList() {
        return requestVoucherList;
    }


    /**
     * Sets the requestVoucherList value for this GenerateVoucherRequest.
     * 
     * @param requestVoucherList
     */
    public void setRequestVoucherList(org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.VoucherRequest[] requestVoucherList) {
        this.requestVoucherList = requestVoucherList;
    }


    /**
     * Gets the serverId value for this GenerateVoucherRequest.
     * 
     * @return serverId
     */
    public java.lang.Long getServerId() {
        return serverId;
    }


    /**
     * Sets the serverId value for this GenerateVoucherRequest.
     * 
     * @param serverId
     */
    public void setServerId(java.lang.Long serverId) {
        this.serverId = serverId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GenerateVoucherRequest)) return false;
        GenerateVoucherRequest other = (GenerateVoucherRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accessCode==null && other.getAccessCode()==null) || 
             (this.accessCode!=null &&
              this.accessCode.equals(other.getAccessCode()))) &&
            ((this.customerEmailId==null && other.getCustomerEmailId()==null) || 
             (this.customerEmailId!=null &&
              this.customerEmailId.equals(other.getCustomerEmailId()))) &&
            ((this.customerFirstName==null && other.getCustomerFirstName()==null) || 
             (this.customerFirstName!=null &&
              this.customerFirstName.equals(other.getCustomerFirstName()))) &&
            ((this.customerLastName==null && other.getCustomerLastName()==null) || 
             (this.customerLastName!=null &&
              this.customerLastName.equals(other.getCustomerLastName()))) &&
            ((this.customerMobileNumber==null && other.getCustomerMobileNumber()==null) || 
             (this.customerMobileNumber!=null &&
              this.customerMobileNumber.equals(other.getCustomerMobileNumber()))) &&
            ((this.dateOfBirth==null && other.getDateOfBirth()==null) || 
             (this.dateOfBirth!=null &&
              this.dateOfBirth.equals(other.getDateOfBirth()))) &&
            ((this.gender==null && other.getGender()==null) || 
             (this.gender!=null &&
              this.gender.equals(other.getGender()))) &&
            ((this.requestId==null && other.getRequestId()==null) || 
             (this.requestId!=null &&
              this.requestId.equals(other.getRequestId()))) &&
            ((this.requestVoucherList==null && other.getRequestVoucherList()==null) || 
             (this.requestVoucherList!=null &&
              java.util.Arrays.equals(this.requestVoucherList, other.getRequestVoucherList()))) &&
            ((this.serverId==null && other.getServerId()==null) || 
             (this.serverId!=null &&
              this.serverId.equals(other.getServerId())));
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
        if (getAccessCode() != null) {
            _hashCode += getAccessCode().hashCode();
        }
        if (getCustomerEmailId() != null) {
            _hashCode += getCustomerEmailId().hashCode();
        }
        if (getCustomerFirstName() != null) {
            _hashCode += getCustomerFirstName().hashCode();
        }
        if (getCustomerLastName() != null) {
            _hashCode += getCustomerLastName().hashCode();
        }
        if (getCustomerMobileNumber() != null) {
            _hashCode += getCustomerMobileNumber().hashCode();
        }
        if (getDateOfBirth() != null) {
            _hashCode += getDateOfBirth().hashCode();
        }
        if (getGender() != null) {
            _hashCode += getGender().hashCode();
        }
        if (getRequestId() != null) {
            _hashCode += getRequestId().hashCode();
        }
        if (getRequestVoucherList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRequestVoucherList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRequestVoucherList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getServerId() != null) {
            _hashCode += getServerId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GenerateVoucherRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "GenerateVoucherRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "AccessCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerEmailId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "CustomerEmailId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerFirstName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "CustomerFirstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerLastName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "CustomerLastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerMobileNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "CustomerMobileNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateOfBirth");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "DateOfBirth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gender");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Gender"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "RequestId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestVoucherList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "RequestVoucherList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "VoucherRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "VoucherRequest"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serverId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "ServerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
