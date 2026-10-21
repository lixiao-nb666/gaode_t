package com.nrmyw.gaode_t.util;

import com.nrmyw.gaode_t.R;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.type.HudDevice;

public class HudImageUtil {

    public static int getNowHudEasyImageRs(){
        HudDevice hudDevice= HudManager.getInstance().getHudDevice();
        if(null==hudDevice){
            return R.drawable.hud_icon_easy_show;
        }
        switch (hudDevice){
            case HUD_T:
            case HUT_T:
            case T700:
            case T700_1:
            case T700_2:
                return R.drawable.t700_1;
            case T800:

            case T900:
            case T900_1:
            case T900_2:
            case H3000:
            case H3000_1:
            case T3000:
            case T3000_1:
                return R.drawable.t800_1;
            case HUYNEW_AR:
            case HUYNEW_AR_DEBUG:
                return R.drawable.huynew_green_ar_2;

        }
        return R.drawable.hud_icon_easy_show;
    }
}
