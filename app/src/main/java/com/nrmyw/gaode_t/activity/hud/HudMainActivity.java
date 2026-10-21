package com.nrmyw.gaode_t.activity.hud;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amap.api.maps.AMapException;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.model.AMapCarInfo;
import com.gaode.demo.activity.IndexActivity;
import com.gaode.demo.util.CheckPermissionsActivity;
import com.newbee.ble_lib.NewBeeBleManager;
import com.newbee.bulid_lib.mybase.activity.util.ActivityManager;
import com.newbee.bulid_lib.mybase.activity.util.ActivityUtil;
import com.newbee.gson_lib.gson.MyGson;
import com.newbee.yd_translation.activity.NrmywYdTranslationActivity;
import com.nrmyw.ble_event_lib.bean.BleSendImageEndInfoBean;
import com.nrmyw.ble_event_lib.bean.BleSendImageStartInfoBean;
import com.nrmyw.ble_event_lib.statu.BleStatu;
import com.nrmyw.ble_event_lib.statu.BleStatuEventObserver;
import com.nrmyw.ble_event_lib.statu.BleStatuEventSubscriptionSubject;
import com.nrmyw.gaode_t.BuildConfig;
import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.activity.MyGaoDeActivity;
import com.nrmyw.gaode_t.activity.set.ble.BleSetActivity;
import com.nrmyw.gaode_t.activity.set.ble.HudUpdateBean;
import com.nrmyw.gaode_t.activity.set.user.UserSetActivity;
import com.nrmyw.gaode_t.activity.t800.T800BleDataTestActivity;
import com.nrmyw.gaode_t.activity.t800.T800NumbUtil;
import com.nrmyw.gaode_t.gaode.GaoDeListenManager;
import com.nrmyw.gaode_t.log.LG;
import com.nrmyw.gaode_t.util.HudDeviceUtil;
import com.nrmyw.gaode_t.util.share.HudShareUtil;
import com.nrmyw.hud_manager_lib.HudManager;

import java.io.IOException;




public class HudMainActivity extends CheckPermissionsActivity  {
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
                        setViewByBleConnectStatu(NewBeeBleManager.getInstance().isConnect());
                        bleStatuIV.setVisibility(View.VISIBLE);
//                        PackageManager packageManager=MainActivity.this.getPackageManager();
//                        Intent intent=packageManager.getLaunchIntentForPackage("com.newbee.ble_tool");
//                        startActivity(intent);


                        break;
                    case DISCONNECTED:
                        setStatuStr(bleStatu.getStrId());
                        setHudDeviceNameAndImageShow(false);
                        setViewByBleConnectStatu(NewBeeBleManager.getInstance().isConnect());
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



                    default:
                        setConnectStr(bleStatu.getStrId());
                        break;
                }
                if (msg.arg1 != 0) {
                    bleConnectTV.append("-"+getResources().getText(msg.arg1));
                }
            }catch (Exception e){
                Log.i("tag", "kankanshenmegui----:" +e.toString());
            }
        }
    };

    private void setStatuStr(int rsId){
        if(rsId==0){
            return;
        }
        String bleStatuStr=getResources().getText(rsId).toString();
        bleStatuStr= HudDeviceUtil.getInstance().getStatuStr(bleStatuStr);
        bleStatuTV.setText(bleStatuStr);

    }

    private void setConnectStr(int rsId){
        if(rsId==0){
            return;
        }
        String bleConnectStr=getResources().getText(rsId).toString();
        bleConnectStr= HudDeviceUtil.getInstance().getStatuStr(bleConnectStr);
        bleConnectTV.setText(bleConnectStr);
    }

    private Runnable showDeviceImageRunnable =new Runnable() {
        @Override
        public void run() {
            int showRs=HudDeviceUtil.getInstance().getDeviceNextResource();
            if(showRs==0){
                return;
            }
            deviceIV.setImageResource(showRs);
            handler.removeCallbacks(showDeviceImageRunnable);
            handler.postDelayed(showDeviceImageRunnable,3000);
        }
    };

    private void setHudDeviceNameAndImageShow(boolean nowGetDevice){
        if(nowGetDevice){
            int showRs=HudDeviceUtil.getInstance().getDeviceResource();
            if(showRs!=0){
                deviceIV.setImageResource(showRs);
            }
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
//    private Runnable initRunnable=new Runnable() {
//        @Override
//        public void run() {
//            try {
//
//            }catch (Exception e){}
//        }
//    };
    private LinearLayout bg1,bg2;
    private TextView bleNameTV,bleConnectTV,bleStatuTV;
    private ImageView bleStatuIV,deviceIV;
//    ,disconnectedBT
    private Button initBT,searchBT,bleSetBT,sendTestBT,startGaoDeBT,toGaoDeDemoBT,toAppSetBT,toMapSetBT;
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
                case R.id.bt_ble_set:
                    ActivityUtil.toActivity(HudMainActivity.this, BleSetActivity.class);
                    break;
                case R.id.bt_send_test:
                    ActivityUtil.toActivity(HudMainActivity.this, T800BleDataTestActivity.class);
                    break;
                case R.id.bt_to_gaode_t:
                    ActivityUtil.toActivity(HudMainActivity.this, UserSetActivity.class);
                    break;
                case R.id.bt_map_set:
                    AmapNaviParams params = new AmapNaviParams();
//                    null
                    //新增加的---开始
//                    params.setNaviMode(AmapNaviType.WALK.ordinal());
//                    params.setNeedCalculateRouteWhenPresent(true);
//                    params.setNeedDestroyDriveManagerInstanceWhenNaviExit(true);



                            //新增加的---结束
                    params.setShowCrossImage(true);
                    params.setScaleAutoChangeEnable(getBaseContext(),true);
                    params.setTrafficEnabled(true);
                    params.setSecondActionVisible(true);
                    params.setDayAndNightMode(getBaseContext(),0);
                    params.setBroadcastMode(getBaseContext(),2);
                    params.setCarDirectionMode(getBaseContext(),2);
                    params.showEagleMap(getBaseContext(),true);
                    params.setShowVoiceSetings(true);
                    AMapCarInfo aMapCarInfo=new AMapCarInfo();
                    aMapCarInfo.setCarNumber("粤BGG7266");
                    params.setCarInfo(aMapCarInfo);
//                    //传递上下文和导航参数
                    AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, GaoDeListenManager.getInstance().getiNaviInfoCallback(), MyGaoDeActivity.class);
                    try {
                        AMapNavi.getInstance(getApplicationContext()).addAMapNaviListener(GaoDeListenManager.getInstance().getaMapNaviListener());
//                      AMapNavi.getInstance(getApplicationContext()).addParallelRoadListener(GaoDeListenManager.getInstance().getParallelRoadListener());
                    } catch (AMapException e) {
                        LG.i("kankanshenmegui"+e.toString());
                    }
//                      startActivity(new Intent(getApplicationContext(), IndexActivity.class));
//                      startActivity(new Intent(MainActivity.this, RestRouteShowActivity.class));
                    break;
                case R.id.bt_to_gaode_demo:
                    Log.e("TAG","onCreate------------？？？weishenmbutiao");
                    startActivity(new Intent(getApplicationContext(), NrmywYdTranslationActivity.class));
                    break;
                case R.id.bt_app_set:
                    startActivity(new Intent(getApplicationContext(), IndexActivity.class));
                    break;

//                    break;

            }

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int sdkVersion = Build.VERSION.SDK_INT;
        Log.e("TAG","onCreate------------"+sdkVersion);



        //这是为了应用程序安装完后直接打开，按home键退出后，再次打开程序出现的BUG
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
        setContentView(R.layout.activity_main);
        deviceIV=findViewById(R.id.iv_device_pic);
        bg1=findViewById(R.id.ll_bg_1);
        bg2=findViewById(R.id.ll_bg_2);
        bleStatuIV=findViewById(R.id.iv_ble_statu);
        bleStatuTV=findViewById(R.id.tv_ble_statu);
        bleNameTV=findViewById(R.id.tv_ble_name);
        bleConnectTV=findViewById(R.id.tv_ble_connect_statu);

        initBT=findViewById(R.id.bt_init);
        searchBT=findViewById(R.id.bt_search);
        bleSetBT=findViewById(R.id.bt_ble_set);
        sendTestBT=findViewById(R.id.bt_send_test);
        startGaoDeBT=findViewById(R.id.bt_to_gaode_t);
        toGaoDeDemoBT=findViewById(R.id.bt_to_gaode_demo);
        toAppSetBT=findViewById(R.id.bt_app_set);
        toMapSetBT=findViewById(R.id.bt_map_set);

        initBT.setOnClickListener(onClickListener);
        searchBT.setOnClickListener(onClickListener);
        bleSetBT.setOnClickListener(onClickListener);
        sendTestBT.setOnClickListener(onClickListener);
        startGaoDeBT.setOnClickListener(onClickListener);
        toGaoDeDemoBT.setOnClickListener(onClickListener);
        toAppSetBT.setOnClickListener(onClickListener);
        toMapSetBT.setOnClickListener(onClickListener);


        //法律法规这个必须调用
//        handler.postDelayed(initRunnable,1000);
        // 4 step
        BleStatuEventSubscriptionSubject.getInstance().attach(bleStatuEventObserver);
        // 3 step  when you have all permissions
        HudManager.getInstance().nowGetAllPermissions();
        //----------------------
        ActivityManager.getInstance().add(this);
    }


    /**
     * 返回键处理事件
     */
    private long firstTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(HudMainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                finish();
                ActivityManager.getInstance().finishAllActivity();
                System.exit(0);// 退出程序
                return true;
            }
        }



        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        // 5 step
        BleStatuEventSubscriptionSubject.getInstance().detach(bleStatuEventObserver);
        ActivityManager.getInstance().delete(this);
    }

    public void setViewByBleConnectStatu(boolean isConnected){
        if(isConnected){
            bg1.setVisibility(View.GONE);
            bg2.setVisibility(View.VISIBLE);
        }else {
            bg1.setVisibility(View.VISIBLE);
            bg2.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setViewByBleConnectStatu(NewBeeBleManager.getInstance().isConnect());
        HudShareUtil.reSetUserConfigBean();


    }




}
