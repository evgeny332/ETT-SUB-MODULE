package com.rh.inmobi.ads;

import java.util.ArrayList;

import com.rh.inmobi.net.ErrorCode;
import com.rh.inmobi.request.Request;
import com.rh.inmobi.request.enums.AdRequest;
import com.rh.inmobi.response.NativeResponse;
import com.rh.inmobi.response.parser.JSONNativeResponseParser;

/**
 * Publishers may use this class to request Native ads from InMobi.
 * 
 */
public class Native extends AdFormat {

	private JSONNativeResponseParser jsonParser = new JSONNativeResponseParser();

	public Native() {
		requestType = AdRequest.NATIVE;
	}

	private ArrayList<NativeResponse> loadRequestInternal(Request request) {
		errorCode = null;
		ArrayList<NativeResponse> ads = null;
		request.setRequestType(requestType);
		String response = manager.fetchAdResponseAsString(request);
//		System.out.println(response);
		errorCode = manager.getErrorCode();
		ads = jsonParser.fetchNativeAdsFromResponse(response);
		isRequestInProgress.set(false);
		if (ads == null) {
			errorCode = new ErrorCode(ErrorCode.NO_FILL, "Server returned a no-fill.");
		} else if (ads.size() == 0) {
			errorCode = new ErrorCode(ErrorCode.NO_FILL, "Server returned a no-fill.");
		}
		return ads;
	}
	
	private String loadRequestInternal1(Request request) {
		errorCode = null;
		request.setRequestType(requestType);
		String response = manager.fetchAdResponseAsString(request);
//		System.out.println(response);
		errorCode = manager.getErrorCode();
//		ads
//		ads = jsonParser.fetchNativeAdsFromResponse(response);
		isRequestInProgress.set(false);
//		if (ads == null) {
//			errorCode = new ErrorCode(ErrorCode.NO_FILL, "Server returned a no-fill.");
//		} else if (ads.size() == 0) {
//			errorCode = new ErrorCode(ErrorCode.NO_FILL, "Server returned a no-fill.");
//		}
		return response;
	}

	/**
	 * This function loads native ads synchronously.
	 * 
	 * @note Please check for isRequestInProgress to false, before calling this
	 *       function.<br/>
	 *       The function returns null if the request was already in progress.
	 *       Please also provide a valid Request Object. You may check if the
	 *       IMAdRequest object is valid by calling isValid() on the object.
	 * @return ArrayList containing the NativeResponse objects.
	 */
	public synchronized ArrayList<NativeResponse> loadRequest(Request request) {
		ArrayList<NativeResponse> ads = null;

		if (canLoadRequest(request, requestType) == true) {
			ads = loadRequestInternal(request);
		}
		return ads;
	}
	
	public synchronized String getloadRequest(Request request) {
		String pubContent = null;

		if (canLoadRequest(request, requestType) == true) {
			pubContent = loadRequestInternal1(request);
		}
		return pubContent;
	}

}
