package com.nrmyw.gaode_t.activity.t800;




import com.nrmyw.gaode_t.R;

import java.util.ArrayList;
import java.util.List;

public enum T800TestTitleType {
    MQ(R.string.t800_test_title_mq),
    TIME(R.string.t800_test_title_time),
    SPEED(R.string.t800_test_title_speed , R.string.t800_test_title_speed_body),
    INTERVALSPEED_SPEED(R.string.t800_test_title_intercalspeed, R.string.t800_test_title_intercalspeed_body),
    SPEEDING(R.string.t800_test_title_speeding, R.string.t800_test_title_speeding_body),
    WarningPoint(R.string.t800_test_title_warning_point , R.string.t800_test_title_warning_point_body),
    REACH(R.string.t800_test_title_reach , R.string.t800_test_title_reach_body),
    LANE(R.string.t800_test_title_lane,R.string.t800_test_title_lane_body),
    HIGH_LANE(R.string.t800_test_title_high_lane),
    TRUN(R.string.t800_test_title_trun,R.string.t800_test_title_trun_body),
    ROAD(R.string.t800_test_title_road, R.string.t800_test_title_road_body),
    GPS(R.string.t800_test_title_gps,R.string.t800_test_title_gps_body),
    Brightness(R.string.t800_test_title_brightness,R.string.t800_test_title_brightness_body),
    SOUND(R.string.t800_test_title_sound),
    SET(R.string.t800_test_title_set,R.string.t800_test_title_set_body),
    SEND_IMAGE(R.string.t800_test_title_send_image,R.string.t800_test_title_send_image_body),
    STATU(R.string.t800_test_title_statu,R.string.t800_test_title_statu_body),
    DISPLAY(R.string.t800_test_title_display,R.string.t800_test_title_display_body),

    ;
    private int titleRsId;
    private int tsStrRsId;
    private T800TestTitleType(int titleRsId){
        this.titleRsId=titleRsId;
    }

    private T800TestTitleType(int titleRsId,int tsStrRsId){

        this.titleRsId=titleRsId;
        this.tsStrRsId=tsStrRsId;
    }

    public int getTitleRsId() {
        return titleRsId;
    }

    public int getTsStrRsId() {
        return tsStrRsId;
    }

    public List<T800TestDataType> getNeedDataTypeList(){
        List<T800TestDataType> dataTypeList=new ArrayList<>();
        for(T800TestDataType dataType:T800TestDataType.values()){
            if(dataType.getTitleType()==this){
                dataTypeList.add(dataType);
            }
        }
        return dataTypeList;
    }
}
