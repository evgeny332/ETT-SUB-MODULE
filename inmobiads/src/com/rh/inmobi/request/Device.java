package com.rh.inmobi.request;

import com.rh.inmobi.response.Validator;
import com.rh.utils.InternalUtil;

/**
 * The device  provides rmation pertaining to a device, including its
 * hardware, platform, location, and carrier.</br>
 * <p>
 * <b>Note:</b>
 * </p>
 * <b>1.</b> The User-Agent, and carrierIP are mandatory rmation, without
 * which a request will always be terminated.</br> <b>2.</b> The Carrier IP must
 * be a valid Mobile Country code, and <b>not</b> of your
 * local-wifi/LAN/WAN.</br> Please refer for additional details:
 * http://en.wikipedia.org/wiki/Mobile_country_code</br> For eg: 10.14.x.y, or
 * 192.168.x.y are internal IPs, and hence passing them would terminate the
 * request.</br> <b>3.</b>The User Agent string passed should be a valid,
 * WebView User Agent of the device, for which ads are being requested.
 * 
 * @author rishabhchowdhary
 * 
 */
public class Device implements Validator {

	private String carrierIP = null;
	private String userAgent = null;
	private String gpId = null;
	private String androidId = null;
	private String ida = null;
	private Geo geo = null;
	private String locale = null;
	private boolean isAdTrackingDisabled = false;
	
	
	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * 
	 * @return The String value containing the carrier IP.
	 */
	public String getCarrierIP() {
		return carrierIP;
	}

	/**
	 * The String value of the carrier IP, for which the ad thus obtained would
	 * be send.
	 * 
	 * @param carrierIP
	 *            Must not be empty.
	 */
	public void setCarrierIP(String carrierIP) {
		if (!InternalUtil.isBlank(carrierIP)) {
			this.carrierIP = carrierIP;
		}

	}

	/**
	 * 
	 * 
	 * @return The User-Agent value as set.
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * 
	 * @param userAgent
	 *            The Browser/WebView User Agent String of the device. Must not
	 *            be empty.
	 */
	public void setUserAgent(String userAgent) {
		if (!InternalUtil.isBlank(userAgent)) {
			this.userAgent = userAgent;
		}

	}

	/**
	 * 
	 * @return The Google Play Identifier as set.
	 */
	public String getGpId() {
		return gpId;
	}

	/**
	 * 
	 * @param gpId
	 *            The Google Play Advertising Identifier, as obtained from your
	 *            device.
	 */
	public void setGpId(String gpId) {
		if (!InternalUtil.isBlank(gpId)) {
			this.gpId = gpId;
		}

	}

	/**
	 * 
	 * @return The ANDROID_ID value of the device, as set.
	 */
	public String getAndroidId() {
		return androidId;
	}

	/**
	 * 
	 * @param androidId
	 *            The ANDROID_ID value, as obtained for the android device.
	 */
	public void setAndroidId(String androidId) {
		if (!InternalUtil.isBlank(androidId)) {
			this.androidId = androidId;
		}

	}

	/**
	 * 
	 * @return The Apple advertising identifier value, as set.
	 */
	public String getIda() {
		return ida;
	}

	/**
	 * 
	 * @param ida
	 *            The Apple advertising identifier ID, as obtained from the iOS
	 *            device.
	 */
	public void setIda(String ida) {
		if (!InternalUtil.isBlank(ida)) {
			this.ida = ida;
		}
	}

	/**
	 * 
	 * @return The Geo, as set for this instance.
	 */
	public Geo getGeo() {
		return geo;
	}

	/**
	 * 
	 * @param geo
	 *            The Geo, having the accurate geo coordinate .</br>
	 *            <p>
	 *            <b>Note:</b>
	 *            </p>
	 *            Passing the accurate values increases targeting accuracy.
	 */
	public void setGeo(Geo geo) {
		this.geo = geo;
	}

	public Device(String carrierIP, String ua) {
		setCarrierIP(carrierIP);
		setUserAgent(ua);
	}

	public Device() {

	}

	/**
	 * Use this method to check if this Object has all the required parameters
	 * present, to be used to construct a JSON request. The required parameters
	 * would be specific to an ad-format.
	 * 
	 * @return If the mandatory params are present, then true, otherwise false.
	 */
	public boolean isValid() {
		boolean isValid = false;
		if (carrierIP != null && userAgent != null) {
			isValid = true;
		} else {
			if (carrierIP == null) {
				System.out.println("Carrier IP is mandatory in the request");
			}
			if (userAgent == null) {
				System.out.println("Valid Mobile User Agent is mandatory in the request");
			}
		}

		return isValid;
	}

	public boolean isAdTrackingDisabled() {
		return isAdTrackingDisabled;
	}

	public void setAdTrackingDisabled(boolean isAdTrackingDisabled) {
		this.isAdTrackingDisabled = isAdTrackingDisabled;
	}
}
