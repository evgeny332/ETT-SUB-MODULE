
package com.rh.clicksmob.beans;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Countries_ {

    @SerializedName("country")
    @Expose
    private List<String> country = new ArrayList<String>();

    /**
     * 
     * @return
     *     The country
     */
    public List<String> getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    public void setCountry(List<String> country) {
        this.country = country;
    }

}
