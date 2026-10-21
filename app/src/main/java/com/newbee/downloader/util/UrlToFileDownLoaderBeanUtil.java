package com.newbee.downloader.util;

import android.os.Environment;

import com.newbee.downloader.bean.NewBeeFileDownLoaderBean;

import java.io.File;

public class UrlToFileDownLoaderBeanUtil {
    public static final String pack_name = "lhcx";
    public static final String S_APK_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + pack_name;// 总文件夹
    public static NewBeeFileDownLoaderBean getDownLoaderBean(String url){
        NewBeeFileDownLoaderBean newBeeFileDownLoaderBean=new NewBeeFileDownLoaderBean();
        newBeeFileDownLoaderBean.setFileUrl(url);
        int index = url.lastIndexOf("/");
        if (index != -1) {
            String fileName=url.substring(index);
            newBeeFileDownLoaderBean.setFilePath(S_APK_PATH+fileName);
        }
        return newBeeFileDownLoaderBean;


    }




}
