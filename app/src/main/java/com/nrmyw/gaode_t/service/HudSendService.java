//package com.nrmyw.gaode_t.service;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Handler;
//import android.os.Message;
//import androidx.annotation.NonNull;
//import com.amap.api.maps.MapsInitializer;
//
//
//import com.newbee.bulid_lib.mybase.service.BaseService;
//import com.newbee.t800_lib.manager.T800SendManager;
//import com.newbee.t800_lib.type.T800CmdType;
//import com.newbee.t800_lib.type.T800SendImageType;
//import com.newbee.t800_lib.util.T800CmdRetrunDataUtil;
//import com.newbee.t800_lib.util.T800SendUtil;
//
//import com.nrmyw.ble_event_lib.bean.BleSendImageEndInfoBean;
//import com.nrmyw.ble_event_lib.bean.BleSendImageStartInfoBean;
//import com.nrmyw.ble_event_lib.send.BleEventSubscriptionSubject;
//import com.nrmyw.ble_event_lib.statu.BleStatu;
//import com.nrmyw.ble_event_lib.statu.BleStatuEventObserver;
//import com.nrmyw.ble_event_lib.statu.BleStatuEventSubscriptionSubject;
//import com.nrmyw.ble_event_lib.type.BleSendBitmapQualityType;
//import com.nrmyw.ble_event_lib.type.BleSendFileType;
//import com.nrmyw.gaode_t.config.GaoDeHudSendConfig;
//
//import java.io.File;
//import java.util.Date;
//
//public class HudSendService extends BaseService {
////    private BluetoothGattServiceDao bluetoothGattServiceDao;
//    private BleStatuEventObserver bleStatuEventObserver=new BleStatuEventObserver() {
//        @Override
//        public void sendBleStatu(BleStatu bleStatu, Object... objects) {
//            switch (bleStatu){
//                case CONNECTING:
//                    GaoDeHudSendConfig.getInstance().setBleDeviceConnecting();
//                    break;
//                case CONNECTED:
//                    initTime();
//                    GaoDeHudSendConfig.getInstance().setBleDeviceConnected();
//                    break;
//                case DISCONNECTED:
//                    GaoDeHudSendConfig.getInstance().setBleDeviceDisconnected();
//                    break;
//
//                case SEND_IMAGE_START:
//                    doSendImageStartThing((BleSendImageStartInfoBean) objects[0]);
//                    break;
//                case SEND_IMAGE_END:
//                    doSendImageEndThing((BleSendImageEndInfoBean) objects[0]);
//                    break;
//                case RUN_ERR:
//                    //                    ToastUtil.showToast(getBaseContext().getApplicationContext(),bleStatu.name());
//                    break;
//                case RETRUN_BYTES:
//                    byte[] retrunBytes= (byte[]) objects[0];
//                    T800CmdRetrunDataUtil.doVue(retrunBytes);
//                    break;
//
//            }
//        }
//
//        private void doSendImageStartThing(BleSendImageStartInfoBean startInfoBean){
//
//            T800CmdType cmdType=T800CmdType.READY_SEND_IMAGE;
//            cmdType.useObjectSSetBody(startInfoBean.getW(),startInfoBean.getH(),startInfoBean.getSize(), T800SendImageType.START);
//            byte[] startBytes=cmdType.getAllByte();
////            if(null!=getBluetoothGattService()){
////                getBluetoothGattService().sendImageIndexCmd(0,startBytes);
////            }
//            BleEventSubscriptionSubject.getInstance().sendImageIndexCmd(0,startBytes);
//        }
//
//        private void doSendImageEndThing(BleSendImageEndInfoBean endInfoBean){
//
//            T800CmdType cmdType=T800CmdType.READY_SEND_IMAGE;
//            cmdType.useObjectSSetBody(endInfoBean.getW(),endInfoBean.getH(),endInfoBean.getSize(), T800SendImageType.END);
//            byte[] endBytes=cmdType.getAllByte();
////            if(null!=getBluetoothGattService()){
////                getBluetoothGattService().sendImageIndexCmd(endInfoBean.getIndex(),endBytes);
////            }
//            BleEventSubscriptionSubject.getInstance().sendImageIndexCmd(endInfoBean.getIndex(),endBytes);
//            T800SendUtil.showImage();
//        }
//    };
//
////    private T800SendManager.Listen t800SendManagerListen=new T800SendManager.Listen() {
////        @Override
////        public void nowSendCmd(byte[] bytes) {
//////            BleCmdEventSubscriptionSubject.getInstance().sendCmd(bytes);
//////            if(null!=getBluetoothGattService()){
//////                getBluetoothGattService().sendCmd(bytes);
//////            }
////            BleEventSubscriptionSubject.getInstance().sendCmd(bytes);
////        }
////
////        @Override
////        public void sendImage(Bitmap bitmap, T800BitmapQualityType qualityType) {
////            int index=0;
////            if(null!=qualityType){
////                index=qualityType.ordinal();
////            }
////            if(index>=BitmapQualityType.values().length||index<0){
////                index=0;
////            }
////            BitmapQualityType type=BitmapQualityType.values()[index];
//////            if(null!=getBluetoothGattService()){
//////                getBluetoothGattService().sendImage(bitmap,type );
//////            }
////            BleEventSubscriptionSubject.getInstance().sendImage(bitmap,type );
////        }
////    };
//
////    private BleCmdSendListen bleCmdSendListen=new BleCmdSendListen() {
////        @Override
////        public void nowSendCmd(byte[] bytes) {
////            BleEventSubscriptionSubject.getInstance().sendCmd(bytes);
////        }
////
////        @Override
////        public void sendImage(Bitmap bitmap, BleSendBitmapQualityType qualityType) {
////            BleEventSubscriptionSubject.getInstance().sendImage(bitmap,qualityType );
////        }
////
////        @Override
////        public void sendFile(File file, BleSendFileType fileType) {
////
////        }
////    };
//
//
//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            try {
//                HudSendServiceMsgType msgType=HudSendServiceMsgType.values()[msg.what];
//                switch (msgType){
//                    case SEND_TIME:
//                        handler.removeMessages(HudSendServiceMsgType.SEND_TIME.ordinal());
//                        T800SendUtil.sendTime();;
//                        long nowTime=System.currentTimeMillis();
//                        Date date = new Date(nowTime);
//                        int needS=60-date.getSeconds();
//                        handler.sendEmptyMessageDelayed(HudSendServiceMsgType.SEND_TIME.ordinal(),needS*1000);
//                        break;
//                }
//            }catch (Exception e){}
//        }
//    };
//
//    private void initTime(){
//        handler.removeMessages(HudSendServiceMsgType.SEND_TIME.ordinal());
//        handler.sendEmptyMessageDelayed(HudSendServiceMsgType.SEND_TIME.ordinal(),3000);
//    }
//
//
//
//    @Override
//    public void init() {
////        PrivacyComplianceDialogUtil.getInstance().privacyCompliance(this);
//        MapsInitializer.updatePrivacyShow(this,true,true);
//        MapsInitializer.updatePrivacyAgree(this,true);
////        T800SendManager.getInstance().setListen(bleCmdSendListen);
//        BleStatuEventSubscriptionSubject.getInstance().attach(bleStatuEventObserver);
////        bluetoothGattServiceDao=new BluetoothGattServiceDao(getApplicationContext());
////        bluetoothGattServiceDao.startServiceIsBind();
//    }
//
//    @Override
//    public void start(Intent intent, int flags, int startId) {
//
//    }
//
//    @Override
//    public void close() {
//        handler.removeCallbacksAndMessages(null);
////        if(null!=bluetoothGattServiceDao){
////            bluetoothGattServiceDao.stopServiceIsBind();
////            bluetoothGattServiceDao=null;
////        }
//        BleStatuEventSubscriptionSubject.getInstance().detach(bleStatuEventObserver);
//    }
//
//
//}
