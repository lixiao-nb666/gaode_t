package com.nrmyw.gaode_t.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amap.api.maps.AMapException;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;


import com.gaode.demo.activity.IndexActivity;
import com.gaode.demo.activity.RestRouteShowActivity;
import com.newbee.ble_lib.NewBeeBleManager;
import com.newbee.bulid_lib.mybase.activity.util.ActivityUtil;

import com.nrmyw.ble_event_lib.bean.BleSendImageEndInfoBean;
import com.nrmyw.ble_event_lib.bean.BleSendImageStartInfoBean;

import com.nrmyw.ble_event_lib.statu.BleStatu;
import com.nrmyw.ble_event_lib.statu.BleStatuEventObserver;
import com.nrmyw.ble_event_lib.statu.BleStatuEventSubscriptionSubject;
import com.nrmyw.ble_event_lib.type.BleSendBitmapQualityType;
import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.activity.ble.BleSetActivity;
import com.nrmyw.gaode_t.activity.t800.T800BleDataTestActivity;

import com.gaode.demo.util.CheckPermissionsActivity;
import com.nrmyw.gaode_t.gaode.GaoDeListenManager;
import com.nrmyw.gaode_t.log.LG;
import com.nrmyw.hud_data_event_lib.HudEventManager;
import com.nrmyw.hud_data_event_lib.b.a;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.type.HudDevice;


public class MainActivity extends CheckPermissionsActivity  {
    private BleStatuEventObserver bleStatuEventObserver=new BleStatuEventObserver() {
        @Override
        public void sendBleStatu(BleStatu bleStatu, Object... objects) {
            Message msg=new Message();
            msg.what=bleStatu.ordinal();
            if(objects.length>0){
                msg.obj=objects[0];
            }
            handler.sendMessage(msg);
        }
    };
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            BleStatu bleStatu=BleStatu.values()[msg.what];
            bleStatuTV.setText(getResources().getText(bleStatu.getStrId()));
            switch (bleStatu){
                case CONNECTING:
                    HudDevice hudDevice= HudManager.getInstance().getHudDevice();
//                    BleDeviceType bleDeviceType= GaoDeHudSendConfig.getInstance().getBleDeviceType();
                    if(null!=hudDevice){
                        bleNameTV.setText(hudDevice.name()+"-");
                    }
                    break;
                case CONNECTED:
                    bleConnectTV.setText(getResources().getText(R.string.ble_connected));
                    setViewByBleConnectStatu(NewBeeBleManager.getInstance().isConnect());
                    break;
                case DISCONNECTED:
                    bleConnectTV.setText(getResources().getText(R.string.ble_disconnect));
                    setViewByBleConnectStatu(NewBeeBleManager.getInstance().isConnect());
                    break;
                case RUN_ERR:

                    break;
                case SEND_IMAGE_START:
                    BleSendImageStartInfoBean startInfoBean= (BleSendImageStartInfoBean) msg.obj;
                    break;
                case SEND_IMAGE_END:
                    BleSendImageEndInfoBean endInfoBean= (BleSendImageEndInfoBean) msg.obj;
                    Log.i("Navie-bitmap----6","Navie-bitmap----6---666:---"+endInfoBean.getUseTime());
                    break;
            }
        }
    };
    private Runnable initRunnable=new Runnable() {
        @Override
        public void run() {
            try {
                BleStatuEventSubscriptionSubject.getInstance().attach(bleStatuEventObserver);
                HudManager.getInstance().getBleEvent().havePermissionInitBle();

            }catch (Exception e){}
        }
    };
    private LinearLayout bg1,bg2;
    private TextView bleNameTV,bleConnectTV,bleStatuTV;
//    ,disconnectedBT
    private Button initBT,searchBT,bleSetBT,sendTestBT,startGaoDeBT;
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
                    ActivityUtil.toActivity(MainActivity.this, BleSetActivity.class);
                    break;
                case R.id.bt_send_test:
                    ActivityUtil.toActivity(MainActivity.this, T800BleDataTestActivity.class);
                    break;
                case R.id.bt_to_gaode_t:
                    AmapNaviParams params = new AmapNaviParams(null);
                    params.setShowCrossImage(true);
                    params.setScaleAutoChangeEnable(getBaseContext(),true);
                    params.setTrafficEnabled(true);
                    params.setSecondActionVisible(true);
                    params.setDayAndNightMode(getBaseContext(),0);
                    params.setBroadcastMode(getBaseContext(),2);
                    params.setCarDirectionMode(getBaseContext(),2);
                    params.showEagleMap(getBaseContext(),true);
                    params.setShowVoiceSetings(true);
//                    //传递上下文和导航参数
                    AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, GaoDeListenManager.getInstance().getiNaviInfoCallback(),MyGaoDeActivity.class);

                    try {
                        AMapNavi.getInstance(getApplicationContext()).addAMapNaviListener(GaoDeListenManager.getInstance().getaMapNaviListener());
//                      AMapNavi.getInstance(getApplicationContext()).addParallelRoadListener(GaoDeListenManager.getInstance().getParallelRoadListener());
                    } catch (AMapException e) {
                        LG.i("kankanshenmegui"+e.toString());
                    }
//                      startActivity(new Intent(getApplicationContext(), IndexActivity.class));
//                      startActivity(new Intent(MainActivity.this, RestRouteShowActivity.class));
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG","onCreate------------");
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
        bg1=findViewById(R.id.ll_bg_1);
        bg2=findViewById(R.id.ll_bg_2);
        bleNameTV=findViewById(R.id.tv_ble_name);
        bleConnectTV=findViewById(R.id.tv_ble_connect_statu);
        bleStatuTV=findViewById(R.id.tv_ble_statu);
        initBT=findViewById(R.id.bt_init);
        searchBT=findViewById(R.id.bt_search);
        bleSetBT=findViewById(R.id.bt_ble_set);
        sendTestBT=findViewById(R.id.bt_send_test);
        startGaoDeBT=findViewById(R.id.bt_to_gaode_t);
        initBT.setOnClickListener(onClickListener);
        searchBT.setOnClickListener(onClickListener);
        bleSetBT.setOnClickListener(onClickListener);
        sendTestBT.setOnClickListener(onClickListener);
        startGaoDeBT.setOnClickListener(onClickListener);
        //法律法规这个必须调用
        handler.postDelayed(initRunnable,1000);


    }


    /**
     * 返回键处理事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            System.exit(0);// 退出程序
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        BleStatuEventSubscriptionSubject.getInstance().detach(bleStatuEventObserver);
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
    }
}
