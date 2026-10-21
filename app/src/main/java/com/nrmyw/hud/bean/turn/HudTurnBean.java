package com.nrmyw.hud.bean.turn;

//import com.nrmyw.gaode_t.util.PinYinUtil;
import com.nrmyw.hud.util.HudCheckUtil;
import com.nrmyw.hud_data_event_lib.HudEventManager;
import com.nrmyw.hud_data_lib.type.turn.HudTurnBjType;
import com.nrmyw.hud_data_lib.type.turn.HudTurnType;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.type.HudDevice;

import java.io.Serializable;

public class HudTurnBean implements Serializable {
    private HudTurnBjType turnBjType;
    private String nextLaneName;
    private HudTurnType turnType1;
    private int dis1;
    private HudTurnType turnType2;
    private int dis2;

    public void setTurnType(HudTurnType type1	, int distance1){
        if(null!=turnType2||
                !HudCheckUtil.checkTurnTypeNeedChange(turnType1,dis1,type1,distance1)){
            return;
        }
        this.turnType1=type1;
        this.dis1=distance1;
        this.turnType2=null;
        this.dis2=0;
        HudEventManager.getInstance().getHudEvent().sendTurnType(this.turnType1,this.dis1);
        switch (HudManager.getInstance().getHudDevice()){
            case T700:
                HudEventManager.getInstance().getHudEvent().sendNewTurnType(this.turnType1,this.dis1);
                break;
            default:
                HudEventManager.getInstance().getHudEvent().sendTurnType(this.turnType1,this.dis1);
                break;
        }
    }
    public void setTurnType(HudTurnType type1	, int distance1,HudTurnType type2	, int distance2){
        if(!HudCheckUtil.checkTurnTypeNeedChange(turnType1,dis1,type1,distance1)
                &&!HudCheckUtil.checkTurnTypeNeedChange(turnType2,dis2,type2,distance2)){
            return;
        }
        this.turnType1=type1;
        this.dis1=distance1;
        this.turnType2=type2;
        this.dis2=distance2;
        HudEventManager.getInstance().getHudEvent().sendTurnType(this.turnType1,this.dis1,this.turnType2,this.dis2);

    }
    public void setTurnBj(HudTurnBjType turnBj){
        if(!HudCheckUtil.checkTurnBjTypeNeedChange(this.turnBjType,turnBj)){
            return;
        }
        this.turnBjType=turnBj;
        HudEventManager.getInstance().getHudEvent().setTurnBj(this.turnBjType);
    }

    public void setNextLaneName(String nextLaneName){
        if(!HudCheckUtil.checkStrNeedChange(this.nextLaneName,nextLaneName)){
            return;
        }
        this.nextLaneName=nextLaneName;
        HudDevice hudDevice=HudManager.getInstance().getHudDevice();
        if(null==hudDevice){
            return;
        }
        HudEventManager.getInstance().getHudEvent().sendNextLaneName(this.nextLaneName);

//        switch (hudDevice){
//
//            case T800:
//
//                HudEventManager.getInstance().getHudEvent().sendNextLaneName(this.nextLaneName);
//                break;
//            default:
//
//                break;
//        }


    }

}
