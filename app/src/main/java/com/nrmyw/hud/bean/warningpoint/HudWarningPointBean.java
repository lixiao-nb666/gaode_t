package com.nrmyw.hud.bean.warningpoint;

import com.nrmyw.hud.util.HudCheckUtil;
import com.nrmyw.hud_data_event_lib.HudEventManager;
import com.nrmyw.hud_data_lib.type.warningproint.HudWarningPointType;

import java.io.Serializable;

public class HudWarningPointBean implements Serializable {
    private HudWarningPointType type1;
    private int dis1;
    private HudWarningPointType type2;
    private int dis2;

    public void setWarningPoint(HudWarningPointType type1,int dis1){
        if(type2!=null||
                !HudCheckUtil.checkWarningPointNeedChange(this.type1,this.dis1,type1,dis1)){
            return;
        }
        this.type1=type1;
        this.dis1=dis1;
        this.type2=null;
        this.dis2=0;
        HudEventManager.getInstance().getHudEvent().sendBigWarningPoint(this.type1,this.dis1);
    }


    public void setWarningPoint(HudWarningPointType type1,int dis1,HudWarningPointType type2,int dis2){
        if(!HudCheckUtil.checkWarningPointNeedChange(this.type1,this.dis1,type1,dis1)
                &&!HudCheckUtil.checkWarningPointNeedChange(this.type2,this.dis2,type2,dis2)){
            return;
        }

        this.type1=type1;
        this.dis1=dis1;
        this.type2=type2;
        this.dis2=dis2;
        HudEventManager.getInstance().getHudEvent().sendWarningPoint(this.type1,this.dis1,this.type2,this.dis2);
    }


    @Override
    public String toString() {
        return "HudWarningPointBean{" +
                "type1=" + type1 +
                ", dis1=" + dis1 +
                ", type2=" + type2 +
                ", dis2=" + dis2 +
                '}';
    }
}
