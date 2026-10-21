package com.newbee.yd_translation.util;

import android.util.Log;

import com.nrmyw.hud_data_lib.type.lane.HudNowLaneStrType;
import com.nrmyw.hud_manager_lib.HudManager;

public class NrmywTranslationUtil {

    public static final boolean isDebug=false;

    public static void sendTranslationing(String str){
        if(isDebug){
            Log.i(" NrmywTranslationUtil", "kankan text 81:"+str );
            HudManager.getInstance().getHudEvent().sendNextLaneName(str);
        }else {
            Log.i(" NrmywTranslationUtil", "kankan text 91:"+str );
            HudManager.getInstance().getHudEvent().sendTranslationing(str);
        }

    }

    public static void sendTranslationingResult(String str){
        if(isDebug){
            Log.i(" NrmywTranslationUtil", "kankan text 82:"+str );
            HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.def,str);
        }else {
            Log.i(" NrmywTranslationUtil", "kankan text 92:"+str );
            HudManager.getInstance().getHudEvent().sendTranslationingResult(str);
        }

    }


    public static void sendTranslationed(String str){
        if(isDebug){
            Log.i(" NrmywTranslationUtil", "kankan text 83:"+str );
            HudManager.getInstance().getHudEvent().sendNextLaneName(str);
        }else {
            Log.i(" NrmywTranslationUtil", "kankan text 93:"+str );
            HudManager.getInstance().getHudEvent().sendTranslationed(str);
        }

    }

    public static void sendTranslationedResult(String str){
        if(isDebug){
            Log.i(" NrmywTranslationUtil", "kankan text 84:"+str );
            HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.def,str);
        }else {
            Log.i(" NrmywTranslationUtil", "kankan text 94:"+str );
            HudManager.getInstance().getHudEvent().sendTranslationedResult(str);
        }

    }
}
