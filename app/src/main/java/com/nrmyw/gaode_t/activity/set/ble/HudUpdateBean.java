package com.nrmyw.gaode_t.activity.set.ble;

import java.io.Serializable;

public class HudUpdateBean implements Serializable {
    private String version;
    private String downUrl;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    @Override
    public String toString() {
        return "HudUpdateBean{" +
                "version='" + version + '\'' +
                ", downUrl='" + downUrl + '\'' +
                '}';
    }
}
