package com.nrmyw.gaode_t.type;

import android.content.Context;
import android.text.TextUtils;

public enum GaoDeNaviLanguageType {

    CHINESE(0,"简体中文","zh"),
    TraditionalChinese(1,"繁体中文","TW"),
    English(2,"英语","en"),
    Spanish(3,"西班牙语","es"),
    Portuguese(4,"葡萄牙语","pt"),
    French(5,"法语","fr"),

    German(6,"德语","de"),
    Thai(7,"泰语","th") ,
    Japanese8(8,"日语","ja"),
    Korean( 9,"韩语","ko") ,
    Turkish(10,"土耳其语","tr"),
    Hebrew(11,"希伯来语","iw"),
    Italian(12,"意大利语","it"),
    Russian( 13,"俄语","ru"),
    Malay(14,"马来语","ms"),

    Indonesian (15,"印尼语","id"),
    Vietnamese (16,"越南语","vi"),
    AUTO(999,"自动","");
            private int code;
            private String langName;
            private String systemStr;
    private GaoDeNaviLanguageType(int code,String langName,String systemStr){
        this.code=code;
        this.langName=langName;
        this.systemStr=systemStr;
    }

    public int getSetCode(String systemLangStr){
        if(code!=999){
            return code;
        }
        if(TextUtils.isEmpty(systemLangStr)){
            return GaoDeNaviLanguageType.CHINESE.code;
        }


        for(GaoDeNaviLanguageType languageType:GaoDeNaviLanguageType.values()){
            if(systemLangStr.contains(languageType.systemStr)){
                return languageType.code;
            }
        }
        return GaoDeNaviLanguageType.CHINESE.code;

    }


}
