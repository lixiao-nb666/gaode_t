package com.newbee.downloader.event.retrun;//package com.newbee.ble_lib.event.send;




import com.newbee.downloader.bean.NewBeeFileDownLoaderRetrunBean;



public interface NewBeeFileDownLoaderRetrunEventSubject {
    /**
     * 增加订阅者
     *
     * @param observer
     */
    public void attach(NewBeeFileDownLoaderRetrunEventObserver observer);

    /**
     * 删除订阅者
     *
     * @param observer
     */
    public void detach(NewBeeFileDownLoaderRetrunEventObserver observer);

    public void completed(NewBeeFileDownLoaderRetrunBean retrunBean);

    public void progress(NewBeeFileDownLoaderRetrunBean retrunBean, int progress, int soFarBytes, int totalBytes);

    public void err(NewBeeFileDownLoaderRetrunBean retrunBean, Throwable e);

    public void statu();
}
