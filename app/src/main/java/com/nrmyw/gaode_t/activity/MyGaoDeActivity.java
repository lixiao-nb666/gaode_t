package com.nrmyw.gaode_t.activity;

import android.Manifest;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;



import com.amap.api.navi.AmapRouteActivity;

import com.nrmyw.gaode_t.service.manager.HudManagerService;
import com.nrmyw.gaode_t.util.ActivityToUtil;
import com.nrmyw.hud_data_lib.type.yellow_statu.HudYellowStatuBjType;
import com.nrmyw.hud_manager_lib.HudManager;


public class MyGaoDeActivity extends AmapRouteActivity {



    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);




        needPermissions=new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE
        };
        needPermissionsForQ=new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE
        };;
        Log.i("进来自定义","进来字典贵位置了");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
                Toast.makeText(MyGaoDeActivity.this, "再按一次返回首页", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                ActivityToUtil.toMainActivity(this);
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }




}
