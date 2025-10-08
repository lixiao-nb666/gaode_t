package com.nrmyw.gaode_t.event.navi;

import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.NaviInfo;


import com.nrmyw.gaode_t.bean.HudCameraBean;
import com.nrmyw.gaode_t.config.GaoDeHudSendConfig;
import com.nrmyw.gaode_t.type.NaviSdkCamera;
import com.nrmyw.gaode_t.util.GaoDeCameraUtil;

import java.util.ArrayList;
import java.util.List;

public class GaoDeEventManager {
    private final String tag=getClass().getName()+">>>>";
    private static GaoDeEventManager gaoDeEventManager;


    private GaoDeEvent gaoDeEvent=new GaoDeEvent();

    private GaoDeEventManager(){
    }
    
    public static GaoDeEventManager getInstance(){
        if(null==gaoDeEventManager){
            synchronized (GaoDeEventManager.class){
                if(gaoDeEventManager==null){
                    gaoDeEventManager=new GaoDeEventManager();
                }
            }
        }
        return gaoDeEventManager;
    }



    public void eventSpeed(int newNowSpeed){
//        if(null==GaoDeHudSendConfig.getInstance().getBleDeviceType()){
//            return;
//        }
        gaoDeEvent.setNowSpeed(newNowSpeed);
    }

    public int  getNowSpeed(){
//        if(null==GaoDeHudSendConfig.getInstance().getBleDeviceType()){
//            return;
//        }
        return gaoDeEvent.getNowSpeed();
    }
    
    public void event(NaviInfo naviInfo){
//        if(null==GaoDeHudSendConfig.getInstance().getBleDeviceType()){
//            return;
//        }
        gaoDeEvent.setNowSpeed(naviInfo.getCurrentSpeed());
        //设置当前到位的索引位置
        gaoDeEvent.setCurPointIndex(naviInfo.getCurPoint(),naviInfo.getCurLink());
        //设置当前道路的名称
        gaoDeEvent.setCurRoadName(naviInfo.getCurrentRoadName());
        //设置当前道路剩余时间
        gaoDeEvent.setSegRemainTime(naviInfo.getCurStepRetainTime());
        //设置当前的转向类型和当前道路剩余距离
        gaoDeEvent.setSegRemainDis(naviInfo.getIconType(),naviInfo.getCurStepRetainDistance());
        //设置下条道路的名称
        gaoDeEvent.setNextRoadName(naviInfo.getNextRoadName());
        //设置总共还需要的时间和距离
        gaoDeEvent.setRouteRemainInfo(naviInfo.getPathRetainTime(),naviInfo.getPathRetainDistance());
        gaoDeEvent.setRouteRemainLightCount(naviInfo.getRouteRemainLightCount());
        //设置限制图标
        gaoDeEvent.setAvoid(naviInfo.getNotAvoidInfo());
        //设置结束标识符号
        gaoDeEvent.setEventDoOver();
//        forbidType
//        禁行类型 0：禁止左转 1：禁止右转 2：禁止左掉头 3：禁止右掉头 4：禁止直行
//        AMapExitDirectionInfo aMapExitDirectionInfo=naviInfo.getExitDirectionInfo();
//        String exitName= StringUtil.useStringArrayGetStr(aMapExitDirectionInfo.getExitNameInfo(),"-");//出口文字描述
//        gaoDeEvent.setExitName(exitName);
//        String exitDirection=StringUtil.useStringArrayGetStr(aMapExitDirectionInfo.getDirectionInfo(),"-");//出口方向文字描述
//        gaoDeEvent.setExitDirection(exitDirection);


//        LG.i(tag,"onNaviInfoUpdate()---curInfo()小路段信息:"+naviInfo.getCurPoint()+"/"+naviInfo.getCurLink()+"道路名字为:"+naviInfo.getCurrentRoadName());
//        LG.i(tag,"onNaviInfoUpdate()---curInfo()当前路段信息:"+naviInfo.getCurStep()+"剩余时间："+naviInfo.getCurStepRetainTime()+"剩余距离:"+naviInfo.getCurStepRetainDistance());
//        LG.i(tag,"onNaviInfoUpdate()---curInfo()总路段信息:剩余总时间："+naviInfo.getPathRetainTime()+"剩余总距离:"+naviInfo.getPathRetainDistance());
//        LG.i(tag,"onNaviInfoUpdate()---NextRoadName下个道路信息():"+naviInfo.getNextRoadName());

//        String[] exitNameInfo=;
//        if(null!=exitNameInfo){
//            for(String exitNameStr:exitNameInfo){
//
//                LG.i(tag,"onNaviInfoUpdate()---ExitDirectionInfo()出口方向名字:"+exitName);
//            }
//        }
//        String[] directionInfo=aMapExitDirectionInfo.getDirectionInfo();
//        if(null!=directionInfo){
//            for(String direction:directionInfo){
//                LG.i(tag,"onNaviInfoUpdate()---ExitDirectionInfo()出口方向:"+direction);
//            }
//        }
//        LG.i(tag,"onNaviInfoUpdate()---IconType()转向图标类型:"+ NaviSdkIconType.getNeedType(naviInfo.getIconType()));
//        LG.i(tag,"onNaviInfoUpdate()---NaviType()导航信息类型:"+ naviInfo.getNaviType());
//        LG.i(tag,"onNaviInfoUpdate()RouteRemainLightCount红灯数量:"+naviInfo.getRouteRemainLightCount());
//        LG.i(tag,"onNaviInfoUpdate()---------------------------------------------------");
    }



    public void cameraEvent(AMapNaviCameraInfo[] aMapNaviCameraInfos){
        if(null==aMapNaviCameraInfos||aMapNaviCameraInfos.length==0){
            return;
        }
        List<HudCameraBean> cameras=GaoDeCameraUtil.getNeedCameras(aMapNaviCameraInfos);
        if(null==cameras||cameras.size()==0){
            return;
        }
        GaoDeHudSendConfig.getInstance().getEventImp().sendCamera(cameras);
    }

    public void eventServiceArea(AMapServiceAreaInfo[] aMapServiceAreaInfos){
        if(null==aMapServiceAreaInfos||aMapServiceAreaInfos.length==0){
            return;
        }
        AMapServiceAreaInfo aMapServiceAreaInfo=null;
        for(AMapServiceAreaInfo info:aMapServiceAreaInfos){
            if(null==aMapServiceAreaInfo){
                aMapServiceAreaInfo=info;
            }else {
                if(info.getRemainDist()<aMapServiceAreaInfo.getRemainDist()){
                    aMapServiceAreaInfo=info;
                }
            }
        }
        if(null!=aMapServiceAreaInfo){
            GaoDeHudSendConfig.getInstance().getEventImp().sendAMapServiceAreaInfo(aMapServiceAreaInfo);
        }
    }







}
