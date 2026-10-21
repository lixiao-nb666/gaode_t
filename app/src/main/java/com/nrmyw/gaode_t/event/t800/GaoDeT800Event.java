package com.nrmyw.gaode_t.event.t800;

import android.graphics.Bitmap;
import android.util.Log;

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
import com.nrmyw.hud_data_event_lib.HudEventManager;
import com.nrmyw.hud_data_lib.bean.HudLaneCountBean;
import com.nrmyw.hud_data_lib.type.image.HudImageType;
import com.nrmyw.hud_data_lib.type.lane.HudLaneType;
import com.nrmyw.hud_data_lib.type.lane.HudNowLaneStrType;
import com.nrmyw.hud_data_lib.type.reach.HudReachType;
import com.nrmyw.hud_data_lib.type.set.HudGpsStatuType;
import com.nrmyw.hud_data_lib.type.turn.HudTurnType;
import com.nrmyw.hud_data_lib.type.type.HudStatuType;
import com.nrmyw.hud_data_lib.type.warningproint.HudWarningPointType;
import com.nrmyw.hud_data_lib.type.yellow_statu.HudYellowStatuBjType;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.manager.set_child.HudSpeedManager;
import com.nrmyw.hud_manager_lib.manager.set_child.HudTurnTypeManager;
import com.nrmyw.hud_manager_lib.type.HudDevice;

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
//        HudDataEvent.getInstance().setTurnType(HudTurnType.numb_200,0);
        HudEventManager.getInstance().getHudEvent().sendTurnType(HudTurnType.c_200,0);

    }

    @Override
    public void onArriveDestination() {
//        HudDataEvent.getInstance().setTurnType(HudTurnType.numb_201,0);
//        String destinationName="到达目的地";
//        HudDataEvent.getInstance().setNowLane(HudNowLaneStrType.destination,destinationName);

        HudEventManager.getInstance().getHudEvent().sendTurnType(HudTurnType.c_201,0);


        //发送目的地道路名字
        String destinationName="到达目的地";
        HudEventManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.destination,destinationName);
        HudManager.getInstance().getHudEvent().hideReach();
        HudManager.getInstance().getHudEvent().iconFlicherClose();
    }

    @Override
    public void sendNavigationText(String text) {
//        HudDataEvent.getInstance().setNowLane(HudNowLaneStrType.def,text);
    }


    @Override
    public void sendIntervalInfo(int intervalSpeed, int interval, int averageSpeed) {
        if(interval==0){
//            HudEventManager.getInstance().getHudEvent().sendWarningPoint(HudWarningPointType.none,0);
//            HudDataEvent.getInstance().sendWarningPoint(HudWarningPointType.none,0);
//            HudDataEvent.getInstance().setIntervalSpeedOver();
            setIntervalSpeedOver();
        }else {
//            int km=interval/1000;
//            int timeM=km/averageSpeed*60;
//            int useTimeH=timeM/60;
//            int useTimeM=timeM%60;
//            HudDataEvent.getInstance().setIntervalSpeed(intervalSpeed,interval,averageSpeed);
            setIntervalSpeed(intervalSpeed,interval,averageSpeed);
        }
    }

//    private boolean needCheckIntervalSpeed;
    private long lastNeedCheckTime;

//    public void nowCheckIntervalSpeed(){
//        if(!needCheckIntervalSpeed){
//            return;
//        }
//        if(System.currentTimeMillis()-lastNeedCheckTime>3000){
//            setIntervalSpeedOver();
//        }
//
//    }
    public void setIntervalSpeed(int intervalSpeed,int interval ,int averageSpeed){
        HudManager.getInstance().getHudEvent().setTitleYellowStatu(HudYellowStatuBjType.STYLE_T700);
            HudSpeedManager.getInstance().setIntervalSpeed(intervalSpeed,interval,averageSpeed);

//            double km=interval/1000;
//            double needTimeH=km/averageSpeed;
//            int needM= (int) (needTimeH*60);
//            int timeHours=needM/60;
//            int timeMin=needM%60;
//            HudEventManager.getInstance().getHudEvent().sendIntervalSpeed(intervalSpeed,interval,averageSpeed,timeHours,timeMin);

//            if(interval<50){
//                needCheckIntervalSpeed=true;
//                lastNeedCheckTime=System.currentTimeMillis();
//            }else {
//                needCheckIntervalSpeed=false;
//            }
    }


    public void setIntervalSpeedOver(){
//        HudDevice hudDevice= HudManager.getInstance().getHudDevice();
//        if(null==hudDevice){
//            return;
//        }
//
//        switch (hudDevice){
//            case T800:
//                HudEventManager.getInstance().getHudEvent().sendBigWarningPoint(HudWarningPointType.none,0);
//                break;
//            default:
//
//                break;
//
//        }
//        needCheckIntervalSpeed=false;
        HudManager.getInstance().getHudEvent().setTitleYellowStatu(HudYellowStatuBjType.HIDE);
        HudEventManager.getInstance().getHudEvent().hideIntervalSpeed();
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

            HudEventManager.getInstance().getHudEvent().sendLaneInformation(t800LaneCountBean);
        }
    }

    @Override
    public void hideLaneInfo() {
//        HudEventManager.getInstance().getHudEvent().sendLaneHide();
//        HudEventManager.getInstance().getHudEvent().sendLaneHide();
        HudLaneCountBean t800LaneCountBean=new HudLaneCountBean();
        t800LaneCountBean.add(HudLaneType.C_F_Y);
        HudEventManager.getInstance().getHudEvent().sendLaneInformation(t800LaneCountBean);
    }

    @Override
    public void sendImage(Bitmap bitmap, HudImageType type) {

        HudEventManager.getInstance().getHudEvent().sendImage(bitmap,type);
    }





    @Override
    public void hideImage() {

        HudEventManager.getInstance().getHudEvent().hideImage();
    }

    public boolean needShowYellow(HudWarningPointType warningPointType){
        switch (warningPointType){
            case c01:
            case c02:
            case c16:
            case c_60:

                return true;
        }
        return false;
    }

    @Override
    public void sendCamera(List<HudCameraBean> cameras) {
        if(cameras.size()==1){
            HudCameraBean camera=cameras.get(0);
            HudWarningPointType type= GaoDeTypeToWarningPointUtil.useCameraGetType(camera);
//            if(camera.getSpeed()!=0){
//                HudEventManager.getInstance().getHudEvent().sendNowSpeed(GaoDeEventManager.getInstance().getNowSpeed(),camera.getSpeed(),0);
//            }


            HudSpeedManager.getInstance().setLimitSpeedToCountShowSpeedBj(camera.getSpeed());
            HudEventManager.getInstance().getHudEvent().sendBigWarningPoint(type,camera.getDis());
            if(camera.getDis()<50){
                nowSetNeedCheckCamera();
            }
            HudYellowStatuBjType yellowStatuBjType=HudYellowStatuBjType.HIDE;
            if(needShowYellow(type)){
                yellowStatuBjType=HudYellowStatuBjType.STYLE_T700;
            }
            Log.i("ble_code_check","ble_code_check  camera 1:"+yellowStatuBjType);
            HudManager.getInstance().getHudEvent().setWarningPointYellowStatu(yellowStatuBjType,HudYellowStatuBjType.HIDE);
            if(camera.getDis()<100){
                HudManager.getInstance().getHudEvent().setWarningPointPass(HudStatuType.OPEN,HudStatuType.CLOSE,HudStatuType.CLOSE);
            }else {
                HudManager.getInstance().getHudEvent().setWarningPointPass(HudStatuType.CLOSE,HudStatuType.CLOSE,HudStatuType.CLOSE);
            }


        }else if(cameras.size()>1){
            HudCameraBean camera1=cameras.get(0);


            HudCameraBean camera2=cameras.get(1);
            HudWarningPointType type1=GaoDeTypeToWarningPointUtil.useCameraGetType(camera1);
            HudYellowStatuBjType yellowStatuBjType1=HudYellowStatuBjType.HIDE;
            if(needShowYellow(type1)){
                yellowStatuBjType1=HudYellowStatuBjType.STYLE_T700;
            }
            if(camera1.getDis()<25){
                HudManager.getInstance().getHudEvent().setWarningPointPass(HudStatuType.OPEN,HudStatuType.CLOSE,HudStatuType.CLOSE);
            }else {
                HudManager.getInstance().getHudEvent().setWarningPointPass(HudStatuType.CLOSE,HudStatuType.CLOSE,HudStatuType.CLOSE);
            }

            HudWarningPointType type2=GaoDeTypeToWarningPointUtil.useCameraGetType(camera2);
            HudYellowStatuBjType yellowStatuBjType2=HudYellowStatuBjType.HIDE;
            if(needShowYellow(type2)){
                yellowStatuBjType2=HudYellowStatuBjType.STYLE_T700;
            }
//            if(camera1.getSpeed()!=0||camera2.getSpeed()!=0){
//                HudEventManager.getInstance().getHudEvent().sendNowSpeed(GaoDeEventManager.getInstance().getNowSpeed(),camera1.getSpeed(),camera2.getSpeed());
//            }
            HudSpeedManager.getInstance().setLimitSpeedToCountShowSpeedBj(camera1.getSpeed(),camera2.getSpeed());
            HudEventManager.getInstance().getHudEvent().sendWarningPoint(type1,camera1.getDis(),type2,camera2.getDis());
            needCheckCamera=false;
            Log.i("ble_code_check","ble_code_check  camera 2:"+yellowStatuBjType1+"-"+yellowStatuBjType2);
            HudManager.getInstance().getHudEvent().setWarningPointYellowStatu(yellowStatuBjType1,yellowStatuBjType2);
        }
    }

    private boolean needCheckCamera;
    private long lastCheckCameraTime;
    private void nowSetNeedCheckCamera(){
        needCheckCamera=true;
        lastCheckCameraTime=System.currentTimeMillis();
    }


    private void nowCheckCamera(){
        if(!needCheckCamera){
            return;
        }
        if(System.currentTimeMillis()-lastCheckCameraTime>3000){
            HudEventManager.getInstance().getHudEvent().sendBigWarningPoint(HudWarningPointType.none,0);
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
//        HudDataEvent.getInstance().setNowSpeed(nowSpeed);
//        HudEventManager.getInstance().getHudEvent().sendNowSpeed(nowSpeed);
        HudSpeedManager.getInstance().setNowSpeed(nowSpeed);
    }

    @Override
    public void sendYellowStatu(boolean isOpen) {
//        if(isOpen){
//            HudEventManager.getInstance().getHudEvent().setWarningPointYellowStatu();YellowStatu(HudYellowStatuBjType.STYLE_T700);
//        }else {
//            HudEventManager.getInstance().getHudEvent().setYellowStatu(HudYellowStatuBjType.HIDE);
//        }
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
            Log.i("kankanshanshuo","kankanshanshuomei:11112223");
            HudTurnTypeManager.getInstance().sendTurnType(t800TrunType,segRemainDis,secondTrunType,secondTrunTypeSegRemainDis);
            secondTrunType=null;
            secondTrunTypeSegRemainDis=0;
        }else {
            Log.i("kankanshanshuo","kankanshanshuomei:11112224");
            HudTurnTypeManager.getInstance().sendTurnType(t800TrunType,segRemainDis);
//            switch (HudManager.getInstance().getHudDevice()){
//                case T700:
//                    HudEventManager.getInstance().getHudEvent().sendNewTurnType(t800TrunType,segRemainDis);
//                    break;
//                default:
//                    HudEventManager.getInstance().getHudEvent().sendTurnType(t800TrunType,segRemainDis);
//                    break;
//            }


        }
        if(segRemainDis<25 ){
            HudManager.getInstance().getHudEvent().showTurnMsgKorean();
        }else {
            HudManager.getInstance().getHudEvent().hideTurnMsgKorean();
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
        HudEventManager.getInstance().getHudEvent().sendReachInfo(routeRemainDis,useTimeH,useTimeM, HudReachType.ARRIVAL);
//        nowCheckIntervalSpeed();
        nowCheckCamera();
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
    public void deviceSetPromptToneStatu(boolean isOpen) {
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
