package com.newbee.downloader.bean;

import java.io.Serializable;

public class NewBeeFileDownLoaderRetrunBean implements Serializable {
    private String tag;
    private int taskId;
    private String fileName;
    private String filePath;
    private String fileUrl;


    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "NewBeeFileDownLoaderRetrunBean{" +
                "tag='" + tag + '\'' +
                ", taskId=" + taskId +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}
