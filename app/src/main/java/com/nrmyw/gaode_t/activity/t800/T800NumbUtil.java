package com.nrmyw.gaode_t.activity.t800;

import com.nrmyw.hud_data_lib.type.warningproint.HudWarningPointType;

public class T800NumbUtil {

    public static String[] getNeedNumb(int start,int end){
        String[] ss=new String[end-start+1];

        for(int i=0;i<ss.length;i++){
            ss[i]=(start+i)+"";
        }
        return ss;
    }



    public static String formatDistance(int distance) {
        if (distance >= 1000) {
            float km = distance / 1000f;
            if (km >= 10) {
                return ((int) km) + "km";
            } else {
                return String.valueOf((int) (km * 10 + 0.5f) / 10f) + "km";
            }
        } else {

            return distance + "m";
        }
    }


}
