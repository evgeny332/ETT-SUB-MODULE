
package com.rh.clicksmob.beans;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferPayouts {

    @SerializedName("offerPayout")
    @Expose
    private List<OfferPayout> offerPayout = new ArrayList<OfferPayout>();

    /**
     * 
     * @return
     *     The offerPayout
     */
    public List<OfferPayout> getOfferPayout() {
        return offerPayout;
    }

    /**
     * 
     * @param offerPayout
     *     The offerPayout
     */
    public void setOfferPayout(List<OfferPayout> offerPayout) {
        this.offerPayout = offerPayout;
    }

}
