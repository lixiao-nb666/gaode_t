package com.nrmyw.gaode_t.type;

import com.nrmyw.gaode_t.R;

public enum BleSetType {
    DEVICE_VERSION(R.string.ble_set_version),//返回版本信息
    SN(R.string.ble_set_sn),//返回SN码
    BRIGHTNESS(R.string.ble_set_brightness),//返回亮度值
    SOUND(R.string.ble_set_sound),//返回声音值
    DEVICE_SOUND_STATU(R.string.ble_set_device_sound),//设备提示音
    GPS_SPEED(R.string.ble_set_gps_speed),//GPS速度比例值
    IMAGE_QUALITY(R.string.ble_set_gps_speed),

//    ACK_SIZE((byte)0x55),//获得包的大小

    ;
    private int rsId;
    private String v;
    BleSetType(int rsId){
        this.rsId=rsId;
    }

    public int getRsId() {
        return rsId;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
}
