package com.rh.inmobi.api;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rh.dao.DBQueries;
import com.rh.inmobi.ads.Banner;
import com.rh.inmobi.ads.Interstitial;
import com.rh.inmobi.ads.Native;
import com.rh.inmobi.request.Data;
import com.rh.inmobi.request.Device;
import com.rh.inmobi.request.Impression;
import com.rh.inmobi.request.Property;
import com.rh.inmobi.request.Request;
import com.rh.inmobi.request.Slot;
import com.rh.inmobi.request.User;
import com.rh.inmobi.request.UserSegment;
import com.rh.inmobi.request.enums.AdRequest;
import com.rh.inmobi.request.enums.Gender;
import com.rh.inmobi.response.BannerResponse;
import com.rh.inmobi.response.NativeResponse;
import com.rh.utils.CreateJSONPayload;

@Path("/{adsType}/")
public class NativeAds {

	public int adCount;
	private final static Logger log = Logger.getLogger(NativeAds.class);
	DBQueries db = new DBQueries();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAds2(@Context UriInfo info, @PathParam("adsType") String adType, @Context HttpServletRequest httpServletRequest, String data) {

		System.out.println(adType);
		System.out.println("Request: " + data);
		System.out.println("EttId: " + httpServletRequest.getParameter("ettId"));

		QueryParameters queryParameters = null;

		if (data == null || data.equals("")) {
			MultivaluedMap<String, String> queryParams = info.getQueryParameters();
			queryParameters = new QueryParameters(queryParams, httpServletRequest);
			System.out.println("EttId: " + queryParams.getFirst("ettId"));

		} else {
			queryParameters = new QueryParameters(data, httpServletRequest);
		}

		String resp = null;
		if (queryParameters.isValid) {

			if (adType.equals("InMobi") || adType.equals("native")) {
				resp = getNativeAds(queryParameters);
				log.info("Parameters = { type=" + adType + " " + queryParameters.Log + " adsCount=" + adCount + " }");
			} else if (adType.equals("banner")) {
				resp = getBannerAds(queryParameters);
				log.info("Parameters = { type=" + adType + " " + queryParameters.Log + " }");
			} else if (adType.equals("interstitial")) {
				resp = getInterstitialAds(queryParameters);
				log.info("Parameters = { type=" + adType + " " + queryParameters.Log + " }");
			}

			if (resp != null) {
				if (resp.equals("[]")) {
					log.error("ettId= " + queryParameters.getEttId() + "|resp[]");
					return Response.status(400).entity("Bad Request").build();
				} else {
					log.info("ettId= " + queryParameters.getEttId() + "|response successfully send GET");
					return Response.ok(resp).build();
				}
			} else {
				log.error("ettId= " + queryParameters.getEttId() + "|resp=resp is NULL");
				return Response.status(400).entity("Bad Request").build();
			}
		} else {
			log.error("ettId= " + queryParameters.getEttId() + "|resp=Query Parameters InValid");
			return Response.status(400).entity("Bad Request").build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAds(@Context UriInfo info, @PathParam("adsType") String adType, @Context HttpServletRequest httpServletRequest, String data) {

		System.out.println(adType);
		System.out.println("Request: " + data);

		QueryParameters queryParameters = null;

		if (data == null || data.equals("")) {
			MultivaluedMap<String, String> queryParams = info.getQueryParameters();
			queryParameters = new QueryParameters(queryParams, httpServletRequest);
			System.out.println("EttId: " + queryParams.getFirst("ettId"));
		} else {
			queryParameters = new QueryParameters(data, httpServletRequest);
		}

		String resp = null;
		if (queryParameters.isValid) {

			if (adType.equals("InMobi") || adType.equals("native")) {
				resp = getDummyAd(queryParameters);
				log.info("Parameters = { type=" + adType + " " + queryParameters.Log + " adsCount=" + adCount + " }");
			} else if (adType.equals("banner")) {
				resp = getBannerAds(queryParameters);
				log.info("Parameters = { type=" + adType + " " + queryParameters.Log + " }");
			} else if (adType.equals("interstitial")) {
				resp = getInterstitialAds(queryParameters);
				log.info("Parameters = { type=" + adType + " " + queryParameters.Log + " }");
			}

			if (resp != null) {
				if (resp.equals("[]")) {
					log.error("ettId= " + queryParameters.getEttId() + "|resp[]");
					return Response.status(400).entity("Bad Request").build();
				} else {
					log.info("ettId= " + queryParameters.getEttId() + "|response successfully send POST|");
					return Response.ok(resp).build();
				}
			} else {
				log.error("ettId= " + queryParameters.getEttId() + "|resp is NULL");
				return Response.status(400).entity("Bad Request").build();
			}
		} else {
			log.error("ettId= " + queryParameters.getEttId() + "|resp=Query Parameters InValid");
			return Response.status(400).entity("Bad Request").build();
		}
	}

	private String getDummyAd(QueryParameters queryParameters) {
		
		String resp = db.getAds();

//		List<JsonFormat> list = new ArrayList<JsonFormat>();
//		JsonFormat jf = new JsonFormat();
//		try {
//			
//			jf.setOfferName("Flipkart");
//			jf.setActionUrl("http://dl.flipkart.com/dl/offers?affid=affiliate270&affExtParam1=riebytes");
//			jf.setDescription("Shop the Online Megastore with the free Flipkart Android app");
//			jf.setOfferButtonText("install or use");
//			jf.setImageUrl("https://lh3.googleusercontent.com/REVWVIfDnMlClUnFat1qgVljxhvFxd2azTIIH2sPSmLZI-1KTonBD8niz8fh7pg4WX9U=w300-rw");
//			jf.setNamespace("Flipkart");
//			jf.setScript("script");
//			jf.setRating("4.2");
//			
//			list.add(jf);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("Error in Creating Dummy json: ", e);
//		}
//
//		Gson json = new Gson();
//		resp = json.toJson(list);
		return resp;
	}

	private String getNativeAds(QueryParameters queryParameters) {

		String resp = null;

		Request request = new Request();
		request.setRequestType(AdRequest.NATIVE);

		Property property = null;
		property = new Property(queryParameters.getSiteid());
		request.setProperty(property);

		Impression imp = new Impression(new Slot(15, "top"));
		imp.setInterstitial(false);
		imp.setNoOfAds(10);
		request.setImpression(imp);

		Device device = new Device(queryParameters.getIp(), queryParameters.getUa());
		device.setGpId(queryParameters.getAdvertisingId());
		device.setLocale(queryParameters.getLocale());
		request.setDevice(device);

		HashMap<String, String> userSegmentArray = new HashMap<String, String>();
		userSegmentArray.put("marital", queryParameters.getMarital());

		UserSegment segment = new UserSegment();
		segment.setUserSegmentArray(userSegmentArray);
		User user = new User(queryParameters.getYob(), Gender.UNKNOWN, new Data("1", "Publisher", segment));

		if (queryParameters.getGender().equals("m")) {
			user.setGender(Gender.MALE);
		} else if (queryParameters.getGender().equals("f")) {
			user.setGender(Gender.FEMALE);
		}
		request.setUser(user);

		if (request.isValid()) {

			Native nativeAd = new Native();

			ArrayList<NativeResponse> ads = nativeAd.loadRequest(request);
			JSONArray jsonArray = new JSONArray();
			int i = 0;
			for (NativeResponse b : ads) {
				try {

					// JSONObject ns = new JSONObject();
					// JSONObject script = new JSONObject();
					// script.put("script", b.contextCode.toString());
					// ns.put("namespace", b.ns.toString());

					JSONObject array = new JSONObject();
					String temp = b.pubContent.toString();
					array.put("pubContent", b.convertPubContentToJsonObject(temp));
					array.put("script", b.contextCode.toString());
					array.put("namespace", b.ns.toString());
					jsonArray.put(i, array);

					i++;

				} catch (JSONException e) {
					e.printStackTrace();
					log.error("NativeAds | JsonException error: ", e);
					return null;
				}
			}

			// System.out.println("JSONArray"+jsonArray.toString());
			CreateJSONPayload payload = new CreateJSONPayload();

			adCount = jsonArray.length();
			resp = payload.respJSONFormat(jsonArray);

			// System.out.println("Resp "+resp + "Query" + queryParameters.Log);
		} else {
			return null;
		}

		return resp;
	}

	private String getInterstitialAds(QueryParameters queryParameters) {

		String resp = null;

		Request request = new Request();
		request.setRequestType(AdRequest.INTERSTITIAL);

		Property property = null;
		property = new Property(queryParameters.getSiteid());
		request.setProperty(property);

		Impression imp = new Impression(new Slot(14, "top"));
		imp.setInterstitial(true);
		request.setImpression(imp);

		Device device = new Device(queryParameters.getIp(), queryParameters.getUa());
		device.setGpId(queryParameters.getAdvertisingId());
		device.setLocale(queryParameters.getLocale());
		request.setDevice(device);

		HashMap<String, String> userSegmentArray = new HashMap<String, String>();
		userSegmentArray.put("marital", queryParameters.getMarital());

		UserSegment segment = new UserSegment();
		segment.setUserSegmentArray(userSegmentArray);
		User user = new User(queryParameters.getYob(), Gender.UNKNOWN, new Data("1", "Publisher", segment));

		if (queryParameters.getGender().equals("m")) {
			user.setGender(Gender.MALE);
		} else if (queryParameters.getGender().equals("f")) {
			user.setGender(Gender.FEMALE);
		}
		request.setUser(user);

		if (request.isValid()) {

			Interstitial nativeAd = new Interstitial();

			ArrayList<BannerResponse> ads = nativeAd.loadRequest(request);
			for (BannerResponse b : ads) {
				try {
					System.out.println(b.toString());
				} catch (Exception e) {
					e.printStackTrace();
					log.error("InterstitialAds | Exception in ads");
				}
			}

		} else {
			return null;
		}

		return resp;
	}

	private String getBannerAds(QueryParameters queryParameters) {

		String resp = null;

		Request request = new Request();
		request.setRequestType(AdRequest.BANNER);

		Property property = null;
		property = new Property(queryParameters.getSiteid());
		request.setProperty(property);

		Impression imp = new Impression(new Slot(15, "top"));
		imp.setInterstitial(false);
		request.setImpression(imp);

		Device device = new Device(queryParameters.getIp(), queryParameters.getUa());
		device.setGpId(queryParameters.getAdvertisingId());
		device.setLocale(queryParameters.getLocale());
		request.setDevice(device);

		HashMap<String, String> userSegmentArray = new HashMap<String, String>();
		userSegmentArray.put("marital", queryParameters.getMarital());

		UserSegment segment = new UserSegment();
		segment.setUserSegmentArray(userSegmentArray);
		User user = new User(queryParameters.getYob(), Gender.UNKNOWN, new Data("1", "Publisher", segment));

		if (queryParameters.getGender().equals("m")) {
			user.setGender(Gender.MALE);
		} else if (queryParameters.getGender().equals("f")) {
			user.setGender(Gender.FEMALE);
		}
		request.setUser(user);

		if (request.isValid()) {

			try {
				String str = null;
				Banner banner = new Banner();

				ArrayList<BannerResponse> ads = banner.loadRequest(request);
				for (BannerResponse b : ads) {
					try {
						byte[] encodedBytes = Base64.encodeBase64(b.CDATA.getBytes());
						str = new String(encodedBytes);
						// System.out.println("CDATA: " +b.CDATA);
						log.info("Banner Response Success to ettId=" + queryParameters.getEttId());
					} catch (Exception e) {
						e.printStackTrace();
						log.error("Banner Ads | Exception in ads");
						return null;
					}
				}
				// String ads = banner.getloadRequest(request);
				CreateJSONPayload payload = new CreateJSONPayload();
				resp = payload.respBannerJSON1(str);

			} catch (Exception e) {
				e.printStackTrace();
				log.error("BannerAds | error in getting bannerads");
				return null;
			}

		} else {
			return null;
		}
		return resp;
	}
}