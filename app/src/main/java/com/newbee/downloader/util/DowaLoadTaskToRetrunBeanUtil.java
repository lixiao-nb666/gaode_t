package com.newbee.downloader.util;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.newbee.downloader.bean.NewBeeFileDownLoaderRetrunBean;

public class DowaLoadTaskToRetrunBeanUtil {

    public static NewBeeFileDownLoaderRetrunBean getRetrunBean(BaseDownloadTask baseDownloadTask){
        NewBeeFileDownLoaderRetrunBean newBeeFileDownLoaderRetrunBean=new NewBeeFileDownLoaderRetrunBean();
        newBeeFileDownLoaderRetrunBean.setFileName(baseDownloadTask.getFilename());
        newBeeFileDownLoaderRetrunBean.setFilePath(baseDownloadTask.getTargetFilePath());
        newBeeFileDownLoaderRetrunBean.setFileUrl(baseDownloadTask.getUrl());
        newBeeFileDownLoaderRetrunBean.setTag(baseDownloadTask.getTag().toString());
        newBeeFileDownLoaderRetrunBean.setTaskId(baseDownloadTask.getId());
        return newBeeFileDownLoaderRetrunBean;

    }
}
