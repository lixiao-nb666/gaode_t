package com.nrmyw.hud.bean.time;

import com.nrmyw.hud_data_event_lib.HudEventManager;

import java.io.Serializable;

public class HudTimeBean implements Serializable {
    private long nowTime;

    public void setNowTime(long nowTime) {
        if(nowTime>this.nowTime){
            this.nowTime = nowTime;
            HudEventManager.getInstance().getHudEvent().sendTime();
        }
    }

    @Override
    public String toString() {
        return "HudTimeBean{" +
                "nowTime=" + nowTime +
                '}';
    }
}
