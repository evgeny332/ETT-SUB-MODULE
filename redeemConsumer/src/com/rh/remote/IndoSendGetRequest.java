package com.rh.remote;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.axis.client.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tempuri.EPin_ReloadSoap_BindingStub;

import com.rh.config.ConfigHolder;


public class IndoSendGetRequest {
	private static Log oLog = LogFactory.getLog(IndoSendGetRequest.class);

	public String reloadTranglo(String Url, String msisdn, float amount1, String trans_id) {
		try {
			final ConfigHolder configHolder = new ConfigHolder();
			
			int amount = (int) amount1;
			URL url = new URL(Url);
			Service service = new Service();
			EPin_ReloadSoap_BindingStub stub = new EPin_ReloadSoap_BindingStub(url, service);
			stub.setTimeout(60000);
			String resp = stub.request_Reload("62"+msisdn, "62"+msisdn, "", amount, configHolder.getProperties().getProperty("TRANGLO_USERNAME"), configHolder.getProperties().getProperty("TRANGLO_PASSWORD"), trans_id);

			return resp;

		} catch (Exception ex) {
			oLog.error("[Error in  curl][" + Url + "] [postData][trans_id:" + trans_id + ",msisdn:" + "62"+msisdn + ",amount:" + amount1 + "]" + ex);
			ex.printStackTrace();
			return "";
		}
	}

	public String checkStatusTranglo(String Url, String trans_id) {
		try {
			final ConfigHolder configHolder = new ConfigHolder();
			
			URL url = new URL(Url);
			Service service = new Service();
			EPin_ReloadSoap_BindingStub stub = new EPin_ReloadSoap_BindingStub(url, service);
			stub.setTimeout(60000);
			String resp = stub.transaction_Inquiry(trans_id, configHolder.getProperties().getProperty("TRANGLO_USERNAME"), configHolder.getProperties().getProperty("TRANGLO_PASSWORD"));

			return resp;

		} catch (Exception ex) {
			oLog.error("[Error in  curl][" + Url + "] [postData][trans_id:" + trans_id + "]" + ex);
			ex.printStackTrace();
			return "";
		}
	}

	public String reloadMobilePulsa(String url, String urlParameters) {
		
		try {
			final ConfigHolder configHolder = new ConfigHolder();
			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/xml");
			con.setRequestProperty("Accept", "application/xml");
			con.setReadTimeout(50000);
			con.setConnectTimeout(50000);
			con.setDoOutput(true);
			Authenticator.setDefault(new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(configHolder.getProperties().getProperty("MOBILE_PULSA_USERNAME"), configHolder.getProperties().getProperty("MOBILE_PULSA_PASSWORD").toCharArray());
				}
			});
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
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

				return response.toString();
			} else if (responseCode == 201) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				System.out.println(response.toString());
				return response.toString();

			} else {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				System.out.println(response.toString());
				return response.toString();

			}
		} catch (SocketTimeoutException se) {
			se.printStackTrace();
			oLog.error("[TimeOutError in  curl][" + url + "] [postData][" + urlParameters + "]" + se);
			return "TIMEOUT";
		} catch (Exception e) {
			e.printStackTrace();
			oLog.error("[Error in  curl][" + url + "] [postData][" + urlParameters + "]" + e);
			return "";
		}
	}

	// *****************************************************SSL_SECURE_CONNECTION*******************************************************//
	@SuppressWarnings("unused")
	private static String sendPost(String url, String urlParameters) throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}

		} };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		HostnameVerifier allHostsValid = new HostnameVerifier() {

			@Override
			public boolean verify(String hostname, SSLSession session) {
				System.out.println("allHostsValid hostname :" + hostname);
				System.out.println("allHostsValid SSLSession : " + session);
				return true;
			}
		};

		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Authorization", "Basic UmF0aW9uYWxoZWFkczpSYXRpb25hbGhlYWRzQG1wMTIz");
		con.setReadTimeout(50000);
		con.setConnectTimeout(50000);
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		System.out.println("URL Parameter : " + urlParameters);

		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
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

			return response.toString();
		} else if (responseCode == 201) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			System.out.println(response.toString());
			return response.toString();

		} else {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			System.out.println(response.toString());
			return response.toString();

		}
	}
	// ******************************************************************************************************************************//
}
