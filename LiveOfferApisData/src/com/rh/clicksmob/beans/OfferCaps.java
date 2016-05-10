
package com.rh.clicksmob.beans;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferCaps {

    @SerializedName("noDailyCap")
    @Expose
    private String noDailyCap;
    @SerializedName("totalCapLimit")
    @Expose
    private Object totalCapLimit;
    @SerializedName("countryPlatformSet")
    @Expose
    private List<CountryPlatformSet> countryPlatformSet = new ArrayList<CountryPlatformSet>();

    /**
     * 
     * @return
     *     The noDailyCap
     */
    public String getNoDailyCap() {
        return noDailyCap;
    }

    /**
     * 
     * @param noDailyCap
     *     The noDailyCap
     */
    public void setNoDailyCap(String noDailyCap) {
        this.noDailyCap = noDailyCap;
    }

    /**
     * 
     * @return
     *     The totalCapLimit
     */
    public Object getTotalCapLimit() {
        return totalCapLimit;
    }

    /**
     * 
     * @param totalCapLimit
     *     The totalCapLimit
     */
    public void setTotalCapLimit(Object totalCapLimit) {
        this.totalCapLimit = totalCapLimit;
    }

    /**
     * 
     * @return
     *     The countryPlatformSet
     */
    public List<CountryPlatformSet> getCountryPlatformSet() {
        return countryPlatformSet;
    }

    /**
     * 
     * @param countryPlatformSet
     *     The countryPlatformSet
     */
    public void setCountryPlatformSet(List<CountryPlatformSet> countryPlatformSet) {
        this.countryPlatformSet = countryPlatformSet;
    }

}
