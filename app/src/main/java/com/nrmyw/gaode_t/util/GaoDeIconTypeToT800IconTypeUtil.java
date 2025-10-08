package com.nrmyw.gaode_t.util;



import com.nrmyw.gaode_t.type.NaviSdkTurnIconType;
import com.nrmyw.hud_data_lib.type.turn.HudTurnType;

public class GaoDeIconTypeToT800IconTypeUtil {


    public static HudTurnType getNeedTrunType(NaviSdkTurnIconType naviSdkTrunIconType){
        switch (naviSdkTrunIconType){
            case LEFT://左转图标（数值：2）
                return HudTurnType.tbt12;
            case RIGHT://右转图标（数值：3）
                return HudTurnType.tbt13;
            case   LEFT_FRONT ://左前方图标（数值：4）
                return HudTurnType.tbt17;
            case   RIGHT_FRONT ://右前方图标（数值：5）
                return HudTurnType.tbt18;
            case     LEFT_BACK  ://左后方图（数值：6）
                return HudTurnType.tbt16;
            case      RIGHT_BACK ://右后方图标（数值：7）
                return HudTurnType.tbt19;
            case      LEFT_TURN_AROUND ://左转掉头图标（数值：8）
                return HudTurnType.tbt14;
            case      STRAIGHT ://直行图标（数值：9）
                return HudTurnType.tbt11;
            case  ARRIVED_SERVICE_AREA ://到达服务区
                return HudTurnType.tbt152;
            case   ARRIVED_TOLLGATE ://到达收费站
                return HudTurnType.numb_154;
            case  ARRIVED_DESTINATION ://到达目的的(15)
                return HudTurnType.numb_201;
            case       ARRIVED_TUNNEL ://到达隧道(16) ,
                return HudTurnType.tbt121;
            case  SPECIAL_CONTINUE ://顺行图标（数值：20）
                return HudTurnType.tbt103;
            case     ENTRY_RING_LEFT ://标准小环岛,绕环岛左转,右侧通行地区的逆时针环岛（数值：21）
                return HudTurnType.tbt139;
            case     ENTRY_RING_RIGHT ://标准小环岛,绕环岛右转,右侧通行地区的逆时针环岛（数值：22）
                return HudTurnType.tbt133;
            case   ENTRY_RING_CONTINUE ://标准小环岛,绕环岛直行,右侧通行地区的逆时针环岛（数值：23）
                return HudTurnType.tbt142;
            case  ENTRY_RING_UTURN ://标准小环岛,绕环岛调头,右侧通行地区的逆时针环岛（数值：24）
                return HudTurnType.tbt136;
            case MERGE_LEFT://65 靠左图标
                return HudTurnType.tbt75;
            case MERGE_RIGHT://66 靠右图标
                return HudTurnType.tbt74;
        }
//        ARRIVED_WAYPOINT (10) ,//到达途经点
//        case    ENTER_ROUNDABOUT://进入环岛图标（数值：11）
//        case          OUT_ROUNDABOUT ://驶出环岛图标（数值：12）
//        case    ENTRY_LEFT_RING:   //进入环岛，注意，这个是左侧通行地区的顺时针环岛（数值：17）
//        LEAVE_LEFT_RING (18) ,//驶出环岛，注意，这个是左侧通行地区的顺时针环岛（数值：18）
//                U_TURN_RIGHT (19) ,//右转掉头图标 ，注意，这个是左侧通行地区的掉头（数值：19）
//        case    ENTRY_LEFT_RING_LEFT ://标准小环岛,绕环岛左转,左侧通行地区的顺时针环岛（数值：25）
//        ENTRY_LEFT_RING_RIGHT (26) ,//标准小环岛 绕环岛右转，左侧通行地区的顺时针环岛（数值：26）
//                ENTRY_LEFT_RING_CONTINUE (27) ,//标准小环岛 绕环岛直行，左侧通行地区的顺时针环岛（数值：27）
//                ENTRY_LEFTRINGU_TURN (28) ,//标准小环岛 绕环岛调头，左侧通行地区的顺时针环岛（数值：28）
        return HudTurnType.none;
    }

}
