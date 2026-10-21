package com.nrmyw.gaode_t.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

public class HudNeedCheckUtil {


    public static boolean isExternalStorageManager(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {

           return false;
        }else {
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.R)
    public static void gotoManageExternalStorage(Context context) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static boolean canUseBle(Context context){
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            return false;
        }else {
            return true;
        }
    }

    public static boolean canUseBluetooth(Context context){
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            return false;
        }else {
            return true;
        }
    }

    public static boolean checkBattery(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = context.getPackageName();
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
//                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//                intent.setData(Uri.parse("package:" + packageName));
//                context.startActivity(intent);
                return false;
            }
        }
        return true;
    }

    public static void gotoManageBattery(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = context.getPackageName();
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                context.startActivity(intent);
            }
        }

    }


    public static BluetoothOpenErr checkBluetoothIsOpen(Activity context){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(null==bluetoothAdapter){

            return BluetoothOpenErr.BLE_NULL;
        }
        if(!bluetoothAdapter.isEnabled()){
            if(userOpenBle(context)){
                return BluetoothOpenErr.BLE_USER_DO_OPEN;
            }
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return BluetoothOpenErr.BLE_AUTO_OPEN;
            }
            bluetoothAdapter.enable();
            return BluetoothOpenErr.BLE_NEED_USER_HAND_OPEN;
        }
        return BluetoothOpenErr.OPEN_OK;
    }

    private static boolean userOpenBle(Activity context){
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }
        context.startActivityForResult(enableBtIntent, 1);
        return true;
    }


    public static boolean requestBackgroundLocationPermission(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return true;


        return true;
    }





    public enum BluetoothOpenErr{
        OPEN_OK,//打开成功
        BLE_NULL,//BLE为空
        BLE_USER_DO_OPEN,//需要用户操作打开
        BLE_AUTO_OPEN,//自动打开中
        BLE_NEED_USER_HAND_OPEN,//用户需要手动打开
    }

}
