package com.rh.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class ReadUrl {
	static Logger error = Logger.getLogger("error");
	static Logger logger = Logger.getLogger("DATA");
	public String readURL(String url) {
		
		String result = "";
		try {
			URL urldemo = new URL(url);
			URLConnection yc = urldemo.openConnection();
			yc.addRequestProperty("Content-Type", "Application/json");
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

			StringBuilder sb = new StringBuilder();
			sb.append(in.readLine() + "\n");
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line + "\n");
			}

			result = sb.toString();
			logger.info("done parsing URL");
		} catch (Exception e) {
			e.printStackTrace();
			error.info("Read Url Error "+e);
		}
		return result;
	}
}
