package com.nrmyw.gaode_t.util;

import com.nrmyw.gaode_t.R;
import com.nrmyw.hud_manager_lib.HudManager;

public class HudDeviceUtil {
    private static HudDeviceUtil hudDeviceUtil;
    private HudDeviceUtil(){}

    public static HudDeviceUtil getInstance(){
        if(null==hudDeviceUtil){
            synchronized (HudDeviceUtil.class){
                if(null==hudDeviceUtil){
                    hudDeviceUtil=new HudDeviceUtil();
                }
            }
        }
        return hudDeviceUtil;
    }


    private int[] t700RsS={R.drawable.t700_1,R.drawable.t700_2,R.drawable.t700_3,R.drawable.t700_4,R.drawable.t700_5,R.drawable.t700_6,R.drawable.t700_7,R.drawable.t700_8,R.drawable.t700_9,R.drawable.t700_10,R.drawable.t700_11,R.drawable.t700_12};
    private int[] t800RsS={R.drawable.t800_1,R.drawable.t800_2,R.drawable.t800_3,R.drawable.t800_4,R.drawable.t800_5,R.drawable.t800_6,R.drawable.t800_7,R.drawable.t800_8};

    private int[] huynewGreenArRsS={R.drawable.huynew_green_ar_2,R.drawable.huynew_green_ar_3,R.drawable.huynew_green_ar_4};
    private int index=0;
    public int getDeviceResource(){
        index=0;
        return getDeviceRsUseIndex();
    }

    public int getDeviceNextResource(){
        index++;
        return getDeviceRsUseIndex();
    }

    private int getDeviceRsUseIndex(){
        if(null==HudManager.getInstance().getHudDevice()){
            return 0;
        }
        switch (HudManager.getInstance().getHudDevice()){
            case T800:
                if(index>=t800RsS.length){
                    index=0;
                }
                return t800RsS[index];
            case HUYNEW_AR:
            case HUYNEW_AR_DEBUG:
                if(index>=huynewGreenArRsS.length){
                    index=0;
                }
                return huynewGreenArRsS[index];
            default:
                if(index>=t700RsS.length){
                    index=0;
                }
                return t700RsS[index];
        }
    }

    public String getDeviceShowName(){
        if(null==HudManager.getInstance().getHudDevice()){
            return "";
        }
       return HudManager.getInstance().getHudDevice().name();
    }

    public String getStatuStr(String bleStatuStr){

        int index=bleStatuStr.indexOf(":");
        if(index==-1){
            index=bleStatuStr.indexOf("：");
        }

        if(index!=-1&&index+1<bleStatuStr.length()-1){
            bleStatuStr=bleStatuStr.substring(index+1);
        }
        return bleStatuStr;
    }
}
