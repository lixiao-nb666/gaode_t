package com.nrmyw.gaode_t.type;

public enum NaviSdkAvoidForbidType {
//    禁行类型 0：禁止左转 1：禁止右转 2：禁止左掉头 3：禁止右掉头 4：禁止直行
    NONE(-1),
    L(0),
    R(1),
    LT(2),
    RT(3),
    A(4),








    ;
    private int iconType;
    NaviSdkAvoidForbidType(int iconType){
        this.iconType=iconType;
    }

    public void setIconType(int iconType) {
        this.iconType = iconType;
    }

    public int getIconType() {
        return iconType;
    }

    public static NaviSdkAvoidForbidType getNeedType(int iconType){
        for(NaviSdkAvoidForbidType type: NaviSdkAvoidForbidType.values()){
            if(type.iconType==iconType){
                return type;
            }
        }
        return NONE;
    }





}
