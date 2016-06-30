package com.rh.inmobi.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONObject;

import com.rh.inmobi.request.Request;

public class RequestResponseManager {

	private ErrorCode errorCode = null;
	private static final String INMOBI_API_2_0_URL = "http://api.w.inmobi.com/showad/v2";
	private boolean isRequestInProgress;

	public String fetchAdResponseAsString(Request request) {

		String response = null;
		errorCode = null;
		try {
			// System.out.println(request);
			InputStream is = fetchAdResponseAsStream(request);
//			System.out.println(is);
			response = convertStreamToString(is);
//			System.out.println(response);
		} catch (Exception e) {
			System.out.println("Error in fetchAdResponseAsString: ");
			e.printStackTrace();
			setErrorCode(null);
		}
		return response;
	}

	public InputStream fetchAdResponseAsStream(Request request) {
		// TODO Auto-generated method stub
		isRequestInProgress = true;
		JSONObject obj = JSONPayloadCreator.generateInMobiAdRequestPayload(request);
		errorCode = null;
		InputStream is = null;

		if (obj != null) {
			try {
				String postBody = obj.toString();
				URL serverUrl = new URL(INMOBI_API_2_0_URL);
				HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();
				setConnectionParams(connection, request);
//				System.out.println(postBody);
				postData(connection, postBody);
				if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					is = connection.getInputStream();
				} else {
//					System.out.println(connection.getInputStream());
					setErrorMessage(connection);
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
				errorCode = new ErrorCode(ErrorCode.MALFORMED_URL, e.getLocalizedMessage());
			} catch (IOException e) {
				e.printStackTrace();
				errorCode = new ErrorCode(ErrorCode.IO_EXCEPTION, e.getLocalizedMessage());
			}

		}
		isRequestInProgress = false;
		return is;
	}

	private void setErrorMessage(HttpURLConnection connection) {
		int code = ErrorCode.UNKNOWN;
		String msg = "";
		try {
			int responseCode = connection.getResponseCode();
			switch (responseCode) {
			case 400:
				code = ErrorCode.INVALID_REQUEST;
				msg = "Invalid Request. Please check the mandatory parameters passed in the request.\n" + "Mandatory params include:" + "1. Property ID must be valid. Please check on InMobi portal\n" + "2. User Agent string must be a valid Mobile User Agent.\n" + "3. Carrier IP passed must be a valid Mobile Country Code.";
				break;
			case 204:
				code = ErrorCode.NO_FILL;
				msg = "Server returned a no fill. Please try again later.";
			case 504:
				code = ErrorCode.GATEWAY_TIME_OUT;
				msg = "Served timed out your request. Please try again later.";
			default:
				code = responseCode;
				msg = "Serve returned response code:" + responseCode;
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			code = ErrorCode.IO_EXCEPTION;
			msg = e.getLocalizedMessage();
		}
		errorCode = new ErrorCode(code, msg);
	}

	private static void setConnectionParams(HttpURLConnection connection, Request request) throws ProtocolException {
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setConnectTimeout(60 * 1000);
		connection.setReadTimeout(30 * 1000);
		connection.setUseCaches(false);
		connection.setRequestProperty("Content-Type", "application/json");
//		connection.setRequestProperty("x-device-user-agent", request.getDevice().getUserAgent());
		connection.setRequestProperty("x-forwarded-for", request.getDevice().getCarrierIP());
//		System.out.println(connection.getRequestProperties());
	}

	private void postData(HttpURLConnection connection, String postBody) throws IOException {

		connection.setRequestProperty("Content-Length", Integer.toString(postBody.length()));

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
			writer.write(postBody);
		} finally {
			closeResource(writer);
		}
	}

	private void closeResource(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String convertStreamToString(InputStream is) {
		BufferedReader reader = null;
		String response = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder adResponseStrBuff = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				adResponseStrBuff.append(line).append("\n");
			}
			response = adResponseStrBuff.toString();

		} catch (IOException e) {
			System.out.println("Error in convertStreamToString: ");
			e.printStackTrace();
		} finally {
			closeResource(reader);
		}
		return response;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 
	 * @return true, if the request is in progress. else returns false.
	 */
	public boolean isRequestInProgress() {
		return isRequestInProgress;
	}

}
