package com.gaode.demo.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.navi.AmapRouteActivity;

/**
 *
 * @author liqi
 * @date 2019/2/22
 */

public class CustomAmapRouteActivity extends AmapRouteActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.e("TAG","CustomAmapRouteActivity onCreate");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //StatusBarUtil.immersive(this,Color.BLACK);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
