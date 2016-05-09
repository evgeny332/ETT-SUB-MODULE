package com.rh.Controller;

/*
 * This is a rough class. please do not compile it.
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Try {
	private static Map<String, String> myMap;

	public static void main(String[] args) {

		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(new File("src/hashfile")));
			String li = null;
			myMap = new HashMap<String, String>();
			while ((li = reader.readLine()) != null) {
	            if (li.contains("=")) {
	                String[] strings = li.split("=");
	                myMap.put(strings[0],strings[1]);
	            }
	        }
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String s = "vodafonertyis";
		String pu = getoperator(s);

		System.out.println(pu);
	}

	private static String getoperator(String operator) {
		if (operator != null) {
			String[] opera = operator.split(",");
			for (Map.Entry<String, String> entry : myMap.entrySet()) {
				if (opera[0].toUpperCase().contains(entry.getKey())) {
					operator = entry.getValue();
					break;
				}
			}
			return operator;
		} else {
			return operator;
		}
	}
}
