package com.nrmyw.gaode_t.type;

public enum NaviSdkLaneType {
    LANE_ACTION_NULL (255),//无对应车道
    LANE_ACTION_AHEAD  (0),//直行
    LANE_ACTION_LEFT (1),//左转
    LANE_ACTION_AHEAD_LEFT( 2),//直行+左转
    LANE_ACTION_RIGHT  ( 3),//右转
    LANE_ACTION_AHEAD_RIGHT(4),//直行+右转
    LANE_ACTION_LU_TURN (5),//左掉头
    LANE_ACTION_LEFT_RIGHT(6),//左转+右转
    LANE_ACTION_AHEAD_LEFT_RIGHT (7),//直行+左转+右转
    LANE_ACTION_RU_TURN (8),//右掉头
    LANE_ACTION_AHEAD_LU_TURN (9),//直行+左掉头
    LANE_ACTION_AHEAD_RU_TURN (10),//直行+右掉头
    LANE_ACTION_LEFT_LU_TURN (11),//左转+左掉头
    LANE_ACTION_RIGHT_RU_TURN (12),//右转+右掉头
    LANE_ACTION_LEFT_IN_AHEAD (13),//直行+扩展
    LANE_ACTION_LEFT_IN_LEFT_LU_TURN (14),//左转+左掉头+扩展
    LANE_ACTION_RESERVED (15),//保留
    LANE_ACTION_AHEAD_LEFT_LU_TURN ( 16),//直行+左转+左掉头
    LANE_ACTION_RIGHT_RU_TURN_EX( 17),//右转+左掉头
    LANE_ACTION_LEFT_RU_TURN ( 18),//左转+右转+左掉头
    LANE_ACTION_AHEAD_RIGHT_RU_TURN ( 19),//直行+右转+左掉头
    LANE_ACTION_LEFT_LU_TURN_EX (20),//左转+右掉头
    LANE_ACTION_BUS ( 21),//公交车道
    LANE_ACTION_EMPTY (22),//空车道
    LANE_ACTION_VARIABLE ( 23),//可变车道
    LANE_ACTION_DEDICATED (24),//专用车道
    LANE_ACTION_TIDAL (25),//潮汐车道












    ;
    private int iconType;
    NaviSdkLaneType(int iconType){
        this.iconType=iconType;
    }

    public void setIconType(int iconType) {
        this.iconType = iconType;
    }

    public int getIconType() {
        return iconType;
    }

    public static NaviSdkLaneType getNeedType(int iconType){
        for(NaviSdkLaneType laneType: NaviSdkLaneType.values()){
            if(laneType.iconType==iconType){
                return laneType;
            }
        }
        NaviSdkLaneType trunIconType= NaviSdkLaneType.LANE_ACTION_NULL;
        trunIconType.setIconType(iconType);
        return trunIconType;
    }





}
