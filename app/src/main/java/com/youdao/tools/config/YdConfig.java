package com.youdao.tools.config;

import com.newbee.bulid_lib.mybase.share.MyShare;
import com.youdao.tools.type.YdLangType;


public class YdConfig {

//    public static final String  appKey="22bc516de11b3365";
    public static final String  appKey="411c4598093b4a42";

    private static YdConfig ydConfig;

    private YdLangType fromType,toType;
    private final YdLangType defFrom= YdLangType.en_US;
    private final YdLangType defTo= YdLangType.zh_CHS;
    private final String fromShareKey="fromShareKey";
    private final String toShareKey="toShareKey";

    private YdConfig(){
            String fromIndexStr= MyShare.getInstance().getString(fromShareKey,defFrom.ordinal()+"");
            int fromIndex=Integer.valueOf(fromIndexStr);
            fromType= YdLangType.values()[fromIndex];
            String toIndexStr = MyShare.getInstance().getString(toShareKey,defTo.ordinal()+"");
            int toIndex=Integer.valueOf(toIndexStr);
            toType= YdLangType.values()[toIndex];
    }

    public static YdConfig getInstance(){
        if(null==ydConfig){
            synchronized (YdConfig.class){
                if(null==ydConfig){
                    ydConfig=new YdConfig();
                }
            }
        }
        return ydConfig;
    }



    public YdLangType getFromType() {
        return fromType;
    }

    public void setFromType(YdLangType fromType) {
        if(null==fromType){
            return;
        }
        this.fromType = fromType;
        MyShare.getInstance().putString(fromShareKey,fromType.ordinal()+"");
    }

    public YdLangType getToType() {
        return toType;
    }

    public void setToType(YdLangType toType) {
        if(null==toType){
            return;
        }
        this.toType = toType;
        MyShare.getInstance().putString(toShareKey,toType.ordinal()+"");
    }
}
