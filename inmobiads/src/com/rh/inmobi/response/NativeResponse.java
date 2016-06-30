package com.rh.inmobi.response;

import org.json.JSONObject;

import com.rh.utils.InternalUtil;
/**
 * This class is used to construct Native response as a plain Java object.
 *
 */
public class NativeResponse extends AdResponse implements Validator {

	/**
	 * The 'namespace' parameter associated with this native ad unit. Use this
	 * String value in the client side code to trigger javascript function
	 * callbacks in the WebView.
	 */
	public String ns;
	/**
	 * The html/javascript code, which is to be executed in a hidden WebView on
	 * the client side. Please note that this code doesn't perform any rendering
	 * of the Native ad itself(<i>that responsibility is yours</i>) but this
	 * code must be used to track impression/clicks from the html/js. <br/>
	 * <b>Refer:</b> <br/>
	 * iOS: https://github.com/InMobi/iOS-Native-Samplecode-InMobi/ <br/>
	 * Android: https://github.com/InMobi/android-Native-Samplecode-InMobi/ <br/>
	 * examples to understand triggering of InMobi impression/clicks.
	 * 
	 * @warning <b>Please do not tamper with this String, and load it as it is
	 *          on the client side. Tampering with the String to scrape out any
	 *          specific html/javascript components may result in incorrect
	 *          results on our portal, or may also lead to your site being
	 *          marked as invalid.</b>
	 */
	public String contextCode;

	/**
	 * The Base64 encoded String, which contains the native metadata info. This
	 * info is the same as the "template" created on the InMobi dashboard,
	 * containing the K-V pair info of fields such as "title", "subtitle",
	 * "icon", "screenshots" etc.
	 */
	public String pubContent;

	@Override
	public String toString() {
		return "{\"pubContent\"" + ":" + "\"" + pubContent + "\"" + ","
				+ "\"contextCode\"" + ":" + "\"" + contextCode + "\"" + ","
				+ "\"namespace\"" + ":" + "\"" + ns + "\"" + "}";
	}

	/**
	 * Use this method to check if this object has all the required parameters
	 * present. If this method returns false, the object generated after JSON
	 * parsing would be discarded.
	 * 
	 * @return True, If the mandatory params are present.False, otherwise.
	 */
	public boolean isValid() {
		boolean isValid = false;
		if (!InternalUtil.isBlank(contextCode) && !InternalUtil.isBlank(ns)
				&& !InternalUtil.isBlank(pubContent)) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * Use this method to convert the Base64 encoded pubContent to a JsonObject.
	 * You may use jsonObject.get("<key-name>") to obtain the required metadata
	 * value.
	 * 
	 * @return
	 */
	public JSONObject convertPubContentToJsonObject(String pubContent1) {
		JSONObject jsonObject = null;
		if (pubContent1 != null) {
			byte[] bytes = org.apache.tomcat.util.codec.binary.Base64.decodeBase64(pubContent1);
			String jsonString;
			try {
				jsonString = new String(bytes, "UTF-8") + "\n";
				jsonObject = new JSONObject(jsonString);
//				System.out.println("json: "+jsonString);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}
	
	/*public Collection convertPubContentToJsonObject(String temp) {
		// TODO Auto-generated method stub
		return null;
	}*/

}
