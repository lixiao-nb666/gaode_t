package com.gaode.demo.test;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.amap.api.navi.AmapRouteActivity;

public class CustomAmapRouteActivity extends AmapRouteActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
