
package com.rh.clicksmob.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferPreview {

    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("androidPreviewURL")
    @Expose
    private String androidPreviewURL;
    @SerializedName("otherPreviewURL")
    @Expose
    private String otherPreviewURL;
    @SerializedName("ipadPreviewURL")
    @Expose
    private String ipadPreviewURL;
    @SerializedName("iphonePreviewURL")
    @Expose
    private String iphonePreviewURL;
    @SerializedName("ipodPreviewURL")
    @Expose
    private String ipodPreviewURL;

    /**
     * 
     * @return
     *     The language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 
     * @param language
     *     The language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * 
     * @return
     *     The androidPreviewURL
     */
    public String getAndroidPreviewURL() {
        return androidPreviewURL;
    }

    /**
     * 
     * @param androidPreviewURL
     *     The androidPreviewURL
     */
    public void setAndroidPreviewURL(String androidPreviewURL) {
        this.androidPreviewURL = androidPreviewURL;
    }

    /**
     * 
     * @return
     *     The otherPreviewURL
     */
    public String getOtherPreviewURL() {
        return otherPreviewURL;
    }

    /**
     * 
     * @param otherPreviewURL
     *     The otherPreviewURL
     */
    public void setOtherPreviewURL(String otherPreviewURL) {
        this.otherPreviewURL = otherPreviewURL;
    }

    /**
     * 
     * @return
     *     The ipadPreviewURL
     */
    public String getIpadPreviewURL() {
        return ipadPreviewURL;
    }

    /**
     * 
     * @param ipadPreviewURL
     *     The ipadPreviewURL
     */
    public void setIpadPreviewURL(String ipadPreviewURL) {
        this.ipadPreviewURL = ipadPreviewURL;
    }

    /**
     * 
     * @return
     *     The iphonePreviewURL
     */
    public String getIphonePreviewURL() {
        return iphonePreviewURL;
    }

    /**
     * 
     * @param iphonePreviewURL
     *     The iphonePreviewURL
     */
    public void setIphonePreviewURL(String iphonePreviewURL) {
        this.iphonePreviewURL = iphonePreviewURL;
    }

    /**
     * 
     * @return
     *     The ipodPreviewURL
     */
    public String getIpodPreviewURL() {
        return ipodPreviewURL;
    }

    /**
     * 
     * @param ipodPreviewURL
     *     The ipodPreviewURL
     */
    public void setIpodPreviewURL(String ipodPreviewURL) {
        this.ipodPreviewURL = ipodPreviewURL;
    }

}
