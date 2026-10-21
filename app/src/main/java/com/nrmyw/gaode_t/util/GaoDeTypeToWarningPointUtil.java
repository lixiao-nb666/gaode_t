package com.nrmyw.gaode_t.util;


import android.util.Log;

import com.nrmyw.gaode_t.bean.HudCameraBean;
import com.nrmyw.gaode_t.type.NaviSdkAvoidType;
import com.nrmyw.hud_data_lib.type.warningproint.HudWarningPointType;

public class GaoDeTypeToWarningPointUtil {

    public static HudWarningPointType useNaviSdkAvoidType(NaviSdkAvoidType naviSdkAvoidType){
        switch (naviSdkAvoidType){
            case AVOID_WEIGHT:
                break;
            case AVOID_TURN:
                break;
            case AVOID_W:
                break;
            case AVOID_H:
                break;
        }
        return HudWarningPointType.none;
    }

    public static HudWarningPointType useCameraGetType(HudCameraBean camera){
        Log.i("kankanxiansu","kankanxiansudaxiao:"+camera.getSpeed()+"---"+camera);
        if(camera.getSpeed()>0){
            switch (camera.getCameraType()){
                case BREAKRULE:
                    return HudWarningPointType.c01;
                case TRAFFICLIGHT:
                    return HudWarningPointType.c02;
                case SURVEILLANCE ://监控摄像
                    return HudWarningPointType.c16;
                default:
                    return HudWarningPointType.c05;
            }
        }else {
            switch (camera.getCameraType()){
                case BREAKRULE://违章拍照
                   return HudWarningPointType.numb_6;
                case TRAFFICLIGHT://闯红灯拍照
                    return HudWarningPointType.numb_37;


                case EMERGENCY://应急车道拍照
                        return HudWarningPointType.c_60;
                case BUSWAY://公交专用道摄像头
                    return HudWarningPointType.numb_9;
                default:
                    return HudWarningPointType.numb_14;
            }
        }



//        if(camera.getCameraSpeed()==0){
//            switch (camera){
//
//            }
//        }else {
//            //当速度不等于0的时候，发限速度的框即可
//            switch (camera){
//                case SPEED://测速摄像,还要发送限速指令
//                    return HudWarningPointType.c01;
//
//                case TRAFFICLIGHT://闯红灯拍照
//                    return HudWarningPointType.c02;
//                case BREAKRULE://违章拍照
//                case EMERGENCY://应急车道拍照
//                case BUSWAY://公交专用道摄像头
//                    return HudWarningPointType.c16;
//                case  FLOWSPEED:// 流动测速电子眼
//                case ETC://ETC计费拍照
//                case SURVEILLANCE ://监控摄像
//                    return HudWarningPointType.c04;
//            }
//        }

    }



}
