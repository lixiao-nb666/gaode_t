package com.nrmyw.gaode_t.activity.welcome;//package com.newbee.build;


import android.Manifest;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.newbee.bulid_lib.mybase.share.MyShare;
import com.nrmyw.gaode_t.BuildConfig;
import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.activity.hud.HudMainActivity;
import com.nrmyw.gaode_t.activity.t800.T800NumbUtil;
import com.nrmyw.gaode_t.config.UiConfig;
import com.nrmyw.gaode_t.service.manager.HudManagerServiceDao;
import com.nrmyw.gaode_t.util.ActivityToUtil;
import com.nrmyw.gaode_t.util.HudNeedCheckUtil;
import com.nrmyw.gaode_t.util.PermissionUtils;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.util.DistanceToHudUtil;
import com.nrmyw.huynew.activity.HuyNewFileActivity;
import com.nrmyw.huynew.activity.HuyNewFileDownActivity;
import com.nrmyw.huynew.activity.HuyNewMainActivity;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class WelcomeActivity extends BaseCompatActivity implements EasyPermissions.PermissionCallbacks {
    private TextView tv1,tv2,tv3,tv4,errShowTV;
    private ImageView imageView;

    private LinearLayout bjLL,ts1LL,ts2LL;

    private Button    getPermissionsBT,getFilePermissionBT,getBatteryPermissionBT;


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            MsgWhat msgWhat=MsgWhat.values()[msg.what];
            handler.removeMessages(msg.what);
            switch (msgWhat){
                case TO_GET_FILE_Permission:

                    needWaitTime--;
                    if(needWaitTime<=0){
                        HudNeedCheckUtil.gotoManageExternalStorage(WelcomeActivity.this);
                    }else {
                        errShowTV.setText("该设备没有文件权限，请点击授权或等待"+needWaitTime+"S");
                        handler.sendEmptyMessageDelayed(msgWhat.ordinal(),1000);
                    }
                    break;
                case TO_GET_BATTERY_Permission:
                    needWaitTime--;
                    if(needWaitTime<=0){
                        HudNeedCheckUtil.gotoManageBattery(WelcomeActivity.this);
                    }else {
                        errShowTV.setText("请授权程序能后台使用电池"+needWaitTime+"S");
                        handler.sendEmptyMessageDelayed(msgWhat.ordinal(),1000);
                    }
                    break;

                case CHECK_ALL:
                    checkHudNeed();
                    break;
            }
        }
    };

//    @Override
//    public void initWelcomeControl() {

//    }

    @Override
    public int getViewLayoutRsId() {
        if (!isTaskRoot()) {
            finish();
        }
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
        bjLL=findViewById(R.id.ll_welcome_bj);
        imageView=findViewById(R.id.iv_hud_device);
        imageView.setImageResource(R.drawable.t800_1);
        ts1LL=findViewById(R.id.ll_ts_1);
        ts2LL=findViewById(R.id.ll_ts_2);
        errShowTV=findViewById(R.id.tv_err_show);
        getPermissionsBT=findViewById(R.id.bt_get_permissions);
        getFilePermissionBT=findViewById(R.id.bt_get_file_permission);
        getBatteryPermissionBT=findViewById(R.id.bt_get_battery_permission);
        getPermissionsBT.setOnClickListener(onClickListener);
        getFilePermissionBT.setOnClickListener(onClickListener);
        getBatteryPermissionBT.setOnClickListener(onClickListener);

        Log.i("kankanlog","numbnumb  1:"+ T800NumbUtil.formatDistance(555));
        Log.i("kankanlog","numbnumb  2:"+ T800NumbUtil.formatDistance(6666));
        Log.i("kankanlog","numbnumb  3:"+ T800NumbUtil.formatDistance(11000));
        Log.i("kankanlog","numbnumb  11:"+ DistanceToHudUtil.getTmapExitDistance(555));
        Log.i("kankanlog","numbnumb  22:"+ DistanceToHudUtil.getTmapExitDistance(6666));
        Log.i("kankanlog","numbnumb  33:"+ DistanceToHudUtil.getTmapExitDistance(11000));
    }


    private List<String> permissions;

    public static final int TIRAMISU = 33;
    public static final String READ_MEDIA_AUDIO = "android.permission.READ_MEDIA_AUDIO";
    public static final String READ_MEDIA_IMAGES = "android.permission.READ_MEDIA_IMAGES";
    public static final String READ_MEDIA_VIDEO = "android.permission.READ_MEDIA_VIDEO";
    @Override
    public void initData() {
        permissions=new ArrayList<>();
//         if (!PermissionUtils.isAndroidQOrAbove() ||Environment.isExternalStorageLegacy() ) {
//            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//             Log.i("kankandaozhelimei","kankandaozhelimei1");
//        }else {
//
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if(!Environment.isExternalStorageManager()){
//                if(Build.VERSION.SDK_INT >= TIRAMISU){
//                    Log.i("kankandaozhelimei","kankandaozhelimei2");
//                    permissions.add( READ_MEDIA_AUDIO);
//                    permissions.add( READ_MEDIA_IMAGES);
//                    permissions.add( READ_MEDIA_VIDEO);
//                }else{
//                    permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//                    permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                }
            }
        }


        if (PermissionUtils.isAndroidSOrAbove()) {
            permissions.add(Manifest.permission.BLUETOOTH_SCAN);
            permissions.add(Manifest.permission.BLUETOOTH_CONNECT);
            permissions.add(  Manifest.permission.BLUETOOTH_ADVERTISE);
        }else {
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            permissions. add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            permissions.add( Manifest.permission.FOREGROUND_SERVICE);
        }
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            permissions. add(Manifest.permission.ACCESS_FINE_LOCATION);
//        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add( Manifest.permission.READ_PHONE_STATE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            permissions.add( Manifest.permission.CAMERA);
//            permissions.add( Manifest.permission.RECORD_AUDIO);
        }

//        protected String[] needPermissions = new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE"};
//        protected String[] needPermissionsForQ = new String[]{"android.permission.ACCESS_BACKGROUND_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//
//        }
//        permissions.add(  Manifest.permission.ACCESS_COARSE_LOCATION);
//        permissions.add(  Manifest.permission.ACCESS_FINE_LOCATION);
//        permissions.add( Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        permissions.add(   Manifest.permission.READ_EXTERNAL_STORAGE);
//
//        permissions.add( Manifest.permission.CHANGE_NETWORK_STATE);
//        permissions.add( Manifest.permission.INTERNET);
//        permissions.add( Manifest.permission.WAKE_LOCK);
//        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.P){
//            permissions.add( Manifest.permission.FOREGROUND_SERVICE);
//        }
//        permissions.add(Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//        if(Build.VERSION.SDK_INT>=13){
//            permissions.add(Manifest.permission_group.STORAGE);
//
//
//        }

    }

    @Override
    public void initControl() {


    }

    @Override
    public void closeActivity() {
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void viewIsShow() {
        nowCheckAll();
    }

    @Override
    public void viewIsPause() {
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void changeConfig() {

    }

    @Override
    public boolean needFullScreen() {
        return true;
    }


    private void okLetsGo(){
        UiConfig.w= bjLL.getMeasuredWidth();
        UiConfig.h=bjLL.getMeasuredHeight();
        if(UiConfig.h<400){
            UiConfig.w=getWindowManager().getDefaultDisplay().getWidth();
            UiConfig.h=getWindowManager().getDefaultDisplay().getHeight();
        }
        UiConfig.bottomH=myWindowSet.getBottomNavigationHeigh();

        startNeedService();

//        toActivity(HuyNewFileDownActivity.class);

        ActivityToUtil.toMainActivity(this);
        finish();
    }

    private void startNeedService(){
        HudManagerServiceDao.getInstance().start(this);
    }


    private void showErrLL(boolean showErr ){
        if(showErr){
            ts1LL.setVisibility(View.GONE);
            ts2LL.setVisibility(View.VISIBLE);
        }else {
            ts1LL.setVisibility(View.VISIBLE);
            ts2LL.setVisibility(View.GONE);
        }
        getPermissionsBT.setVisibility(View.GONE);
        getFilePermissionBT.setVisibility(View.GONE);
        getBatteryPermissionBT.setVisibility(View.GONE);
    }

    private void showGetPermissions(){
        ts1LL.setVisibility(View.GONE);
        ts2LL.setVisibility(View.VISIBLE);
        getPermissionsBT.setVisibility(View.VISIBLE);
        getFilePermissionBT.setVisibility(View.GONE);
        getBatteryPermissionBT.setVisibility(View.GONE);
    }


    private void showGetFilePermission(){
        ts1LL.setVisibility(View.GONE);
        ts2LL.setVisibility(View.VISIBLE);
        getPermissionsBT.setVisibility(View.GONE);
        getFilePermissionBT.setVisibility(View.VISIBLE);
        getBatteryPermissionBT.setVisibility(View.GONE);
    }

    private void showGetBatteryPermission(){
        ts1LL.setVisibility(View.GONE);
        ts2LL.setVisibility(View.VISIBLE);
        getPermissionsBT.setVisibility(View.GONE);
        getFilePermissionBT.setVisibility(View.GONE);
        getBatteryPermissionBT.setVisibility(View.VISIBLE);

    }

    private AppSettingsDialog appSettingsDialog;
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_get_permissions:
                    if(null==appSettingsDialog){
                        appSettingsDialog=new AppSettingsDialog.Builder(WelcomeActivity.this).build();
                    }
                    appSettingsDialog.show();
                    break;
                case R.id.bt_get_file_permission:
                    handler.removeMessages(MsgWhat.TO_GET_FILE_Permission.ordinal());
                    HudNeedCheckUtil.gotoManageExternalStorage(WelcomeActivity.this);
                    break;
                case R.id.bt_get_battery_permission:
                    handler.removeMessages(MsgWhat.TO_GET_BATTERY_Permission.ordinal());
                    HudNeedCheckUtil.gotoManageBattery(WelcomeActivity.this);
                    break;
            }
        }
    };
    private int needWaitTime;

    private void nowCheckAll(){
        handler.removeMessages(MsgWhat.CHECK_ALL.ordinal());
        handler.sendEmptyMessageDelayed(MsgWhat.CHECK_ALL.ordinal(), 234);
    }
    private void checkHudNeed(){
        for (MsgWhat msgWhat:MsgWhat.values()){
            handler.removeMessages(msgWhat.ordinal());
        }

        if (!checkPermissions()) {
            // 跳转到下一个界面
            showGetPermissions();
            errShowTV.setText(
                    "请授权，才能正常使用该应用。\n" +
                    "如无法自动授权，可能已拒绝！\n" +
                    "该情况，请单击进入设置修改！\n" +
                    "如继续异常，请卸载重新安装！\n");
            requestPermissions();
            return;
        }
        if(!HudNeedCheckUtil.canUseBluetooth(this)){
            showErrLL(true);
            errShowTV.setText("该设备不支持蓝牙,无法使用，请更换设备");
            return;
        }
        if(!HudNeedCheckUtil.canUseBle(this)){
            showErrLL(true);
            errShowTV.setText("该设备不支持BLE,无法使用，请更换设备");
            return;
        }
        if(! HudNeedCheckUtil.checkBattery(this)){
            showGetBatteryPermission();
//            showErrLL(true);
            needWaitTime=2;
            errShowTV.setText("请授权程序能后台使用电池"+needWaitTime+"S");
            handler.sendEmptyMessageDelayed(MsgWhat.TO_GET_BATTERY_Permission.ordinal(), 1000);
            return;
        }
        if(!HudNeedCheckUtil.isExternalStorageManager(this)){
            showGetFilePermission();
//            showErrLL(true);
            needWaitTime=2;
            errShowTV.setText("该设备没有文件权限，请点击授权或等待"+needWaitTime+"S");
            handler.sendEmptyMessageDelayed(MsgWhat.TO_GET_FILE_Permission.ordinal(), 1000);

            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions=new ArrayList<>();
            permissions.add( Manifest.permission.ACCESS_BACKGROUND_LOCATION);
            if (!checkPermissions()) {
                // 跳转到下一个界面
                showGetPermissions();
                errShowTV.setText(
                        "没后台定位权限" );
                requestPermissions();
                return;
            }
        }
        if(!PermissionUtils.isHasWritePermission()){

            PermissionUtils.toAppSetPermission(this);
            showToast("请打开文件权限！");
            return;
        }


        if(!PermissionUtils.isHasAudioPermission()){
            PermissionUtils.toAppSetPermission(this);
            showToast("请授权麦克风！" );
            return;
        }



        HudNeedCheckUtil.BluetoothOpenErr bluetoothOpenErr=HudNeedCheckUtil.checkBluetoothIsOpen(this);
        switch (bluetoothOpenErr){
            case OPEN_OK:
                showErrLL(false);
                okLetsGo();
                break;
            case BLE_NULL:
                showErrLL(true);
                errShowTV.setText("该设备可能不支持BLE，请重新更换设备");
                break;
            case BLE_USER_DO_OPEN:
                showErrLL(true);
                errShowTV.setText("蓝牙已关闭，请点击授权授权开启");
                break;
            case BLE_AUTO_OPEN:
                showErrLL(true);
                errShowTV.setText("蓝牙已关闭，自动开启中。。如无法开启，请手动点击开启");
                break;
            case BLE_NEED_USER_HAND_OPEN:
                showErrLL(true);
                errShowTV.setText("蓝牙已关闭，，请手动点击开启");
                break;
        }
        handler.removeCallbacksAndMessages(null);
    }




    private boolean checkPermissions(){
        return EasyPermissions.hasPermissions(this, permissions.toArray(new String[0]));
    }

    private void requestPermissions() {
        EasyPermissions.requestPermissions(
                new PermissionRequest.Builder(this, RC_CAMERA_AND_LOCATION,permissions.toArray(new String[0]))
                        .setRationale(R.string.request_permissions)
//                            .setPositiveButtonText(R.string.rationale_ask_ok)
//                            .setNegativeButtonText(R.string.rationale_ask_cancel)
//                            .setTheme(R.style.my_fancy_style)
                        .build());




            // 获取权限
//            EasyPermissions.requestPermissions(this,
//                    rationale[0],
//                    PERMISSIONS_REQUEST_CODE,
//                    permissions.toArray(new String[0])
//            );
//        }
    }




//    @Override



//    @Override
//    public WelcomeInfoBean getWelcomeInfoBean() {
//        WelcomeInfoBean welcomeInfoBean=new WelcomeInfoBean();
//        List<String> permissions=new ArrayList<>();
//        permissions.add( Manifest.permission.BLUETOOTH_SCAN);
//        permissions.add( Manifest.permission.BLUETOOTH_CONNECT);
//        permissions.add(  Manifest.permission.BLUETOOTH_ADVERTISE);
//        permissions.add(  Manifest.permission.ACCESS_COARSE_LOCATION);
//        permissions.add(  Manifest.permission.ACCESS_FINE_LOCATION);
//        permissions.add( Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        permissions.add(   Manifest.permission.READ_EXTERNAL_STORAGE);
//        permissions.add( Manifest.permission.READ_PHONE_STATE);
//        welcomeInfoBean.setPermissionList(permissions);
//        welcomeInfoBean.setNeedFilePermission(true);
//        return welcomeInfoBean;
//    }

//    @Override
//    public void userNoPermission() {
//
//    }
//
//    @Override
//    public void userGetAllPermission() {
//        toActivity(MainActivity.class);
//
////
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////            this.startService(service);
////        }
//
    ////        Intent service = new Intent(getApplicationContext(), HudWindowService.class);
    ////        // Android 8.0使用startForegroundService在前台启动新服务
    ////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    ////            startForegroundService(service);
    ////        } else {
    ////            startService(service);
    ////        }
//
//        finish();
//
//    }







    public static final int RC_CAMERA_AND_LOCATION = 1; // requestCode
    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.action_settings),
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        // ...
        nowCheckAll();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
        if (EasyPermissions.somePermissionPermanentlyDenied(this, permissions)) {
            showErrLL(true);
            errShowTV.setText("拒绝授权应用，无法使用，请手动进入设置授权之后，再进入应用！");
//            new AppSettingsDialog.Builder(this).build().show();
        }

    }



    private enum MsgWhat{
        NONE,
        TO_GET_FILE_Permission,
        TO_GET_BATTERY_Permission,
        CHECK_ALL,

    }






}
