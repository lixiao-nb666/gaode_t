package com.nrmyw.gaode_t.util;

import android.text.TextUtils;

public class CheckUtil {

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
}
