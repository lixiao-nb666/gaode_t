package com.nrmyw.gaode_t.type;

public enum NaviSdkAvoidType {
//    类型：1-限高，2-限宽，3-限重，4-禁行
    NONE(0),
    AVOID_H(1),
    AVOID_W(2),
    AVOID_WEIGHT(3),
    AVOID_TURN(4),








    ;
    private int iconType;
    NaviSdkAvoidType(int iconType){
        this.iconType=iconType;
    }

    public void setIconType(int iconType) {
        this.iconType = iconType;
    }

    public int getIconType() {
        return iconType;
    }

    public static NaviSdkAvoidType getNeedType(int iconType){
        for(NaviSdkAvoidType type: NaviSdkAvoidType.values()){
            if(type.iconType==iconType){
                return type;
            }
        }
        return NONE;
    }





}
