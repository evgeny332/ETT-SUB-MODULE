/**
 * Denomination.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces;

public class Denomination implements java.io.Serializable {
	private java.lang.String faceValue;

	private java.lang.String purchaseValue;

	public Denomination() {
	}

	public Denomination(java.lang.String faceValue, java.lang.String purchaseValue) {
		this.faceValue = faceValue;
		this.purchaseValue = purchaseValue;
	}

	/**
	 * Gets the faceValue value for this Denomination.
	 * 
	 * @return faceValue
	 */
	public java.lang.String getFaceValue() {
		return faceValue;
	}

	/**
	 * Sets the faceValue value for this Denomination.
	 * 
	 * @param faceValue
	 */
	public void setFaceValue(java.lang.String faceValue) {
		this.faceValue = faceValue;
	}

	/**
	 * Gets the purchaseValue value for this Denomination.
	 * 
	 * @return purchaseValue
	 */
	public java.lang.String getPurchaseValue() {
		return purchaseValue;
	}

	/**
	 * Sets the purchaseValue value for this Denomination.
	 * 
	 * @param purchaseValue
	 */
	
	public void setPurchaseValue(java.lang.String purchaseValue) {
		this.purchaseValue = purchaseValue;
	}

	@Override
	public String toString() {
		return " PurchaseValue=" + getFaceValue() + " , FaceValue=" + getPurchaseValue();
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Denomination))
			return false;
		Denomination other = (Denomination) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.faceValue == null && other.getFaceValue() == null) || (this.faceValue != null && this.faceValue.equals(other.getFaceValue())))
				&& ((this.purchaseValue == null && other.getPurchaseValue() == null) || (this.purchaseValue != null && this.purchaseValue.equals(other.getPurchaseValue())));
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
		if (getFaceValue() != null) {
			_hashCode += getFaceValue().hashCode();
		}
		if (getPurchaseValue() != null) {
			_hashCode += getPurchaseValue().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(Denomination.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "Denomination"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("faceValue");
		elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "FaceValue"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("purchaseValue");
		elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Pinelabs.NovaWebController.Interfaces", "PurchaseValue"));
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
