package com.nrmyw.gaode_t.event.debug;

import android.graphics.Bitmap;

import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.nrmyw.gaode_t.bean.HudCameraBean;
import com.nrmyw.gaode_t.event.GaoDeNaviEventImp;
import com.nrmyw.gaode_t.log.LG;
import com.nrmyw.gaode_t.type.NaviSdkAvoidForbidType;
import com.nrmyw.gaode_t.type.NaviSdkAvoidType;
import com.nrmyw.gaode_t.type.NaviSdkGpsType;
import com.nrmyw.gaode_t.type.NaviSdkTurnIconType;
import com.nrmyw.hud_data_lib.type.image.HudImageType;

import java.util.List;

/**
 * 没连接所有的测试数据处理，都来这里
 */
public class GaoDeDeBugEvent implements GaoDeNaviEventImp {
    private static final String tag="gaode_event_debug";
    private static GaoDeDeBugEvent gaoDeDeBugEvent;
    private GaoDeDeBugEvent(){}

    public static GaoDeDeBugEvent getInstance(){
        if(null==gaoDeDeBugEvent){
            synchronized (GaoDeDeBugEvent.class){
                if(null==gaoDeDeBugEvent){
                    gaoDeDeBugEvent=new GaoDeDeBugEvent();
                }
            }
        }
        LG.e(tag,"please connect hud ble!!!!");
        return gaoDeDeBugEvent;
    }


    @Override
    public void retrunBytes(byte[] bytes) {

    }

    @Override
    public void start(int i) {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void sendNavigationText(String text) {

    }


    @Override
    public void sendIntervalInfo(int intervalSpeed, int interval, int averageSpeed) {

    }

    @Override
    public void sendLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void sendImage(Bitmap bitmap, HudImageType type) {

    }





    @Override
    public void hideImage() {

    }

    @Override
    public void sendCamera(List<HudCameraBean> cameras) {

    }



    @Override
    public void sendGpsStatu(NaviSdkGpsType gpsType) {

    }

    @Override
    public void sendAMapServiceAreaInfo(AMapServiceAreaInfo aMapServiceAreaInfo) {

    }


    @Override
    public void updateNowSpeed(int nowSpeed) {

    }

    @Override
    public void sendYellowStatu(boolean isOpen) {

    }

    @Override
    public void updateCurPointIndexStr(String curPointIndexStr) {

    }

    @Override
    public void updateCurRoadName(String curRoadName) {

    }

    @Override
    public void updateSegRemainInfo(NaviSdkTurnIconType turnIconType, int segRemainDis) {

    }

    @Override
    public void updateSegRemainTime(int segRemainTime) {

    }

    @Override
    public void updateNextRoadName(String nextRoadName) {

    }

    @Override
    public void updateCurSegIndex(int curSegIndex) {

    }

    @Override
    public void updateTotalRemainInfo(int routeRemainTime, int routeRemainDis) {

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

    }

    @Override
    public void updateOver() {

    }

    @Override
    public void deviceQueVersion() {

    }

    @Override
    public void deviceQueSN() {

    }

    @Override
    public void deviceQueBrightness() {

    }

    @Override
    public void deviceQueSound() {

    }

    @Override
    public void deviceSetSound(int v) {

    }

    @Override
    public void deviceQuePromptToneStatu() {

    }

    @Override
    public void deviceSetePromptToneStatu(boolean isOpen) {

    }


    @Override
    public void deviceQueGpsSpeed() {

    }

    @Override
    public void deviceSetGpsSpeed(int v) {

    }
}
