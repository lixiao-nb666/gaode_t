package com.nrmyw.gaode_t.bean;

import com.nrmyw.gaode_t.type.NaviSdkCamera;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HudCameraBean implements Serializable {
    private List<NaviSdkCamera> list;
    private int speed;
    private int dis;

    public void add(NaviSdkCamera naviSdkCamera){
        if(null==list){
            list=new ArrayList<>();
        }
        if(!list.contains(naviSdkCamera)){
            list.add(naviSdkCamera);
        }

    }

    public NaviSdkCamera getCameraType(){
        if(null==list||list.size()==0){
            return NaviSdkCamera.NONE;
        }
        if(list.size()==1){
            return list.get(0);
        }

        if(list.contains(NaviSdkCamera.TRAFFICLIGHT)){
            return NaviSdkCamera.TRAFFICLIGHT;
        }
        if(list.contains(NaviSdkCamera.SURVEILLANCE)
                ||list.contains(NaviSdkCamera.SPEED)
                ||list.contains(NaviSdkCamera.FLOWSPEED)){
            return NaviSdkCamera.SURVEILLANCE;
        }
        return NaviSdkCamera.BREAKRULE;
    }


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if(this.speed<speed){
            this.speed = speed;
        }

    }

    public int getDis() {
        return dis;
    }

    public void setDis(int dis) {
        this.dis = dis;
    }

    @Override
    public String toString() {
        return "HudCameraBean{" +
                "list=" + list +
                ", speed=" + speed +
                ", dis=" + dis +
                '}';
    }
}
