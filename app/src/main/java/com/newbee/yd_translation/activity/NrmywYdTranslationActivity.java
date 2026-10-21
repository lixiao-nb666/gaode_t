package com.newbee.yd_translation.activity;


import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.newbee.translation_ui_lib.activity.base.BaseTranslationSimpleOneDialogActivity;
import com.newbee.translation_ui_lib.process.BaseVoiceToTextProcess;

import com.newbee.yd_translation.activity.lang.ToLangActivity;
import com.newbee.yd_translation.activity.lang.FromLangActivity;
import com.newbee.yd_translation.activity.set.SetActivity;
import com.nrmyw.gaode_t.activity.MyGaoDeActivity;
import com.nrmyw.gaode_t.util.ActivityToUtil;
import com.nrmyw.gaode_t.util.HudToNeedPageUtil;
import com.nrmyw.gaode_t.util.PermissionUtils;
import com.nrmyw.hud_data_lib.type.turn.HudTurnType;
import com.nrmyw.hud_data_lib.type.type.HudStatuType;
import com.nrmyw.hud_data_lib.type.ui.HudUiType;
import com.nrmyw.hud_manager_lib.HudManager;
import com.youdao.process.YdProcess;
import com.youdao.tools.config.YdConfig;


public class NrmywYdTranslationActivity extends BaseTranslationSimpleOneDialogActivity {


    public final long waitFinshTime=120*1000;

    public YdProcess.Listen processListen=new YdProcess.Listen() {
        @Override
        public void needWaitFinsh() {
            setAfterTimeToFinsh(waitFinshTime);
        }

        @Override
        public void nowMustFinsh() {
            finish();
        }

        @Override
        public void needToSetPermission() {
            PermissionUtils.toAppSetPermission(NrmywYdTranslationActivity.this);
            showToast("请授权音频，视频，麦克风，网络！");
        }


    };



    private YdProcess ydProcess;

    @Override
    public BaseVoiceToTextProcess getVoiceToTextProcess() {
        if(null==ydProcess){
            synchronized (NrmywYdTranslationActivity.class){
                if(null==ydProcess){
                    ydProcess=new YdProcess(this,processListen);
                    HudToNeedPageUtil.toTranslationPage();

                }
            }
        }
        Log.i("kankanzenmehuishi","kankanchushihuameui");
        return ydProcess;
    }

    @Override
    public void initControl() {
        super.initControl();
    }

    @Override
    public Class getFromLangActivity() {
        return FromLangActivity.class;
    }

    @Override
    public Class getToLangActivity() {
        return ToLangActivity.class;
    }

    @Override
    public Class getSetActivity() {
        return SetActivity.class;
    }

    @Override
    public String getFromLangStr() {
        try {
            String str= YdConfig.getInstance().getFromType().getStr();
            String[] ss=  str.split("-");
            return ss[0];
        }catch (Exception e){
            return "";
        }
    }

    @Override
    public String getToLangStr() {
        try {
            String str=YdConfig.getInstance().getToType().getStr();
            String[] ss=  str.split("-");
            return ss[0];
        }catch (Exception e){
            return "";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HudToNeedPageUtil.toMainPage();
    }


//    /**
//     * 返回键处理事件
//     */
//    private long firstTime = 0;
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK ) {
//            long secondTime = System.currentTimeMillis();
//            if (secondTime - firstTime > 2000) {
//                Toast.makeText(NrmywYdTranslationActivity.this, "再按一次返回首页", Toast.LENGTH_SHORT).show();
//                firstTime = secondTime;
//                return true;
//            } else {
//                ActivityToUtil.toMainActivity(this);
//                finish();
//                return true;
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
//            long secondTime = System.currentTimeMillis();
//            if (secondTime - firstTime > 2000) {
//                Toast.makeText(NrmywYdTranslationActivity.this, "再按一次返回首页", Toast.LENGTH_SHORT).show();
//                firstTime = secondTime;
//                return true;
//            } else {
//                ActivityToUtil.toMainActivity(this);
//                finish();
//                return true;
//            }
//        }
//        return super.dispatchKeyEvent(event);
//    }
}
