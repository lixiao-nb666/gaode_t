package com.nrmyw.gaode_t.util;



import com.nrmyw.gaode_t.log.LG;
import com.nrmyw.gaode_t.type.NaviSdkLaneType;
import com.nrmyw.hud_data_lib.type.lane.HudLaneType;

public class GaoDeLaneTypeToT800LaneTypeUtil {


    public static HudLaneType getNeedLaneType(int backgroundLane, int frontLane){
        LG.i("找不到看看值得111:"+backgroundLane+"----"+frontLane);
        int imageRs=HudLaneTypeUtil.getInstance().getNeedImageRs(backgroundLane,frontLane);
        if(imageRs!=0&&imageRs!=-1){
            HudLaneType hudLaneType=NaviSdkLaneType.useImageRsGetHudLaneType(imageRs);
            if(null!=hudLaneType&&hudLaneType!=HudLaneType.NONE){
                return hudLaneType;
            }
        }
        NaviSdkLaneType backgroundLaneType=NaviSdkLaneType.getNeedType(backgroundLane);
        NaviSdkLaneType frontLaneLaneType=NaviSdkLaneType.getNeedType(frontLane);
        switch (backgroundLaneType){
            case LANE_ACTION_NULL://找不到 255
               return HudLaneType.NONE;
            case LANE_ACTION_AHEAD://直行 0
                if(frontLaneLaneType==backgroundLaneType){
                    //直行选择
                    return HudLaneType.F_Y;
                }else {
                    //直行未选择
                    return HudLaneType.F_N;
                }
            case    LANE_ACTION_LEFT://左转 1
                if(frontLaneLaneType==backgroundLaneType){
                    return HudLaneType.L_Y;
                }else {
                  //左转未选择
                    return HudLaneType.L_N;
                }
            case   LANE_ACTION_AHEAD_LEFT://直行+左转 2
                    if(frontLaneLaneType==backgroundLaneType){
                           //左转选择
                        return HudLaneType.L_F_Y;
                    }else if(frontLaneLaneType==NaviSdkLaneType.LANE_ACTION_AHEAD) {

                        return HudLaneType.L_F_Y_F;
                    }else if(frontLaneLaneType==NaviSdkLaneType.LANE_ACTION_LEFT){
                        return HudLaneType.L_F_Y_L;
                    }else {
                        return HudLaneType.L_F_N;
                    }
            case LANE_ACTION_RIGHT://右转 3
                if(frontLaneLaneType==backgroundLaneType){
                    return HudLaneType.R_Y;
                }else {
                    return HudLaneType.R_N;
                }
            case    LANE_ACTION_AHEAD_RIGHT://直行+右转 4
                if(frontLaneLaneType==backgroundLaneType){
                    return HudLaneType.R_F_Y;
                }else if(frontLaneLaneType==NaviSdkLaneType.LANE_ACTION_AHEAD){
                    return HudLaneType.R_F_Y_F;
                }else if(frontLaneLaneType==NaviSdkLaneType.LANE_ACTION_RIGHT){
                    return HudLaneType.R_F_Y_R;
                }else {
                    return HudLaneType.R_F_N;
                }
            case  LANE_ACTION_LU_TURN ://左掉头 5
                if(frontLaneLaneType==backgroundLaneType){
                    return HudLaneType.LT_Y;
                }else {
                    return HudLaneType.LT_N;
                }
            case   LANE_ACTION_LEFT_RIGHT://左转+右转 6
                if(frontLaneLaneType==backgroundLaneType){
                    return HudLaneType.L_R_Y;
                }else if(frontLaneLaneType==NaviSdkLaneType.LANE_ACTION_LEFT){
                    return HudLaneType.L_R_Y_L;
                }else if(frontLaneLaneType==NaviSdkLaneType.LANE_ACTION_RIGHT){
                    return HudLaneType.L_R_Y_R;
                }else {
                    return HudLaneType.L_R_N;
                }

            case   LANE_ACTION_AHEAD_LEFT_RIGHT ://直行+左转+右转 7
                //这里T800是没做图标的

                return useFrontLaneLaneTypeGetHudLaneType(frontLaneLaneType,HudLaneType.L_R_N);





            case  LANE_ACTION_AHEAD_LU_TURN ://直行+左掉头 9
                if(frontLaneLaneType==backgroundLaneType){
                    return HudLaneType.F_LT_Y;
                }else if(frontLaneLaneType==NaviSdkLaneType.LANE_ACTION_AHEAD){
                    return HudLaneType.F_LT_Y_F;
                }else if(frontLaneLaneType==NaviSdkLaneType.LANE_ACTION_LU_TURN){
                    return HudLaneType.F_LT_Y_T;
                }else {
                    return HudLaneType.F_LT_N;
                }
            case      LANE_ACTION_RU_TURN ://右掉头 8
            case LANE_ACTION_AHEAD_RU_TURN://直行+右掉头 10
                if(frontLaneLaneType==backgroundLaneType){
                    return HudLaneType.RF_F_Y;
                }else {
                    return useFrontLaneLaneTypeGetHudLaneType(frontLaneLaneType,HudLaneType.RF_F_N);
                }


            case LANE_ACTION_LEFT_LU_TURN ://左转+左掉头 11
                if(frontLaneLaneType==backgroundLaneType){
                    return HudLaneType.L_T_Y;
                }else if(frontLaneLaneType==NaviSdkLaneType.LANE_ACTION_LEFT){
                    return HudLaneType.L_T_Y_L;
                }else if(frontLaneLaneType==NaviSdkLaneType.LANE_ACTION_LU_TURN){
                    return HudLaneType.L_T_Y_T;
                }else {
                    return HudLaneType.L_T_N;
                }
            case LANE_ACTION_RIGHT_RU_TURN ://右转+右掉头 12
                if(frontLaneLaneType==backgroundLaneType){
                    return HudLaneType.R_T_Y;
                }else if(frontLaneLaneType==NaviSdkLaneType.LANE_ACTION_RIGHT){
                    return HudLaneType.R_T_Y_R;
                }else if(frontLaneLaneType==NaviSdkLaneType.LANE_ACTION_RU_TURN ){
                    return HudLaneType.R_T_Y_T;
                }else {
                    return HudLaneType.R_T_N;
                }

            case   LANE_ACTION_LEFT_IN_AHEAD ://直行+扩展 13
                if(frontLaneLaneType==backgroundLaneType){
                    return HudLaneType.LF_Y;
                }else {
                    return useFrontLaneLaneTypeGetHudLaneType(frontLaneLaneType,HudLaneType.LF_N);
                }
            case    LANE_ACTION_LEFT_IN_LEFT_LU_TURN ://左转+左掉头+扩展 14
            case   LANE_ACTION_AHEAD_LEFT_LU_TURN ://直行+左转+左掉头 16
                if(frontLaneLaneType==backgroundLaneType){
                    return HudLaneType.L_LF_Y;
                }else {
                    return useFrontLaneLaneTypeGetHudLaneType(frontLaneLaneType,HudLaneType.L_LF_N);
                }
            case LANE_ACTION_RESERVED ://保留 15
                return useFrontLaneLaneTypeGetHudLaneType(frontLaneLaneType,HudLaneType.F_N);

            case  LANE_ACTION_RIGHT_RU_TURN_EX://右转+左掉头 17
                if(frontLaneLaneType==backgroundLaneType){
                    return HudLaneType. LT_RF_Y;
                }else if(frontLaneLaneType==NaviSdkLaneType.LANE_ACTION_LU_TURN ){
                    return HudLaneType. LT_RF_Y_LT;
                }else if(frontLaneLaneType==NaviSdkLaneType.LANE_ACTION_RIGHT){
                    return HudLaneType. LT_RF_Y_RF;
                }else if(frontLaneLaneType==NaviSdkLaneType.LANE_ACTION_NULL){
                    return HudLaneType. LT_RF_N;
                }else {
                    return useFrontLaneLaneTypeGetHudLaneType(frontLaneLaneType,HudLaneType. LT_RF_N);
                }

            case    LANE_ACTION_LEFT_RU_TURN ://左转+右转+左掉头 18
                return useFrontLaneLaneTypeGetHudLaneType(frontLaneLaneType,HudLaneType. LF_N);
            case           LANE_ACTION_AHEAD_RIGHT_RU_TURN ://直行+右转+左掉头 19
                return useFrontLaneLaneTypeGetHudLaneType(frontLaneLaneType,HudLaneType. F_N);
            case          LANE_ACTION_LEFT_LU_TURN_EX ://左转+右掉头 20
                break;
            case        LANE_ACTION_BUS ://公交车道 21
                if(frontLaneLaneType==backgroundLaneType){
                    return HudLaneType.BUS_Y;
                }else {
                    return HudLaneType.BUS_N_B;
                }


            case          LANE_ACTION_EMPTY ://空车道 22
                return useFrontLaneLaneTypeGetHudLaneType(frontLaneLaneType,HudLaneType. F_N);
            case         LANE_ACTION_VARIABLE ://可变车道 23
                return useFrontLaneLaneTypeGetHudLaneType(frontLaneLaneType,HudLaneType. F_N);
            case         LANE_ACTION_DEDICATED ://专用车道 24
                return useFrontLaneLaneTypeGetHudLaneType(frontLaneLaneType,HudLaneType. F_N);
            case         LANE_ACTION_TIDAL://潮汐车道 25
                return useFrontLaneLaneTypeGetHudLaneType(frontLaneLaneType,HudLaneType. F_N);



        }


        LG.i("找不到看看值得:"+backgroundLaneType+"----"+frontLaneLaneType);
        return HudLaneType.NONE;
    }

    private static HudLaneType useFrontLaneLaneTypeGetHudLaneType(NaviSdkLaneType laneType,HudLaneType def){
        switch (laneType){
            case LANE_ACTION_AHEAD:
                return HudLaneType.F_Y;
            case LANE_ACTION_AHEAD_LEFT:
                return HudLaneType.L_F_Y;
            case LANE_ACTION_AHEAD_RIGHT:
                return HudLaneType.R_F_Y;
            case LANE_ACTION_LEFT:
                return HudLaneType.L_Y;
            case LANE_ACTION_LEFT_LU_TURN:
                return HudLaneType.LT_Y;

            case LANE_ACTION_RIGHT:
                return HudLaneType.R_Y;
            case LANE_ACTION_RU_TURN :
                return HudLaneType.R_T_Y;
        }

        return def;
    }

}
