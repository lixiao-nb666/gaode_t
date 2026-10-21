package com.nrmyw.gaode_t.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.newbee.bulid_lib.mybase.activity.util.ActivityUtil;
import com.nrmyw.gaode_t.BuildConfig;
import com.nrmyw.gaode_t.activity.hud.HudMainActivity;
import com.nrmyw.huynew.activity.HuyNewMainActivity;

public class ActivityToUtil {

    public static void toMainActivity(Activity activity){
        Class  toClass;
        switch (BuildConfig.BUILD_TYPE){
            case "hn_version":
                toClass=HuyNewMainActivity.class;
                break;
            default:
                toClass=HudMainActivity.class;
                break;
        }
        Intent intent = new Intent(activity, toClass);
        activity.startActivity(intent);
    }
}
