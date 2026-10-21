package com.nrmyw.gaode_t.activity.set.user;

import android.text.TextUtils;

import com.nrmyw.gaode_t.app.MyApp;
import com.nrmyw.hud_data_event_lib.config.HudSetConfig;
import com.nrmyw.hud_manager_lib.config.HudUserSetConfig;

public enum UserSetType {

    MAX_BRIGHTNESS("最高亮度"),//返回亮度值
    MIN_BRIGHTNESS("最低亮度"),//返回亮度值

    CAN_SHOW_BigWarningPoint("显示大警示图"),

    AUTO_HideIntervalSpeed("自动隐藏区间限速"),

    TURN_TYPE_MIN("转向距离差,建议66\n转向会加弯的距离"),

    TURN_FLICKER("转向提醒"),
    SPEEDING("超速检测"),

    TEST("测试模式"),

    ;
    private int titleRsId;
    private String titleStr;
    private String v;
    UserSetType(int titleRsId){
        this.titleRsId=titleRsId;
    }

    UserSetType(String titleStr){
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
        if(!TextUtils.isEmpty(v)){
            return v;
        }


        switch (this){
            case MAX_BRIGHTNESS:
                return HudUserSetConfig.getInstance().getUserConfigBean().getBrightnessMaxV()+"";
            case MIN_BRIGHTNESS:
                return HudUserSetConfig.getInstance().getUserConfigBean().getBrightnessMinV()+"";
            case CAN_SHOW_BigWarningPoint:
                return HudSetConfig.getInstance().isNeedBigWarningPoint()+"";
            case TURN_TYPE_MIN:
                return HudUserSetConfig.getInstance().getUserConfigBean().getTurnMCha()+"";
            case AUTO_HideIntervalSpeed:
                return HudSetConfig.getInstance().isAutoHideIntervalSpeed()+"";
        }

        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
}
