package com.nrmyw.gaode_t.config;

import android.text.TextUtils;

import com.newbee.bulid_lib.mybase.share.MyShare;
import com.nrmyw.gaode_t.type.GaoDeNaviLanguageType;

public class GaoDeUserSetConfig {
    private static GaoDeUserSetConfig gaoDeUserSetConfig;
    private final String tag="GaoDeUserSetConfig>>>";
    private String langShareKey=tag+"Lang";
    private GaoDeNaviLanguageType languageType=GaoDeNaviLanguageType.AUTO;
    private GaoDeUserSetConfig(){
        try {
            String langShareStr= MyShare.getInstance().getString(langShareKey);
            if(!TextUtils.isEmpty(langShareStr)){
                int langIndex=Integer.valueOf(langShareStr);
                languageType=GaoDeNaviLanguageType.values()[langIndex];
            }
        }catch (Exception e){
            languageType=GaoDeNaviLanguageType.AUTO;
        }


    }

    public static GaoDeUserSetConfig getInstance(){
        if(null==gaoDeUserSetConfig){
            synchronized (GaoDeUserSetConfig.class){
                if(null==gaoDeUserSetConfig){
                    gaoDeUserSetConfig=new GaoDeUserSetConfig();
                }
            }
        }
        return gaoDeUserSetConfig;
    }

    public void setLanguageType(GaoDeNaviLanguageType languageType){
        if(null==languageType){
            return;
        }
        this.languageType=languageType;
        MyShare.getInstance().putString(langShareKey,this.languageType.ordinal()+"");
    }

    public GaoDeNaviLanguageType getLanguageType(){
        if(null==languageType){
            languageType=GaoDeNaviLanguageType.AUTO;
        }
        return languageType;
    }

}
