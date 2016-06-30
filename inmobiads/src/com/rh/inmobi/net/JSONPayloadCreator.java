package com.rh.inmobi.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rh.inmobi.request.Data;
import com.rh.inmobi.request.Device;
import com.rh.inmobi.request.Geo;
import com.rh.inmobi.request.Impression;
import com.rh.inmobi.request.Property;
import com.rh.inmobi.request.Request;
import com.rh.inmobi.request.Slot;
import com.rh.inmobi.request.User;
import com.rh.inmobi.request.UserSegment;
import com.rh.inmobi.request.enums.AdRequest;
import com.rh.inmobi.request.enums.Gender;

public class JSONPayloadCreator {

	public static JSONObject generateInMobiAdRequestPayload(Request request) {

		if (request == null) {
			System.out.println("Request object cannot be null");
			return null;
		}
		if (!request.isValid()) {
			System.out.println("Please provide valid parameters in the request object");
			return null;
		}
		JSONObject mainObject = new JSONObject();
		// request format
		try {
			if (request.getRequestType() != AdRequest.NATIVE) {

				mainObject.put("responseformat", "axml");
			} else {
				mainObject.put("responseformat", "native");
			}

			// site format

			JSONObject propertyObject = getPropertyJSON(request.getProperty());
			if (propertyObject != null) {

				mainObject.put("site", propertyObject);
			}

			JSONArray impArray = getImpressionJSON(request.getImpression(), request.getRequestType());
			if (impArray != null) {
				mainObject.put("imp", impArray);
			}

			JSONObject userObject = getUserJSON(request.getUser());
			if (userObject != null) {
				mainObject.put("user", userObject);
			}

			JSONObject deviceObject = getDeviceJSON(request.getDevice());
			if (deviceObject != null) {
				mainObject.put("device", deviceObject);
			}
		} catch (JSONException e) {
		}

		return mainObject;

	}

	private static JSONObject getPropertyJSON(Property property) throws JSONException {
		JSONObject siteObject = null;
		if (property != null) {
			siteObject = new JSONObject();
			String propertyId = property.getPropertyId();
			if (propertyId != null) {
				siteObject.put("id", propertyId);
			}

		}
		return siteObject;
	}

	private static JSONArray getImpressionJSON(Impression imp, AdRequest type) throws JSONException {
		JSONArray impArray = null;
		if (imp != null) {
			JSONObject impressionObject = new JSONObject();

			Slot banner = imp.getSlot();
			if (type != AdRequest.NATIVE) {
				JSONObject bannerObject = new JSONObject();

				String pos = banner.getPosition();
				if (pos != null) {
					bannerObject.put("pos", pos);
				}
				bannerObject.put("adsize", banner.getAdSize());
				impressionObject.put("banner", bannerObject);
			}
			// displaymanager/ver values have a default value, ads too has a
			// default value.
			impressionObject.put("ads", imp.getNoOfAds());
			impressionObject.put("displaymanager",imp.getDisplayManager());
			impressionObject.put("displaymanagerver",imp.getDisplayManagerVersion());

			// adtype=int, for interstitial ads.
			if (type == AdRequest.INTERSTITIAL) {
				impressionObject.put("adtype", "int");
			}
			// impression object is an array
			impArray = new JSONArray();
			impArray.put(impressionObject);

		}
		return impArray;
	}

	private static JSONObject getDeviceJSON(Device device) throws JSONException {
		JSONObject deviceObject = null;
		if (device != null) {
			deviceObject = new JSONObject();
			if (device != null) {
				String ip = device.getCarrierIP();
				if (ip != null) {
					deviceObject.put("ip", ip);
				}
				String ua = device.getUserAgent();
				if (ua != null) {
					deviceObject.put("ua", ua);
				}
				// for Android sites..
				String gpid = device.getGpId();
				if (gpid != null) {
					deviceObject.put("gpid", gpid);
				}
				String locale = device.getLocale();
				if (locale !=null) {
					deviceObject.put("locale", locale);
				}
			}

			Geo geo = device.getGeo();
			if (geo != null && geo.isValid()) {
				JSONObject geoObject = new JSONObject();
				geoObject.put("lat", geo.getLatitude());
				geoObject.put("lon", geo.getLongitude());
				geoObject.put("accu", geo.getAccuracy());
				deviceObject.put("geo", geoObject);
			}
		}
		return deviceObject;
	}

	private static JSONObject getUserJSON(User user) throws JSONException {
		JSONObject userObject = null;
		if (user != null) {
			userObject = new JSONObject();
			if (user.getYearOfBirth() > 0) {
				userObject.put("yob", user.getYearOfBirth());
			}
			Gender gender = user.getGender();
			if (gender != Gender.UNKNOWN) {
				if (gender == Gender.MALE) {
					userObject.put("gender", "m");
				} else {
					userObject.put("gender", "f");
				}
			}
			JSONObject dataObject = new JSONObject();
			Data data = user.getData();
			if (data != null) {
				String ID = data.getID();
				if (ID != null) {
					dataObject.put("id", ID);
				}
				String name = data.getName();
				if (name != null) {
					dataObject.put("name", name);
				}
				UserSegment segmentObject = data.getUserSegment();
				if (segmentObject != null) {
					if (segmentObject.getUserSegmentArray() != null) {
//						dataObject.put("segment",new JSONObject());
						JSONArray dataArray = new JSONArray();
						dataArray.put(dataObject);
						userObject.put("data", dataArray);
					}
				}
			}
		}
		return userObject;
	}

}
