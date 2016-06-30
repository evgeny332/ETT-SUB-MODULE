/**
 * Brand.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces;

public class Brand implements java.io.Serializable {
	private java.lang.String brandDescription;

	private java.lang.String brandDetailUrl;

	private java.lang.Long brandId;

	private java.lang.String brandName;

	private org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.Scheme[] schemeList;

	public Brand() {
	}

	public Brand(java.lang.String brandDescription, java.lang.String brandDetailUrl, java.lang.Long brandId, java.lang.String brandName,
			org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.Scheme[] schemeList) {
		this.brandDescription = brandDescription;
		this.brandDetailUrl = brandDetailUrl;
		this.brandId = brandId;
		this.brandName = brandName;
		this.schemeList = schemeList;
	}

	/**
	 * Gets the brandDescription value for this Brand.
	 * 
	 * @return brandDescription
	 */
	public java.lang.String getBrandDescription() {
		return brandDescription;
	}

	/**
	 * Sets the brandDescription value for this Brand.
	 * 
	 * @param brandDescription
	 */
	public void setBrandDescription(java.lang.String brandDescription) {
		this.brandDescription = brandDescription;
	}

	/**
	 * Gets the brandDetailUrl value for this Brand.
	 * 
	 * @return brandDetailUrl
	 */
	public java.lang.String getBrandDetailUrl() {
		return brandDetailUrl;
	}

	/**
	 * Sets the brandDetailUrl value for this Brand.
	 * 
	 * @param brandDetailUrl
	 */
	public void setBrandDetailUrl(java.lang.String brandDetailUrl) {
		this.brandDetailUrl = brandDetailUrl;
	}

	/**
	 * Gets the brandId value for this Brand.
	 * 
	 * @return brandId
	 */
	public java.lang.Long getBrandId() {
		return brandId;
	}

	/**
	 * Sets the brandId value for this Brand.
	 * 
	 * @param brandId
	 */
	public void setBrandId(java.lang.Long brandId) {
		this.brandId = brandId;
	}

	/**
	 * Gets the brandName value for this Brand.
	 * 
	 * @return brandName
	 */
	public java.lang.String getBrandName() {
		return brandName;
	}

	/**
	 * Sets the brandName value for this Brand.
	 * 
	 * @param brandName
	 */
	public void setBrandName(java.lang.String brandName) {
		this.brandName = brandName;
	}

	/**
	 * Gets the schemeList value for this Brand.
	 * 
	 * @return schemeList
	 */
	public org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.Scheme[] getSchemeList() {

		return schemeList;
	}

	/**
	 * Sets the schemeList value for this Brand.
	 * 
	 * @param schemeList
	 */
	public void setSchemeList(org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.Scheme[] schemeList) {
		this.schemeList = schemeList;
	}

	@Override
	public String toString() {

		String schema = "";
		Scheme[] schemes = getSchemeList();
		for (int i = 0; i < schemes.length; i++) {
			Scheme sch = schemes[i];
			String deno = "";
			Denomination[] denominations  = sch.getDenominationList();
			
			for(Denomination denomination : denominations){
				deno = denomination.toString();
			}
			
			schema = " SchemeId=" + sch.getSchemeId() + " , SchemeName=" + sch.getSchemeName() + " , SchemeMessage=" + sch.getSchemeMessage() + " , SchemeDescription=" + sch.getSchemeDescription() + " , SchemeDetailUrl=" + sch.getSchemeDetailUrl() + " ,IsEvvScheme="+sch.getIsEvvScheme()+", IsOtpEnabled="+sch.getIsOtpEnabled()+ ", TermsAndConditions=" + sch.getTermsAndConditions()
					+ ", " + deno +"";
		}
		String brand = "BrandId=" + getBrandId() + " , BrandName=" + getBrandName() + " , BrandDescription=" + getBrandDescription() + " , BrandDetailUrl=" + getBrandDetailUrl() + " , " + schema + " ]";
		return brand;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Brand))
			return false;
		Brand other = (Brand) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.brandDescription == null && other.getBrandDescription() == null) || (this.brandDescription != null && this.brandDescription.equals(other.getBrandDescription())))
				&& ((this.brandDetailUrl == null && other.getBrandDetailUrl() == null) || (this.brandDetailUrl != null && this.brandDetailUrl.equals(other.getBrandDetailUrl())))
				&& ((this.brandId == null && other.getBrandId() == null) || (this.brandId != null && this.brandId.equals(other.getBrandId())))
				&& ((this.brandName == null && other.getBrandName() == null) || (this.brandName != null && this.brandName.equals(other.getBrandName())))
				&& ((this.schemeList == null && other.getSchemeList() == null) || (this.schemeList != null && java.util.Arrays.equals(this.schemeList, other.getSchemeList())));
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
		if (getBrandDescription() != null) {
			_hashCode += getBrandDescription().hashCode();
		}
		if (getBrandDetailUrl() != null) {
			_hashCode += getBrandDetailUrl().hashCode();
		}
		if (getBrandId() != null) {
			_hashCode += getBrandId().hashCode();
		}
		if (getBrandName() != null) {
			_hashCode += getBrandName().hashCode();
		}
		if (getSchemeList() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getSchemeList()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getSchemeList(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(Brand.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Brand"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("brandDescription");
		elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "BrandDescription"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("brandDetailUrl");
		elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "BrandDetailUrl"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("brandId");
		elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "BrandId"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("brandName");
		elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "BrandName"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("schemeList");
		elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "SchemeList"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Scheme"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Scheme"));
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
	public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType, java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType, java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

}
