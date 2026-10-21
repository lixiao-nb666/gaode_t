package com.newbee.downloader.event.send;//package com.newbee.ble_lib.event.send;



import com.newbee.downloader.bean.NewBeeFileDownLoaderBean;
import com.newbee.downloader.bean.ResultNewBeeFileDownLoaderBean;


public interface NewBeeFileDownLoaderEventSubject {
    /**
     * 增加订阅者
     *
     * @param observer
     */
    public void attach(NewBeeFileDownLoaderEventObserver observer);

    /**
     * 删除订阅者
     *
     * @param observer
     */
    public void detach(NewBeeFileDownLoaderEventObserver observer);

    public void start(NewBeeFileDownLoaderBean newBeeFileDownLoaderBean);

    public void start(ResultNewBeeFileDownLoaderBean resultNewBeeFileDownLoaderBean);

    public void pause(int taskId);
}
