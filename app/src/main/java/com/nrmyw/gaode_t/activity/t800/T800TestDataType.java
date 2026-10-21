package com.nrmyw.gaode_t.activity.t800;


import com.nrmyw.gaode_t.R;


public enum T800TestDataType {
    mq_test(T800TestTitleType.MQ, R.string.t800_test_item_mq_100),
    time(T800TestTitleType.TIME,R.string.t800_test_item_time),
    speed_1(T800TestTitleType.SPEED,R.string.t800_test_item_speed_1),
    speed_2(T800TestTitleType.SPEED,R.string.t800_test_item_speed_2),
    speed_3(T800TestTitleType.SPEED,R.string.t800_test_item_speed_3),
    speeding(T800TestTitleType.SPEEDING,R.string.t800_test_item_speeding),
    IntervalSpeed(T800TestTitleType.INTERVALSPEED_SPEED,R.string.t800_test_item_intervalspeed),
    Hide_IntervalSpeed(T800TestTitleType.INTERVALSPEED_SPEED,R.string.t800_test_item_hide_intervalspeed),
    WarningPoint_LimitedSpeed(T800TestTitleType.WarningPoint,R.string.t800_test_item_wp_speed),
    WarningPoint_1(T800TestTitleType.WarningPoint,R.string.t800_test_item_warning_point_1),
    WarningPoint_2(T800TestTitleType.WarningPoint,R.string.t800_test_item_warning_point_2),
    BIG_WarningPoint_1(T800TestTitleType.WarningPoint,R.string.t800_test_item_big_warning_point),
    WarningPoint_1_T(T800TestTitleType.WarningPoint,R.string.t800_test_item_warning_point_1_t),
    WarningPoint_1_B(T800TestTitleType.WarningPoint,R.string.t800_test_item_warning_point_1_b),
    WarningPoint_2_T(T800TestTitleType.WarningPoint,R.string.t800_test_item_warning_point_2_t),
    WarningPoint_2_B(T800TestTitleType.WarningPoint,R.string.t800_test_item_warning_point_2_b),

    WarningPoint_PASS_SHOW_1(T800TestTitleType.WarningPoint,R.string.t800_test_item_warning_point_pass_1),
    WarningPoint_PASS_SHOW_2(T800TestTitleType.WarningPoint,R.string.t800_test_item_warning_point_pass_2),
    WarningPoint_PASS_SHOW_3(T800TestTitleType.WarningPoint,R.string.t800_test_item_warning_point_pass_3),

    WarningPoint_PASS_SHOW_4(T800TestTitleType.WarningPoint,R.string.t800_test_item_warning_point_pass_4),
    WarningPoint_PASS_HIDE(T800TestTitleType.WarningPoint,R.string.t800_test_item_warning_point_pass_hide),
    ReachInfo_1(T800TestTitleType.REACH,R.string.t800_test_item_reachinfo_1),
    ReachInfo_2(T800TestTitleType.REACH,R.string.t800_test_item_reachinfo_2),

    LaneInformation_new_or_old(T800TestTitleType.LANE,R.string.t800_test_item_laneinformation_set_new_or_old),
    LaneInformation_hide(T800TestTitleType.LANE,R.string.t800_test_item_laneinformation_hide),
    LaneInformation_0(T800TestTitleType.LANE,R.string.t800_test_item_laneinformation_0),
    LaneInformation_1(T800TestTitleType.LANE,R.string.t800_test_item_laneinformation_1),
    LaneInformation_2(T800TestTitleType.LANE,R.string.t800_test_item_laneinformation_2),
    LaneInformation_3(T800TestTitleType.LANE,R.string.t800_test_item_laneinformation_3),
    LaneInformation_4(T800TestTitleType.LANE,R.string.t800_test_item_laneinformation_4),
    LaneInformation_5(T800TestTitleType.LANE,R.string.t800_test_item_laneinformation_5),
    LaneInformation_6(T800TestTitleType.LANE,R.string.t800_test_item_laneinformation_6),
    LaneInformation_7(T800TestTitleType.LANE,R.string.t800_test_item_laneinformation_7),
    LaneInformation_8(T800TestTitleType.LANE,R.string.t800_test_item_laneinformation_8),
//    LaneInformation_hi_pass_auto_l(T800TestTitleType.HIGH_LANE,R.string.t800_test_item_laneinformation_hi_pass_auto_l),
    LaneInformation_hi_pass_1(T800TestTitleType.HIGH_LANE,R.string.t800_test_item_laneinformation_hi_pass_1),
    LaneInformation_hi_pass_2(T800TestTitleType.HIGH_LANE,R.string.t800_test_item_laneinformation_hi_pass_2),
    TurnType_new_or_old(T800TestTitleType.TRUN,R.string.t800_test_item_trun_type_set_new_or_old),
    TurnType_1(T800TestTitleType.TRUN,R.string.t800_test_item_trun_type_1),
    TurnType_2(T800TestTitleType.TRUN,R.string.t800_test_item_trun_type_2),
    TurnType_3(T800TestTitleType.TRUN,R.string.t800_test_item_trun_type_3),

    BIG_TurnType_SHOW(T800TestTitleType.TRUN,R.string.t800_test_item_big_trun_type_show),

    BIG_TurnType_HIDE(T800TestTitleType.TRUN,R.string.t800_test_item_big_trun_type_hide),

    TURN_MSG_SHOW(T800TestTitleType.TRUN,R.string.t800_test_item_trun_msg_show),
    TURN_MSG_HIDE(T800TestTitleType.TRUN,R.string.t800_test_item_trun_msg_hide),
    NEXT_ROAD_NAME(T800TestTitleType.ROAD,R.string.t800_test_item_next_road_name),
    NOW_ROAD_NAME_HIDE(T800TestTitleType.ROAD,R.string.t800_test_item_now_road_hide),
    NOW_ROAD_NAME_1(T800TestTitleType.ROAD,R.string.t800_test_item_now_road_name_1),
    NOW_ROAD_NAME_2(T800TestTitleType.ROAD,R.string.t800_test_item_now_road_name_2),
    NOW_ROAD_NAME_3(T800TestTitleType.ROAD,R.string.t800_test_item_now_road_name_3),
    NOW_ROAD_NAME_4(T800TestTitleType.ROAD,R.string.t800_test_item_now_road_name_4),
    gps_0(T800TestTitleType.GPS,R.string.t800_test_item_gps_1),
    gps_1(T800TestTitleType.GPS,R.string.t800_test_item_gps_2),
    gps_2(T800TestTitleType.GPS,R.string.t800_test_item_gps_3),
    SET_GPS(T800TestTitleType.GPS,R.string.t800_test_item_gps_speed_set),
    que_GPS(T800TestTitleType.GPS,R.string.t800_test_item_gps_speed_que),
    Brightness_1(T800TestTitleType.Brightness,R.string.t800_test_item_brightness_1),
    Brightness_2(T800TestTitleType.Brightness,R.string.t800_test_item_brightness_2),
    LuminancePercent(T800TestTitleType.Brightness,R.string.t800_test_item_luminance_percent),
    que_Brightness(T800TestTitleType.Brightness,R.string.t800_test_item_brightness_que),
    daylight_Statu_open(T800TestTitleType.Brightness,R.string.t800_test_item_brightness_daylight_statu_open),
    daylight_Statu_close(T800TestTitleType.Brightness,R.string.t800_test_item_brightness_daylight_statu_close),
    que_SN(T800TestTitleType.SET,R.string.t800_test_item_sn_que),
    rewrite_SN(T800TestTitleType.SET,R.string.t800_test_item_sn_write),
    que_DeviceVersion(T800TestTitleType.SET,R.string.t800_test_item_que_device_version),
    factory_set(T800TestTitleType.SET,R.string.t800_test_item_factory_set),
    SET_BLE_DATA_NAME(T800TestTitleType.SET,R.string.t800_test_item_set_ble_name),
    SET_BLE_TWS_NAME(T800TestTitleType.SET,R.string.t800_test_item_set_tws_name),
    SET_SOUND(T800TestTitleType.SOUND,R.string.t800_test_item_sound_set),
    que_SOUND(T800TestTitleType.SOUND,R.string.t800_test_item_sound_que),
    device_Sound_open(T800TestTitleType.SOUND,R.string.t800_test_item_device_sound_open),
    device_Sound_close(T800TestTitleType.SOUND,R.string.t800_test_item_device_sound_close),
    que_device_Sound_Statu(T800TestTitleType.SOUND,R.string.t800_test_item_device_sound_que),
    send_Image1(T800TestTitleType.SEND_IMAGE,R.string.t800_test_item_image_send_1),
    send_Image2(T800TestTitleType.SEND_IMAGE,R.string.t800_test_item_image_send_2),
    send_Image3(T800TestTitleType.SEND_IMAGE,R.string.t800_test_item_image_send_3),
    send_Image4(T800TestTitleType.SEND_IMAGE,R.string.t800_test_item_image_send_4),
    send_Image5(T800TestTitleType.SEND_IMAGE,R.string.t800_test_item_image_send_5),
    show_Image(T800TestTitleType.SEND_IMAGE,R.string.t800_test_item_image_show),
    hide_Image(T800TestTitleType.SEND_IMAGE,R.string.t800_test_item_image_hide),
    hide_Image_bar(T800TestTitleType.SEND_IMAGE,R.string.t800_test_item_bar_hide),

    show_wp_Yellow_Statu(T800TestTitleType.STATU,R.string.t800_test_item_show_yellow_statu_2),

    hide_WP_Yellow_Statu(T800TestTitleType.STATU,R.string.t800_test_item_hide_wp_yellow_statu),

    show_title_Yellow_Statu(T800TestTitleType.STATU,R.string.t800_test_item_show_yellow_statu),

    hide_title_Yellow_Statu(T800TestTitleType.STATU,R.string.t800_test_item_hide_title_yellow_statu),

    hide_Yellow_Statu(T800TestTitleType.STATU,R.string.t800_test_item_hide_yellow_statu),
    Yellow_Statu_STR(T800TestTitleType.STATU,R.string.t800_test_item_yellow_statu_str),
    icon_flicker_open(T800TestTitleType.STATU,R.string.t800_test_item_icon_flicker_open),
    icon_flicker_close(T800TestTitleType.STATU,R.string.t800_test_item_icon_flicker_close),
//    YELLOW_STATU_BJ(T800TestTitleType.STATU,R.string.t800_test_item_set_yellow_statu_bj),
    TURN_BJ(T800TestTitleType.STATU, R.string.t800_test_item_set_trun_bj),
//    SPEEDING_BJ(T800TestTitleType.STATU,R.string.t800_test_item_set_speeding_bj),
    DISPLAY_RECT_1(T800TestTitleType.DISPLAY,R.string.t800_test_item_set_display_rect_init),
    DISPLAY_RECT_2(T800TestTitleType.DISPLAY,R.string.t800_test_item_set_display_rect_top_update),
    DISPLAY_RECT_3(T800TestTitleType.DISPLAY,R.string.t800_test_item_set_display_rect_bottom_update),
    NOTIFICTION_MSG_1(T800TestTitleType.DISPLAY,R.string.t800_test_item_set_display_notifiction_1),
    NOTIFICTION_MSG_2(T800TestTitleType.DISPLAY,R.string.t800_test_item_set_display_notifiction_2),
    NOTIFICTION_MSG_HIDE(T800TestTitleType.DISPLAY,R.string.t800_test_item_set_display_notifiction_hide),


    NOTIFICTION_ICON(T800TestTitleType.DISPLAY,R.string.t800_test_item_set_display_notifiction_icon),

    FACTORY_TEST(T800TestTitleType.DISPLAY,R.string.t800_test_item_set_factory_test),
    ;
    private T800TestTitleType titleType;
    private int titleRsId;
    private T800TestDataType(T800TestTitleType titleType,int titleRsId){
        this.titleType=titleType;
        this.titleRsId=titleRsId;
    }


    public int getTitleRsId() {
        return titleRsId;
    }

    public T800TestTitleType getTitleType() {
        return titleType;
    }



}
