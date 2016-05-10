
package com.rh.clicksmob.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WindowsPhone {

    @SerializedName("windowsPhoneMinimalVersion")
    @Expose
    private Object windowsPhoneMinimalVersion;
    @SerializedName("windowsPhoneMaximalVersion")
    @Expose
    private Object windowsPhoneMaximalVersion;

    /**
     * 
     * @return
     *     The windowsPhoneMinimalVersion
     */
    public Object getWindowsPhoneMinimalVersion() {
        return windowsPhoneMinimalVersion;
    }

    /**
     * 
     * @param windowsPhoneMinimalVersion
     *     The windowsPhoneMinimalVersion
     */
    public void setWindowsPhoneMinimalVersion(Object windowsPhoneMinimalVersion) {
        this.windowsPhoneMinimalVersion = windowsPhoneMinimalVersion;
    }

    /**
     * 
     * @return
     *     The windowsPhoneMaximalVersion
     */
    public Object getWindowsPhoneMaximalVersion() {
        return windowsPhoneMaximalVersion;
    }

    /**
     * 
     * @param windowsPhoneMaximalVersion
     *     The windowsPhoneMaximalVersion
     */
    public void setWindowsPhoneMaximalVersion(Object windowsPhoneMaximalVersion) {
        this.windowsPhoneMaximalVersion = windowsPhoneMaximalVersion;
    }

}
