
package com.rh.clicksmob.beans;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClicksMobOfferBean {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("offerName")
    @Expose
    private String offerName;
    @SerializedName("subHeadline")
    @Expose
    private String subHeadline;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("categories")
    @Expose
    private Categories categories;
    @SerializedName("conversionMode")
    @Expose
    private String conversionMode;
    @SerializedName("conversionUserFlow")
    @Expose
    private String conversionUserFlow;
    @SerializedName("restrictions")
    @Expose
    private String restrictions;
    @SerializedName("notes")
    @Expose
    private Object notes;
    @SerializedName("androidPackageName")
    @Expose
    private String androidPackageName;
    @SerializedName("adultTraffic")
    @Expose
    private Integer adultTraffic;
    @SerializedName("incentiveAllowed")
    @Expose
    private Integer incentiveAllowed;
    @SerializedName("mediabuyerAllowed")
    @Expose
    private Integer mediabuyerAllowed;
    @SerializedName("keyworderAllowed")
    @Expose
    private Integer keyworderAllowed;
    @SerializedName("pushAllowed")
    @Expose
    private Integer pushAllowed;
    @SerializedName("applicationTrafficAllowed")
    @Expose
    private Integer applicationTrafficAllowed;
    @SerializedName("publisherValidationAutomatic")
    @Expose
    private Integer publisherValidationAutomatic;
    @SerializedName("allowedUseOwnCreative")
    @Expose
    private Integer allowedUseOwnCreative;
    @SerializedName("allowedCustomizeBannerTarget")
    @Expose
    private Integer allowedCustomizeBannerTarget;
    @SerializedName("eachCreativeMustHaveDifferentLink")
    @Expose
    private Integer eachCreativeMustHaveDifferentLink;
    @SerializedName("approvalRequired")
    @Expose
    private Integer approvalRequired;
    @SerializedName("needAcceptTerms")
    @Expose
    private Integer needAcceptTerms;
    @SerializedName("allowedWiFi")
    @Expose
    private Integer allowedWiFi;
    @SerializedName("allowed3G")
    @Expose
    private Integer allowed3G;
    @SerializedName("minimalAge")
    @Expose
    private Integer minimalAge;
    @SerializedName("rebrokerAllowed")
    @Expose
    private Integer rebrokerAllowed;
    @SerializedName("discoveryAllowed")
    @Expose
    private Integer discoveryAllowed;
    @SerializedName("emailingAllowed")
    @Expose
    private Integer emailingAllowed;
    @SerializedName("bannersURL")
    @Expose
    private String bannersURL;
    @SerializedName("targetURL")
    @Expose
    private String targetURL;
    @SerializedName("thumbnailURL")
    @Expose
    private String thumbnailURL;
    @SerializedName("s2S")
    @Expose
    private Integer s2S;
    @SerializedName("android")
    @Expose
    private Android android;
    @SerializedName("ios")
    @Expose
    private Ios ios;
    @SerializedName("windowsPhone")
    @Expose
    private WindowsPhone windowsPhone;
    @SerializedName("offerPreviews")
    @Expose
    private OfferPreviews offerPreviews;
    @SerializedName("offerPayouts")
    @Expose
    private OfferPayouts offerPayouts;
    @SerializedName("offerCaps")
    @Expose
    private OfferCaps offerCaps;
    @SerializedName("smsallowed")
    @Expose
    private Integer smsallowed;
    @SerializedName("iosbundleID")
    @Expose
    private Object iosbundleID;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The offerName
     */
    public String getOfferName() {
        return offerName;
    }

    /**
     * 
     * @param offerName
     *     The offerName
     */
    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    /**
     * 
     * @return
     *     The subHeadline
     */
    public String getSubHeadline() {
        return subHeadline;
    }

    /**
     * 
     * @param subHeadline
     *     The subHeadline
     */
    public void setSubHeadline(String subHeadline) {
        this.subHeadline = subHeadline;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The categories
     */
    public Categories getCategories() {
        return categories;
    }

    /**
     * 
     * @param categories
     *     The categories
     */
    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    /**
     * 
     * @return
     *     The conversionMode
     */
    public String getConversionMode() {
        return conversionMode;
    }

    /**
     * 
     * @param conversionMode
     *     The conversionMode
     */
    public void setConversionMode(String conversionMode) {
        this.conversionMode = conversionMode;
    }

    /**
     * 
     * @return
     *     The conversionUserFlow
     */
    public String getConversionUserFlow() {
        return conversionUserFlow;
    }

    /**
     * 
     * @param conversionUserFlow
     *     The conversionUserFlow
     */
    public void setConversionUserFlow(String conversionUserFlow) {
        this.conversionUserFlow = conversionUserFlow;
    }

    /**
     * 
     * @return
     *     The restrictions
     */
    public String getRestrictions() {
        return restrictions;
    }

    /**
     * 
     * @param restrictions
     *     The restrictions
     */
    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    /**
     * 
     * @return
     *     The notes
     */
    public Object getNotes() {
        return notes;
    }

    /**
     * 
     * @param notes
     *     The notes
     */
    public void setNotes(Object notes) {
        this.notes = notes;
    }

    /**
     * 
     * @return
     *     The androidPackageName
     */
    public String getAndroidPackageName() {
        return androidPackageName;
    }

    /**
     * 
     * @param androidPackageName
     *     The androidPackageName
     */
    public void setAndroidPackageName(String androidPackageName) {
        this.androidPackageName = androidPackageName;
    }

    /**
     * 
     * @return
     *     The adultTraffic
     */
    public Integer getAdultTraffic() {
        return adultTraffic;
    }

    /**
     * 
     * @param adultTraffic
     *     The adultTraffic
     */
    public void setAdultTraffic(Integer adultTraffic) {
        this.adultTraffic = adultTraffic;
    }

    /**
     * 
     * @return
     *     The incentiveAllowed
     */
    public Integer getIncentiveAllowed() {
        return incentiveAllowed;
    }

    /**
     * 
     * @param incentiveAllowed
     *     The incentiveAllowed
     */
    public void setIncentiveAllowed(Integer incentiveAllowed) {
        this.incentiveAllowed = incentiveAllowed;
    }

    /**
     * 
     * @return
     *     The mediabuyerAllowed
     */
    public Integer getMediabuyerAllowed() {
        return mediabuyerAllowed;
    }

    /**
     * 
     * @param mediabuyerAllowed
     *     The mediabuyerAllowed
     */
    public void setMediabuyerAllowed(Integer mediabuyerAllowed) {
        this.mediabuyerAllowed = mediabuyerAllowed;
    }

    /**
     * 
     * @return
     *     The keyworderAllowed
     */
    public Integer getKeyworderAllowed() {
        return keyworderAllowed;
    }

    /**
     * 
     * @param keyworderAllowed
     *     The keyworderAllowed
     */
    public void setKeyworderAllowed(Integer keyworderAllowed) {
        this.keyworderAllowed = keyworderAllowed;
    }

    /**
     * 
     * @return
     *     The pushAllowed
     */
    public Integer getPushAllowed() {
        return pushAllowed;
    }

    /**
     * 
     * @param pushAllowed
     *     The pushAllowed
     */
    public void setPushAllowed(Integer pushAllowed) {
        this.pushAllowed = pushAllowed;
    }

    /**
     * 
     * @return
     *     The applicationTrafficAllowed
     */
    public Integer getApplicationTrafficAllowed() {
        return applicationTrafficAllowed;
    }

    /**
     * 
     * @param applicationTrafficAllowed
     *     The applicationTrafficAllowed
     */
    public void setApplicationTrafficAllowed(Integer applicationTrafficAllowed) {
        this.applicationTrafficAllowed = applicationTrafficAllowed;
    }

    /**
     * 
     * @return
     *     The publisherValidationAutomatic
     */
    public Integer getPublisherValidationAutomatic() {
        return publisherValidationAutomatic;
    }

    /**
     * 
     * @param publisherValidationAutomatic
     *     The publisherValidationAutomatic
     */
    public void setPublisherValidationAutomatic(Integer publisherValidationAutomatic) {
        this.publisherValidationAutomatic = publisherValidationAutomatic;
    }

    /**
     * 
     * @return
     *     The allowedUseOwnCreative
     */
    public Integer getAllowedUseOwnCreative() {
        return allowedUseOwnCreative;
    }

    /**
     * 
     * @param allowedUseOwnCreative
     *     The allowedUseOwnCreative
     */
    public void setAllowedUseOwnCreative(Integer allowedUseOwnCreative) {
        this.allowedUseOwnCreative = allowedUseOwnCreative;
    }

    /**
     * 
     * @return
     *     The allowedCustomizeBannerTarget
     */
    public Integer getAllowedCustomizeBannerTarget() {
        return allowedCustomizeBannerTarget;
    }

    /**
     * 
     * @param allowedCustomizeBannerTarget
     *     The allowedCustomizeBannerTarget
     */
    public void setAllowedCustomizeBannerTarget(Integer allowedCustomizeBannerTarget) {
        this.allowedCustomizeBannerTarget = allowedCustomizeBannerTarget;
    }

    /**
     * 
     * @return
     *     The eachCreativeMustHaveDifferentLink
     */
    public Integer getEachCreativeMustHaveDifferentLink() {
        return eachCreativeMustHaveDifferentLink;
    }

    /**
     * 
     * @param eachCreativeMustHaveDifferentLink
     *     The eachCreativeMustHaveDifferentLink
     */
    public void setEachCreativeMustHaveDifferentLink(Integer eachCreativeMustHaveDifferentLink) {
        this.eachCreativeMustHaveDifferentLink = eachCreativeMustHaveDifferentLink;
    }

    /**
     * 
     * @return
     *     The approvalRequired
     */
    public Integer getApprovalRequired() {
        return approvalRequired;
    }

    /**
     * 
     * @param approvalRequired
     *     The approvalRequired
     */
    public void setApprovalRequired(Integer approvalRequired) {
        this.approvalRequired = approvalRequired;
    }

    /**
     * 
     * @return
     *     The needAcceptTerms
     */
    public Integer getNeedAcceptTerms() {
        return needAcceptTerms;
    }

    /**
     * 
     * @param needAcceptTerms
     *     The needAcceptTerms
     */
    public void setNeedAcceptTerms(Integer needAcceptTerms) {
        this.needAcceptTerms = needAcceptTerms;
    }

    /**
     * 
     * @return
     *     The allowedWiFi
     */
    public Integer getAllowedWiFi() {
        return allowedWiFi;
    }

    /**
     * 
     * @param allowedWiFi
     *     The allowedWiFi
     */
    public void setAllowedWiFi(Integer allowedWiFi) {
        this.allowedWiFi = allowedWiFi;
    }

    /**
     * 
     * @return
     *     The allowed3G
     */
    public Integer getAllowed3G() {
        return allowed3G;
    }

    /**
     * 
     * @param allowed3G
     *     The allowed3G
     */
    public void setAllowed3G(Integer allowed3G) {
        this.allowed3G = allowed3G;
    }

    /**
     * 
     * @return
     *     The minimalAge
     */
    public Integer getMinimalAge() {
        return minimalAge;
    }

    /**
     * 
     * @param minimalAge
     *     The minimalAge
     */
    public void setMinimalAge(Integer minimalAge) {
        this.minimalAge = minimalAge;
    }

    /**
     * 
     * @return
     *     The rebrokerAllowed
     */
    public Integer getRebrokerAllowed() {
        return rebrokerAllowed;
    }

    /**
     * 
     * @param rebrokerAllowed
     *     The rebrokerAllowed
     */
    public void setRebrokerAllowed(Integer rebrokerAllowed) {
        this.rebrokerAllowed = rebrokerAllowed;
    }

    /**
     * 
     * @return
     *     The discoveryAllowed
     */
    public Integer getDiscoveryAllowed() {
        return discoveryAllowed;
    }

    /**
     * 
     * @param discoveryAllowed
     *     The discoveryAllowed
     */
    public void setDiscoveryAllowed(Integer discoveryAllowed) {
        this.discoveryAllowed = discoveryAllowed;
    }

    /**
     * 
     * @return
     *     The emailingAllowed
     */
    public Integer getEmailingAllowed() {
        return emailingAllowed;
    }

    /**
     * 
     * @param emailingAllowed
     *     The emailingAllowed
     */
    public void setEmailingAllowed(Integer emailingAllowed) {
        this.emailingAllowed = emailingAllowed;
    }

    /**
     * 
     * @return
     *     The bannersURL
     */
    public String getBannersURL() {
        return bannersURL;
    }

    /**
     * 
     * @param bannersURL
     *     The bannersURL
     */
    public void setBannersURL(String bannersURL) {
        this.bannersURL = bannersURL;
    }

    /**
     * 
     * @return
     *     The targetURL
     */
    public String getTargetURL() {
        return targetURL;
    }

    /**
     * 
     * @param targetURL
     *     The targetURL
     */
    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    /**
     * 
     * @return
     *     The thumbnailURL
     */
    public String getThumbnailURL() {
        return thumbnailURL;
    }

    /**
     * 
     * @param thumbnailURL
     *     The thumbnailURL
     */
    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    /**
     * 
     * @return
     *     The s2S
     */
    public Integer getS2S() {
        return s2S;
    }

    /**
     * 
     * @param s2S
     *     The s2S
     */
    public void setS2S(Integer s2S) {
        this.s2S = s2S;
    }

    /**
     * 
     * @return
     *     The android
     */
    public Android getAndroid() {
        return android;
    }

    /**
     * 
     * @param android
     *     The android
     */
    public void setAndroid(Android android) {
        this.android = android;
    }

    /**
     * 
     * @return
     *     The ios
     */
    public Ios getIos() {
        return ios;
    }

    /**
     * 
     * @param ios
     *     The ios
     */
    public void setIos(Ios ios) {
        this.ios = ios;
    }

    /**
     * 
     * @return
     *     The windowsPhone
     */
    public WindowsPhone getWindowsPhone() {
        return windowsPhone;
    }

    /**
     * 
     * @param windowsPhone
     *     The windowsPhone
     */
    public void setWindowsPhone(WindowsPhone windowsPhone) {
        this.windowsPhone = windowsPhone;
    }

    /**
     * 
     * @return
     *     The offerPreviews
     */
    public OfferPreviews getOfferPreviews() {
        return offerPreviews;
    }

    /**
     * 
     * @param offerPreviews
     *     The offerPreviews
     */
    public void setOfferPreviews(OfferPreviews offerPreviews) {
        this.offerPreviews = offerPreviews;
    }

    /**
     * 
     * @return
     *     The offerPayouts
     */
    public OfferPayouts getOfferPayouts() {
        return offerPayouts;
    }

    /**
     * 
     * @param offerPayouts
     *     The offerPayouts
     */
    public void setOfferPayouts(OfferPayouts offerPayouts) {
        this.offerPayouts = offerPayouts;
    }

    /**
     * 
     * @return
     *     The offerCaps
     */
    public OfferCaps getOfferCaps() {
        return offerCaps;
    }

    /**
     * 
     * @param offerCaps
     *     The offerCaps
     */
    public void setOfferCaps(OfferCaps offerCaps) {
        this.offerCaps = offerCaps;
    }

    /**
     * 
     * @return
     *     The smsallowed
     */
    public Integer getSmsallowed() {
        return smsallowed;
    }

    /**
     * 
     * @param smsallowed
     *     The smsallowed
     */
    public void setSmsallowed(Integer smsallowed) {
        this.smsallowed = smsallowed;
    }

    /**
     * 
     * @return
     *     The iosbundleID
     */
    public Object getIosbundleID() {
        return iosbundleID;
    }

    /**
     * 
     * @param iosbundleID
     *     The iosbundleID
     */
    public void setIosbundleID(Object iosbundleID) {
        this.iosbundleID = iosbundleID;
    }
    
    public class Android {

        @SerializedName("androidMinimalVersion")
        @Expose
        private Object androidMinimalVersion;
        @SerializedName("androidMaximalVersion")
        @Expose
        private Object androidMaximalVersion;
        @SerializedName("isAPK")
        @Expose
        private Integer isAPK;

        /**
         * 
         * @return
         *     The androidMinimalVersion
         */
        public Object getAndroidMinimalVersion() {
            return androidMinimalVersion;
        }

        /**
         * 
         * @param androidMinimalVersion
         *     The androidMinimalVersion
         */
        public void setAndroidMinimalVersion(Object androidMinimalVersion) {
            this.androidMinimalVersion = androidMinimalVersion;
        }

        /**
         * 
         * @return
         *     The androidMaximalVersion
         */
        public Object getAndroidMaximalVersion() {
            return androidMaximalVersion;
        }

        /**
         * 
         * @param androidMaximalVersion
         *     The androidMaximalVersion
         */
        public void setAndroidMaximalVersion(Object androidMaximalVersion) {
            this.androidMaximalVersion = androidMaximalVersion;
        }

        /**
         * 
         * @return
         *     The isAPK
         */
        public Integer getIsAPK() {
            return isAPK;
        }

        /**
         * 
         * @param isAPK
         *     The isAPK
         */
        public void setIsAPK(Integer isAPK) {
            this.isAPK = isAPK;
        }

    }
    
    public class Categories {

        @SerializedName("category")
        @Expose
        private List<String> category = new ArrayList<String>();

        /**
         * 
         * @return
         *     The category
         */
        public List<String> getCategory() {
            return category;
        }

        /**
         * 
         * @param category
         *     The category
         */
        public void setCategory(List<String> category) {
            this.category = category;
        }

    }



}
