package com.rh.inmobi.response.parser;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.rh.inmobi.response.NativeResponse;
import com.rh.utils.InternalUtil;

/**
 * This class can be used to obtain an ArrayList of NativeResponse objects, from
 * the raw response(as String), obtained from the InMobi API 2.0 Server
 * response.
 * 
 * @author rishabhchowdhary
 * 
 */
public class JSONNativeResponseParser {

	private final Gson GSON = new Gson();

	/**
	 * This function converts the String response to NativeResponse
	 * objects.</br>
	 * 
	 * <p>
	 * <b>Note:</b>
	 * </p>
	 * If the server returned a no-fill, or there was a parsing error, this
	 * function would return an empty arraylist.
	 * 
	 * @param response
	 *            The String response as obtained from InMobi ad server.
	 * @return ArrayList of NativeResponse objects.
	 */
	public ArrayList<NativeResponse> fetchNativeAdsFromResponse(String response) {
		ArrayList<NativeResponse> ads = new ArrayList<NativeResponse>();
		if (!InternalUtil.isBlank(response)) {
			try {
//				System.out.println("response:"+response);
				JSONObject rootObject = new JSONObject(response);
				JSONArray responA = new JSONArray();
				if (rootObject != null) {
					responA = rootObject.getJSONArray("ads");
					for (int i=0; i<responA.length(); i++) {
						NativeResponse nativeAd = new NativeResponse();
						JSONObject pubCo = responA.getJSONObject(i);
						nativeAd.contextCode = pubCo.getString("contextCode").toString().trim();
						nativeAd.ns = pubCo.getString("namespace").toString().trim();
						nativeAd.pubContent = pubCo.getString("pubContent").toString().trim();
						
//						System.out.println(nativeAd.pubContent);
						if (nativeAd.isValid())
							ads.add(nativeAd);
					}
				}
			} catch (Exception e) {
				 e.printStackTrace();
			}

		}
		return ads;
	}

	private JsonObject toNativeResponseJson(String response) {

		return GSON.fromJson(response,
				JSONNativeResponseParser.getNativeRootObjectClassToken());
	}

	private static Type getNativeRootObjectClassToken() {
		return new TypeToken<JsonObject>() {
		}.getType();
	}
}
