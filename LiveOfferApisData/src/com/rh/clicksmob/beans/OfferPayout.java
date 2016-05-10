
package com.rh.clicksmob.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferPayout {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("payout")
    @Expose
    private Double payout;
    @SerializedName("countries")
    @Expose
    private Countries countries;
    @SerializedName("platforms")
    @Expose
    private Platforms platforms;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The payout
     */
    public Double getPayout() {
        return payout;
    }

    /**
     * 
     * @param payout
     *     The payout
     */
    public void setPayout(Double payout) {
        this.payout = payout;
    }

    /**
     * 
     * @return
     *     The countries
     */
    public Countries getCountries() {
        return countries;
    }

    /**
     * 
     * @param countries
     *     The countries
     */
    public void setCountries(Countries countries) {
        this.countries = countries;
    }

    /**
     * 
     * @return
     *     The platforms
     */
    public Platforms getPlatforms() {
        return platforms;
    }

    /**
     * 
     * @param platforms
     *     The platforms
     */
    public void setPlatforms(Platforms platforms) {
        this.platforms = platforms;
    }

}
