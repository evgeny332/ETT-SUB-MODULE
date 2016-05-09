package com.rh.ett.uttility;

import java.io.ByteArrayInputStream;
import java.util.zip.GZIPInputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rh.main.Receiver;

public class ApiImpl{
	private static Log log = LogFactory.getLog(ApiImpl.class);
	public String unCompressData(String str) {
		String result = null;
			try {
					byte[] bytes = Base64.decodeBase64(str.getBytes());
					GZIPInputStream zi = null;
				try {
						zi = new GZIPInputStream(new ByteArrayInputStream(bytes));
						result = IOUtils.toString(zi);
					} finally {
						IOUtils.closeQuietly(zi);
					}
				}catch(Exception ex) {
				System.out.println("Error in unCompressData|"+ex);
				log.error("Error in unCompressData|"+ex+"| Data|"+str);
				ex.printStackTrace();
				result="";
			}
			return result;
		}
	}
