package com.nrmyw.gaode_t.activity.set.ble;

import android.text.TextUtils;

import com.newbee.ble_lib.NewBeeBleManager;
import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.app.MyApp;
import com.nrmyw.hud_manager_lib.HudManager;

public enum BleSetType {
    DEVICE_VERSION(R.string.ble_set_version),//返回版本信息
    SN(R.string.ble_set_sn),//返回SN码
    BRIGHTNESS(R.string.ble_set_brightness),//返回亮度值
    SOUND(R.string.ble_set_sound),//返回声音值
    DEVICE_SOUND_STATU(R.string.ble_set_device_sound),//设备提示音
//    GPS_SPEED(R.string.ble_set_gps_speed),//GPS速度比例值
    IMAGE_QUALITY(R.string.ble_set_image_quality),//图片质量
    PROGRESS_QUALITY(R.string.ble_set_progress_quality),//进度条指令
    IMAGE_SIZE(R.string.ble_set_image_size),//图片尺寸

    TIME_DIFFERENCE(R.string.ble_set_time_difference),//时间差
    PAIR_TYPE(R.string.ble_set_pair_type),//匹配方式

    DAYLIGHT_SEARCH(R.string.ble_set_daylight_search),

//    ACK_SIZE((byte)0x55),//获得包的大小


    ;
    private int titleRsId;
    private String titleStr;
    private String v;
    BleSetType(int titleRsId){
        this.titleRsId=titleRsId;
    }

    BleSetType(String titleStr){
        this.titleStr=titleStr;
    }

    public String getTitleStr(){
        if(!TextUtils.isEmpty(titleStr)){
            return titleStr;
        }else if(titleRsId!=0){
            return MyApp.getRsString(titleRsId);
        }else {
            return "";
        }
    }


    public String getV() {
        switch (this){
            case IMAGE_SIZE:
                return HudManager.getInstance().getHudSetConfig().getImageMaxW()+"*"+HudManager.getInstance().getHudSetConfig().getImageMaxH();
            case IMAGE_QUALITY:
                return HudManager.getInstance().getHudSetConfig().getBleSendBitmapQualityType().name();
            case PROGRESS_QUALITY:
                return HudManager.getInstance().getHudSetConfig().getProgressMaxW()+"*"+HudManager.getInstance().getHudSetConfig().getProgressMaxH();
            case TIME_DIFFERENCE:
                return HudManager.getInstance().getHudSetConfig().getTimeDifference()+"S";
            case PAIR_TYPE:
                if(null==NewBeeBleManager.getInstance().getNowUseBleDevice()||null==NewBeeBleManager.getInstance().getNowUseBleDevice().getPairFuntionType()){
                    return "";
                }else {
                    return NewBeeBleManager.getInstance().getNowUseBleDevice().getPairFuntionType().name();
                }

        }

        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
}
