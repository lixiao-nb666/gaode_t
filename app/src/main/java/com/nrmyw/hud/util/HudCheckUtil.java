package com.nrmyw.hud.util;

import android.text.TextUtils;

import com.nrmyw.hud_data_lib.type.turn.HudTurnBjType;
import com.nrmyw.hud_data_lib.type.turn.HudTurnType;
import com.nrmyw.hud_data_lib.type.warningproint.HudWarningPointType;

public class HudCheckUtil {

    public static boolean checkStrNeedChange(String defStr,String newStr){
        if(TextUtils.isEmpty(defStr)){
            return true;
        }else {
            if(!defStr.equals(newStr)){
                return true;
            }
        }
        return false;
    }


    public static boolean checkIntNeedChange(int defInt,int newInt){
        return defInt!=newInt;
    }

    public static boolean checkWarningPointNeedChange(HudWarningPointType oldType,int oldDis,HudWarningPointType newType,int newDis){
        if(null==newType){
            return false;
        }
        if(null==oldType){
            return true;
        }
        if(newType==oldType&&newDis==oldDis){
            return false;
        }else {
            return true;
        }
    }


    public static boolean checkTurnTypeNeedChange(HudTurnType oldType, int oldDis, HudTurnType newType, int newDis){
        if(null==newType){
            return false;
        }
        if(null==oldType){
            return true;
        }
        if(newType==oldType&&newDis==oldDis){
            return false;
        }else {
            return true;
        }
    }

    public static boolean checkTurnBjTypeNeedChange(HudTurnBjType oldType, HudTurnBjType newType){
        if(null==newType){
            return false;
        }
        if(null==oldType){
            return true;
        }
        if(newType==oldType){
            return false;
        }else {
            return true;
        }
    }


}
