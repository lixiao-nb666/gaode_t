package com.nrmyw.gaode_t.config;



import com.nrmyw.ble_event_lib.type.BleSendBitmapQualityType;
import com.nrmyw.gaode_t.event.GaoDeNaviEventImp;
import com.nrmyw.gaode_t.event.t800.GaoDeT800Event;



public class GaoDeHudSendConfig {

    private static GaoDeHudSendConfig config;

    private boolean isNeedAddLaneInfo=true;

    private BleSendBitmapQualityType qualityType;

//    private BleDeviceType connectedBleDevice;

    private GaoDeHudSendConfig(){
        qualityType=BleSendBitmapQualityType.DEF;
    }

    public static GaoDeHudSendConfig getInstance(){
        if(null==config){
            synchronized (GaoDeHudSendConfig.class){
                if(null==config){
                    config=new GaoDeHudSendConfig();
                }
            }
        }
        return config;
    }

    public boolean isNeedAddLaneInfo() {
        return isNeedAddLaneInfo;
    }

//    public void setNeedAddLaneInfo(boolean needAddLaneInfo) {
//        isNeedAddLaneInfo = needAddLaneInfo;
//    }
//
//    public BleSendBitmapQualityType getQualityType() {
//        return qualityType;
//    }
//
//    public void setQualityType(BleSendBitmapQualityType qualityType) {
//        this.qualityType = qualityType;
//    }

//    public BleDeviceType getBleDeviceType() {
//        BleDeviceBean bleDeviceBean= NewBeeBleManager.getInstance().getNowUseBleDevice();
//        if(null!=bleDeviceBean){
//            connectedBleDevice=BleDeviceType.values()[bleDeviceBean.getDeviceType()];
//        }
//        return connectedBleDevice;
//    }

    public GaoDeNaviEventImp getEventImp(){
//        if(null==connectedBleDevice||null==connectedBleDevice.getEventImp()){
//            return GaoDeDeBugEvent.getInstance();
//        }

        return GaoDeT800Event.getInstance();
    }

//    public void setBleDeviceConnecting(){
//        BleDeviceBean bleDeviceBean= NewBeeBleManager.getInstance().getNowUseBleDevice();
//        if(null!=bleDeviceBean){
//            connectedBleDevice=BleDeviceType.values()[bleDeviceBean.getDeviceType()];
//        }
//    }
//
//    public void setBleDeviceConnected(){
//        BleDeviceBean bleDeviceBean= NewBeeBleManager.getInstance().getNowUseBleDevice();
//        if(null!=bleDeviceBean){
//            connectedBleDevice=BleDeviceType.values()[bleDeviceBean.getDeviceType()];
////            GaoDeEventManager.getInstance().sendEventImp(connectedBleDevice.getEventImp());
//        }
//    }
//
//
//    public void setBleDeviceDisconnected() {
////        connectedBleDevice=null;
////        GaoDeEventManager.getInstance().sendEventImp(null);
//    }
}
