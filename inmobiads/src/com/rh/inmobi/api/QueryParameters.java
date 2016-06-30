package com.rh.inmobi.api;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

public class QueryParameters {

	private final static Logger log = Logger.getLogger(QueryParameters.class);

	private String siteid;
	private String gender;
	private String income;
	private String ip;
	private String ua;
	private String locale;
	private String marital;
	private int yob;
	private long ettId;
	private String advertisingId;
	private String otp;
	boolean isValid = true;
	public String Log;
	private Properties prop = new Properties();

	public QueryParameters(MultivaluedMap<String, String> map, HttpServletRequest request) {

		try {
			prop.load(QueryParameters.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			log.error("QueryParameters| error in getting config file");
		}

		if (map.containsKey("site")) {
			if (map.get("site").equals("1")) {
				setSiteid(prop.getProperty("SITEID_1"));
				log.info("multivalued function parameter site_id present: [" + prop.getProperty("SITEID_1") + "][" + map.get("ettId") + "]");
			} else if (map.get("site").equals("2")) {
				setSiteid(prop.getProperty("SITEID_2"));
				log.info("multivalued function parameter site_id present: [" + prop.getProperty("SITEID_2") + "][" + map.get("ettId") + "]");
			} else {
				setSiteid(prop.getProperty("SITEID_1"));
				log.info("multivalued function parameter site_id present: [" + prop.getProperty("SITEID_1") + "][" + map.get("ettId") + "]");
			}
		} else {
			setSiteid(prop.getProperty("SITEID_1"));
			log.info("multivalued function parameter site_id not present: [" + map.get("ettId") + "]");
		}
		if (getSiteid() == null) {
			setValid(false);
		}
		setGender(prop.getProperty("DEFAULT_GENDER"));
		setIncome(prop.getProperty("DEFAULT_INCOME"));
		setLocale(prop.getProperty("DEFAULT_LOCALE"));
		setMarital(prop.getProperty("DEFAULT_MARITAL"));
		setUa(prop.getProperty("DEFAULT_UA"));
		setYob(Integer.parseInt(prop.getProperty("DEFAULT_YOB")));

		try {
			if (map.containsKey("ettId")) {
//				System.out.println("multivalued function" + map.get("ettId"));
				setEttId(Long.parseLong(map.getFirst("ettId")));
			}

			setAdvertisingId(map.getFirst("advertisingId"));
			setOtp(map.getFirst("otp"));

			try {
				if (map.containsKey("yob")) {
					setYob(Integer.parseInt(map.getFirst("yob")));
				}
			} catch (Exception e) {
				// e.printStackTrace();
				log.error("Error in YOB");
			}

			try {

				if (map.containsKey("gender")) {
					if (map.getFirst("gender").equalsIgnoreCase("MALE")) {
						setGender("m");
					} else if (map.getFirst("gender").equalsIgnoreCase("FEMALE")) {
						setGender("f");
					} else {
						setGender("m");
					}
				}

			} catch (Exception e) {
				// e.printStackTrace();
				log.error("multivalued function Error in gender");
			}

			try {
				if (map.containsKey("marital")) {
					setMarital(map.getFirst("marital"));
				}
				if (map.containsKey("locale")) {
					setLocale(map.getFirst("locale"));
				}
				if (map.containsKey(income)) {
					setIncome(map.getFirst("income"));
				}
			} catch (Exception e) {
				// e.printStackTrace();
				log.error("multivalued function Error !! in marital or locale");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("multivalued function error !! in parameters sent");
		}

		if (getEttId() != 0 && getOtp() != null && getAdvertisingId() != null) {

			if (getEttId() == 2950449 || getEttId() == 17) {
				setUa(request.getHeader("User-Agent").replace("Android 5.1", "Android 5.0"));
			} else {
				setUa(request.getHeader("User-Agent"));
			}

			if (getIp() == null) {
				setIp(request.getHeader("HTTP_X_FORWARDED_FOR"));
				if (getIp() == null) {
					setIp(request.getRemoteAddr().toString());
				}
			}

		} else {
			setValid(false);
		}

		Log = "multivalued function | ettId=" + getEttId() + "| siteid=" + getSiteid() + "|otp=" + getOtp() + "|adId=" + getAdvertisingId() + "|gender=" + getGender() + "|income=" + getIncome() + "|yob=" + getYob() + "|locale=" + getLocale()
				+ "|marital=" + getMarital() + "|ua=" + getUa() + "|ip=" + getIp() + "|";
	}

	public QueryParameters(String data, HttpServletRequest request) {

		Map<String, String> map = null;
		try {
			map = new HashMap<String, String>();
			for (String pair : data.split("&")) {
				if(pair != null && !pair.equals("")){
					String[] kv = pair.split("=");
					if(kv[0].equalsIgnoreCase("income")){
						String tempIncome = URLDecoder.decode(kv[1],"utf-8");
						map.put(kv[0], tempIncome);
					}else if(kv[0].equalsIgnoreCase("ip")){
						
					}else{
						map.put(kv[0], kv[1]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("data function Error in map|" +"data ["+data, e);
		}

		try {
			prop.load(QueryParameters.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			log.error("data function QueryParameters| error in getting config file");
		}

		if (map.containsKey("site")) {
			if (map.get("site").equals("1")) {
				setSiteid(prop.getProperty("SITEID_1"));
				log.info("data function parameter site_id present: [" + prop.getProperty("SITEID_1") + "][" + map.get("ettId") + "]");
			} else if (map.get("site").equals("2")) {
				setSiteid(prop.getProperty("SITEID_2"));
				log.info("data function parameter site_id present: [" + prop.getProperty("SITEID_2") + "][" + map.get("ettId") + "]");
			} else {
				setSiteid(prop.getProperty("SITEID_1"));
				log.info("data function parameter site_id present: [" + prop.getProperty("SITEID_1") + "][" + map.get("ettId") + "]");
			}
		} else {
			setSiteid(prop.getProperty("SITEID_1"));
			log.info("data function parameter site_id not present: [" + map.get("ettId") + "]");
		}
		if (getSiteid() == null) {
			setValid(false);
		}
		setGender(prop.getProperty("DEFAULT_GENDER"));
		setIncome(prop.getProperty("DEFAULT_INCOME"));
		setLocale(prop.getProperty("DEFAULT_LOCALE"));
		setMarital(prop.getProperty("DEFAULT_MARITAL"));
		setUa(prop.getProperty("DEFAULT_UA"));
		setYob(Integer.parseInt(prop.getProperty("DEFAULT_YOB")));

		try {
			if (map.containsKey("ettId")) {
//				System.out.println("data function" + map.get("ettId"));
				setEttId(Long.parseLong(map.get("ettId")));
			}

			setAdvertisingId(map.get("advertisingId"));
			setOtp(map.get("otp"));

			try {
				if (map.containsKey("yob")) {
					setYob(Integer.parseInt(map.get("yob")));
				}
			} catch (Exception e) {
//				e.printStackTrace();
				log.error("data function Error !! in YOB");
			}

			try {

				if (map.containsKey("gender")) {
					if (map.get("gender").equalsIgnoreCase("MALE")) {
						setGender("m");
					} else if (map.get("gender").equalsIgnoreCase("FEMALE")) {
						setGender("f");
					} else {
						setGender("m");
					}
				}

			} catch (Exception e) {
				// e.printStackTrace();
				log.error("data function Error!! in gender");
			}

			try {
				if (map.containsKey("marital")) {
					setMarital(map.get("marital"));
				}
				if (map.containsKey("locale")) {
					setLocale(map.get("locale"));
				}
				if (map.containsKey(income)) {
					setIncome(map.get("income"));
				}
			} catch (Exception e) {
				// e.printStackTrace();
				log.error("data function Error !! in marital or locale");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("data function error !! in parameters sent");
		}

		if (getEttId() != 0 && getOtp() != null && getAdvertisingId() != null) {

			if (getEttId() == 2950449 || getEttId() == 17) {
				setUa(request.getHeader("User-Agent").replace("Android 5.1", "Android 5.0"));
			} else {
				setUa(request.getHeader("User-Agent"));
			}

			if (getIp() == null) {
				setIp(request.getHeader("HTTP_X_FORWARDED_FOR"));
				if (getIp() == null) {
					setIp(request.getRemoteAddr().toString());
				}
			}
		} else {
			setValid(false);
		}

		Log = "data function | ettId=" + getEttId() + "| siteid=" + getSiteid() + "|otp=" + getOtp() + "|adId=" + getAdvertisingId() + "|gender=" + getGender() + "|income=" + getIncome() + "|yob=" + getYob() + "|locale=" + getLocale()
				+ "|marital=" + getMarital() + "|ua=" + getUa() + "|ip=" + getIp() + "|";
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUa() {
		return ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getMarital() {
		return marital;
	}

	public void setMarital(String marital) {
		this.marital = marital;
	}

	public int getYob() {
		return yob;
	}

	public void setYob(int yob) {
		this.yob = yob;
	}

	public long getEttId() {
		return ettId;
	}

	public void setEttId(long ettId) {
		this.ettId = ettId;
	}

	public String getAdvertisingId() {
		return advertisingId;
	}

	public void setAdvertisingId(String advertisingId) {
		this.advertisingId = advertisingId;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

}
