package com.nrmyw.hud.bean.intervalspeed;

import com.nrmyw.hud.util.HudCheckUtil;
import com.nrmyw.hud_data_event_lib.HudEventManager;

import java.io.Serializable;

public class HudIntervalSpeedBean implements Serializable {
    private int intervalSpeed;
    private int interval ;
    private int averageSpeed;
    private int timeHours;
    private int timeMin;


    public void setIntervalSpeed(int intervalSpeed,int interval ,int averageSpeed){
        if(HudCheckUtil.checkIntNeedChange(this.intervalSpeed,intervalSpeed)||
                HudCheckUtil.checkIntNeedChange(this.interval,interval)||
                HudCheckUtil.checkIntNeedChange(this.averageSpeed,averageSpeed)){
            this.intervalSpeed=intervalSpeed;
            this.interval=interval;
            this.averageSpeed=averageSpeed;
            int km=interval/1000;
            int timeM=km/averageSpeed*60;
            int timeHours=timeM/60;
            int timeMin=timeM%60;
            HudEventManager.getInstance().getHudEvent().sendIntervalSpeed(intervalSpeed,interval,averageSpeed,timeHours,timeMin);
        }


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
