package com.newbee.downloader.bean;

import android.text.TextUtils;

import java.io.Serializable;

public class NewBeeFileDownLoaderBean implements Serializable {

    private String tag;
    private String fileUrl;
    private String filePath;


    public String getTag() {
        if(TextUtils.isEmpty(tag)){
            tag=filePath;
        }
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return "NewBeeFileDownLoaderBean{" +
                "tag='" + tag + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
