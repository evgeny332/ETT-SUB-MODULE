package com.rh.inmobi.ads;

import java.io.InputStream;
import java.util.ArrayList;

import com.rh.inmobi.net.ErrorCode;
import com.rh.inmobi.request.Request;
import com.rh.inmobi.request.enums.AdRequest;
import com.rh.inmobi.response.BannerResponse;
import com.rh.inmobi.response.parser.SAXBannerResponseParser;

/**
 * Publishers can use this class instance to request banner ads from InMobi.
 * 
 * @note Please pass the mandatory request params.
 * @author rishabhchowdhary
 * 
 */
public class Banner extends AdFormat {

	private SAXBannerResponseParser xmlParser = new SAXBannerResponseParser();

	public Banner() {
		requestType = AdRequest.BANNER;
	}

	/**
	 * This method is internally called from loadRequest.
	 * 
	 * @param request
	 *            The Request object
	 * @param requestType
	 *            One of Banner,Interstitial or Native ad-format
	 * @return
	 */
	protected ArrayList<BannerResponse> loadRequestInternal(
			Request request, AdRequest requestType) {
		ArrayList<BannerResponse> ads = null;
		errorCode = null;
		request.setRequestType(requestType);
		InputStream is = manager.fetchAdResponseAsStream(request);
		errorCode = manager.getErrorCode();
		ads = xmlParser.fetchBannerAdsFromResponse(is, false);
		isRequestInProgress.set(false);
		if (ads == null) {
			errorCode = new ErrorCode(ErrorCode.NO_FILL,
					"Server returned a no-fill.");
		} else if (ads.size() == 0) {
			errorCode = new ErrorCode(ErrorCode.NO_FILL,
					"Server returned a no-fill.");
		}
		return ads;
	}

	/**
	 * This function loads banner ads synchronously.
	 * 
	 * @param request
	 *            The Request object, containing the required request params.
	 * @NotNull request
	 * @note Please check for isRequestInProgress to false, before calling this
	 *       function.<br/>
	 *       The function returns null if the request was already in progress.
	 *       Please also provide a valid Request Object. You may check if the
	 *       IMAdRequest object is valid by calling isValid() on the object.
	 * @return ArrayList containing the BannerResponse objects.
	 */

	public synchronized ArrayList<BannerResponse> loadRequest(
			Request request) {

		ArrayList<BannerResponse> ads = null;
		if (canLoadRequest(request, requestType) == true) {
			ads = loadRequestInternal(request, requestType);
		}

		return ads;
	}
	
	//Temporary Code
	
	public synchronized String getloadRequest(Request request) {
		String pubContent = null;

		if (canLoadRequest(request, requestType) == true) {
			pubContent = loadRequestInternal1(request);
		}
		return pubContent;
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
}
