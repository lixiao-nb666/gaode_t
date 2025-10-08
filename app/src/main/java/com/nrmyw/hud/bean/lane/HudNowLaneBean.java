package com.nrmyw.hud.bean.lane;

import com.nrmyw.hud_data_event_lib.HudEventManager;
import com.nrmyw.hud_data_lib.type.lane.HudNowLaneStrType;

import java.io.Serializable;

public class HudNowLaneBean implements Serializable {
    private HudNowLaneStrType nowLaneStrType;
    private String showString;

    public void setNowLane(HudNowLaneStrType nowLaneStrType,String showString){
        HudEventManager.getInstance().getHudEvent().sendNowLaneStr(nowLaneStrType,showString);
    }
}
