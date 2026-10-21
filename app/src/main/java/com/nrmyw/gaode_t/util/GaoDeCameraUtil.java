package com.nrmyw.gaode_t.util;

import android.util.ArrayMap;
import android.util.Log;

import com.amap.api.navi.enums.CameraType;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.nrmyw.gaode_t.bean.HudCameraBean;
import com.nrmyw.gaode_t.type.NaviSdkCamera;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GaoDeCameraUtil {


    public static  List<HudCameraBean> getNeedCameras(AMapNaviCameraInfo[] aMapNaviCameraInfos){
        Map<String,HudCameraBean> camerasMap=getCameraMapByDis(aMapNaviCameraInfos);
        //当摄像头不为空，并且距离不一样的时候，才能添加，有时候是一个位置多个摄像头,代码直接处理好了
//        NaviSdkCamera camera= GaoDeCameraUtil.getCanAddCamera(aMapNaviCameraInfo,cameras);
//        if(null!=camera){
////                switch (camera){
////                    case TRAFFICLIGHT:
////                    case SPEED:
////                    case FLOWSPEED:
////                        cameras.add(0,camera);
////                        break;
////                    default:
////                        cameras.add(camera);
////                        break;
////                }
//            cameras.add(camera);

        return getCamerasUseMap(camerasMap);
    }

    private static Map<String, HudCameraBean> getCameraMapByDis(AMapNaviCameraInfo[] aMapNaviCameraInfos){
        Map<String,HudCameraBean> camerasMap=new ArrayMap<>();
        //这里不出区间测速的摄像头，因为有另外的方法处理了
        int size=0;
        for(AMapNaviCameraInfo aMapNaviCameraInfo:aMapNaviCameraInfos){
            NaviSdkCamera nowCamera=NaviSdkCamera.getNeedType(aMapNaviCameraInfo.getCameraType());
            int dis=aMapNaviCameraInfo.getCameraDistance();
            int speed=aMapNaviCameraInfo.getCameraSpeed();
            String mapK=dis+"";
            HudCameraBean hudCameraBean=camerasMap.get(mapK);
            if(null==hudCameraBean){
                size++;
                if(size>2){
                    Log.i("kankanshenmegui","knkanxiansudaxia--------------------------------------------------");
                    return camerasMap;
                }
                hudCameraBean=new HudCameraBean();
                hudCameraBean.setDis(dis);

            }
            hudCameraBean.setSpeed(speed);
            hudCameraBean.add(nowCamera);
            Log.i("kankanshenmegui","knkanxiansudaxia---------"+aMapNaviCameraInfo.getCameraType()+"--"+aMapNaviCameraInfo.getCameraSpeed()+"--"+aMapNaviCameraInfo.getCameraDistance());
            camerasMap.put(mapK,hudCameraBean);
        }
        Log.i("kankanshenmegui","knkanxiansudaxia--------------------------------------------------");
        return camerasMap;
    }

    private static List<HudCameraBean> getCamerasUseMap(Map<String,HudCameraBean> checkMap){
        List<HudCameraBean> list=new ArrayList<>();
        for(String keyS:checkMap.keySet()){
            list.add(checkMap.get(keyS));
        }
        return list;
    }

//    public static NaviSdkCamera getCanAddCamera(AMapNaviCameraInfo aMapNaviCameraInfo,List<NaviSdkCamera> naviSdkCameras){
//        NaviSdkCamera nowCamera=NaviSdkCamera.getNeedType(aMapNaviCameraInfo.getCameraType());
//        for(NaviSdkCamera camera:naviSdkCameras){
//            if(aMapNaviCameraInfo.getDistance()==camera.getDis()){
//                return null;
//            }
//            if(nowCamera.ordinal()==camera.ordinal()){
//                return null;
//            }
//
////            if(nowCamera.ordinal()==camera.ordinal()&&aMapNaviCameraInfo.getCameraDistance()>camera.getDis()){
////                return null;
////            }
//        }
//        switch (nowCamera){
//            case NONE:
////            case INTERVALVELOCITYSTART:
////            case INTERVALVELOCITYEND:
////            case BICYCLE:
//                return null;
//
//            default:
//                nowCamera.setDis(aMapNaviCameraInfo.getCameraDistance());
//                nowCamera.setCameraSpeed(aMapNaviCameraInfo.getCameraSpeed());
//                return nowCamera;
//        }
//
//
//    }



}
