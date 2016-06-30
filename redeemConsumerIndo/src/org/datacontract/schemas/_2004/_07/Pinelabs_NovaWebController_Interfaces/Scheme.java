/**
 * Scheme.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces;

public class Scheme  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.Denomination[] denominationList;

    private java.lang.Boolean isEvvScheme;

    private java.lang.Boolean isOtpEnabled;

    private java.lang.String schemeDescription;

    private java.lang.String schemeDetailUrl;

    private java.lang.String schemeId;

    private java.lang.String schemeMessage;

    private java.lang.String schemeName;

    private java.lang.String termsAndConditions;

    public Scheme() {
    }

    public Scheme(
           org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.Denomination[] denominationList,
           java.lang.Boolean isEvvScheme,
           java.lang.Boolean isOtpEnabled,
           java.lang.String schemeDescription,
           java.lang.String schemeDetailUrl,
           java.lang.String schemeId,
           java.lang.String schemeMessage,
           java.lang.String schemeName,
           java.lang.String termsAndConditions) {
           this.denominationList = denominationList;
           this.isEvvScheme = isEvvScheme;
           this.isOtpEnabled = isOtpEnabled;
           this.schemeDescription = schemeDescription;
           this.schemeDetailUrl = schemeDetailUrl;
           this.schemeId = schemeId;
           this.schemeMessage = schemeMessage;
           this.schemeName = schemeName;
           this.termsAndConditions = termsAndConditions;
    }


    /**
     * Gets the denominationList value for this Scheme.
     * 
     * @return denominationList
     */
    public org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.Denomination[] getDenominationList() {
        return denominationList;
    }


    /**
     * Sets the denominationList value for this Scheme.
     * 
     * @param denominationList
     */
    public void setDenominationList(org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.Denomination[] denominationList) {
        this.denominationList = denominationList;
    }


    /**
     * Gets the isEvvScheme value for this Scheme.
     * 
     * @return isEvvScheme
     */
    public java.lang.Boolean getIsEvvScheme() {
        return isEvvScheme;
    }


    /**
     * Sets the isEvvScheme value for this Scheme.
     * 
     * @param isEvvScheme
     */
    public void setIsEvvScheme(java.lang.Boolean isEvvScheme) {
        this.isEvvScheme = isEvvScheme;
    }


    /**
     * Gets the isOtpEnabled value for this Scheme.
     * 
     * @return isOtpEnabled
     */
    public java.lang.Boolean getIsOtpEnabled() {
        return isOtpEnabled;
    }


    /**
     * Sets the isOtpEnabled value for this Scheme.
     * 
     * @param isOtpEnabled
     */
    public void setIsOtpEnabled(java.lang.Boolean isOtpEnabled) {
        this.isOtpEnabled = isOtpEnabled;
    }


    /**
     * Gets the schemeDescription value for this Scheme.
     * 
     * @return schemeDescription
     */
    public java.lang.String getSchemeDescription() {
        return schemeDescription;
    }


    /**
     * Sets the schemeDescription value for this Scheme.
     * 
     * @param schemeDescription
     */
    public void setSchemeDescription(java.lang.String schemeDescription) {
        this.schemeDescription = schemeDescription;
    }


    /**
     * Gets the schemeDetailUrl value for this Scheme.
     * 
     * @return schemeDetailUrl
     */
    public java.lang.String getSchemeDetailUrl() {
        return schemeDetailUrl;
    }


    /**
     * Sets the schemeDetailUrl value for this Scheme.
     * 
     * @param schemeDetailUrl
     */
    public void setSchemeDetailUrl(java.lang.String schemeDetailUrl) {
        this.schemeDetailUrl = schemeDetailUrl;
    }


    /**
     * Gets the schemeId value for this Scheme.
     * 
     * @return schemeId
     */
    public java.lang.String getSchemeId() {
        return schemeId;
    }


    /**
     * Sets the schemeId value for this Scheme.
     * 
     * @param schemeId
     */
    public void setSchemeId(java.lang.String schemeId) {
        this.schemeId = schemeId;
    }


    /**
     * Gets the schemeMessage value for this Scheme.
     * 
     * @return schemeMessage
     */
    public java.lang.String getSchemeMessage() {
        return schemeMessage;
    }


    /**
     * Sets the schemeMessage value for this Scheme.
     * 
     * @param schemeMessage
     */
    public void setSchemeMessage(java.lang.String schemeMessage) {
        this.schemeMessage = schemeMessage;
    }


    /**
     * Gets the schemeName value for this Scheme.
     * 
     * @return schemeName
     */
    public java.lang.String getSchemeName() {
        return schemeName;
    }


    /**
     * Sets the schemeName value for this Scheme.
     * 
     * @param schemeName
     */
    public void setSchemeName(java.lang.String schemeName) {
        this.schemeName = schemeName;
    }


    /**
     * Gets the termsAndConditions value for this Scheme.
     * 
     * @return termsAndConditions
     */
    public java.lang.String getTermsAndConditions() {
        return termsAndConditions;
    }


    /**
     * Sets the termsAndConditions value for this Scheme.
     * 
     * @param termsAndConditions
     */
    public void setTermsAndConditions(java.lang.String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Scheme)) return false;
        Scheme other = (Scheme) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.denominationList==null && other.getDenominationList()==null) || 
             (this.denominationList!=null &&
              java.util.Arrays.equals(this.denominationList, other.getDenominationList()))) &&
            ((this.isEvvScheme==null && other.getIsEvvScheme()==null) || 
             (this.isEvvScheme!=null &&
              this.isEvvScheme.equals(other.getIsEvvScheme()))) &&
            ((this.isOtpEnabled==null && other.getIsOtpEnabled()==null) || 
             (this.isOtpEnabled!=null &&
              this.isOtpEnabled.equals(other.getIsOtpEnabled()))) &&
            ((this.schemeDescription==null && other.getSchemeDescription()==null) || 
             (this.schemeDescription!=null &&
              this.schemeDescription.equals(other.getSchemeDescription()))) &&
            ((this.schemeDetailUrl==null && other.getSchemeDetailUrl()==null) || 
             (this.schemeDetailUrl!=null &&
              this.schemeDetailUrl.equals(other.getSchemeDetailUrl()))) &&
            ((this.schemeId==null && other.getSchemeId()==null) || 
             (this.schemeId!=null &&
              this.schemeId.equals(other.getSchemeId()))) &&
            ((this.schemeMessage==null && other.getSchemeMessage()==null) || 
             (this.schemeMessage!=null &&
              this.schemeMessage.equals(other.getSchemeMessage()))) &&
            ((this.schemeName==null && other.getSchemeName()==null) || 
             (this.schemeName!=null &&
              this.schemeName.equals(other.getSchemeName()))) &&
            ((this.termsAndConditions==null && other.getTermsAndConditions()==null) || 
             (this.termsAndConditions!=null &&
              this.termsAndConditions.equals(other.getTermsAndConditions())));
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
        if (getDenominationList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDenominationList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDenominationList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIsEvvScheme() != null) {
            _hashCode += getIsEvvScheme().hashCode();
        }
        if (getIsOtpEnabled() != null) {
            _hashCode += getIsOtpEnabled().hashCode();
        }
        if (getSchemeDescription() != null) {
            _hashCode += getSchemeDescription().hashCode();
        }
        if (getSchemeDetailUrl() != null) {
            _hashCode += getSchemeDetailUrl().hashCode();
        }
        if (getSchemeId() != null) {
            _hashCode += getSchemeId().hashCode();
        }
        if (getSchemeMessage() != null) {
            _hashCode += getSchemeMessage().hashCode();
        }
        if (getSchemeName() != null) {
            _hashCode += getSchemeName().hashCode();
        }
        if (getTermsAndConditions() != null) {
            _hashCode += getTermsAndConditions().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Scheme.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Scheme"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominationList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "DenominationList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Denomination"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Denomination"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isEvvScheme");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "IsEvvScheme"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isOtpEnabled");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "IsOtpEnabled"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schemeDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "SchemeDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schemeDetailUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "SchemeDetailUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schemeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "SchemeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schemeMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "SchemeMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schemeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "SchemeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("termsAndConditions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "TermsAndConditions"));
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
