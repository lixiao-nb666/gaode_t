package com.nrmyw.gaode_t.config;

public class UiConfig {

    public static int w=1080;
    public static int h=1920;
    public static int bottomH;


    public static int getMapViewSetViewH(){
        if(h<=400){
            h=1920;
        }
        int needH=h/20;
        if(bottomH<=0){
            return needH;
        }else {
            return needH<bottomH?needH:bottomH;
        }

    }

}
