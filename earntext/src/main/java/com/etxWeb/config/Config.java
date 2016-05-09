package com.etxWeb.config;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.extWeb.service.impl.RuleCheckServiceImpl;

@Component
public class Config {

	@Value("${etx.ON_SCREEN_RULE_CHECK_API}")
	private String OnScreenRuleCheckApi;
	
	@Value("${etx.ON_SCREEN_RULE_CHECK_API_RTO}")
	private int OnScreenRuleCheckApiRto;
	
	@Value("${etx.ON_SCREEN_RULE_CHECK_API_CTO}")
	private int OnScreenRuleCheckApiCto;
	
	@Value("${etx.OTP_REQUEST_API}")
	private String otpRequesstApi;
	
	@Value("${etx.OTP_REQUEST_API_RTO}")
	private int otpRequesstApiRto;
	
	@Value("${etx.OTP_REQUEST_API_CTO}")
	private int otpRequesstApiCto;
	
	@Value("${etx.OTP_REQUEST_API_SEND_SMS}")
	private String otpRequesstApiSendSMS;
	
	@Value("${etx.OTP_REQUEST_CHECK_API}")
	private String otpRequstCheckApi;
	
	
	@Value("${etx.OTP_REQUEST_CHECK_API_RTO}")
	private int otpRequstCheckApiRto;
	
	@Value("${etx.OTP_REQUEST_CHECK_API_CTO}")
	private int otpRequstCheckApiCto;
	
	@Value("${etx.DND_HOUR}")
	private String dndHour;
	
	private int dndInitialHour;
	private int dndOverHour;
	
	@Value("${etx.DAY_LIMIT_API}")
	private String dayLimitApi;
	
	@Value("${etx.DAY_LIMIT_API_RTO}")
	private int dayLimitApiRto;
	
	@Value("${etx.DAY_LIMIT_API_CTO}")
	private int dayLimitApiCto;
	
	@Value("${etx.DAY_LIMIT_COUNT}")
	private int dayLimitCount;
	
	@Value("${etx.DND_CHECK_URL}")
	private String dndCheckUrl;
	
	@Value("${etx.DND_CHECK_URL_RTO}")
	private int dndCheckUrlRto;
	
	@Value("${etx.DND_CHECK_URL_CTO}")
	private int dndCheckUrlCto;
	
	
	@Value("${etx.SMS_FORMAT}")
	private String smsFormat;
	
	@Value("${etx.SMS_PUSH_URL}")
	private String smsPushUrl;
	
	
	@Value("${etx.SMS_PUSH_URL_RTO}")
	private int smsPushUrlRto;
	
	@Value("${etx.SMS_PUSH_URL_CTO}")
	private int smsPushUrlCto;
	
	@Value("${etx.SMS_DETAILS_REPORT}")
	private String smsDetailsReport;

	@Value("${etx.SMS_DETAILS_REPORT_RTO}")
	private int smsDetailsReportRto;

	@Value("${etx.SMS_DETAILS_REPORT_CTO}")
	private int smsDetailsReportCto;
	
	@Value("${etx.DND_HOUR_CHECK_FAIL_TEXT}")
	private String dndHourCheckFailText;

	@Value("${etx.DND_LIST_CONTAIN_FAIL_TEXT}")
	private String dndListContainFailText;
	
	@Value("${etx.SMS_SUBMIT_ERROR}")
	private String smsSubmitError;
	
	@Value("${etx.SMS_SUBMIT_SUCCESS_TEXT}")
	private String smsSubmitSuccessText;
	
	@Value("${etx.SMS_DOWN_TEXT}")
	private String smsDownText;

	@Value("${etx.DAY_LIMIT_CHECK_TEXT}")
	private String dayLimitCheckText;
	
	@Value("${etx.REDEEM_COUNT_LIMIT}")
	private int redeemCountLimit;
	
	@Value("${etx.REDEEM_COUNT_LIMIT_TEXT}")
	private String redeemCountLimitText;
	
	@Value("${etx.OTP_VALIDATION_CHARGE_AMOUNT}")
	private float otpValidationChargeAmount;
	
	@Value("${etx.OTP_EXPIRY_LIMIT}")
	private int otpExpiryLimit;
	
	@Value("${etx.OTP_EXPIRY_LIMIT_TEXT}")
	private String otpExpiryLimitText;
	
	
	@Value("${etx.cli}")
	private String cli;
	
	@Value("${etx.cli}")
	private List<String> cliList;
	
	
	@Value("${etx.nextStatus}")
	private int nextStatus;
	
	
	public String getOnScreenRuleCheckApi() {
		return OnScreenRuleCheckApi;
	}

	public void setOnScreenRuleCheckApi(String onScreenRuleCheckApi) {
		OnScreenRuleCheckApi = onScreenRuleCheckApi;
	}

	public String getOtpRequesstApi() {
		return otpRequesstApi;
	}

	public void setOtpRequesstApi(String otpRequesstApi) {
		this.otpRequesstApi = otpRequesstApi;
	}

	public String getOtpRequstCheckApi() {
		return otpRequstCheckApi;
	}

	public void setOtpRequstCheckApi(String otpRequstCheckApi) {
		this.otpRequstCheckApi = otpRequstCheckApi;
	}

	public String getDndHour() {
		return dndHour;
	}

	public void setDndHour(String dndHour) {
		this.dndHour = dndHour;
	}

	public String getDayLimitApi() {
		return dayLimitApi;
	}

	public void setDayLimitApi(String dayLimitApi) {
		this.dayLimitApi = dayLimitApi;
	}

	public int getDayLimitCount() {
		return dayLimitCount;
	}

	public void setDayLimitCount(int dayLimitCount) {
		this.dayLimitCount = dayLimitCount;
	}

	public String getDndCheckUrl() {
		return dndCheckUrl;
	}

	public void setDndCheckUrl(String dndCheckUrl) {
		this.dndCheckUrl = dndCheckUrl;
	}

	public String getSmsFormat() {
		return smsFormat;
	}

	public void setSmsFormat(String smsFormat) {
		this.smsFormat = smsFormat;
	}

	public String getSmsPushUrl() {
		return smsPushUrl;
	}

	public void setSmsPushUrl(String smsPushUrl) {
		this.smsPushUrl = smsPushUrl;
	}

	public String getSmsDetailsReport() {
		return smsDetailsReport;
	}

	public void setSmsDetailsReport(String smsDetailsReport) {
		this.smsDetailsReport = smsDetailsReport;
	}

	public int getOnScreenRuleCheckApiRto() {
		return OnScreenRuleCheckApiRto;
	}

	public void setOnScreenRuleCheckApiRto(int onScreenRuleCheckApiRto) {
		OnScreenRuleCheckApiRto = onScreenRuleCheckApiRto;
	}

	public int getOnScreenRuleCheckApiCto() {
		return OnScreenRuleCheckApiCto;
	}

	public void setOnScreenRuleCheckApiCto(int onScreenRuleCheckApiCto) {
		OnScreenRuleCheckApiCto = onScreenRuleCheckApiCto;
	}

	public int getOtpRequesstApiRto() {
		return otpRequesstApiRto;
	}

	public void setOtpRequesstApiRto(int otpRequesstApiRto) {
		this.otpRequesstApiRto = otpRequesstApiRto;
	}

	public int getOtpRequesstApiCto() {
		return otpRequesstApiCto;
	}

	public void setOtpRequesstApiCto(int otpRequesstApiCto) {
		this.otpRequesstApiCto = otpRequesstApiCto;
	}

	public int getOtpRequstCheckApiRto() {
		return otpRequstCheckApiRto;
	}

	public void setOtpRequstCheckApiRto(int otpRequstCheckApiRto) {
		this.otpRequstCheckApiRto = otpRequstCheckApiRto;
	}

	public int getOtpRequstCheckApiCto() {
		return otpRequstCheckApiCto;
	}

	public void setOtpRequstCheckApiCto(int otpRequstCheckApiCto) {
		this.otpRequstCheckApiCto = otpRequstCheckApiCto;
	}

	public int getDayLimitApiRto() {
		return dayLimitApiRto;
	}

	public void setDayLimitApiRto(int dayLimitApiRto) {
		this.dayLimitApiRto = dayLimitApiRto;
	}

	public int getDayLimitApiCto() {
		return dayLimitApiCto;
	}

	public void setDayLimitApiCto(int dayLimitApiCto) {
		this.dayLimitApiCto = dayLimitApiCto;
	}

	public int getDndCheckUrlRto() {
		return dndCheckUrlRto;
	}

	public void setDndCheckUrlRto(int dndCheckUrlRto) {
		this.dndCheckUrlRto = dndCheckUrlRto;
	}

	public int getDndCheckUrlCto() {
		return dndCheckUrlCto;
	}

	public void setDndCheckUrlCto(int dndCheckUrl_CTO) {
		this.dndCheckUrlCto = dndCheckUrl_CTO;
	}

	public int getSmsPushUrlRto() {
		return smsPushUrlRto;
	}

	public void setSmsPushUrlRto(int smsPushUrlRto) {
		this.smsPushUrlRto = smsPushUrlRto;
	}

	public int getSmsPushUrlCto() {
		return smsPushUrlCto;
	}

	public void setSmsPushUrlCto(int smsPushUrlCto) {
		this.smsPushUrlCto = smsPushUrlCto;
	}

	public int getSmsDetailsReportRto() {
		return smsDetailsReportRto;
	}

	public void setSmsDetailsReportRto(int smsDetailsReportRto) {
		this.smsDetailsReportRto = smsDetailsReportRto;
	}

	public int getSmsDetailsReportCto() {
		return smsDetailsReportCto;
	}

	public void setSmsDetailsReportCto(int smsDetailsReportCto) {
		this.smsDetailsReportCto = smsDetailsReportCto;
	}

	
	
	public String getDndHourCheckFailText() {
		return dndHourCheckFailText;
	}

	public void setDndHourCheckFailText(String dndHourCheckFailText) {
		this.dndHourCheckFailText = dndHourCheckFailText;
	}

	public String getDndListContainFailText() {
		return dndListContainFailText;
	}

	public void setDndListContainFailText(String dndListContainFailText) {
		this.dndListContainFailText = dndListContainFailText;
	}

	public String getSmsSubmitError() {
		return smsSubmitError;
	}

	public void setSmsSubmitError(String smsSubmitError) {
		this.smsSubmitError = smsSubmitError;
	}

	public String getSmsSubmitSuccessText() {
		return smsSubmitSuccessText;
	}

	public void setSmsSubmitSuccessText(String smsSubmitSuccessText) {
		this.smsSubmitSuccessText = smsSubmitSuccessText;
	}

	public String getSmsDownText() {
		return smsDownText;
	}

	public void setSmsDownText(String smsDownText) {
		this.smsDownText = smsDownText;
	}

	
	public String getDayLimitCheckText() {
		return dayLimitCheckText;
	}

	public void setDayLimitCheckText(String dayLimitCheckText) {
		this.dayLimitCheckText = dayLimitCheckText;
	}

	
	public int getDndInitialHour() {
		return dndInitialHour;
	}

	public void setDndInitialHour(int dndInitialHour) {
		this.dndInitialHour = dndInitialHour;
	}

	public int getDndOverHour() {
		return dndOverHour;
	}

	public void setDndOverHour(int dndOverHour) {
		this.dndOverHour = dndOverHour;
	}

	
	public int getRedeemCountLimit() {
		return redeemCountLimit;
	}

	public void setRedeemCountLimit(int redeemCountLimit) {
		this.redeemCountLimit = redeemCountLimit;
	}

	public String getRedeemCountLimitText() {
		return redeemCountLimitText;
	}

	public void setRedeemCountLimitText(String redeemCountLimitText) {
		this.redeemCountLimitText = redeemCountLimitText;
	}

	public float getOtpValidationChargeAmount() {
		return otpValidationChargeAmount;
	}

	public void setOtpValidationChargeAmount(float otpValidationChargeAmount) {
		this.otpValidationChargeAmount = otpValidationChargeAmount;
	}

	public int getOtpExpiryLimit() {
		return otpExpiryLimit;
	}

	public void setOtpExpiryLimit(int otpExpiryLimit) {
		this.otpExpiryLimit = otpExpiryLimit;
	}

	public String getOtpExpiryLimitText() {
		return otpExpiryLimitText;
	}

	public void setOtpExpiryLimitText(String otpExpiryLimitText) {
		this.otpExpiryLimitText = otpExpiryLimitText;
	}

	
	public String getOtpRequesstApiSendSMS() {
		return otpRequesstApiSendSMS;
	}

	public void setOtpRequesstApiSendSMS(String otpRequesstApiSendSMS) {
		this.otpRequesstApiSendSMS = otpRequesstApiSendSMS;
	}

	
	public String getCli() {
		return cli;
	}

	public void setCli(String cli) {
		this.cli = cli;
	}

	public List<String> getCliList() {
		return cliList;
	}

	public void setCliList(List<String> cliList) {
		this.cliList = cliList;
	}

	public int getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(int nextStatus) {
		this.nextStatus = nextStatus;
	}

	@PostConstruct
	public void intId()
	{
		String hour [] = this.getDndHour().split(",");
		this.setDndInitialHour(Integer.parseInt(hour[0]));
		this.setDndOverHour(Integer.parseInt(hour[1]));
		System.out.print(this.toString());
		
		String cli1 [] = this.getCli().split(",");
		this.setCliList(Arrays.asList(cli1));
	}

	@Override
	public String toString() {
		return "Config [OnScreenRuleCheckApi=" + OnScreenRuleCheckApi
				+ ", OnScreenRuleCheckApiRto=" + OnScreenRuleCheckApiRto
				+ ", OnScreenRuleCheckApiCto=" + OnScreenRuleCheckApiCto
				+ ", otpRequesstApi=" + otpRequesstApi + ", otpRequesstApiRto="
				+ otpRequesstApiRto + ", otpRequesstApiCto="
				+ otpRequesstApiCto + ", otpRequesstApiSendSMS="
				+ otpRequesstApiSendSMS + ", otpRequstCheckApi="
				+ otpRequstCheckApi + ", otpRequstCheckApiRto="
				+ otpRequstCheckApiRto + ", otpRequstCheckApiCto="
				+ otpRequstCheckApiCto + ", dndHour=" + dndHour
				+ ", dndInitialHour=" + dndInitialHour + ", dndOverHour="
				+ dndOverHour + ", dayLimitApi=" + dayLimitApi
				+ ", dayLimitApiRto=" + dayLimitApiRto + ", dayLimitApiCto="
				+ dayLimitApiCto + ", dayLimitCount=" + dayLimitCount
				+ ", dndCheckUrl=" + dndCheckUrl + ", dndCheckUrlRto="
				+ dndCheckUrlRto + ", dndCheckUrlCto=" + dndCheckUrlCto
				+ ", smsFormat=" + smsFormat + ", smsPushUrl=" + smsPushUrl
				+ ", smsPushUrlRto=" + smsPushUrlRto + ", smsPushUrlCto="
				+ smsPushUrlCto + ", smsDetailsReport=" + smsDetailsReport
				+ ", smsDetailsReportRto=" + smsDetailsReportRto
				+ ", smsDetailsReportCto=" + smsDetailsReportCto
				+ ", dndHourCheckFailText=" + dndHourCheckFailText
				+ ", dndListContainFailText=" + dndListContainFailText
				+ ", smsSubmitError=" + smsSubmitError
				+ ", smsSubmitSuccessText=" + smsSubmitSuccessText
				+ ", smsDownText=" + smsDownText + ", dayLimitCheckText="
				+ dayLimitCheckText + ", redeemCountLimit=" + redeemCountLimit
				+ ", redeemCountLimitText=" + redeemCountLimitText
				+ ", otpValidationChargeAmount=" + otpValidationChargeAmount
				+ ", otpExpiryLimit=" + otpExpiryLimit
				+ ", otpExpiryLimitText=" + otpExpiryLimitText
				+ "]";
	}

		
	
	
}
