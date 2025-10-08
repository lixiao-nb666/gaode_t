package com.nrmyw.hud.bean.spped;

import com.nrmyw.hud.util.HudCheckUtil;
import com.nrmyw.hud_data_event_lib.HudEventManager;
import com.nrmyw.hud_data_lib.type.speed.HudSpeedingShowBJType;
import com.nrmyw.hud_data_lib.type.speed.HudSpeedingTextType;

import java.io.Serializable;

public class HudSpeedBean implements Serializable {
    private int nowSpeed;
    private int limitSpeed;
    private int limitSpeed1;
    private int limitSpeed2;
    private HudSpeedingShowBJType showBJType=HudSpeedingShowBJType.NONE;

    public void setNowSpeed(int nowSpeed) {
        if(HudCheckUtil.checkIntNeedChange(this.nowSpeed,nowSpeed)){
            this.nowSpeed = nowSpeed;
            HudEventManager.getInstance().getHudEvent().sendNowSpeed(this.nowSpeed);
            checkIsSpeeding();
        }
    }

    public void setLimitSpeed(int limitSpeed){
        if(HudCheckUtil.checkIntNeedChange(this.limitSpeed,limitSpeed)){
            this.limitSpeed=limitSpeed;
            checkIsSpeeding();
        }
    }

    public void setNowSpeed(int nowSpeed,int limitSpeed1,int limitSpeed2) {
        if(HudCheckUtil.checkIntNeedChange(this.nowSpeed,nowSpeed)||
                HudCheckUtil.checkIntNeedChange(this.limitSpeed1,limitSpeed1)||
                HudCheckUtil.checkIntNeedChange(this.limitSpeed2,limitSpeed2)){
            this.nowSpeed = nowSpeed;
            this.limitSpeed1=limitSpeed1;
            this.limitSpeed2=limitSpeed2;
            HudEventManager.getInstance().getHudEvent().sendNowSpeed(this.nowSpeed,this.limitSpeed1,this.limitSpeed2);
            checkIsSpeeding();
        }
    }

    private void checkIsSpeeding(){
        if(nowSpeed>limitSpeed1){
            setShowBJType(HudSpeedingShowBJType.NONE);
        }else {
            setShowBJType(HudSpeedingShowBJType.RED_80);
        }
    }

    private void setShowBJType(HudSpeedingShowBJType showBJType){
        if(this.showBJType==showBJType){
            return;
        }
        this.showBJType=showBJType;
        switch (this.showBJType){
            case NONE:
                HudEventManager.getInstance().getHudEvent().sendSpeeding(HudSpeedingTextType.WRITE,this.showBJType);
                break;
            default:
                HudEventManager.getInstance().getHudEvent().sendSpeeding(HudSpeedingTextType.RED,this.showBJType);
                break;
        }
    }

    @Override
    public String toString() {
        return "HudSpeedBean{" +
                "nowSpeed=" + nowSpeed +
                ", limitSpeed1=" + limitSpeed1 +
                ", limitSpeed2=" + limitSpeed2 +
                ", showBJType=" + showBJType +
                '}';
    }
}
