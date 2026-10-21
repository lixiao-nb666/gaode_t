package com.nrmyw.huynew.bean;

import java.io.Serializable;

public class HintStrShowBean implements Serializable {
    private long id;
    private String titleStr;
    private String hintStr;
    private long creatTime;
    private long lastChangeTime;
    private int playIndex=-1;

    public HintStrShowBean(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLastChangeTime() {
        return lastChangeTime;
    }

    public void setLastChangeTime(long lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
    }

    public long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
    }

    public String getHintStr() {
        return hintStr;
    }

    public void setHintStr(String hintStr) {
        this.hintStr = hintStr;
        playIndex=-1;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public int getPlayIndex() {
        return playIndex;
    }

    public void setPlayIndex(int playIndex) {
        this.playIndex = playIndex;
    }



    @Override
    public String toString() {
        return "HintStrShowBean{" +
                "id=" + id +
                ", titleStr='" + titleStr + '\'' +
                ", hintStr='" + hintStr + '\'' +
                ", creatTime=" + creatTime +
                ", lastChangeTime=" + lastChangeTime +
                ", playIndex=" + playIndex +
                '}';
    }
}
