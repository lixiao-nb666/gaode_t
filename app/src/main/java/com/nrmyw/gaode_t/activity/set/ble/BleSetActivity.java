package com.nrmyw.gaode_t.activity.set.ble;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.nrmyw.ble_event_lib.type.BleSendBitmapQualityType;
import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.adapter.BleSetAdapter;
import com.nrmyw.hud_data_lib.event.retrun.HudRetrunEventObserver;
import com.nrmyw.hud_data_lib.event.retrun.HudRetrunEventSubscriptionSubject;
import com.nrmyw.hud_data_lib.type.type.HudStatuType;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.manager.set_child.HudBrightnessManager;
import com.nrmyw.hud_manager_lib.manager.set_child.HudDaylightSearchManager;
import com.nrmyw.hud_manager_lib.manager.set_child.HudDeviceSoundStatuManager;
import com.nrmyw.hud_manager_lib.manager.set_child.HudDeviceVersionStatuManager;
import com.nrmyw.hud_manager_lib.manager.set_child.HudSNCodeStatuManager;
import com.nrmyw.hud_manager_lib.manager.set_child.HudSoundManager;


public class BleSetActivity extends BaseCompatActivity {
    private RecyclerView rv;
    private BleSetAdapter adapter;
    private BleSetAdapter.ItemClick itemClick=new BleSetAdapter.ItemClick() {
        @Override
        public void needInit(BleSetType bleSetType) {
            toGetHudDeviceData(bleSetType);
        }

        private void toGetHudDeviceData(BleSetType bleSetType){
            switch (bleSetType){
                case DEVICE_VERSION:
                    HudDeviceVersionStatuManager.getInstance().connectedToQue();
//                    eventImp().deviceQueVersion();
                    break;
                case SN:
//                    eventImp().deviceQueSN();
                    HudSNCodeStatuManager.getInstance().connectedToQue();
                    break;
                case BRIGHTNESS:
                    HudBrightnessManager.getInstance().connectedToQue();
//                    eventImp().deviceQueBrightness();
                    break;
                case SOUND:
                    HudSoundManager.getInstance().connectedToQue();
//                    eventImp().deviceQueSound();
                    break;
                case DEVICE_SOUND_STATU:
//                    eventImp().deviceQuePromptToneStatu();
                    HudDeviceSoundStatuManager.getInstance().connectedToQue();
                    break;
//                case GPS_SPEED:
//                    eventImp().deviceQueGpsSpeed();
//                    break;
                case IMAGE_QUALITY:
//                    setItemV(BleSetType.IMAGE_QUALITY, HudSetConfig.getInstance().getQualityType().name());
                    break;
                case DAYLIGHT_SEARCH:
                    if(HudDaylightSearchManager.getInstance().isDaylightSearch()){
                        setItemV(BleSetType.DAYLIGHT_SEARCH,"开启");
                    }else {
                        setItemV(BleSetType.DAYLIGHT_SEARCH,"关闭");
                    }
                    break;
            }
        }

        @Override
        public void nowSelect(BleSetType bleSetType) {
            toGetHudDeviceData(bleSetType);

        }

        @Override
        public void nowSelectAdd(BleSetType bleSetType) {
            switch (bleSetType){
                case SOUND:
                    HudSoundManager.getInstance().add();
                    handler.sendEmptyMessage(BleSetMsgType.SHOW_SOUND.ordinal());
                    break;
                case BRIGHTNESS:
                    HudBrightnessManager.getInstance().add();
                    handler.sendEmptyMessage(BleSetMsgType.SHOW_BRIGHTNESS.ordinal());
                    break;
//                case GPS_SPEED:
//                    if(nowGpsSpeed!=-1){
//                        nowGpsSpeed++;
//                        if(nowGpsSpeed>10){
//                            nowGpsSpeed=0;
//                        }
//                        eventImp().deviceSetGpsSpeed(nowGpsSpeed);
//                    }
//                    eventImp().deviceQueGpsSpeed();
//                    break;
            }
        }

        @Override
        public void nowSelectReduce(BleSetType bleSetType) {
            switch (bleSetType) {
                case SOUND:
                    HudSoundManager.getInstance().reduce();
                    handler.sendEmptyMessage(BleSetMsgType.SHOW_SOUND.ordinal());
                    break;
                case BRIGHTNESS:
                    HudBrightnessManager.getInstance().reduce();
                    handler.sendEmptyMessage(BleSetMsgType.SHOW_BRIGHTNESS.ordinal());
                    break;
            }
        }

        @Override
        public void nowSelectSet(BleSetType bleSetType) {

        }

        @Override
        public void nowSelectUpdate(BleSetType bleSetType) {
            switch (bleSetType){
                case DEVICE_SOUND_STATU:
                    HudDeviceSoundStatuManager.getInstance().update();
                    handler.sendEmptyMessage(BleSetMsgType.SHOW_DEVICE_SOUND.ordinal());
                    break;
                case IMAGE_QUALITY:
                    int ord= HudManager.getInstance().getHudSetConfig().getBleSendBitmapQualityType().ordinal();
                    ord++;
                    if(ord>= BleSendBitmapQualityType.values().length){
                        ord=0;
                    }
                    HudManager.getInstance().getHudSetConfig().setBleSendBitmapQualityType( BleSendBitmapQualityType.values()[ord]);
                    setItemV(BleSetType.IMAGE_QUALITY,"");
                    break;
                case DAYLIGHT_SEARCH:
                    HudDaylightSearchManager.getInstance().update();
                    if(HudDaylightSearchManager.getInstance().isDaylightSearch()){
                        setItemV(BleSetType.DAYLIGHT_SEARCH,"开启");
                    }else {
                        setItemV(BleSetType.DAYLIGHT_SEARCH,"关闭");
                    }
                    break;
            }
        }


    };


    private HudRetrunEventObserver hudRetrunEventObserver=new HudRetrunEventObserver() {
        @Override
        public void none(byte[] bytes) {

//            setShowTV("还没调的值为:"+new String(bytes, StandardCharsets.UTF_8));
//            setItemV(BleSetType.GPS_SPEED,v+"");
        }

        @Override
        public void getBrightnessV(int mode, int autoV, int handV) {
            //            setShowTV("当前亮度为:"+v);


            HudBrightnessManager.getInstance().returnBrightneesV(mode,autoV,handV);
            handler.sendEmptyMessage(BleSetMsgType.SHOW_BRIGHTNESS.ordinal());
        }

//        @Override
//        public void getBrightnessV(int v) {
//
//        }

        @Override
        public void getSoundV(int v) {
//            setShowTV("当前声音为:"+v);
//            nowSound=v;
            HudSoundManager.getInstance().returnSoundV(v);
            handler.sendEmptyMessage(BleSetMsgType.SHOW_SOUND.ordinal());

        }

        @Override
        public void getDeviceSoundStatu(int deviceSoundStatu) {
//            setShowTV("当前设备声音开关:"+ T800StatuType.values()[deviceSoundStatu]);

            HudDeviceSoundStatuManager.getInstance().returnNowDeviceSoundStatuV(deviceSoundStatu);
            handler.sendEmptyMessage(BleSetMsgType.SHOW_DEVICE_SOUND.ordinal());
        }

        @Override
        public void getSN(String sn) {
//            setShowTV("SN码为:"+sn);
            HudSNCodeStatuManager.getInstance().returnsnCode(sn);
            setItemV(BleSetType.SN,sn);
        }

        @Override
        public void getDeviceVersion(String deviceVersion) {
//            setShowTV("设备版本:"+deviceVersion);
            HudDeviceVersionStatuManager.getInstance().returnNowDeviceVersion(deviceVersion);
            setItemV(BleSetType.DEVICE_VERSION,deviceVersion);
        }

        @Override
        public void getGPSSpeed(int v) {
//            nowGpsSpeed=v;
//            setItemV(BleSetType.GPS_SPEED,v+"");
        }


    };
//    int nowSound=-1;
//    int nowBrightness=-1;

//    int nowDeviceSoundStatu=-1;
//    int nowGpsSpeed=-1;


    private void setItemV(BleSetType bleSetType,String v){
            bleSetType.setV(v);
            Message msg=new Message();
            msg.what=BleSetMsgType.NOTIFY_ITEM.ordinal();
            msg.arg1=bleSetType.ordinal();
            handler.sendMessage(msg);
    }



    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_ble_set;
    }

    @Override
    public void initView() {
        rv=findViewById(R.id.rv);
    }

    @Override
    public void initData() {
        HudRetrunEventSubscriptionSubject.getInstence().attach(hudRetrunEventObserver);
        adapter=new BleSetAdapter(context,itemClick);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }

    @Override
    public void initControl() {

    }

    @Override
    public void closeActivity() {
        handler.removeCallbacksAndMessages(null);
        HudRetrunEventSubscriptionSubject.getInstence().detach(hudRetrunEventObserver);
    }

    @Override
    public void viewIsShow() {

    }

    @Override
    public void viewIsPause() {

    }

    @Override
    public void changeConfig() {

    }


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                BleSetMsgType bleSetMsgType=BleSetMsgType.values()[msg.what];
                switch (bleSetMsgType){
                    case NOTIFY_ITEM:
                        adapter.notifyItemChanged(msg.arg1);
                        break;
                    case SHOW_BRIGHTNESS:
                        showBrightness();
                        break;
                    case SHOW_SOUND:
                        showSound();
                        break;
                    case SHOW_DEVICE_SOUND:
                        setItemV(BleSetType.DEVICE_SOUND_STATU, HudStatuType.values()[HudDeviceSoundStatuManager.getInstance().getNowDeviceSoundStatu()].name());
                        break;
                }

            }catch (Exception e){

            }

        }
    };

    private void showBrightness(){
        int nowBrightness=HudBrightnessManager.getInstance().getNowBrightness();
        if(nowBrightness==0){
            setItemV(BleSetType.BRIGHTNESS,"自动");
        }else {
            setItemV(BleSetType.BRIGHTNESS,nowBrightness+"");
        }
    }

    private void showSound(){
        int nowSound=HudSoundManager.getInstance().getNowSound();
        setItemV(BleSetType.SOUND,nowSound+"");
    }
}
