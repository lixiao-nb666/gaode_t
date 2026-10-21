package com.nrmyw.hud.bean.intervalspeed;

import com.nrmyw.hud.util.HudCheckUtil;
import com.nrmyw.hud_data_event_lib.HudEventManager;
import com.nrmyw.hud_data_lib.type.warningproint.HudWarningPointType;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.type.HudDevice;

import java.io.Serializable;

public class HudIntervalSpeedBean implements Serializable {
    private int intervalSpeed;
    private int interval ;
    private int averageSpeed;
    private int timeHours;
    private int timeMin;

    private boolean needCheck;
    private long lastNeedCheckTime;
    public void setIntervalSpeed(int intervalSpeed,int interval ,int averageSpeed){
        if(HudCheckUtil.checkIntNeedChange(this.intervalSpeed,intervalSpeed)||
                HudCheckUtil.checkIntNeedChange(this.interval,interval)||
                HudCheckUtil.checkIntNeedChange(this.averageSpeed,averageSpeed)){
            this.intervalSpeed=intervalSpeed;
            this.interval=interval;
            this.averageSpeed=averageSpeed;
            double km=interval/1000;
            double needTimeH=km/averageSpeed;
            int needM= (int) (needTimeH*60);
//            int timeM=km/averageSpeed*60;
            int timeHours=needM/60;
            int timeMin=needM%60;


            HudEventManager.getInstance().getHudEvent().sendIntervalSpeed(intervalSpeed,interval,averageSpeed,timeHours,timeMin);
            if(interval<50){
                needCheck=true;
                lastNeedCheckTime=System.currentTimeMillis();
            }
        }
    }

    public void check(){
        if(!needCheck){
            return;
        }
        if(System.currentTimeMillis()-lastNeedCheckTime>3000){
            setIntervalSpeedOver();
        }

    }

    public void setIntervalSpeedOver(){
        HudDevice hudDevice=HudManager.getInstance().getHudDevice();
        if(null==hudDevice){
            return;
        }

        switch (hudDevice){
            case T800:
                HudEventManager.getInstance().getHudEvent().sendBigWarningPoint(HudWarningPointType.none,0);
                break;
            default:
                HudEventManager.getInstance().getHudEvent().hideIntervalSpeed();
                break;

        }

        needCheck=false;
    }

    @Override
    public String toString() {
        return "HudIntervalSpeedBean{" +
                "intervalSpeed=" + intervalSpeed +
                ", interval=" + interval +
                ", averageSpeed=" + averageSpeed +
                ", timeHours=" + timeHours +
                ", timeMin=" + timeMin +
                '}';
    }
}
