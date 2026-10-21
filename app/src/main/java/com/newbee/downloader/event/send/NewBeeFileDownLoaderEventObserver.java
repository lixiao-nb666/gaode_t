package com.newbee.downloader.event.send;//package com.newbee.ble_lib.event.send;



import com.newbee.downloader.bean.NewBeeFileDownLoaderBean;
import com.newbee.downloader.bean.ResultNewBeeFileDownLoaderBean;
import com.nrmyw.ble_event_lib.bean.BleDeviceBean;
import com.nrmyw.ble_event_lib.bean.BleSendFileInfoBean;
import com.nrmyw.ble_event_lib.bean.BleSendImageInfoBean;
import com.nrmyw.ble_event_lib.bean.BleSendOtaInfoBean;


/**
 * Created by lixiao on 2017/5/12.
 * about:
 */

public interface NewBeeFileDownLoaderEventObserver {

    public void start(NewBeeFileDownLoaderBean newBeeFileDownLoaderBean);

    public void start(ResultNewBeeFileDownLoaderBean resultNewBeeFileDownLoaderBean);

    public void pause(int taskId);





}
