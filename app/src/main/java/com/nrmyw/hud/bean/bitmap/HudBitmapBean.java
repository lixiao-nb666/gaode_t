package com.nrmyw.hud.bean.bitmap;

import android.graphics.Bitmap;

import java.io.Serializable;

public class HudBitmapBean implements Serializable {
    private Bitmap bitmap;
    private int type;
    private long lastSendTime;
    private boolean sendStart;


    public void sendBitmap(Bitmap bitmap,int type){
        if(sendStart){

        }
    }



}
