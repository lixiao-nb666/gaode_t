package com.newbee.downloader.event.retrun;//package com.newbee.ble_lib.event.send;



import com.newbee.downloader.bean.NewBeeFileDownLoaderBean;
import com.newbee.downloader.bean.NewBeeFileDownLoaderRetrunBean;
import com.newbee.downloader.bean.ResultNewBeeFileDownLoaderBean;


/**
 * Created by lixiao on 2017/5/12.
 * about:
 */

public interface NewBeeFileDownLoaderRetrunEventObserver {

    public void completed(NewBeeFileDownLoaderRetrunBean retrunBean);

    public void progress(NewBeeFileDownLoaderRetrunBean retrunBean,int progress,int soFarBytes, int totalBytes);

    public void err(NewBeeFileDownLoaderRetrunBean retrunBean,Throwable e);

    public void statu();


}
