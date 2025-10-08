package com.nrmyw.gaode_t.activity.ble;

import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;

import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.adapter.BleSetAdapter;

import com.nrmyw.gaode_t.event.GaoDeNaviEventImp;
import com.nrmyw.gaode_t.event.t800.GaoDeT800Event;
import com.nrmyw.gaode_t.type.BleSetType;
import com.nrmyw.hud_data_lib.event.retrun.HudRetrunEventObserver;
import com.nrmyw.hud_data_lib.event.retrun.HudRetrunEventSubscriptionSubject;
import com.nrmyw.hud_data_lib.type.type.HudStatuType;


public class BleSetActivity extends BaseCompatActivity {
    private RecyclerView rv;
    private BleSetAdapter adapter;
    private BleSetAdapter.ItemClick itemClick=new BleSetAdapter.ItemClick() {
        @Override
        public void needInit(BleSetType bleSetType) {
            switch (bleSetType){
                case DEVICE_VERSION:
                    eventImp().deviceQueVersion();
                    break;
                case SN:
                    eventImp().deviceQueSN();
                    break;
                case BRIGHTNESS:
                    eventImp().deviceQueBrightness();
                    break;
                case SOUND:
                    eventImp().deviceQueSound();
                    break;
                case DEVICE_SOUND_STATU:
                    eventImp().deviceQuePromptToneStatu();
                    break;
                case GPS_SPEED:
                    eventImp().deviceQueGpsSpeed();
                    break;
                case IMAGE_QUALITY:
//                    setItemV(BleSetType.IMAGE_QUALITY, HudSetConfig.getInstance().getQualityType().name());
                    break;
            }
        }

        @Override
        public void nowSelect(BleSetType bleSetType) {
            switch (bleSetType){
                case DEVICE_VERSION:
                    eventImp().deviceQueVersion();
                    break;
                case SN:
                    eventImp().deviceQueSN();
                    break;
                case BRIGHTNESS:
                    if(nowBrightnessMode!=-1&&nowBrightness!=-1){

                    }
                    eventImp().deviceQueBrightness();
                    break;
                case SOUND:
                    if(nowSound!=-1){
                        nowSound++;
                        if(nowSound>10){
                            nowSound=0;
                        }
                        eventImp().deviceSetSound(nowSound);
                    }
                    eventImp().deviceQueSound();
                    break;
                case DEVICE_SOUND_STATU:
                    if(nowDeviceSoundStatu!=-1){
                        if(nowDeviceSoundStatu==0){
                                nowDeviceSoundStatu=1;
                        }else {
                            nowDeviceSoundStatu=0;
                        }
                        eventImp().deviceSetePromptToneStatu(nowDeviceSoundStatu==0?false:true);
                    }
                    eventImp().deviceQuePromptToneStatu();
                    break;
                case GPS_SPEED:
                    if(nowGpsSpeed!=-1){
                        nowGpsSpeed++;
                        if(nowGpsSpeed>10){
                            nowGpsSpeed=0;
                        }
                        eventImp().deviceSetGpsSpeed(nowGpsSpeed);
                    }
                    eventImp().deviceQueGpsSpeed();
                    break;
                case IMAGE_QUALITY:
//                    int ord=GaoDeHudSendConfig.getInstance().getQualityType().ordinal();
//                    ord++;
//                    LG.i("kankanshemeguo","kankanshemeguo:11--"+ord);
//                    if(ord>= BleSendBitmapQualityType.values().length){
//                        ord=0;
//                    }
//                    LG.i("kankanshemeguo","kankanshemeguo:11--"+ord);
//                    GaoDeHudSendConfig.getInstance().setQualityType(BleSendBitmapQualityType.values()[ord]);
//                    setItemV(BleSetType.IMAGE_QUALITY,GaoDeHudSendConfig.getInstance().getQualityType().name());
                    break;
            }
        }


    };
    public GaoDeNaviEventImp eventImp(){
        return GaoDeT800Event.getInstance();
    }

    private HudRetrunEventObserver hudRetrunEventObserver=new HudRetrunEventObserver() {
        @Override
        public void none(byte[] bytes) {

//            setShowTV("还没调的值为:"+new String(bytes, StandardCharsets.UTF_8));
//            setItemV(BleSetType.GPS_SPEED,v+"");
        }

        @Override
        public void getBrightnessV(int mode, int autoV, int handV) {
            //            setShowTV("当前亮度为:"+v);
            nowBrightnessMode=mode;
            if(mode==0){
                nowBrightness=autoV;
            }else {
                nowBrightness=handV;
            }
            setItemV(BleSetType.BRIGHTNESS,mode+"-"+autoV+"-"+handV);
        }

//        @Override
//        public void getBrightnessV(int v) {
//
//        }

        @Override
        public void getSoundV(int v) {
//            setShowTV("当前声音为:"+v);
            nowSound=v;
            setItemV(BleSetType.SOUND,v+"");
        }

        @Override
        public void getDeviceSoundStatu(int deviceSoundStatu) {
//            setShowTV("当前设备声音开关:"+ T800StatuType.values()[deviceSoundStatu]);
            nowDeviceSoundStatu=deviceSoundStatu;
            setItemV(BleSetType.DEVICE_SOUND_STATU, HudStatuType.values()[deviceSoundStatu].name());
        }

        @Override
        public void getSN(String sn) {
//            setShowTV("SN码为:"+sn);
            setItemV(BleSetType.SN,sn);
        }

        @Override
        public void getDeviceVersion(String deviceVersion) {
//            setShowTV("设备版本:"+deviceVersion);
            setItemV(BleSetType.DEVICE_VERSION,deviceVersion);
        }

        @Override
        public void getGPSSpeed(int v) {
            nowGpsSpeed=v;
            setItemV(BleSetType.GPS_SPEED,v+"");
        }
    };
    int nowSound=-1;
    int nowBrightness=-1;
    int nowBrightnessMode=-1;
    int nowDeviceSoundStatu=-1;
    int nowGpsSpeed=-1;


    private void setItemV(BleSetType bleSetType,String v){
            bleSetType.setV(v);
            Message msg=new Message();
            msg.what=0;
            msg.arg1=bleSetType.ordinal();
            handler.sendMessage(msg);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    adapter.notifyItemChanged(msg.arg1);
                    break;
            }
        }
    };

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
}
