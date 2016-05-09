package com.rh.utility;

import java.io.IOException;
import java.io.StringReader;
import java.security.MessageDigest;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Utility {

	private static Log log = LogFactory.getLog(Utility.class);

	public static String getMobilePulsaResponse(String id) {
		if (id.equalsIgnoreCase("TIMEOUT") || id.equalsIgnoreCase("")) {
			return id;
		}
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		String isSuccess = "";
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			InputSource inputSource = new InputSource(new StringReader(id));
			Document document = documentBuilder.parse(inputSource);
			NodeList nodeList = document.getElementsByTagName("status");
			isSuccess = nodeList.item(0).getTextContent().toString();
		} catch (ParserConfigurationException ex) {
			log.error("Error in MobilePulsaResponse| ", ex);
			ex.printStackTrace();
		} catch (SAXException ex) {
			log.error("Error in MobilePulsaResponse| ", ex);
			ex.printStackTrace();
		} catch (IOException ex) {
			log.error("Error in MobilePulsaResponse| ", ex);
			ex.printStackTrace();
		}
		if (isSuccess.equals("0"))
			return "PENDING";
		if (isSuccess.equals("1"))
			return "SUCCESS";
		if (isSuccess.equals("2"))
			return "FAILED";
		return "";
	}

	public static String getMD5(String username, String password, String id) {
		String isSuccess1 = username + password + id;
		String resp = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(isSuccess1.getBytes());
			byte[] byteArray = messageDigest.digest();
			StringBuffer stringBuffer = new StringBuffer();
			for (int k : byteArray)
				stringBuffer.append(String.format("%02x", new Object[] { Integer.valueOf(k & 0xFF) }));
			resp = stringBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	public static String getTrangloResponse(String resp) {
		if (resp.equalsIgnoreCase("TIMEOUT") || resp.equals("")) {
			return resp;
		}
		if (resp.equals("000")) {
			return "SUCCESS";
		}
		if ((resp.equals("001")) || (resp.equals("968"))) {
			return "PENDING";
		}
		return "FAILED";
	}

}
