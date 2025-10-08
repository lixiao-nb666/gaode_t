package com.nrmyw.gaode_t.type;

public enum NaviSdkTurnIconType {
    NONE  (0),//无定义（数值：0）自定义转向图标数组，请忽略这个元素，从左转图标开始
    DEFAULT (1) ,//自定义转向图标数组，请忽略这个元素，从左转图标开始
    LEFT (2) ,//左转图标（数值：2）
    RIGHT (3) ,//右转图标（数值：3）
    LEFT_FRONT (4) ,//左前方图标（数值：4）
    RIGHT_FRONT (5) ,//右前方图标（数值：5）
    LEFT_BACK (6) ,//左后方图（数值：6）
    RIGHT_BACK (7) ,//右后方图标（数值：7）
    LEFT_TURN_AROUND (8) ,//左转掉头图标（数值：8）
    STRAIGHT (9) ,//直行图标（数值：9）
    ARRIVED_WAYPOINT (10) ,//到达途经点
    ENTER_ROUNDABOUT (11) ,//进入环岛图标（数值：11）
    OUT_ROUNDABOUT (12) ,//驶出环岛图标（数值：12）
    ARRIVED_SERVICE_AREA (13) ,//到达服务区
    ARRIVED_TOLLGATE (14) ,//到达收费站
    ARRIVED_DESTINATION (15) ,//到达目的的
    ARRIVED_TUNNEL (16) ,//到达隧道
    ENTRY_LEFT_RING (17) ,//进入环岛，注意，这个是左侧通行地区的顺时针环岛（数值：17）
    LEAVE_LEFT_RING (18) ,//驶出环岛，注意，这个是左侧通行地区的顺时针环岛（数值：18）
    U_TURN_RIGHT (19) ,//右转掉头图标 ，注意，这个是左侧通行地区的掉头（数值：19）
    SPECIAL_CONTINUE (20) ,//顺行图标（数值：20）
    ENTRY_RING_LEFT (21) ,//标准小环岛,绕环岛左转,右侧通行地区的逆时针环岛（数值：21）
    ENTRY_RING_RIGHT (22) ,//标准小环岛,绕环岛右转,右侧通行地区的逆时针环岛（数值：22）
    ENTRY_RING_CONTINUE (23) ,//标准小环岛,绕环岛直行,右侧通行地区的逆时针环岛（数值：23）
    ENTRY_RING_UTURN (24) ,//标准小环岛,绕环岛调头,右侧通行地区的逆时针环岛（数值：24）
    ENTRY_LEFT_RING_LEFT (25 ) ,//标准小环岛,绕环岛左转,左侧通行地区的顺时针环岛（数值：25）
    ENTRY_LEFT_RING_RIGHT (26) ,//标准小环岛 绕环岛右转，左侧通行地区的顺时针环岛（数值：26）
    ENTRY_LEFT_RING_CONTINUE (27) ,//标准小环岛 绕环岛直行，左侧通行地区的顺时针环岛（数值：27）
    ENTRY_LEFTRINGU_TURN (28) ,//标准小环岛 绕环岛调头，左侧通行地区的顺时针环岛（数值：28）
    MERGE_LEFT (65),//65 靠左图标
    MERGE_RIGHT (66) ,//66 靠右图标
//    CROSSWALK (29) ,//通过人行横道图标（数值：29）骑行、步行专有图标
//    OVERPASS (30) ,//通过过街天桥图标（数值：30）骑行、步行专有图标
//    UNDERPASS (31) ,//通过地下通道图标（数值：31）骑行、步行专有图标
//    SQUARE (32) ,//通过广场图标（数值：32）骑行、步行专有图标
//    PARK (33) ,//通过公园图标（数值：33）骑行、步行专有图标
//    STAIRCASE (34) ,//通过扶梯图标（数值：34）骑行、步行专有图标
//    LIFT (35) ,//通过直梯图标（数值：35）骑行、步行专有图标
//    CABLEWAY (36) ,//通过索道图标（数值：36）骑行、步行专有图标
//    SKY_CHANNEL (37) ,//通过空中通道图标（数值：37）骑行、步行专有图标
//    CHANNEL (38) ,//通过通道、建筑物穿越通道图标（数值：38）骑行、步行专有图标
//    WALK_ROAD (39) ,//通过行人道路图标（数值：39）骑行、步行专有图标
//    CRUISE_ROUTE (40) ,//通过游船路线图标（数值：40）骑行、步行专有图标
//    SIGHTSEEING_BUSLINE (41) ,//通过观光车路线图标（数值：41）骑行、步行专有图标
//    SLIDEWAY (42) ,//通过滑道图标（数值：42）骑行、步行专有图标
//    LADDER (43) ,//通过阶梯图标（数值：43）骑行、步行专有图标
//    SLOPE (44) ,//通过斜坡（数值：44）骑行、步行专有图标
//    BRIDGE (45) ,//通过桥（数值：45）骑行、步行专有图标
//    FERRY (46) ,//通过轮渡（数值：46）骑行、步行专有图标
//    SUBWAY (47) ,//通过地铁通道（数值：47）骑行、步行专有图标
//    ENTER_BUILDING (48) ,//进入建筑物（数值：48）骑行、步行专有图标
//    LEAVE_BUILDING (49) ,//离开建筑物（数值：49）骑行、步行专有图标
//    BY_ELEVATOR (50) ,//电梯换层（数值：50）骑行、步行专有图标
//    BY_STAIR (51) ,//楼梯换层（数值：51）骑行、步行专有图标
//    BY_ESCALATOR  (52),//扶梯换层（数值：52）骑行、步行专有图标
//    LOW_TRAFFIC_CROSS (53)  ,//非导航段通过红绿灯路口（数值：53）骑行、步行专有图标
//    LOW_CROSS  (54),//非导航段通过普通路口（数值：54）骑行、步行专有图标
        ;
    private int iconType;
    NaviSdkTurnIconType(int iconType){
        this.iconType=iconType;
    }

    public void setIconType(int iconType) {
        this.iconType = iconType;
    }

    public int getIconType() {
        return iconType;
    }

    public static NaviSdkTurnIconType getNeedType(int iconType){
        for(NaviSdkTurnIconType naviSdkIconType: NaviSdkTurnIconType.values()){
            if(naviSdkIconType.iconType==iconType){
                return naviSdkIconType;
            }
        }
        NaviSdkTurnIconType trunIconType= NaviSdkTurnIconType.NONE;
        trunIconType.setIconType(iconType);
        return trunIconType;
    }





}
