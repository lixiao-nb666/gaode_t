package com.nrmyw.gaode_t.app;






import android.os.Message;

import com.amap.api.maps.MapsInitializer;
import com.amap.api.navi.AMapNavi;
import com.newbee.ble_lib.NewBeeBleManager;
import com.newbee.bulid_lib.mybase.appliction.BaseApplication;

import com.nrmyw.ble_event_lib.bean.BleSendImageEndInfoBean;
import com.nrmyw.ble_event_lib.bean.BleSendImageStartInfoBean;
import com.nrmyw.ble_event_lib.config.NewBeeBleConfig;

import com.nrmyw.ble_event_lib.statu.BleStatu;
import com.nrmyw.ble_event_lib.statu.BleStatuEventObserver;
import com.nrmyw.ble_event_lib.statu.BleStatuEventSubscriptionSubject;
import com.nrmyw.ble_event_lib.type.BleSendBitmapQualityType;
import com.nrmyw.ble_event_lib.util.BleByteUtil;
import com.nrmyw.gaode_t.log.LG;
import com.nrmyw.hud_data_event_lib.HudEventManager;
import com.nrmyw.hud_data_lib.config.HudConfig;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.type.HudDevice;


/**
 * Created by shixin on 16/8/23.
 * bug反馈QQ:1438734562
 */
public class MyApp extends BaseApplication {


    private BleStatuEventObserver bleStatuEventObserver=new BleStatuEventObserver() {
        @Override
        public void sendBleStatu(BleStatu bleStatu, Object... objects) {
            //It's useless. Just print it out and view it.
            String bleStatuStr=getResources().getString(bleStatu.getStrId());
            switch (bleStatu){
                case NONE:
                    break;
                case RUN_ERR:
                    //It's useless. Just print it out and view it.
                   String runErrStr=getBleStatuRsStr(objects);
                    LG.i("kaishifasongtupianle","kaishifasongtupianle-zhiling:"+runErrStr);
                    break;
                case CONNECTING:
                case CONNECTED:
                case DISCONNECTED:
                    HudDevice hudDevice= HudManager.getInstance().getHudDevice();
                    if(null!=hudDevice){
                        String deviceName=hudDevice.name();
                        boolean isConnected=NewBeeBleManager.getInstance().isConnect();
                    }
                    break;
                case USER_DO:
                case OPENING:
                case STATE_ON:
                case STATE_OFF:
                case CAN_SEND_DATA:
                    //It's useless. Just print it out and view it.
                    String bleOtherStr=getBleStatuRsStr(objects);
                    break;
                case RETRUN_BYTES:
                    //It's useless. Just print it out and view it.
                    byte[] retrunCmd= (byte[]) objects[0];
                    String retrunCmdStr= BleByteUtil.parseByte2HexStr(retrunCmd);
                    break;
                case SENDING_DATA:
                    //It's useless. Just print it out and view it.
                    byte[] sendCmd= (byte[]) objects[0];
                    String sendCmdStr= BleByteUtil.parseByte2HexStr(sendCmd);
//                    LG.i("kaishifasongtupianle","kaishifasongtupianle-zhiling:"+sendCmdStr);
                    break;
                case SEND_IMAGE_START:
                    //It's useless. Just print it out and view it.
                    BleSendImageStartInfoBean startInfoBean= (BleSendImageStartInfoBean) objects[0];
                    break;
                case SEND_IMAGE_END:
                    //It's useless. Just print it out and view it.
                    BleSendImageEndInfoBean endInfoBean= (BleSendImageEndInfoBean) objects[0];
                    break;
            }

        }

        private String getBleStatuRsStr(Object... objects){
            try {
                if(null==objects||objects.length==0){
                    return "";
                }
                String str=null;
                if(objects[0] instanceof  String){
                    str= objects[0].toString();
                }else {
                    int rsId= (int) objects[0];
                    str= getResources().getString(rsId);
                }
                if(objects.length>1){
                    return str+"-"+objects[1].toString();
                }else {
                    return str;
                }

            }catch (Exception e){
                return "";
            }

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * setApiKey是静态方法,内部引用了Context，建议放在Application中
         * 如果你在meta-data中配置了key，那么以meta-data中的为准，此行代码
         * 可以忽略，这个方法主要是为那些不想在xml里配置key的用户使用。
         * **/
        try {
            AMapNavi.getInstance(this).setIsOpenTrafficLight("TLTRUE");
        }catch (Exception e){}
//        AMapNavi.setApiKey(this, "你的KEY");
//        NewBeeBleConfig.getInstance().init(false, HudConfig.mtu,HudConfig.serviceID,HudConfig.writeID,HudConfig.noticeID, BleDeviceType.getBleDeviceTypeList());
//        NewBeeBleManager.getInstance().init(getBaseContext());
////        hudSendServiceDao=new HudSendServiceDao(getContext());
////        hudSendServiceDao.startService();
//        HudEventManager.getInstance().init(getContext());
        HudManager.getInstance().init(getApplicationContext());
        HudManager.getInstance().getHudSetConfig().setTimeDifference(-60*60);
        HudManager.getInstance().getHudSetConfig().setBleSendBitmapQualityType(BleSendBitmapQualityType.ULTRA_LOW);
        MapsInitializer.updatePrivacyShow(this,true,true);
        MapsInitializer.updatePrivacyAgree(this,true);
        BleStatuEventSubscriptionSubject.getInstance().attach(bleStatuEventObserver);


    }




    @Override
    protected void init() {

    }

    @Override
    protected void needClear(String str) {

    }

    @Override
    protected void close() {
//        if(null!=hudSendServiceDao){
//            hudSendServiceDao.stopService();
//        }
//        HudEventManager.getInstance().close();
//       NewBeeBleManager.getInstance().close();
        HudManager.getInstance().close();
        BleStatuEventSubscriptionSubject.getInstance().detach(bleStatuEventObserver);
    }


}
