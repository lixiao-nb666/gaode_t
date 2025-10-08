//package com.nrmyw.gaode_t.type;
//
//
//import com.nrmyw.ble_event_lib.bean.BleDeviceBean;
//import com.nrmyw.gaode_t.event.HudNaviEventImp;
//import com.nrmyw.gaode_t.event.t800.GaoDeT800Event;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//public enum BleDeviceType {
//
//    T800("T800","data",new GaoDeT800Event()),
//    T700("T800","DATA",new GaoDeT800Event()),
//    ;
//    private String title;
//    private String body;
//    private HudNaviEventImp eventImp;
//    BleDeviceType(String title, String body,HudNaviEventImp eventImp){
//        this.title=title;
//        this.body=body;
//        this.eventImp=eventImp;
//    }
//
//    public  BleDeviceBean getBleDevice(){
//        BleDeviceBean bleDeviceBean=new BleDeviceBean();
//        bleDeviceBean.setDeviceName(name());
//        bleDeviceBean.setDeviceType(ordinal());
//        bleDeviceBean.setDeviceTitle(title);
//        bleDeviceBean.setDeviceBody(body);
//        return bleDeviceBean;
//    }
//
//    public HudNaviEventImp getEventImp() {
//        return eventImp;
//    }
//
//    public static List<BleDeviceBean> getBleDeviceTypeList(){
//        List<BleDeviceBean> typeList=new ArrayList<>();
//        for(BleDeviceType deviceType:BleDeviceType.values()){
//            typeList.add(deviceType.getBleDevice());
//        }
//        return typeList;
//    }
//
//    public static BleDeviceType getBleDeviceType(BleDeviceBean bleDeviceBean){
//        try {
//            return BleDeviceType.values()[bleDeviceBean.getDeviceType()];
//        }catch (Exception e){
//            return null;
//        }
//    }
//}
