package com.nrmyw.gaode_t.type;

import com.nrmyw.gaode_t.R;
import com.nrmyw.hud_data_lib.type.lane.HudLaneType;

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


    public static HudLaneType useImageRsGetHudLaneType(int imageRs){
        switch (imageRs){
            case R.drawable.landback_0:
                return HudLaneType.F_N;
            case R.drawable.landback_1:
                return HudLaneType.L_N;
            case R.drawable.landback_2:
                return HudLaneType.L_F_N;
            case R.drawable.landback_3:
                return HudLaneType.R_N;
            case R.drawable.landback_4:
                return HudLaneType.R_F_N;
            case R.drawable.landback_5:
                return HudLaneType.LT_N;
            case R.drawable.landback_6:
                return HudLaneType.L_R_N;
            case R.drawable.landback_7:
                return HudLaneType.gd_landback_7;
            case R.drawable.landback_8:
                return HudLaneType.gd_landback_8;
            case R.drawable.landback_9:
                return HudLaneType.F_LT_N;
            case R.drawable.landback_a:
                return HudLaneType.gd_landback_a;
            case R.drawable.landback_b:
                return HudLaneType.LF_T_N;
            case R.drawable.landback_c:
                return HudLaneType.gd_landback_c;
            case R.drawable.landback_d:
                return HudLaneType.gd_landback_d;
            case R.drawable.landback_e:
                return HudLaneType.gd_landback_e;
            case R.drawable.landback_f:
                return HudLaneType.gd_landback_f;
            case R.drawable.landback_g:
                return HudLaneType.LT_RF_N;
            case R.drawable.landback_h:
                return HudLaneType.gd_landback_h;
            case R.drawable.landback_i:
                return HudLaneType.gd_landback_i;
            case R.drawable.landback_j:
                return HudLaneType.gd_landback_j;
            case R.drawable.landback_k:
                return HudLaneType.BUS_N_B;
            case R.drawable.landback_l:
                return HudLaneType.gd_landback_l;
            case  R.drawable.landfront_0:
            case  R.drawable.landfront_00:
                return HudLaneType.F_Y;
            case  R.drawable.landfront_1:
            case  R.drawable.landfront_11:
                return HudLaneType.L_Y;
            case  R.drawable.landfront_3:
            case  R.drawable.landfront_33:
                return HudLaneType.R_Y;
            case  R.drawable.landfront_5:

                return HudLaneType.gd_landfront_5;
            case  R.drawable.landfront_8:
                return HudLaneType.gd_landfront_8;
            case  R.drawable.landfront_20:
                return HudLaneType.L_F_Y_F;
            case  R.drawable.landfront_21:
                return HudLaneType.L_F_Y_L;
            case  R.drawable.landfront_22:
                return HudLaneType.L_F_Y;
            case  R.drawable.landfront_40:
                return HudLaneType.R_F_Y_F;
            case  R.drawable.landfront_43:
                return HudLaneType.R_F_Y_R;
            case  R.drawable.landfront_44:
                return HudLaneType.R_F_Y;

            case  R.drawable.landfront_55:
                return HudLaneType.LT_Y;
            case  R.drawable.landfront_61:
                return HudLaneType.L_R_Y_L;
            case  R.drawable.landfront_63:
                return HudLaneType.L_R_Y_R;
            case  R.drawable.landfront_66:
                return HudLaneType.L_R_Y;
            case  R.drawable.landfront_70:
                return HudLaneType.gd_landfront_70;
            case  R.drawable.landfront_71:
                return HudLaneType.gd_landfront_71;
            case  R.drawable.landfront_73:
                return HudLaneType.gd_landfront_73;
            case  R.drawable.landfront_77:
                return HudLaneType.gd_landfront_77;
            case  R.drawable.landfront_88:
                return HudLaneType.gd_landfront_88;
            case  R.drawable.landfront_90:
                return HudLaneType.F_LT_Y_F;
            case  R.drawable.landfront_95:
                return HudLaneType.F_LT_Y_T;
            case  R.drawable.landfront_99:
                return HudLaneType.F_LT_Y;
            case  R.drawable.landfront_a0:
                return HudLaneType.gd_landfront_a0;
            case  R.drawable.landfront_a8:
                return HudLaneType.gd_landfront_a8;
            case  R.drawable.landfront_aa:
                return HudLaneType.gd_landfront_aa;
            case  R.drawable.landfront_b1:
                return HudLaneType.L_T_Y_L;
            case  R.drawable.landfront_b5:
                return HudLaneType.L_T_Y_T;
            case  R.drawable.landfront_bb:
                return HudLaneType.L_T_Y;
            case  R.drawable.landfront_c3:
                return HudLaneType.gd_landfront_c3;
            case  R.drawable.landfront_c8:
                return HudLaneType.gd_landfront_c8;
            case  R.drawable.landfront_cc:
                return HudLaneType.gd_landfront_cc;
            case R.drawable.landfront_d:
            case R.drawable.landfront_dd:
                return HudLaneType.gd_landfront_d;
            case  R.drawable.landfront_e1:
                return HudLaneType.gd_landfront_e1;
            case  R.drawable.landfront_e5:
                return HudLaneType.gd_landfront_e5;
            case  R.drawable.landfront_ee:
                return HudLaneType.gd_landfront_ee;
            case  R.drawable.landfront_f0:
                return HudLaneType.gd_landfront_f0;
            case  R.drawable.landfront_f1:
                return HudLaneType.gd_landfront_f1;
            case  R.drawable.landfront_f5:
                return HudLaneType.gd_landfront_f5;
            case  R.drawable.landfront_ff:
                return HudLaneType.gd_landfront_ff;
            case  R.drawable.landfront_g3:
                return HudLaneType.LT_RF_Y_RF;
            case  R.drawable.landfront_g5:
                return HudLaneType.LT_RF_Y_LT;
            case  R.drawable.landfront_gg:
                return HudLaneType.LT_RF_Y;
            case  R.drawable.landfront_h1:
                return HudLaneType.gd_landfront_h1;
            case  R.drawable.landfront_h3:
                return HudLaneType.gd_landfront_h3;
            case  R.drawable.landfront_h5:
                return HudLaneType.gd_landfront_h5;
            case  R.drawable.landfront_hh:
                return HudLaneType.gd_landfront_hh;
            case  R.drawable.landfront_i0:
                return HudLaneType.gd_landfront_i0;
            case  R.drawable.landfront_i3:
                return HudLaneType.gd_landfront_i3;
            case  R.drawable.landfront_i5:
                return HudLaneType.gd_landfront_i5;
            case  R.drawable.landfront_ii:
                return HudLaneType.gd_landfront_ii;
            case  R.drawable.landfront_j1:
                return HudLaneType.gd_landfront_j1;
            case  R.drawable.landfront_j8:
                return HudLaneType.gd_landfront_j8;
            case  R.drawable.landfront_jj:
                return HudLaneType.gd_landfront_jj;
            case  R.drawable.landfront_kk:
                return HudLaneType.BUS_Y;
            case  R.drawable.landfront_ll:
                return HudLaneType.gd_landfront_ll;
        }
        return HudLaneType.NONE;
    }





}
