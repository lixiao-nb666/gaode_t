package com.nrmyw.hud.bean.lane;

import com.nrmyw.hud_data_event_lib.HudEventManager;
import com.nrmyw.hud_data_lib.bean.HudLaneCountBean;
import com.nrmyw.hud_data_lib.bean.HudLaneHiPassCountBean;

import java.io.Serializable;

public class HudLaneInfoBean implements Serializable {
    private boolean isHide;
    private HudLaneCountBean laneCountBean;
    private HudLaneHiPassCountBean laneHiPassCountBean;


    public void setLaneHide(){
        isHide=true;
        laneCountBean=null;
        laneHiPassCountBean=null;
        HudEventManager.getInstance().getHudEvent().sendLaneHide();
    }

    public void setLaneCountBean(HudLaneCountBean hudLaneCountBean){
        if(null==hudLaneCountBean||null==hudLaneCountBean.getLaneList()||hudLaneCountBean.getLaneList().size()==0){
            return;
        }
        isHide=false;
        this.laneCountBean=hudLaneCountBean;
        this.laneHiPassCountBean=null;
        HudEventManager.getInstance().getHudEvent().sendLaneInformation(hudLaneCountBean);
    }

    public void setLaneHiPassCountBean(HudLaneHiPassCountBean hudLaneHiPassCountBean){
        if(null==hudLaneHiPassCountBean||null==hudLaneHiPassCountBean.getLaneList()||hudLaneHiPassCountBean.getLaneList().size()==0){
            return;
        }
        isHide=false;
        this.laneCountBean=null;
        this.laneHiPassCountBean=hudLaneHiPassCountBean;
        HudEventManager.getInstance().getHudEvent().sendLaneHiPass(hudLaneHiPassCountBean);
    }


}
