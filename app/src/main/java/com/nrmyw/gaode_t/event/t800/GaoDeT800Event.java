package com.nrmyw.gaode_t.event.t800;

import android.graphics.Bitmap;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.nrmyw.gaode_t.bean.HudCameraBean;
import com.nrmyw.gaode_t.config.GaoDeHudSendConfig;
import com.nrmyw.gaode_t.event.GaoDeNaviEventImp;
import com.nrmyw.gaode_t.event.navi.GaoDeEventManager;
import com.nrmyw.gaode_t.type.NaviSdkAvoidForbidType;
import com.nrmyw.gaode_t.type.NaviSdkAvoidType;
import com.nrmyw.gaode_t.type.NaviSdkGpsType;
import com.nrmyw.gaode_t.type.NaviSdkTurnIconType;
import com.nrmyw.gaode_t.util.GaoDeIconTypeToT800IconTypeUtil;
import com.nrmyw.gaode_t.util.GaoDeLaneTypeToT800LaneTypeUtil;
import com.nrmyw.gaode_t.util.GaoDeTypeToWarningPointUtil;
import com.nrmyw.hud.HudDataEvent;

import com.nrmyw.hud_data_event_lib.HudEvent;
import com.nrmyw.hud_data_event_lib.HudEventManager;
import com.nrmyw.hud_data_lib.bean.HudLaneCountBean;
import com.nrmyw.hud_data_lib.type.image.HudImageType;
import com.nrmyw.hud_data_lib.type.lane.HudLaneType;
import com.nrmyw.hud_data_lib.type.lane.HudNowLaneStrType;
import com.nrmyw.hud_data_lib.type.reach.HudReachType;
import com.nrmyw.hud_data_lib.type.set.HudGpsStatuType;
import com.nrmyw.hud_data_lib.type.turn.HudTurnType;
import com.nrmyw.hud_data_lib.type.warningproint.HudWarningPointType;
import com.nrmyw.hud_data_lib.type.yellow_statu.HudYellowStatuBjType;

import java.util.List;

public class GaoDeT800Event implements GaoDeNaviEventImp {
    private static GaoDeT800Event gaoDeT800Event;
    private GaoDeT800Event(){}

    public static GaoDeT800Event getInstance(){
        if(null==gaoDeT800Event){
            synchronized (GaoDeT800Event.class){
                if(null==gaoDeT800Event){
                    gaoDeT800Event=new GaoDeT800Event();
                }
            }
        }
        return gaoDeT800Event;
    }


    @Override
    public void retrunBytes(byte[] bytes) {

    }

    @Override
    public void start(int i) {
        HudDataEvent.getInstance().setTurnType(HudTurnType.numb_200,0);
//        HudEventManager.getInstance().getHudEvent().sendTurnType(HudTurnType.numb_200,0);
    }

    @Override
    public void onArriveDestination() {
        HudDataEvent.getInstance().setTurnType(HudTurnType.numb_201,0);
        String destinationName="到达目的地";
        HudDataEvent.getInstance().setNowLane(HudNowLaneStrType.destination,destinationName);

//        HudEventManager.getInstance().getHudEvent().sendTurnType(HudTurnType.numb_201,0);
//        //发送目的地道路名字
//        String destinationName="111";
//        HudEventManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.destination,destinationName);

    }

    @Override
    public void sendNavigationText(String text) {
        HudDataEvent.getInstance().setNowLane(HudNowLaneStrType.def,text);
    }


    @Override
    public void sendIntervalInfo(int intervalSpeed, int interval, int averageSpeed) {
        if(interval==0){
//            HudEventManager.getInstance().getHudEvent().sendWarningPoint(HudWarningPointType.none,0);
            HudDataEvent.getInstance().sendWarningPoint(HudWarningPointType.none,0);
        }else {
//            int km=interval/1000;
//            int timeM=km/averageSpeed*60;
//            int useTimeH=timeM/60;
//            int useTimeM=timeM%60;
            HudDataEvent.getInstance().setIntervalSpeed(intervalSpeed,interval,averageSpeed);
        }
    }

    @Override
    public void sendLaneInfo(AMapLaneInfo aMapLaneInfo) {
        if(null==aMapLaneInfo||aMapLaneInfo.laneCount==0){
            return;
        }
        HudLaneCountBean t800LaneCountBean=new HudLaneCountBean();
        int count=aMapLaneInfo.laneCount;
        int firstAdd=0;
        int endAdd=0;
        if(GaoDeHudSendConfig.getInstance().isNeedAddLaneInfo()){
            int qNumb=10-count;
            firstAdd=qNumb/2;
            endAdd=qNumb-firstAdd;
        }
        t800LaneCountBean.addByNumbUseNone(firstAdd);
        boolean canSend=false;
        int[] frontLanes=aMapLaneInfo.frontLane;
        int[] backgroundLanes=aMapLaneInfo.backgroundLane;
        for(int i=0;i<aMapLaneInfo.laneCount;i++){
            HudLaneType t800LaneType= GaoDeLaneTypeToT800LaneTypeUtil.getNeedLaneType(backgroundLanes[i],frontLanes[i]);
            if(!canSend){
                canSend=(t800LaneType!= HudLaneType.NONE);
            }
            t800LaneCountBean.add(t800LaneType);
        }
        t800LaneCountBean.addByNumbUseNone(endAdd);
        if(canSend){
            HudDataEvent.getInstance().setLaneCountBean(t800LaneCountBean);
        }
    }

    @Override
    public void hideLaneInfo() {
//        HudEventManager.getInstance().getHudEvent().sendLaneHide();
        HudDataEvent.getInstance().setLaneHide();
    }

    @Override
    public void sendImage(Bitmap bitmap, HudImageType type) {
        HudEventManager.getInstance().getHudEvent().sendImage(bitmap,type);
    }





    @Override
    public void hideImage() {
        HudEventManager.getInstance().getHudEvent().hideImage();
    }

    @Override
    public void sendCamera(List<HudCameraBean> cameras) {
        if(cameras.size()==1){
            HudCameraBean camera=cameras.get(0);
            HudWarningPointType type= GaoDeTypeToWarningPointUtil.useCameraGetType(camera);
            if(camera.getSpeed()!=0){
                HudEventManager.getInstance().getHudEvent().sendNowSpeed(GaoDeEventManager.getInstance().getNowSpeed(),camera.getSpeed(),0);
            }
            HudEventManager.getInstance().getHudEvent().sendBigWarningPoint(type,camera.getDis());
        }else if(cameras.size()>1){
            HudCameraBean camera1=cameras.get(0);
            HudCameraBean camera2=cameras.get(1);
            HudWarningPointType type1=GaoDeTypeToWarningPointUtil.useCameraGetType(camera1);
            HudWarningPointType type2=GaoDeTypeToWarningPointUtil.useCameraGetType(camera2);
            if(camera1.getSpeed()!=0||camera2.getSpeed()!=0){

                HudEventManager.getInstance().getHudEvent().sendNowSpeed(GaoDeEventManager.getInstance().getNowSpeed(),camera1.getSpeed(),camera2.getSpeed());
            }
            HudEventManager.getInstance().getHudEvent().sendWarningPoint(type1,camera1.getDis(),type2,camera2.getDis());
        }
    }



    @Override
    public void sendGpsStatu(NaviSdkGpsType gpsType) {
        switch (gpsType){
            case ON:
                HudEventManager.getInstance().getHudEvent().sendGpsStatu(HudGpsStatuType.CLOSE);
                break;
            case OFF:
            case LOST:
                HudEventManager.getInstance().getHudEvent().sendGpsStatu(HudGpsStatuType.LOST);
                break;
        }
    }

    @Override
    public void sendAMapServiceAreaInfo(AMapServiceAreaInfo aMapServiceAreaInfo) {
        secondTrunType=HudTurnType.tbt152;
        secondTrunTypeSegRemainDis=aMapServiceAreaInfo.getRemainDist();
    }


    @Override
    public void updateNowSpeed(int nowSpeed) {
        HudDataEvent.getInstance().setNowSpeed(nowSpeed);
//        HudEventManager.getInstance().getHudEvent().sendNowSpeed(nowSpeed);
    }

    @Override
    public void sendYellowStatu(boolean isOpen) {
        if(isOpen){
            HudEventManager.getInstance().getHudEvent().setYellowStatu(HudYellowStatuBjType.STYLE_T700);
        }else {
            HudEventManager.getInstance().getHudEvent().setYellowStatu(HudYellowStatuBjType.HIDE);
        }
    }


    @Override
    public void updateCurPointIndexStr(String curPointIndexStr) {

    }

    @Override
    public void updateCurRoadName(String curRoadName) {
//        String nowLaneName= Cn2Spell.getPinYin(curRoadName);
        HudEventManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.roadName,curRoadName);
    }


    private HudTurnType secondTrunType;
    private int secondTrunTypeSegRemainDis;
    @Override
    public void updateSegRemainInfo(NaviSdkTurnIconType trunIconType, int segRemainDis) {
        HudTurnType t800TrunType= GaoDeIconTypeToT800IconTypeUtil.getNeedTrunType(trunIconType);
        if(null!=secondTrunType&&secondTrunTypeSegRemainDis!=0){
            HudEventManager.getInstance().getHudEvent().sendTurnType(t800TrunType,segRemainDis,secondTrunType,secondTrunTypeSegRemainDis);
            secondTrunType=null;
            secondTrunTypeSegRemainDis=0;
        }else {
            HudEventManager.getInstance().getHudEvent().sendTurnType(t800TrunType,segRemainDis);
        }

//        if(trunIconType==NaviSdkTurnIconType.NONE){
//            LG.i("看看转向信息1---1:"+trunIconType.name()+"---type:"+trunIconType.getIconType());
//        }
//        if(t800TrunType== T800TurnType.none){
//            LG.i("看看转向信息1---2:"+trunIconType.name()+"---type:"+trunIconType.getIconType());
//        }else {
//            LG.i("看看转向信息1---3:"+trunIconType.name()+"---type:"+trunIconType.getIconType());
//        }


    }



    @Override
    public void updateSegRemainTime(int segRemainTime) {

    }

    @Override
    public void updateNextRoadName(String nextRoadName) {
//        String nextLaneName= Cn2Spell.getPinYin(nextRoadName);
        HudEventManager.getInstance().getHudEvent().sendNextLaneName(nextRoadName);
    }

    @Override
    public void updateCurSegIndex(int curSegIndex) {

    }

    @Override
    public void updateTotalRemainInfo(int routeRemainTime, int routeRemainDis) {
        int timeM=routeRemainTime/60;
        int useTimeH=timeM/60;
        int useTimeM=timeM%60;
        HudEventManager.getInstance().getHudEvent().sendReachInfo(routeRemainDis,useTimeH,useTimeM, HudReachType.NONE);
    }

    @Override
    public void updateExitName(String exitName) {

    }

    @Override
    public void updateExitDirection(String exitDirection) {

    }

    @Override
    public void updateRouteRemainLightCount(int routeRemainLightCount) {

    }

    @Override
    public void updateAvoidInfo(NaviSdkAvoidType avoidType, NaviSdkAvoidForbidType avoidForbidType, int dis) {
//        switch (avoidType){
//            case AVOID_WEIGHT:
//                HudEventManager.getInstance().getHudEvent().sendBigWarningPoint(T800WarningPointType.numb_15,dis);
//                break;
//            default:
//                HudEventManager.getInstance().getHudEvent().sendBigWarningPoint(T800WarningPointType.c16,dis);
//                break;
//        }
    }

    @Override
    public void updateOver() {

    }

    @Override
    public void deviceQueVersion() {
        HudEventManager.getInstance().getHudEvent().queDeviceVersion();
    }

    @Override
    public void deviceQueSN() {
        HudEventManager.getInstance().getHudEvent().queSN();
    }

    @Override
    public void deviceQueBrightness() {
        HudEventManager.getInstance().getHudEvent().queBrightness();
    }

    @Override
    public void deviceQueSound() {
        HudEventManager.getInstance().getHudEvent().queSound();
    }

    @Override
    public void deviceSetSound(int v) {
        HudEventManager.getInstance().getHudEvent().setSound(v);
    }

    @Override
    public void deviceQuePromptToneStatu() {
        HudEventManager.getInstance().getHudEvent().queDeviceSound();
    }

    @Override
    public void deviceSetePromptToneStatu(boolean isOpen) {
        if(isOpen){
            HudEventManager.getInstance().getHudEvent().deviceSoundOpen();
        }else {
            HudEventManager.getInstance().getHudEvent().deviceSoundClose();
        }
    }


    @Override
    public void deviceQueGpsSpeed() {
        HudEventManager.getInstance().getHudEvent().queGpsSpeed();
    }

    @Override
    public void deviceSetGpsSpeed(int v) {
        HudEventManager.getInstance().getHudEvent().setGpsSpeed(v);
    }

}
