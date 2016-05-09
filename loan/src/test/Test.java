package test;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String isSuccess = "PENDIN";
		String status = isSuccess.equalsIgnoreCase("SUCCESS") ? isSuccess.toUpperCase() : "SUCESS_P";
       System.out.println(status);
	}

}
