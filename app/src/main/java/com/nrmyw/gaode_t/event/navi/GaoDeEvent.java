package com.nrmyw.gaode_t.event.navi;

import android.text.TextUtils;

import com.amap.api.navi.model.AMapNotAvoidInfo;

import com.nrmyw.gaode_t.event.GaoDeNaviEventImp;
import com.nrmyw.gaode_t.event.t800.GaoDeT800Event;
import com.nrmyw.gaode_t.type.NaviSdkAvoidForbidType;
import com.nrmyw.gaode_t.type.NaviSdkAvoidType;
import com.nrmyw.gaode_t.type.NaviSdkTurnIconType;
import com.nrmyw.gaode_t.util.CheckUtil;

class GaoDeEvent {

    private int nowSpeed;
//    private int mCurPointIndex;//当前道路位置的索引坐标 :1/5
//    private int mCurLinkIndex;//当前位置的总索引长度
    private String mCurPointIndexStr;
    private String mCurRoadName;//当前道路名字
    private int mSegRemainDis;//当前道路剩余的距离
    private int mSegRemainTime;//当前道路剩余的时间
    private int mCurSegIndex;//当前位置，在总道理上面的索引位置坐标6/10
    private long pathID;//当前线路的ID没啥用,切换路线用的，对于HUD来说不用管
    private int mRouteRemainDis;//总记还剩下的距离
    private int mRouteRemainTime;//总记还剩下的时间
    private int mType;//导航类型，模拟导航，真实导航，没啥用
    //
    private String mNextRoadName;//下一条道路的名称
    private NaviSdkTurnIconType turnIconType;//导航转向图标类型
    private String exitName;//出口文字描述
    private String exitDirection;//出口方向文字描述
    private int routeRemainLightCount;//剩余红绿灯的数量
//    private NaviSdkAvoidType avoidType;//禁行大标识
//    private NaviSdkAvoidForbidType avoidForbidType;//禁行小种类



    public GaoDeEvent(){

    }



    public GaoDeNaviEventImp eventImp(){
        return GaoDeT800Event.getInstance();
    }





    public void setNowSpeed(int newNowSpeed) {
        if(CheckUtil.checkIntNeedChange(nowSpeed,newNowSpeed)){
            nowSpeed=newNowSpeed;
            eventImp().updateNowSpeed(nowSpeed);
        }
    }

    public int getNowSpeed(){
        return nowSpeed;
    }


    public void setCurPointIndex(int mCurPointIndex,int mCurLinkIndex) {
//        mCurPointIndex = mCurPointIndex;
//        mCurLinkIndex = mCurLinkIndex;
        String newCurPointIndexStr=mCurPointIndex+"/"+mCurLinkIndex;
        if(CheckUtil.checkStrNeedChange(mCurPointIndexStr,newCurPointIndexStr)){
            mCurPointIndexStr=newCurPointIndexStr;
            eventImp().updateCurPointIndexStr(mCurPointIndexStr);
        }
    }



    public void setCurRoadName(String newCurRoadName) {
        if(TextUtils.isEmpty(newCurRoadName)){
            newCurRoadName="none";
        }
        if(CheckUtil.checkStrNeedChange(mCurRoadName,newCurRoadName)){
            mCurRoadName=newCurRoadName;
            eventImp().updateCurRoadName(mCurRoadName);
        }
    }



    public void setSegRemainDis(int newIconTypeNumb,int newSegRemainDis) {
        int nowIconTypeNumb=0;
        if(null!=turnIconType){
            nowIconTypeNumb =turnIconType.getIconType();
        }
        boolean needChange=false;
        if(CheckUtil.checkIntNeedChange(nowIconTypeNumb,newIconTypeNumb)){
            turnIconType=NaviSdkTurnIconType.getNeedType(newIconTypeNumb);
            needChange=true;
        }

        if(CheckUtil.checkIntNeedChange(mSegRemainDis,newSegRemainDis)){
            mSegRemainDis = newSegRemainDis;
            needChange=true;
        }
        if(needChange){
            eventImp().updateSegRemainInfo(turnIconType,newSegRemainDis);
        }

    }

    public void setSegRemainTime(int newSegRemainTime) {


        if(CheckUtil.checkIntNeedChange(mSegRemainTime,newSegRemainTime)){
            mSegRemainTime = newSegRemainTime;
            eventImp().updateSegRemainTime(mSegRemainTime);
        }
    }

    public void setCurSegIndex(int newCurSegIndex) {
        if(CheckUtil.checkIntNeedChange(mCurSegIndex,newCurSegIndex)){
            mCurSegIndex = newCurSegIndex;
            eventImp().updateCurSegIndex(mCurSegIndex);
        }
    }





    public void setRouteRemainInfo(int newRouteRemainTime,int newRouteRemainDis) {
        boolean needChange=false;
        if(CheckUtil.checkIntNeedChange(mRouteRemainTime ,newRouteRemainTime )){
            mRouteRemainTime = newRouteRemainTime;
            needChange=true;
        }
        if(CheckUtil.checkIntNeedChange(mRouteRemainDis,newRouteRemainDis)){
            mRouteRemainDis = newRouteRemainDis;
            needChange=true;
        }
        if(needChange){
            eventImp().updateTotalRemainInfo(mRouteRemainTime,mRouteRemainDis);
        }
    }



    public void setNextRoadName(String newNextRoadName) {
        if(TextUtils.isEmpty(newNextRoadName)){
            newNextRoadName="none";
        }
        if(CheckUtil.checkStrNeedChange(mCurRoadName,newNextRoadName)){
            mNextRoadName=newNextRoadName;
            eventImp().updateNextRoadName(mNextRoadName);
        }
    }



//    public void setExitName(String newExitName) {
//
//
//
//        if(CheckUtil.checkStrNeedChange(exitName,newExitName)){
//            exitName = newExitName;
//        }
//
//    }

//    public void setExitDirection(String exitDirection) {
//        exitDirection = exitDirection;
//    }

    public void setRouteRemainLightCount(int newRouteRemainLightCount) {
        if(CheckUtil.checkIntNeedChange(routeRemainLightCount,newRouteRemainLightCount)){
            routeRemainLightCount = newRouteRemainLightCount;
            eventImp().updateRouteRemainLightCount(routeRemainLightCount);
        }
    }

    public void setAvoid( AMapNotAvoidInfo aMapNotAvoidInfo){
        if(null==aMapNotAvoidInfo||!aMapNotAvoidInfo.isValid()){
            return;
        }
        NaviSdkAvoidType avoidType=NaviSdkAvoidType.getNeedType(aMapNotAvoidInfo.type);
        NaviSdkAvoidForbidType avoidForbidType=NaviSdkAvoidForbidType.getNeedType(aMapNotAvoidInfo.forbidType);
        int dis=aMapNotAvoidInfo.distToCar;
        switch (avoidType){
            case NONE:
                break;
            default:
                eventImp().updateAvoidInfo(avoidType,avoidForbidType,dis);
                break;
        }
    }

    public void setEventDoOver(){
        eventImp().updateOver();
    }
}
