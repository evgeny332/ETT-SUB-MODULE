
package com.rh.clicksmob.beans;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
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
