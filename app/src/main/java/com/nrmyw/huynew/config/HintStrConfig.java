package com.nrmyw.huynew.config;

import com.nrmyw.huynew.type.HintStrPlayType;

public class HintStrConfig {

    private static HintStrPlayType hintStrPlayType=HintStrPlayType.AUTO;
    private static int PLAY_SPEED=2;
    private static boolean nowIsPlay;

    public static void setPlaySpeed(int setSpeed){
        if(setSpeed<1){
            setSpeed=1;
        }else if(setSpeed>5){
            setSpeed=5;
        }
        PLAY_SPEED=setSpeed;
    }

    public static int getPlaySpeed(){
        if(PLAY_SPEED<1){
            PLAY_SPEED=1;
        }else if(PLAY_SPEED>5){
            PLAY_SPEED=5;
        }
        return PLAY_SPEED;
    }

    public static String getPlaySpeedStr(){
        return getPlaySpeed()+"";
    }

    public static HintStrPlayType getPlayType(){
        if(null==hintStrPlayType){
            hintStrPlayType=HintStrPlayType.AUTO;
        }
        return hintStrPlayType;
    }

    public static void changePlayType(){
        int index=hintStrPlayType.ordinal();
        index++;
        if(index>=HintStrPlayType.values().length){
            index=0;
        }
        hintStrPlayType=HintStrPlayType.values()[index];

    }

    public static void setPlayType(HintStrPlayType setPlayType){
        if(null==setPlayType){
            return;
        }
        hintStrPlayType=setPlayType;
    }

    public static void setNowIsPlay(boolean setNowIsPlay){
        nowIsPlay=setNowIsPlay;
    }

    public static boolean getNowIsPlay(){
        return nowIsPlay;
    }

}
