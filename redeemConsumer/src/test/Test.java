package test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Test {
	public static final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) {

		System.out.println("Hello");
		// String url="https://osg.oximall.com/transservice.aspx";
		// String postData = "transid=12131&merchantrefno=Topup,8285932390,Aircel,&amount=&requestdate="+getTidOxygen("yyyyMMddHHmmss")+"&status=0&bankrefno=8";
		// String postData = getMobilePulsaResponse("<?xml version=\"1.0\"?><mp><status>1</status><message>pulsa5000 ke 2.081299380279 Harga Rp. 6150 SUCCESS.
		// SN:5120420021261008000.</message><tr_id>1600826</tr_id><balance>1066810</balance><ref_id>1449234121456</ref_id></mp>");
		// createJson();
		// System.out.println(postData);
		// sendRequestOxygen("https://osg.oximall.com/transservice.aspx",
		// "transid=201601070216533161077673&merchantrefno=ETOP*AIRC*DEL,8285932390&amount=20&requestdate=20160107195409&status=0&bankrefno=8");

		Date date = new Date();
		Date date2 = new Date(99, 0, 9);

		System.out.println("Date1:" + date + ", Date2:"+date2);
		// make 3 comparisons with them
		int comparison = date.compareTo(date2);
		int comparison2 = date2.compareTo(date);
		int comparison3 = date.compareTo(date);

		// print the results
		System.out.println("Comparison Result:" + comparison);
		System.out.println("Comparison2 Result:" + comparison2);
		System.out.println("Comparison3 Result:" + comparison3);
		float f = 0.000f;

		if (f <= 0f) {
			System.out.println("True");
		} else {
			System.out.println("False");
		}
	}

	public static void sendRequestOxygen1(String url, String postData) {

		try {
			System.setProperty("https.protocols", "TLSv1");

			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Authorization", "Basic UmF0aW9uYWxoZWFkczpSYXRpb25hbGhlYWRzQG1wMTIz");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setReadTimeout(50000);
			con.setConnectTimeout(50000);
			con.setDoOutput(true);

			DataOutputStream osw = new DataOutputStream(con.getOutputStream());
			osw.writeBytes(postData);
			osw.flush();
			osw.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			if (responseCode == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				System.out.println(response.toString());

				// return response.toString();
			} else if (responseCode == 201) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				System.out.println(response.toString());
				// return response.toString();

			} else {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				System.out.println(response.toString());
				// return response.toString();
			}
			// oLog.info(new Date() + "|reqst=" + URL + "|postData=" + postData + "|resp=" + text.toString());
			// System.out.println(new Date() + "|reqst=" + url + "|postData=" + postData + "|resp=" + response.toString());
			// return text.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// oLog.error("[Error in curl][" + URL + "] [postData][" + postData + "]" + e);
			// return null;
		}
	}

	public static void sendRequestOxygen(String URL, String postData) {
		try {
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}

				@SuppressWarnings("unused")
				public boolean isClientTrusted(X509Certificate[] arg0) {
					return true;
				}

				@SuppressWarnings("unused")
				public boolean isServerTrusted(X509Certificate[] arg0) {
					return true;
				}
			} };

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					System.out.println("allHostsValid hostname :" + hostname);
					System.out.println("allHostsValid SSLSession : " + session);
					return true;
				}
			};

			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

			URL url = new URL(URL);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConnection.setRequestProperty("Authorization", "Basic U0JJOm94aWdlbkAxMjM=");
			urlConnection.setReadTimeout(50000);
			urlConnection.setConnectTimeout(50000);
			urlConnection.setDoOutput(true);

			DataOutputStream osw = new DataOutputStream(urlConnection.getOutputStream());
			osw.writeBytes(postData);
			osw.flush();
			osw.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String decodedString;
			String text = "";
			while ((decodedString = in.readLine()) != null) {
				text = text + decodedString;
			}
			in.close();
			// oLog.info(new Date() + "|reqst=" + URL + "|postData=" + postData + "|resp=" + text.toString());
			System.out.println(new Date() + "|reqst=" + URL + "|postData=" + postData + "|resp=" + text.toString());
			// return text.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// oLog.error("[Error in curl][" + URL + "] [postData][" + postData + "]" + e);
			// return null;
		}
	}

	public static void createJson() {

		try {

			JSONObject jsonObject = new JSONObject();
			JSONObject jsonObject1 = new JSONObject();

			jsonObject.put("transactionId", "2377212");
			jsonObject.put("denomination", "50");
			jsonObject1.put("medium", "INLINE");
			jsonObject1.put("format", "JSON");
			jsonObject.put("recipient", jsonObject1);

			System.out.println(jsonObject);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static String getMobilePulsaResponse(String id) {

		System.out.println("Hello2: " + id);
		if (id.equalsIgnoreCase("TIMEOUT"))
			return id;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		String isSuccess = "";
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			InputSource inputSource = new InputSource(new StringReader(id));
			Document document = documentBuilder.parse(inputSource);
			NodeList nodeList = document.getElementsByTagName("status");
			isSuccess = nodeList.item(0).getTextContent().toString();
		} catch (ParserConfigurationException localParserConfigurationException) {
			// log.error("Error in MobilePulsaResponse| ", localParserConfigurationException);
			localParserConfigurationException.printStackTrace();
		} catch (SAXException localSAXException) {
			// log.error("Error in MobilePulsaResponse| ", localSAXException);
			localSAXException.printStackTrace();
		} catch (IOException localIOException) {
			// log.error("Error in MobilePulsaResponse| ", localIOException);
			localIOException.printStackTrace();
		}

		if (isSuccess.equals("0")) {
			System.out.println("Hello2: " + id);
			return "PENDING";
		}

		if (isSuccess.equals("1")) {
			System.out.println("Hello2: " + id);
			return "SUCCESS";
		}

		if (isSuccess.equals("2")) {
			System.out.println("Hello2: " + id);
			return "FAILED";
		}

		return "";
	}
}
