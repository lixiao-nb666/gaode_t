package com.nrmyw.gaode_t.util.share;

import android.text.TextUtils;

import com.newbee.bulid_lib.mybase.share.MyShare;
import com.newbee.gson_lib.gson.MyGson;
import com.nrmyw.hud_data_event_lib.config.HudSetBean;
import com.nrmyw.hud_data_event_lib.config.HudSetConfig;
import com.nrmyw.hud_data_lib.type.warningproint.HudWarningPointType;
import com.nrmyw.hud_manager_lib.bean.HudUserConfigBean;
import com.nrmyw.hud_manager_lib.config.HudUserSetConfig;

public class HudShareUtil {

    public static void putUserConfigBean(){
        MyShare.getInstance().putString("UserConfigBean", MyGson.getInstance().toGsonStr(HudUserSetConfig.getInstance().getUserConfigBean()) );
    }

    public static void reSetUserConfigBean(){

        try {
            String shareStr=MyShare.getInstance().getString("UserConfigBean");
            if(TextUtils.isEmpty(shareStr)){
                return;
            }
            HudUserConfigBean hudUserConfigBean=MyGson.getInstance().fromJson(shareStr,HudUserConfigBean.class);
            if(null==hudUserConfigBean){
                return;
            }
            HudUserSetConfig.getInstance().setUserConfigBean(hudUserConfigBean);
        }catch (Exception e){}
    }


    public static void putHudSetBean(){
        MyShare.getInstance().putString("HudSetBean", MyGson.getInstance().toGsonStr(HudSetConfig.getInstance().getHudSetBean()) );
    }

    public static void reSetHudSetBean(){

        try {
            String shareStr=MyShare.getInstance().getString("HudSetBean");
            if(TextUtils.isEmpty(shareStr)){
                return;
            }
            HudSetBean hudSetBean=MyGson.getInstance().fromJson(shareStr,HudSetBean.class);
            if(null==hudSetBean){
                return;
            }
            HudSetConfig.getInstance().setHudSetBean(hudSetBean);

        }catch (Exception e){}
    }

    public static void reSetAllBean(){
        reSetUserConfigBean();
        reSetHudSetBean();
    }
}
