package com.nrmyw.huynew.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps.AMapException;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.navi.model.AMapCarInfo;
import com.gaode.demo.activity.IndexActivity;
import com.newbee.ble_lib.NewBeeBleManager;
import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.newbee.bulid_lib.mybase.activity.util.ActivityUtil;
import com.newbee.bulid_lib.util.TimeUtil;
import com.newbee.downloader.event.send.NewBeeFileDownLoaderEventSubscriptionSubject;
import com.newbee.downloader.util.UrlToFileDownLoaderBeanUtil;
import com.newbee.yd_translation.activity.NrmywYdTranslationActivity;
import com.nrmyw.ble_event_lib.bean.BleSendImageEndInfoBean;
import com.nrmyw.ble_event_lib.bean.BleSendImageStartInfoBean;
import com.nrmyw.ble_event_lib.statu.BleStatu;
import com.nrmyw.ble_event_lib.statu.BleStatuEventObserver;
import com.nrmyw.ble_event_lib.statu.BleStatuEventSubscriptionSubject;
import com.nrmyw.ble_event_lib.util.BleByteUtil;
import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.activity.MyGaoDeActivity;
import com.nrmyw.gaode_t.activity.hud.HudMainActivity;
import com.nrmyw.gaode_t.activity.hud.HudOtaActivity;
import com.nrmyw.gaode_t.activity.set.ble.BleSetActivity;
import com.nrmyw.gaode_t.activity.set.user.UserSetActivity;
import com.nrmyw.gaode_t.activity.t800.T800BleDataTestActivity;
import com.nrmyw.gaode_t.adapter.BleSetAdapter;
import com.nrmyw.gaode_t.gaode.GaoDeListenManager;
import com.nrmyw.gaode_t.gaode.GaoDeStartNaviUtil;
import com.nrmyw.gaode_t.log.LG;
import com.nrmyw.gaode_t.util.HudDeviceUtil;
import com.nrmyw.hud_data_event_lib.config.HudSetConfig;
import com.nrmyw.hud_data_event_lib.service.HudEventServiceMsgType;
import com.nrmyw.hud_data_lib.type.function.HudFunctionType;
import com.nrmyw.hud_data_lib.type.ui.HudUiType;
import com.nrmyw.hud_data_lib.type.yellow_statu.HudYellowStatuBjType;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.huynew.adapter.HuynewConnectedAdapter;
import com.nrmyw.huynew.type.HuynewFunctionType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HuyNewMainActivity extends BaseCompatActivity {

    private BleStatuEventObserver bleStatuEventObserver=new BleStatuEventObserver() {
        @Override
        public void sendBleStatu(BleStatu bleStatu, Object... objects) {


            Message msg=new Message();
            msg.what=bleStatu.ordinal();
            if(objects.length==1){
                msg.obj=objects[0];
            }else if(objects.length==2){
                if(objects[0] instanceof Integer){
                    msg.arg1= (int) objects[0];
                }
                msg.obj=objects[1];

            }
            handler.sendMessage(msg);
        }
    };
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                BleStatu bleStatu=BleStatu.values()[msg.what];
                switch (bleStatu) {
                    case SEND_OTA_START:
                        break;
                    case SEND_OTA_END:
                        break;
                    case SEND_OTA_PROGRESS:
                        int progress=(int)msg.obj;
                        break;
                    case SEND_OTA_ERR:
                        break;

                    case INIT:
                    case SEARCHING:
                    case OPENING:
                    case STATE_ON:
                    case STATE_OFF:
                        setStatuStr(bleStatu.getStrId());
                        break;
                    case CONNECTING:
                        setStatuStr(bleStatu.getStrId());
                        setHudDeviceNameAndImageShow(true);
                        break;
                    case CONNECTED:
                        setStatuStr(bleStatu.getStrId());
                        setHudDeviceNameAndImageShow(true);
                        setViewByBleConnectStatu();
                        bleStatuIV.setVisibility(View.VISIBLE);
//                        PackageManager packageManager=MainActivity.this.getPackageManager();
//                        Intent intent=packageManager.getLaunchIntentForPackage("com.newbee.ble_tool");
//                        startActivity(intent);


                        break;
                    case DISCONNECTED:
                        setStatuStr(bleStatu.getStrId());
                        setHudDeviceNameAndImageShow(false);
                        setViewByBleConnectStatu();
                        bleStatuIV.setVisibility(View.GONE);
                        break;
                    case RUN_ERR:
                    case NONE:
                    case CONNECTING_ERR:
                        setConnectStr(bleStatu.getStrId());
                        if (null != msg.obj) {
                            bleConnectTV.append(msg.obj.toString());
                        }
                        break;
                    case SEND_IMAGE_START:
//                        long startTime=System.currentTimeMillis();
                        setConnectStr(bleStatu.getStrId());
                        if (msg.obj instanceof BleSendImageStartInfoBean) {
                            BleSendImageStartInfoBean startInfoBean = (BleSendImageStartInfoBean) msg.obj;
                        }
                        break;
                    case SEND_IMAGE_END:
                        setConnectStr(bleStatu.getStrId());
                        if (msg.obj instanceof BleSendImageEndInfoBean) {
                            BleSendImageEndInfoBean endInfoBean = (BleSendImageEndInfoBean) msg.obj;
                            Log.i("tag", "bitmap to byte[] use time:" + endInfoBean.getUseTime());
                        }
                        break;
                    case SEND_IMAGE_DATA_END:
//                        long endTime=System.currentTimeMillis();
//                        long useTime=startTime-endTime;
                        setConnectStr(bleStatu.getStrId());
//                        if (msg.obj instanceof Long) {
//                            Log.i("tag", "send bitmap bytep[] use time:" + (long) msg.obj);
//                            break;
//                        }
//                        BleSendImageInfoBean bleSendImageInfoBean = (BleSendImageInfoBean) ;
                        Log.i("tag", "kankanshenmegui----:" + msg.obj);
                        break;
                    case USER_DO:
                        Log.i("tag", "kankanshenmegui----USER_DO:" +msg.obj);
                        if(msg.obj instanceof HudEventServiceMsgType){
                            HudEventServiceMsgType msgType= (HudEventServiceMsgType) msg.obj;
                            if(msgType.ordinal()==1){
                                setTime();
                            }
                        }
                        break;
                    case SENDING_DATA:
                        byte[] cmd= (byte[]) msg.obj;
                        String cmdStr= BleByteUtil.parseByte2HexStr(cmd);
                        showCMdTV.setText(cmdStr);
                        Log.i("tag", "kankanshenmegui----SENDING_DATA:" +cmdStr);
                        break;

                    default:
                        setConnectStr(bleStatu.getStrId());
                        break;
                }
                if (msg.arg1 != 0) {
                    bleConnectTV.append("-"+getResources().getText(msg.arg1));
                }
            }catch (Exception e){

            }
        }
    };

    private void setTime(){
        long timeStamp = System.currentTimeMillis()-(HudSetConfig.getInstance().getTimeDifference()*1000);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE yyyy/MM/dd HH:mm");
        Date date = new Date(timeStamp);
        String timeStr=sdf.format(date);
        hudDemoShowTimeTV.setText(timeStr);
    }
    private void setConnectStr(int rsId){
        if(rsId==0){
            return;
        }
        String bleConnectStr=getResources().getText(rsId).toString();
        bleConnectStr= HudDeviceUtil.getInstance().getStatuStr(bleConnectStr);
        bleConnectTV.setText(bleConnectStr);
    }

    private void setStatuStr(int rsId){
        if(rsId==0){
            return;
        }
        String bleStatuStr=getResources().getText(rsId).toString();
        bleStatuStr= HudDeviceUtil.getInstance().getStatuStr(bleStatuStr);
        bleStatuTV.setText(bleStatuStr);

    }

    private void setHudDeviceNameAndImageShow(boolean nowGetDevice){
        if(nowGetDevice){
           setNowDeviceRs();
            String showName=HudDeviceUtil.getInstance().getDeviceShowName();
            if(!TextUtils.isEmpty(showName)){
                bleNameTV.setText(showName);
            }
            bleNameTV.setVisibility(View.VISIBLE);
            deviceIV.setVisibility(View.VISIBLE);
            handler.removeCallbacks(showDeviceImageRunnable);
            handler.postDelayed(showDeviceImageRunnable,3000);

        }else {
            bleNameTV.setVisibility(View.GONE);
            deviceIV.setVisibility(View.GONE);
            handler.removeCallbacks(showDeviceImageRunnable);
        }
    }



    private void setNowDeviceRs(){
        int showRs=HudDeviceUtil.getInstance().getDeviceResource();
        if(showRs!=0){
            if(NewBeeBleManager.getInstance().isConnect()){
                connectDeviceIV.setImageResource(showRs);
            }else {
                deviceIV.setImageResource(showRs);
            }

        }
    }

    public void setViewByBleConnectStatu(){
        if(NewBeeBleManager.getInstance().isConnect()){
            setNowDeviceRs();
            disconnectVBg.setVisibility(View.GONE);
            connectBg.setVisibility(View.VISIBLE);
            allBg.setBackgroundResource(R.drawable.huynew_app_bj_3);
            selectArUiTypeShow();
            showIconFuntion();

        }else {
            setNowDeviceRs();
            disconnectVBg.setVisibility(View.VISIBLE);
            connectBg.setVisibility(View.GONE);
            allBg.setBackgroundResource(R.drawable.huynew_app_bj_2);
        }
    }

    private Runnable showDeviceImageRunnable =new Runnable() {
        @Override
        public void run() {
            int showRs=HudDeviceUtil.getInstance().getDeviceNextResource();
            if(showRs==0){
                return;
            }
            if(NewBeeBleManager.getInstance().isConnect()){
                connectDeviceIV.setImageResource(showRs);
            }else {
                deviceIV.setImageResource(showRs);
            }

            handler.removeCallbacks(showDeviceImageRunnable);
            handler.postDelayed(showDeviceImageRunnable,3000);
        }
    };

    private LinearLayout disconnectVBg,connectBg,allBg;
    private TextView bleNameTV,bleConnectTV,bleStatuTV,hudDemoShowTimeTV;
    private ImageView bleStatuIV,deviceIV,connectDeviceIV,bleStatuConnectIV;
    //    ,disconnectedBT
    private Button initBT,searchBT,nowToNaviBT,bleSetBT,sendTestBT,startGaoDeBT,toGaoDeDemoBT,toAppSetBT,toMapSetBT;
    private ImageView arIcon1IV,arIcon2IV,arIcon3IV;
    private TextView showCMdTV;
    private ImageView arKeyOkIV, arKeyReturnIV,arKeyToLeftIV,arKeyToRightIV,arKeyMainIV;
    private TextView arIcon1StrTV;
    private LinearLayout arBodyTranslationLL;
    private RelativeLayout arBodyMainRL;
    private RecyclerView connectPageRV;
    private HuynewConnectedAdapter huynewConnectedAdapter;
    private HuynewConnectedAdapter.ItemClick huynewConnectedAdapterItem=new HuynewConnectedAdapter.ItemClick() {
        @Override
        public void needInit(HuynewFunctionType huynewFunctionType) {

        }

        @Override
        public void nowSelect(HuynewFunctionType huynewFunctionType) {
            switch (huynewFunctionType){
                case TRANSLATION:
                    toActivity(NrmywYdTranslationActivity.class);
                    break;
                case HINT_STR:
                    toActivity(HuyNewHintStrActivity.class);
                    break;
                case DEBUG_PAGE:
                    toActivity(T800BleDataTestActivity.class);
                    break;
                case NANI:
                    GaoDeStartNaviUtil.getInstance().starNavi();
//                    AmapNaviParams params = new AmapNaviParams(null, null, null, AmapNaviType.WALK, AmapPageType.NAVI);
////                    null
//                    //新增加的---开始
//                    params.setNaviMode(1);
////                    params.setNeedCalculateRouteWhenPresent(true);
////                    params.setNeedDestroyDriveManagerInstanceWhenNaviExit(true);
//                    //新增加的---结束
//                    params.setShowCrossImage(true);
//                    params.setScaleAutoChangeEnable(getBaseContext(),true);
//                    params.setTrafficEnabled(true);
//                    params.setSecondActionVisible(true);
//                    params.setDayAndNightMode(getBaseContext(),0);
//                    params.setBroadcastMode(getBaseContext(),2);
//                    params.setCarDirectionMode(getBaseContext(),2);
//                    params.showEagleMap(getBaseContext(),true);
//
//                    params.setShowVoiceSetings(true);
//                    AMapCarInfo aMapCarInfo=new AMapCarInfo();
//                    aMapCarInfo.setCarNumber("粤BGG7266");
//                    params.setCarInfo(aMapCarInfo);
////                    //传递上下文和导航参数
//                    AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, GaoDeListenManager.getInstance().getiNaviInfoCallback(), MyGaoDeActivity.class);
//                    try {
//                        AMapNavi.getInstance(getApplicationContext()).addAMapNaviListener(GaoDeListenManager.getInstance().getaMapNaviListener());
////                      AMapNavi.getInstance(getApplicationContext()).addParallelRoadListener(GaoDeListenManager.getInstance().getParallelRoadListener());
//                    } catch (AMapException e) {
//                        LG.i("kankanshenmegui"+e.toString());
//                    }
                    break;
                case CAMERA:
                    toActivity(HuyNewCameraActivity.class);
                    break;
                case FILE:
                    toActivity(HuyNewFileActivity.class);
                    break;
                case AR_SET:
                    NewBeeFileDownLoaderEventSubscriptionSubject.getInstance().start(UrlToFileDownLoaderBeanUtil.getDownLoaderBean("https://myw-1.oss-cn-hongkong.aliyuncs.com/hud/hn/app-wujinProd-release-1.1.1.apk"));
                    break;
                case HUD_OTA:
                    toActivity(HudOtaActivity.class);
                    break;
                case AI:
                case NANI_LANG:
                    toActivity(IndexActivity.class);
                    break;
            }
        }

        @Override
        public void nowSelectAdd(HuynewFunctionType huynewFunctionType) {

        }

        @Override
        public void nowSelectReduce(HuynewFunctionType huynewFunctionType) {

        }

        @Override
        public void nowSelectSet(HuynewFunctionType huynewFunctionType) {

        }

        @Override
        public void nowSelectUpdate(HuynewFunctionType huynewFunctionType) {

        }
    };

    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_init:
                    NewBeeBleManager.getInstance().getEventImp().havePermissionInitBle();
                    break;
                case R.id.bt_search:
                    NewBeeBleManager.getInstance().getEventImp().startSearchBle();
//                    BleManager.getInstance().getEventImp().disconnectedBle();
                    break;
                case R.id.bt_now_to_navi:
                    if(null!=huynewConnectedAdapterItem){
                        huynewConnectedAdapterItem.nowSelect(HuynewFunctionType.NANI);
                    }

                    break;
                case R.id.iv_ble_statu_connect:
                    NewBeeBleManager.getInstance().getEventImp().disconnectedBle();
                    break;
                case R.id.iv_ar_key_ok:
                    selectArIconShow();
                    break;
                case R.id.iv_ar_key_return:
                    hudUiType=HudUiType.MAIN;
                    selectArUiTypeShow();
                    break;
                case R.id.iv_ar_key_main:
                    hudUiType=HudUiType.MAIN;
                    selectArUiTypeShow();
                    break;
                case R.id.iv_ar_key_to_left:
//                    if(null==hudUiType||hudUiType!=HudUiType.MAIN){
//                        return;
//                    }
                    functionIndex--;
                    if(functionIndex<0){
                        functionIndex=HudFunctionType.values().length-1;
                    }
                    showIconFuntion();
                    break;
                case R.id.iv_ar_key_to_right:
//                    if(null==hudUiType||hudUiType!=HudUiType.MAIN){
//                        return;
//                    }

                    functionIndex++;
                    if(functionIndex>HudFunctionType.values().length-1){
                        functionIndex=0;
                    }
                    showIconFuntion();
                    break;

            }

        }
    };

    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_huynew_main;
    }

    @Override
    public void initView() {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            Log.e("TAG","onCreate------------getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0");
            //结束你的activity
            finish();
            return;
        }

        if (!isTaskRoot()) {
            Log.e("TAG","onCreate------------isTaskRoot");
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent
                    .ACTION_MAIN)) {
                finish();
                return;
            }
        }
        if (!isTaskRoot()) {
            finish();
            return;
        }
        //全部背景控制
        allBg=findViewById(R.id.ll_all);

        //未连接的时候
        disconnectVBg=findViewById(R.id.ll_statu_disconnect);
        deviceIV=findViewById(R.id.iv_device_pic);
        bleStatuIV=findViewById(R.id.iv_ble_statu);
        bleStatuTV=findViewById(R.id.tv_ble_statu);
        bleNameTV=findViewById(R.id.tv_ble_name);
        bleConnectTV=findViewById(R.id.tv_ble_connect_statu);
        initBT=findViewById(R.id.bt_init);
        searchBT=findViewById(R.id.bt_search);
        nowToNaviBT=findViewById(R.id.bt_now_to_navi);
        //连接的时候
        connectBg=findViewById(R.id.ll_statu_connect);
        showCMdTV=findViewById(R.id.tv_show_cmd);
        connectDeviceIV=findViewById(R.id.iv_connect_device_pic);
        bleStatuConnectIV=findViewById(R.id.iv_ble_statu_connect);
        hudDemoShowTimeTV=findViewById(R.id.tv_hud_show_time);
        arIcon1IV=findViewById(R.id.iv_ar_icon_1);
        arIcon2IV=findViewById(R.id.iv_ar_icon_2);
        arIcon3IV=findViewById(R.id.iv_ar_icon_3);
        arIcon1StrTV=findViewById(R.id.tv_ar_icon_str_1);
        arKeyOkIV=findViewById(R.id.iv_ar_key_ok);
        arKeyReturnIV=findViewById(R.id.iv_ar_key_return);
        arKeyToLeftIV=findViewById(R.id.iv_ar_key_to_left);
        arKeyToRightIV=findViewById(R.id.iv_ar_key_to_right);
        arKeyMainIV=findViewById(R.id.iv_ar_key_main);
        arBodyTranslationLL=findViewById(R.id.ll_ar_body_translation_page);
        arBodyMainRL=findViewById(R.id.rl_ar_body_main_page);
        connectPageRV=findViewById(R.id.rv_connect_page);
    }

    @Override
    public void initData() {
        initBT.setOnClickListener(onClickListener);
        searchBT.setOnClickListener(onClickListener);
        nowToNaviBT.setOnClickListener(onClickListener);
        bleStatuConnectIV.setOnClickListener(onClickListener);
        arKeyOkIV.setOnClickListener(onClickListener);
        arKeyReturnIV.setOnClickListener(onClickListener);
        arKeyToLeftIV.setOnClickListener(onClickListener);
        arKeyToRightIV.setOnClickListener(onClickListener);
        arKeyMainIV.setOnClickListener(onClickListener);
    }

    @Override
    public void initControl() {
        huynewConnectedAdapter=new HuynewConnectedAdapter(context,huynewConnectedAdapterItem);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        connectPageRV.setLayoutManager(layoutManager);
        connectPageRV.setAdapter(huynewConnectedAdapter);


        // 4 step
        BleStatuEventSubscriptionSubject.getInstance().attach(bleStatuEventObserver);
        // 3 step  when you have all permissions
        HudManager.getInstance().nowGetAllPermissions();
    }

    @Override
    public void closeActivity() {
        BleStatuEventSubscriptionSubject.getInstance().detach(bleStatuEventObserver);
    }

    @Override
    public void viewIsShow() {
        setViewByBleConnectStatu();
    }

    @Override
    public void viewIsPause() {

    }

    @Override
    public void changeConfig() {

    }

    private int functionIndex=HudFunctionType.TRANSLATION.ordinal();
    private void showIconFuntion(){
        HudFunctionType nowType=getNeedFunctionType(functionIndex);
        arIcon1IV.setImageResource(getArIconIVRs(nowType));
        arIcon1StrTV.setText(nowType.name());
        HudFunctionType lastType=getNeedFunctionType(functionIndex-1);
        arIcon2IV.setImageResource(getArIconIVRs(lastType));
        HudFunctionType nextType=getNeedFunctionType(functionIndex+1);
        arIcon3IV.setImageResource(getArIconIVRs(nextType));
        HudManager.getInstance().getHudEvent().functionSelect(nowType,lastType,nextType,nowType.name());
    }



    private HudFunctionType getNeedFunctionType(int index){
        if(index<0){
            index=HudFunctionType.values().length-1;
        }
        if(index>HudFunctionType.values().length-1){
            index=0;
        }
        return HudFunctionType.values()[index];
    }

    private int getArIconIVRs(HudFunctionType hudFunctionType){
        if(null==hudFunctionType){
            return R.drawable.ar_icon_none;
        }
        switch (hudFunctionType){
            case TRANSLATION:
                return R.drawable.ar_icon_translation;
            case HINT:
                return R.drawable.ar_icon_hint_str;
            case NANI:
                return R.drawable.ar_icon_navi;
            case TAKE_PHOTO:
                return R.drawable.ar_icon_take_photo;
            case RECORD_VIDEO:
                return R.drawable.ar_icon_record_video;
            default:
                return R.drawable.ar_icon_none;
        }

    }



    private void selectArIconShow(){
        HudFunctionType nowType=getNeedFunctionType(functionIndex);
        switch (nowType){
            case NANI:
                hudUiType=HudUiType.NANI;
                break;
            case HINT:
                hudUiType=HudUiType.HINT_STR;
                break;
            case TRANSLATION:
                hudUiType=HudUiType.TRANSLATION;
                break;
            case RECORD_VIDEO:
            case TAKE_PHOTO:
                hudUiType=HudUiType.UI_5;
                break;
            default:
                hudUiType=HudUiType.MAIN;
                break;
        }
        selectArUiTypeShow();
    }

    private HudUiType hudUiType=HudUiType.MAIN;
    private void selectArUiTypeShow(){
        if(null==hudUiType){
            hudUiType=HudUiType.MAIN;
        }
        HudManager.getInstance().getHudEvent().setUiType(hudUiType);
        switch (hudUiType){
            case MAIN:
                arBodyMainRL.setVisibility(View.VISIBLE);
                arBodyTranslationLL.setVisibility(View.GONE);
                break;
            case TRANSLATION:
                arBodyMainRL.setVisibility(View.GONE);
                arBodyTranslationLL.setVisibility(View.VISIBLE);
                break;
            default:
                arBodyMainRL.setVisibility(View.VISIBLE);
                arBodyTranslationLL.setVisibility(View.GONE);
                break;
        }
    }
}
