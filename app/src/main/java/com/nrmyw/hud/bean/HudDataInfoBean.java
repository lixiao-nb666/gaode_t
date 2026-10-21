package com.nrmyw.hud.bean;

import com.nrmyw.hud.bean.intervalspeed.HudIntervalSpeedBean;
import com.nrmyw.hud.bean.lane.HudLaneInfoBean;
import com.nrmyw.hud.bean.lane.HudNowLaneBean;
import com.nrmyw.hud.bean.reach.HudReachInfoBean;
import com.nrmyw.hud.bean.spped.HudSpeedBean;
import com.nrmyw.hud.bean.time.HudTimeBean;
import com.nrmyw.hud.bean.turn.HudTurnBean;
import com.nrmyw.hud.bean.warningpoint.HudWarningPointBean;

import java.io.Serializable;

public class HudDataInfoBean implements Serializable {
    private HudTimeBean timeBean=new HudTimeBean();
    private HudSpeedBean speedBean=new HudSpeedBean();
    private HudReachInfoBean reachInfoBean=new HudReachInfoBean();
    private HudIntervalSpeedBean intervalSpeedBean=new HudIntervalSpeedBean();
    private HudWarningPointBean warningPointBean=new HudWarningPointBean();
    private HudLaneInfoBean laneInfoBean=new HudLaneInfoBean();
    private HudTurnBean turnBean=new HudTurnBean();
    private HudNowLaneBean nowLaneBean=new HudNowLaneBean();


    public HudTimeBean getTimeBean() {
        return timeBean;
    }

    public HudSpeedBean getSpeedBean() {
        return speedBean;
    }

    public HudReachInfoBean getReachInfoBean() {
        return reachInfoBean;
    }

    public HudIntervalSpeedBean getIntervalSpeedBean() {
        return intervalSpeedBean;
    }

    public HudWarningPointBean getWarningPointBean() {
        return warningPointBean;
    }

    public HudLaneInfoBean getLaneInfoBean() {
        return laneInfoBean;
    }

    public HudTurnBean getTurnBean() {
        return turnBean;
    }

    public HudNowLaneBean getNowLaneBean() {
        return nowLaneBean;
    }

    @Override
    public String toString() {
        return "HudDataInfoBean{" +
                "timeBean=" + timeBean +
                ", speedBean=" + speedBean +
                ", reachInfoBean=" + reachInfoBean +
                ", intervalSpeedBean=" + intervalSpeedBean +
                ", warningPointBean=" + warningPointBean +
                ", laneInfoBean=" + laneInfoBean +
                ", turnBean=" + turnBean +
                ", nowLaneBean=" + nowLaneBean +
                '}';
    }
}
