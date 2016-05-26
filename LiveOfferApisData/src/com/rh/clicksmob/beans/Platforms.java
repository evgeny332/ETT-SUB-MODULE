
package com.rh.clicksmob.beans;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Platforms {

    @SerializedName("platform")
    @Expose
    private List<String> platform = new ArrayList<String>();

    /**
     * 
     * @return
     *     The platform
     */
    public List<String> getPlatform() {
        return platform;
    }

    /**
     * 
     * @param platform
     *     The platform
     */
    public void setPlatform(List<String> platform) {
        this.platform = platform;
    }

}
