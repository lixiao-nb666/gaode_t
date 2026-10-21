package com.nrmyw.gaode_t.activity.welcome;//package com.newbee.build;

import static android.view.View.VISIBLE;

import android.Manifest;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.activity.hud.HudMainActivity;
import com.nrmyw.gaode_t.util.HudNeedCheckUtil;
import com.nrmyw.gaode_t.util.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class Welcome3Activity extends BaseCompatActivity implements EasyPermissions.PermissionCallbacks {
    private TextView tv1,tv2,tv3,tv4,errShowTV;
    private ImageView imageView;

    private LinearLayout bjLL,ts1LL,ts2LL;


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    handler.removeMessages(1);
                    needWaitTime--;
                    if(needWaitTime<=0){
                        HudNeedCheckUtil.gotoManageExternalStorage(Welcome3Activity.this);
                    }else {
                        errShowTV.setText("该设备没有文件权限，请点击授权或等待"+needWaitTime+"S");
                        handler.sendEmptyMessageDelayed(1,1000);
                    }

                    break;
            }
        }
    };

//    @Override
//    public void initWelcomeControl() {

//    }

    @Override
    public int getViewLayoutRsId() {
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
    }


    private List<String> permissions;


    @Override
    public void initData() {
        permissions=new ArrayList<>();
        if (!PermissionUtils.isAndroidQOrAbove() || Environment.isExternalStorageLegacy()) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (PermissionUtils.isAndroidSOrAbove()) {
            permissions.add(Manifest.permission.BLUETOOTH_SCAN);
            permissions.add(Manifest.permission.BLUETOOTH_CONNECT);
            permissions.add(Manifest.permission.BLUETOOTH_ADVERTISE);
        }else {

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions.add( Manifest.permission.ACCESS_BACKGROUND_LOCATION);
//            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//            permissions. add(Manifest.permission.ACCESS_FINE_LOCATION);
        }else {

        }
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions. add(Manifest.permission.ACCESS_FINE_LOCATION);
//        permissions.add( Manifest.permission.READ_PHONE_STATE);
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
    }

    @Override
    public void initControl() {
        clickBs=0;
        errShowTV.setOnClickListener(onClickListener);
    }

    @Override
    public void closeActivity() {
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void viewIsShow() {
        checkHudNeed();
    }

    @Override
    public void viewIsPause() {

    }

    @Override
    public void changeConfig() {

    }

    @Override
    public boolean needFullScreen() {
        return true;
    }


    private void okLetsGo(){
        toActivity(HudMainActivity.class);
        finish();
    }




    private void showErrLL(boolean showErr ){
        if(showErr){
            ts1LL.setVisibility(View.GONE);
            ts2LL.setVisibility(VISIBLE);
        }else {
            ts1LL.setVisibility(View.VISIBLE);
            ts2LL.setVisibility(View.GONE);
        }

    }

    private int clickBs=0;
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                switch (clickBs){
                    case 1:
                        new AppSettingsDialog.Builder(Welcome3Activity.this).build().show();
                        break;
                    case 2:
                        handler.removeMessages(1);
                        HudNeedCheckUtil.gotoManageExternalStorage(Welcome3Activity.this);
                        break;
                }
        }
    };
    private int needWaitTime;
    private void checkHudNeed(){
        handler.removeMessages(1);
        clickBs=0;
        if (!checkPermissions()) {
            // 跳转到下一个界面
            showErrLL(true);
            errShowTV.setText("请授权，然后使用该应用。\n" +
                    "如无法再次自动授权，可能点击拒绝授权！\n" +
                    "该情况，单击此处进入设置授权！\n" +
                    "如继续异常，请卸载重新安装！\n" +
                    ">>>>>>Go>>>>>>");

            clickBs=1;
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
        if(!HudNeedCheckUtil.isExternalStorageManager(this)){
            showErrLL(true);
            needWaitTime=3;
            errShowTV.setText("该设备没有文件权限，请点击授权或等待"+needWaitTime+"S");
            handler.sendEmptyMessageDelayed(1,1000);
            clickBs=2;
            return;
        }

        if(! HudNeedCheckUtil.checkBattery(this)){
            showErrLL(true);
            errShowTV.setText("请授权程序能后台使用");
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
        handler.removeMessages(1);
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
        checkHudNeed();
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








}
