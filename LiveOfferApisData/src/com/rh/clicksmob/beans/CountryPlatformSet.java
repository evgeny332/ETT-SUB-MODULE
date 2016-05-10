
package com.rh.clicksmob.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryPlatformSet {

    @SerializedName("countries")
    @Expose
    private Countries_ countries;
    @SerializedName("platforms")
    @Expose
    private Platforms_ platforms;
    @SerializedName("dailyCap")
    @Expose
    private Integer dailyCap;

    /**
     * 
     * @return
     *     The countries
     */
    public Countries_ getCountries() {
        return countries;
    }

    /**
     * 
     * @param countries
     *     The countries
     */
    public void setCountries(Countries_ countries) {
        this.countries = countries;
    }

    /**
     * 
     * @return
     *     The platforms
     */
    public Platforms_ getPlatforms() {
        return platforms;
    }

    /**
     * 
     * @param platforms
     *     The platforms
     */
    public void setPlatforms(Platforms_ platforms) {
        this.platforms = platforms;
    }

    /**
     * 
     * @return
     *     The dailyCap
     */
    public Integer getDailyCap() {
        return dailyCap;
    }

    /**
     * 
     * @param dailyCap
     *     The dailyCap
     */
    public void setDailyCap(Integer dailyCap) {
        this.dailyCap = dailyCap;
    }

}
