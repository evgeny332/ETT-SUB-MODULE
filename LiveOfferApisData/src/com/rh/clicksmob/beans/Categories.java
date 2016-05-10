
package com.rh.clicksmob.beans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
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
