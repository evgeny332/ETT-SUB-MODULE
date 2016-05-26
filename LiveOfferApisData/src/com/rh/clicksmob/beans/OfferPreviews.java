
package com.rh.clicksmob.beans;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferPreviews {

    @SerializedName("offerPreview")
    @Expose
    private List<OfferPreview> offerPreview = new ArrayList<OfferPreview>();

    /**
     * 
     * @return
     *     The offerPreview
     */
    public List<OfferPreview> getOfferPreview() {
        return offerPreview;
    }

    /**
     * 
     * @param offerPreview
     *     The offerPreview
     */
    public void setOfferPreview(List<OfferPreview> offerPreview) {
        this.offerPreview = offerPreview;
    }

}
