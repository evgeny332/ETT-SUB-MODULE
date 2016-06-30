package com.rh.inmobi.request;

import com.rh.inmobi.request.enums.AdRequest;
import com.rh.inmobi.response.Validator;
import com.rh.utils.InternalUtil;
import com.rh.utils.LogLevel;

public class Request implements Validator {

	private Impression impression;
	private User user;
	private Property property;
	private Device device;
	private AdRequest requestType = AdRequest.NONE;

	public Impression getImpression() {
		return impression;
	}

	public void setImpression(Impression impression) {
		this.impression = impression;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Request(Impression impression, User user, Property property,
			Device device) {
		setImpression(impression);
		setUser(user);
		setProperty(property);
		setDevice(device);
	}

	public AdRequest getRequestType() {
		return requestType;
	}

	public void setRequestType(AdRequest requestType) {
		this.requestType = requestType;
	}

	public Request() {

	}

	/**
	 * This method basically checks for mandatory parameters, which should not
	 * be null, and are required in a request:<br/>
	 * <b>InMobi Property ID:</b> should be valid String, as obtained from
	 * InMobi, present in Property <br/>
	 * <b>Carrier IP:</b> should be valid Mobile Country Code, present in device <br/>
	 * <b>User Agent:</b> should be valid Mobile UA string, present in device <br/>
	 * <b>gpID/AndroidId/IDA:</b> A valid device ID is <i>strongly
	 * recommended.</i> Ignore the value if you're developing for Mobile Web.
	 * 
	 * @param type
	 *            The AdRequest Type, one of Native,Banner or Interstitial
	 * @note Passing in garbage values for any of mandatory parameters would
	 *       terminate the request from server side. UA is actually
	 * @return
	 */
	public boolean isValid() {
		boolean isValid = false;
		if (property != null && property.isValid()) {
			if (device != null && device.isValid()) {

				if (requestType == AdRequest.NATIVE) {
					// impression is not mandatory for native ads.
					if (user != null) {
						isValid = user.isValid();
					} else {
						isValid = true;
					}
				} else if (requestType == AdRequest.BANNER
						|| requestType == AdRequest.INTERSTITIAL) {
					if (impression != null && impression.isValid()) {

						if (user != null) {
							isValid = user.isValid();
						} else {
							isValid = true;
						}
					} else {
						InternalUtil.Log("Please provide a valid Impression in the request",LogLevel.ERROR);
					}
				} else {
					InternalUtil.Log("Valid AdRequest enum not found.",LogLevel.ERROR);
				}
			} else {
				InternalUtil.Log("Please provide a valid Device in the request",LogLevel.ERROR);
			}
		} else {
			InternalUtil.Log("Please provide a valid Property in the request",LogLevel.ERROR);
		}

		return isValid;
	}

}
