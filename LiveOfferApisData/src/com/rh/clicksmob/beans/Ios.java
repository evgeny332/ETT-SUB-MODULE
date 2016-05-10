
package com.rh.clicksmob.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ios {

    @SerializedName("iosminimalVersion")
    @Expose
    private Object iosminimalVersion;
    @SerializedName("iosmaximalVersion")
    @Expose
    private Object iosmaximalVersion;

    /**
     * 
     * @return
     *     The iosminimalVersion
     */
    public Object getIosminimalVersion() {
        return iosminimalVersion;
    }

    /**
     * 
     * @param iosminimalVersion
     *     The iosminimalVersion
     */
    public void setIosminimalVersion(Object iosminimalVersion) {
        this.iosminimalVersion = iosminimalVersion;
    }

    /**
     * 
     * @return
     *     The iosmaximalVersion
     */
    public Object getIosmaximalVersion() {
        return iosmaximalVersion;
    }

    /**
     * 
     * @param iosmaximalVersion
     *     The iosmaximalVersion
     */
    public void setIosmaximalVersion(Object iosmaximalVersion) {
        this.iosmaximalVersion = iosmaximalVersion;
    }

}
