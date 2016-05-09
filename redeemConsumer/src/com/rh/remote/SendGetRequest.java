package com.rh.remote;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendGetRequest {
	private static Log oLog = LogFactory.getLog(SendGetRequest.class);

	public String sendRequest(String URL) {
		try {
			URL url = new URL(URL);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.addRequestProperty("User-Agent", "curl/7.19.7 (x86_64-redhat-linux-gnu) libcurl/7.19.7 NSS/3.14.3.0 zlib/1.2.3 libidn/1.18 libssh2/1.4.2");
			// urlConnection.addRequestProperty(key, value);
			urlConnection.setReadTimeout(90000);
			urlConnection.setConnectTimeout(90000);
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line = "";
			String temp = "";
			while ((line = in.readLine()) != null) {
				temp = temp + line;
			}
			in.close();
			oLog.info("|request=" + URL + "| resp=" + temp.toString());
			System.out.println(new Date() + " |request=" + URL + "| resp=" + temp.toString());

			return temp.toString();

		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			oLog.error("[Timeout][" + URL + "] " + e);
			System.out.println(new Date() + "|request=" + URL + "|resp=TIMEOUT");
			return "TIMEOUT";
		} catch (SocketException e) {
			e.printStackTrace();
			oLog.error("[Timeout][" + URL + "] " + e);
			System.out.println(new Date() + "|request=" + URL + "|resp=TIMEOUT");
			return "TIMEOUT";
		} catch (IOException e) {
			e.printStackTrace();
			oLog.error("Error in  curl" + URL + "  " + e);
			return null;
		}
	}

	public String sendRequest(String URL, String postData) {
		try {
			URL url = new URL(URL);
			System.out.println(new Date() + ":send request" + URL);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.addRequestProperty("User-Agent", "curl/7.19.7 (x86_64-redhat-linux-gnu) libcurl/7.19.7 NSS/3.14.3.0 zlib/1.2.3 libidn/1.18 libssh2/1.4.2");
			// urlConnection.addRequestProperty(key, value);
			urlConnection.setReadTimeout(90000);
			urlConnection.setConnectTimeout(90000);
			urlConnection.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
			wr.write(postData);
			wr.flush();

			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line = "";
			String temp = "";
			while ((line = in.readLine()) != null) {
				temp = temp + line;
			}
			in.close();

			oLog.info("|request=" + URL + "| resp=" + temp.toString());
			System.out.println(new Date() + "|request=" + URL + " |resp=" + temp.toString());

			return temp.toString();

		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			oLog.error("[Timeout][" + URL + "] [postData][" + postData + "]" + e);
			System.out.println(new Date() + "|request=" + URL + "|resp=TIMEOUT");
			return "TIMEOUT";
		} catch (SocketException e) {
			e.printStackTrace();
			oLog.error("[Timeout][" + URL + "] [postData][" + postData + "]" + e);
			System.out.println(new Date() + "|request=" + URL + "|resp=TIMEOUT");
			return "TIMEOUT";
		} catch (IOException e) {
			e.printStackTrace();
			oLog.error("[Error in  curl][" + URL + "] [postData][" + postData + "]" + e);
			return null;
		}
	}

	public String sendPost(String url) {

		try {
			System.setProperty("https.protocols", "TLSv1.1");

			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Flipkart-Gifting-Client-Id", "sum8189248");
			con.setRequestProperty("Flipkart-Gifting-Client-Token", "O6kP3UBmERooXYcWEt4H");
			// con.setRequestProperty("Flipkart-Gifting-Client-Id", "sum5570257");
			// con.setRequestProperty("Flipkart-Gifting-Client-Token", "xcEEnXpeabrXBQumkrxh");
			con.setRequestProperty("Content-Type", "application/json");
			con.setReadTimeout(90000);
			con.setConnectTimeout(90000);

			con.setDoOutput(true);

			int responseCode = con.getResponseCode();
			// System.out.println("\nSending 'POST' request to URL : " + url);
			// System.out.println("Response Code : " + responseCode);

			if (responseCode == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				oLog.info("|request=" + url + "|resp=" + response.toString());
				System.out.println(new Date() + "|request=" + url + "|resp=" + response.toString());

				return response.toString();
			} else if (responseCode == 201) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				oLog.info("|request=" + url + "|resp=" + response.toString());
				System.out.println(new Date() + "|request=" + url + "|resp=" + response.toString());
				return response.toString();

			} else {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				oLog.info("|request=" + url + "|resp=" + response.toString());
				System.out.println(new Date() + "|request=" + url + "|resp=" + response.toString());
				return response.toString();
			}

		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			oLog.error("[Timeout][" + url + "] " + e);
			System.out.println(new Date() + "|request=" + url + "|resp=TIMEOUT");
			return "TIMEOUT";
		} catch (SocketException e) {
			e.printStackTrace();
			oLog.error("[Timeout][" + url + "] " + e);
			System.out.println(new Date() + "|request=" + url + "|resp=TIMEOUT");
			return "TIMEOUT";
		} catch (IOException e) {
			oLog.error("[Error in  curl][" + url + "]", e);
			e.printStackTrace();
			return null;
		}
	}

	public String sendPost(String url, String postData) {

		try {
			System.setProperty("https.protocols", "TLSv1.1");

			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Flipkart-Gifting-Client-Id", "sum8189248");
			con.setRequestProperty("Flipkart-Gifting-Client-Token", "O6kP3UBmERooXYcWEt4H");
			con.setRequestProperty("Content-Type", "application/json");
			con.setReadTimeout(90000);
			con.setConnectTimeout(90000);
			con.setDoOutput(true);

			DataOutputStream osw = new DataOutputStream(con.getOutputStream());
			osw.writeBytes(postData);
			osw.flush();
			osw.close();

			int responseCode = con.getResponseCode();

			if (responseCode == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				oLog.info("|request=" + url + "|postData=" + postData + "|resp=" + response.toString());
				System.out.println(new Date() + "|request=" + url + "|resp=" + response.toString());
				return response.toString();

			} else if (responseCode == 201) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				oLog.info("|request=" + url + "|postData=" + postData + "|resp=" + response.toString());
				System.out.println(new Date() + "|request=" + url + "|resp=" + response.toString());
				return response.toString();

			} else {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				oLog.info("|request=" + url + "|postData=" + postData + "|resp=" + response.toString());
				System.out.println(new Date() + "|request=" + url + "|resp=" + response.toString());
				return response.toString();
			}

		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			oLog.error("[Timeout][" + url + "] [postData][" + postData + "]" + e);
			System.out.println(new Date() + "|request=" + url + "|resp=TIMEOUT");
			return "TIMEOUT";
		} catch (SocketException e) {
			e.printStackTrace();
			oLog.error("[Timeout][" + url + "] [postData][" + postData + "]" + e);
			System.out.println(new Date() + "|request=" + url + "|resp=TIMEOUT");
			return "TIMEOUT";
		} catch (IOException e) {
			oLog.error("[Error in  curl][" + url + "] [postData][" + postData + "]" + e);
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * public String sendRequestOxygen(String url, String postData) { try { System.setProperty("https.protocols", "TLSv1.1"); URL obj = new URL(url); HttpsURLConnection con = (HttpsURLConnection)
	 * obj.openConnection(); con.setRequestMethod("POST"); con.setRequestProperty("Authorization", "Basic UmF0aW9uYWxoZWFkczpSYXRpb25hbGhlYWRzQG1wMTIz"); con.setRequestProperty("Content-Type",
	 * "application/x-www-form-urlencoded"); con.setReadTimeout(50000); con.setConnectTimeout(50000); con.setDoOutput(true); DataOutputStream osw = new DataOutputStream(con.getOutputStream());
	 * osw.writeBytes(postData); osw.flush(); osw.close(); int responseCode = con.getResponseCode(); System.out.println("\nSending 'POST' request to URL : " + url); System.out.println(
	 * "Response Code : " + responseCode); if (responseCode == 200) { BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); String inputLine; StringBuffer response = new
	 * StringBuffer(); while ((inputLine = in.readLine()) != null) { response.append(inputLine); } in.close(); oLog.info(new Date() + "|request=" + url + "|postData=" + postData + "|resp=" +
	 * response.toString()); return response.toString(); } else if (responseCode == 201) { BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); String inputLine;
	 * StringBuffer response = new StringBuffer(); while ((inputLine = in.readLine()) != null) { response.append(inputLine); } in.close(); oLog.info(new Date() + "|request=" + url + "|postData=" +
	 * postData + "|resp=" + response.toString()); return response.toString(); } else { BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream())); String inputLine;
	 * StringBuffer response = new StringBuffer(); while ((inputLine = in.readLine()) != null) { response.append(inputLine); } in.close(); oLog.info(new Date() + "|request=" + url + "|postData=" +
	 * postData + "|resp=" + response.toString()); return response.toString(); } } catch (Exception e) { oLog.error("[Error in  curl][" + url + "] [postData][" + postData + "]" + e);
	 * e.printStackTrace(); return ""; } }
	 */

	public void SendMessage(String URL) {
		try {

			URL url = new URL(URL);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setReadTimeout(90000);
			urlConnection.setConnectTimeout(90000);
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line = "";
			String temp = "";
			while ((line = in.readLine()) != null) {
				temp = temp + line;
			}
			in.close();
			oLog.info("|request=" + URL + " | resp=" + temp.toString());
			System.out.println(new Date() + "|request=" + URL + " | resp=" + temp.toString());

		} catch (Exception e) {
			e.printStackTrace();
			oLog.error("Error in  curl" + URL + "  " + e.getMessage());
		}
	}

	public String sendRequestOxygen(String URL, String postData) {
		try {

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
				public boolean verify(String hostname, SSLSession session) {
					System.out.println("allHostsValid hostname :" + hostname);
					System.out.println("allHostsValid SSLSession : " + session);
					return true;
				}
			};

			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

			System.setProperty("https.protocols", "TLSv1");

			URL url = new URL(URL);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConnection.setRequestProperty("Authorization", "Basic UmF0aW9uYWxoZWFkczpSYXRpb25hbGhlYWRzQG1wMTIz");
			urlConnection.setReadTimeout(90000);
			urlConnection.setConnectTimeout(90000);
			urlConnection.setDoOutput(true);

			DataOutputStream osw = new DataOutputStream(urlConnection.getOutputStream());
			osw.writeBytes(postData);
			osw.flush();
			osw.close();

			int responseCode = urlConnection.getResponseCode();
			System.out.println("Response Code : " + responseCode);

			if (responseCode == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				oLog.info("|request=" + URL + "|postData=" + postData + "|resp=" + response.toString());
				System.out.println(new Date() + "|request=" + URL + "|postData=" + postData + "|resp=" + response.toString());

				return response.toString();

			} else if (responseCode == 201) {
				BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				oLog.info("|request=" + URL + "|postData=" + postData + "|resp=" + response.toString());
				System.out.println(new Date() + "|request=" + URL + "|postData=" + postData + "|resp=" + response.toString());
				return response.toString();

			} else {
				BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				oLog.info("|request=" + URL + "|postData=" + postData + "|resp=" + response.toString());
				System.out.println(new Date() + "|request=" + URL + "|postData=" + postData + "|resp=" + response.toString());
				return response.toString();
			}

		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			oLog.error("[Timeout][" + URL + "] [postData][" + postData + "]" + e);
			return "TIMEOUT";
		} catch (SocketException e) {
			e.printStackTrace();
			oLog.error("[Timeout][" + URL + "] [postData][" + postData + "]" + e);
			return "TIMEOUT";
		}  catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
			e.printStackTrace();
			oLog.error("[Error in  curl][" + URL + "] [postData][" + postData + "]" + e);
			return null;
		}
	}
}