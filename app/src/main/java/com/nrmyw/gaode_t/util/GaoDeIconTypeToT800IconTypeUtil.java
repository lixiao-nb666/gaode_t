package com.nrmyw.gaode_t.util;



import com.nrmyw.gaode_t.log.LG;
import com.nrmyw.gaode_t.type.NaviSdkTurnIconType;
import com.nrmyw.hud_data_lib.type.turn.HudTurnType;

public class GaoDeIconTypeToT800IconTypeUtil {


    public static HudTurnType getNeedTrunType(NaviSdkTurnIconType naviSdkTrunIconType){
        LG.i("lankanzhishiduoshao:","kankanzhishiduoshao:"+naviSdkTrunIconType+"---"+naviSdkTrunIconType.getIconType());
        switch (naviSdkTrunIconType){
            case NONE://（数值：0）
            case DEFAULT://（数值：1）
                return HudTurnType.tbt11;
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
            case       ARRIVED_WAYPOINT: //到达途经点    10
                return HudTurnType.c_800;
            case    ENTER_ROUNDABOUT://进入环岛图标（数值：11）
                return HudTurnType.g_t14;
            case     OUT_ROUNDABOUT ://驶出环岛图标（数值：12）
                return HudTurnType.g_t16;
            case  ARRIVED_SERVICE_AREA ://到达服务区( 13)
                return HudTurnType.tbt152;
            case   ARRIVED_TOLLGATE ://到达收费站(14)
                return HudTurnType.c_154;
            case  ARRIVED_DESTINATION ://到达目的的(15)
                return HudTurnType.c_201;
            case       ARRIVED_TUNNEL ://到达隧道(16) ,
                return HudTurnType.tbt121;
            case    ENTRY_LEFT_RING:   //进入环岛，注意，这个是左侧通行地区的顺时针环岛（数值：17）
                return HudTurnType.g_t13;
            case LEAVE_LEFT_RING://驶出环岛，注意，这个是左侧通行地区的顺时针环岛（数值：18）
                return HudTurnType.g_t15;
            case   U_TURN_RIGHT://右转掉头图标 ，注意，这个是左侧通行地区的掉头（数值：19）
                return HudTurnType.tbt19;
            case  SPECIAL_CONTINUE ://顺行图标（数值：20）
                return HudTurnType.g_t1;
            case     ENTRY_RING_LEFT ://标准小环岛,绕环岛左转,右侧通行地区的逆时针环岛（数值：21）
                return HudTurnType.tbt139;
            case     ENTRY_RING_RIGHT ://标准小环岛,绕环岛右转,右侧通行地区的逆时针环岛（数值：22）
                return HudTurnType.tbt133;
            case   ENTRY_RING_CONTINUE ://标准小环岛,绕环岛直行,右侧通行地区的逆时针环岛（数值：23）
                return HudTurnType.tbt142;
            case  ENTRY_RING_UTURN ://标准小环岛,绕环岛调头,右侧通行地区的逆时针环岛（数值：24）
                return HudTurnType.tbt136;
            case    ENTRY_LEFT_RING_LEFT ://标准小环岛,绕环岛左转,左侧通行地区的顺时针环岛（数值：25）
                return HudTurnType.g_t17;
            case ENTRY_LEFT_RING_RIGHT ://标准小环岛 绕环岛右转，左侧通行地区的顺时针环岛（数值：26）
                return HudTurnType.g_t19;
            case   ENTRY_LEFT_RING_CONTINUE ://标准小环岛 绕环岛直行，左侧通行地区的顺时针环岛（数值：27）
                return HudTurnType.g_t29;
            case  ENTRY_LEFTRINGU_TURN ://标准小环岛 绕环岛调头，左侧通行地区的顺时针环岛（数值：28）
                return HudTurnType.g_t31;
            case MERGE_LEFT://65 靠左图标
                return HudTurnType.g_t7;
            case MERGE_RIGHT://66 靠右图标
                return HudTurnType.g_t8;

        }

//
//
//
//
//
//
//
//
//
        return HudTurnType.none;
    }

}
