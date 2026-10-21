package com.nrmyw.huynew.util;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class StringSplitUtil {
    private static String splitStr="[\n,\t,，,。,!,?,？,！,.]";
    private static String splitStr1="[\n,\t,。,!,?,？,！]";

    public static List<String> getNeedSplitStrList(String defStr){
        List<String> stringList=new ArrayList<>();
        if(TextUtils.isEmpty(defStr)){
            return stringList;
        }
        String[] defSS=defStr.split(splitStr1);
        for(String s:defSS){
            if(!TextUtils.isEmpty(s)){
                stringList.add(s);
            }
        }
        return stringList;
    }
}
