package com.nrmyw.gaode_t.util;

import android.text.TextUtils;

public class StringUtil {

    public static String useStringArrayGetStr(String[] ss,String addStr){
        if(null==ss||ss.length==0){
            return "";
        }
        String needStr="";
        for(String s:ss){
            if(!TextUtils.isEmpty(s)){
                if(TextUtils.isEmpty(needStr)){
                    needStr=s;
                }else {
                    needStr+=(addStr+s);
                }
            }
        }
        return needStr;

    }
}
