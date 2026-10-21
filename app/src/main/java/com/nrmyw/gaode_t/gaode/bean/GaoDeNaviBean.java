package com.nrmyw.gaode_t.gaode.bean;

import android.text.TextUtils;

import com.amap.api.navi.AmapNaviTheme;
import com.amap.api.navi.AmapNaviType;
import com.nrmyw.gaode_t.gaode.type.GaoDeBroadCastType;
import com.nrmyw.gaode_t.gaode.type.GaoDeCarType;
import com.nrmyw.gaode_t.gaode.type.GaoDeDayAndNightType;

import java.io.Serializable;

public class GaoDeNaviBean implements Serializable {
    private  AmapNaviType amapNaviType;
    private AmapNaviTheme amapNaviTheme;
    private GaoDeDayAndNightType dayAndNightType;

    private GaoDeBroadCastType broadCastType;

    private GaoDeCarType carType;
    private String carNumb;
    private boolean restriction=true;


    public void setAmapNaviType(AmapNaviType amapNaviType) {
        this.amapNaviType = amapNaviType;
    }

    public AmapNaviType getAmapNaviType() {
        if(null==amapNaviType){
            amapNaviType= AmapNaviType.DRIVER;
        }

        return amapNaviType;
    }


    public void setAmapNaviTheme(AmapNaviTheme amapNaviTheme) {
        this.amapNaviTheme = amapNaviTheme;
    }


    public AmapNaviTheme getAmapNaviTheme() {
        if(null==amapNaviTheme){
            amapNaviTheme=AmapNaviTheme.BLUE;
        }

        return amapNaviTheme;
    }






    public String getCarNumb() {
        if(TextUtils.isEmpty(carNumb)){
            return "粤BGG7266";
        }

        return carNumb;
    }

    public void setCarNumb(String carNumb) {
        this.carNumb = carNumb;
    }




    public GaoDeDayAndNightType getDayAndNightType() {
        if(null==dayAndNightType){
            dayAndNightType=GaoDeDayAndNightType.AUTO;
        }
        return dayAndNightType;
    }

    public void setDayAndNightType(GaoDeDayAndNightType dayAndNightType) {
        this.dayAndNightType = dayAndNightType;
    }

    public GaoDeBroadCastType getBroadCastType() {
        if(null==broadCastType){
            broadCastType=GaoDeBroadCastType.detailed_broadcast;
        }

        return broadCastType;
    }

    public void setBroadCastType(GaoDeBroadCastType broadCastType) {
        this.broadCastType = broadCastType;
    }

    public boolean isRestriction() {
        return restriction;
    }

    public void setRestriction(boolean restriction) {
        this.restriction = restriction;
    }

    public GaoDeCarType getCarType() {
        if(null==carType){
            carType=GaoDeCarType.Fuel_passenger_car;
        }
        return carType;
    }

    public void setCarType(GaoDeCarType carType) {
        this.carType = carType;
    }

    @Override
    public String toString() {
        return "GaoDeNaviBean{" +
                "amapNaviType=" + amapNaviType +
                ", amapNaviTheme=" + amapNaviTheme +
                ", dayAndNightType=" + dayAndNightType +
                ", broadCastType=" + broadCastType +
                ", carType=" + carType +
                ", carNumb='" + carNumb + '\'' +
                ", restriction=" + restriction +
                '}';
    }
}
