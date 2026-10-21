package com.nrmyw.gaode_t.event;

import android.graphics.Bitmap;

import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.nrmyw.gaode_t.bean.HudCameraBean;
import com.nrmyw.gaode_t.type.NaviSdkAvoidForbidType;
import com.nrmyw.gaode_t.type.NaviSdkAvoidType;
import com.nrmyw.gaode_t.type.NaviSdkGpsType;
import com.nrmyw.gaode_t.type.NaviSdkTurnIconType;
import com.nrmyw.hud_data_lib.type.image.HudImageType;

import java.util.List;

public interface GaoDeNaviEventImp {

    public void retrunBytes(byte[] bytes);


    //开始导航了
    public void start(int i);

    //到达目的地,结束导航了
    public void onArriveDestination();

    //导航提示语
    public void sendNavigationText(String text);

    //区间速度
    public void sendIntervalInfo(int intervalSpeed,int interval, int averageSpeed );

    //设置道路信息
    public void sendLaneInfo(AMapLaneInfo aMapLaneInfo);
    //隐藏道路信息
    public void hideLaneInfo();

    //发送实景图
    public void sendImage(Bitmap bitmap, HudImageType type);

    //隐藏实景图
    public void hideImage();

    //监控摄像头
    public void sendCamera(List<HudCameraBean> cameras);

    //GPS信息
    public void sendGpsStatu(NaviSdkGpsType gpsType);

    //发送服务器信息
    public void sendAMapServiceAreaInfo(AMapServiceAreaInfo aMapServiceAreaInfo);
    

    //发送当前速度
    public void updateNowSpeed(int nowSpeed);


    public void sendYellowStatu(boolean isOpen);


    public void updateCurPointIndexStr(String curPointIndexStr);

    public void updateCurRoadName(String curRoadName);

    public void updateSegRemainInfo(NaviSdkTurnIconType turnIconType, int segRemainDis);

    public void updateSegRemainTime(int segRemainTime);

    public void updateNextRoadName(String nextRoadName);

    public void updateCurSegIndex(int curSegIndex);

    public void updateTotalRemainInfo(int routeRemainTime,int routeRemainDis);

    public void updateExitName(String exitName);

    public void updateExitDirection(String exitDirection);

    public void updateRouteRemainLightCount(int routeRemainLightCount);

    //更新警示图标
    public void updateAvoidInfo(NaviSdkAvoidType avoidType, NaviSdkAvoidForbidType avoidForbidType, int dis);

    public void updateOver();

    //查询设备版本号
    public void deviceQueVersion();
    //查询设备SN
    public void deviceQueSN();
    //查询设备亮度
    public void deviceQueBrightness();
    //查询设备声音
    public void deviceQueSound();
    public void deviceSetSound(int v);
    //查询设备提示音
    public void deviceQuePromptToneStatu();
    public void deviceSetPromptToneStatu(boolean isOpen);
    //查询设备GPS速度比例值
    public void deviceQueGpsSpeed();
    public void deviceSetGpsSpeed(int v);
}
