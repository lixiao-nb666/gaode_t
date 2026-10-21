package com.nrmyw.gaode_t.activity.welcome;//package com.newbee.build;

import android.Manifest;
import android.widget.TextView;

import com.newbee.welcome_activity_lib.BaseWelcomeActivity;
import com.newbee.welcome_activity_lib.bean.WelcomeInfoBean;
import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.activity.hud.HudMainActivity;

import java.util.ArrayList;
import java.util.List;

public class Welcome2Activity extends BaseWelcomeActivity {
    private TextView tv1,tv2,tv3,tv4;

    @Override
    public int getWelcomeLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initWelcomeView() {

    }

    @Override
    public void initWelcomeData() {

    }

    @Override
    public void initWelcomeControl() {

    }

    @Override
    public WelcomeInfoBean getWelcomeInfoBean() {
        WelcomeInfoBean welcomeInfoBean=new WelcomeInfoBean();
        List<String> permissions=new ArrayList<>();
        permissions.add( Manifest.permission.BLUETOOTH_SCAN);
        permissions.add( Manifest.permission.BLUETOOTH_CONNECT);
        permissions.add(  Manifest.permission.BLUETOOTH_ADVERTISE);
        permissions.add(  Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(  Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add( Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(   Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add( Manifest.permission.READ_PHONE_STATE);
        welcomeInfoBean.setPermissionList(permissions);
        welcomeInfoBean.setNeedFilePermission(false);
        return welcomeInfoBean;
    }

    @Override
    public void userNoPermission() {

    }

    @Override
    public void userGetAllPermission() {
        toActivity(HudMainActivity.class);
//        toActivity(WhiteActivity.class);
        finish();
    }
}
