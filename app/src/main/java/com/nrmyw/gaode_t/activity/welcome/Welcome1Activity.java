package com.nrmyw.gaode_t.activity.welcome;//package com.newbee.build;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.activity.hud.HudMainActivity;
import com.nrmyw.hud_data_lib.type.HudCmdType;
import com.nrmyw.hud_manager_lib.HudManager;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class Welcome1Activity extends BaseCompatActivity implements EasyPermissions.PermissionCallbacks {
    private TextView tv1,tv2,tv3,tv4;
    private ImageView imageView;

    private LinearLayout bjLL;



//    @Override
//    public void initWelcomeControl() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Intent intent = new Intent();
//            String packageName = getPackageName();
//            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
//                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//                intent.setData(Uri.parse("package:" + packageName));
//                startActivity(intent);
//            }
//        }
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

    }


    private List<String> permissions;

    @Override
    public void initData() {
        permissions=new ArrayList<>();
        permissions.add( Manifest.permission.BLUETOOTH_SCAN);
        permissions.add( Manifest.permission.BLUETOOTH_CONNECT);
        permissions.add(  Manifest.permission.BLUETOOTH_ADVERTISE);
        permissions.add(  Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(  Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add( Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(   Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add( Manifest.permission.READ_PHONE_STATE);
    }

    @Override
    public void initControl() {

    }

    @Override
    public void closeActivity() {

    }

    @Override
    public void viewIsShow() {
        requestPermissions();
    }

    private void okLetsGo(){
        toActivity(HudMainActivity.class);
        finish();
//        gotoManageExternalStorage();
    }

    @TargetApi(Build.VERSION_CODES.R)
    private void gotoManageExternalStorage() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void requestPermissions() {
        if (EasyPermissions.hasPermissions(this, permissions.toArray(new String[0]))) {
            // 跳转到下一个界面
            okLetsGo();
        } else {
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
        }
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

        okLetsGo();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...

        if (EasyPermissions.somePermissionPermanentlyDenied(this, permissions)) {
            new AppSettingsDialog.Builder(this).build().show();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(this, R.string.t800_test_item_warning_point_1_t, Toast.LENGTH_SHORT)
                    .show();
        }

    }






}
