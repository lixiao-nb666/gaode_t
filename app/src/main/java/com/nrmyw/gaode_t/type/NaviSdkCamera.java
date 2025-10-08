package com.nrmyw.gaode_t.type;

public enum NaviSdkCamera {
    NONE(-1),
    TRAFFICLIGHT (2),//闯红灯拍照


    BREAKRULE ( 3),//	违章拍照
    EMERGENCY ( 5),//应急车道拍照
    BUSWAY ( 4),//公交专用道摄像头

    SURVEILLANCE (1),//监控摄像
    SPEED (0),//测速摄像
    FLOWSPEED ( 10),// 流动测速电子眼


//    BICYCLE ( 6),//非机动车道(暂未使用)
//    INTERVALVELOCITYSTART ( 8),//区间测速起始
//    INTERVALVELOCITYEND ( 9),//区间测速终止

    ETC ( 11),//ETC计费拍照
    ;
    private int iconType;
//    private int dis;
//    private int cameraSpeed;
    NaviSdkCamera(int iconType){
        this.iconType=iconType;

    }


    public int getIconType() {
        return iconType;
    }




//    public int getDis() {
//        return dis;
//    }
//
//    public void setDis(int dis) {
//        this.dis = dis;
//    }
//
//
//
//    public int getCameraSpeed() {
//        return cameraSpeed;
//    }
//
//    public void setCameraSpeed(int cameraSpeed) {
//        this.cameraSpeed = cameraSpeed;
//    }

    public static NaviSdkCamera getNeedType(int iconType){
        for(NaviSdkCamera type: NaviSdkCamera.values()){
            if(type.iconType==iconType){
                return type;
            }
        }
        return NONE;
    }


    @Override
    public String toString() {
        return "NaviSdkCamera{" +
                "iconType=" + iconType +
                '}';
    }
}
