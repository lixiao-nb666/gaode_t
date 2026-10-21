package com.newbee.downloader.event.send;//package com.newbee.ble_lib.event.send;


import com.newbee.downloader.bean.NewBeeFileDownLoaderBean;
import com.newbee.downloader.bean.ResultNewBeeFileDownLoaderBean;
import com.nrmyw.ble_event_lib.bean.BleDeviceBean;
import com.nrmyw.ble_event_lib.bean.BleSendFileInfoBean;
import com.nrmyw.ble_event_lib.bean.BleSendImageInfoBean;
import com.nrmyw.ble_event_lib.bean.BleSendOtaInfoBean;
import com.nrmyw.ble_event_lib.send.BleEventObserver;
import com.nrmyw.ble_event_lib.send.BleEventSubject;

import java.util.ArrayList;
import java.util.List;


public class NewBeeFileDownLoaderEventSubscriptionSubject implements NewBeeFileDownLoaderEventSubject {

    private List<NewBeeFileDownLoaderEventObserver> observers;
    private static NewBeeFileDownLoaderEventSubscriptionSubject subscriptionSubject;

    private NewBeeFileDownLoaderEventSubscriptionSubject() {
        observers = new ArrayList<>();
    }

    public static NewBeeFileDownLoaderEventSubscriptionSubject getInstance() {
        if (subscriptionSubject == null) {
            synchronized (NewBeeFileDownLoaderEventSubscriptionSubject.class) {
                if (subscriptionSubject == null)
                    subscriptionSubject = new NewBeeFileDownLoaderEventSubscriptionSubject();
            }
        }
        return subscriptionSubject;

    }



    @Override
    public void attach(NewBeeFileDownLoaderEventObserver observer) {
            observers.add(observer);
    }

    @Override
    public void detach(NewBeeFileDownLoaderEventObserver observer) {
            observers.remove(observer);
    }

    @Override
    public void start(NewBeeFileDownLoaderBean newBeeFileDownLoaderBean) {
        for(NewBeeFileDownLoaderEventObserver observer:observers){
            observer.start(newBeeFileDownLoaderBean);
        }
    }

    @Override
    public void start(ResultNewBeeFileDownLoaderBean resultNewBeeFileDownLoaderBean) {
        for(NewBeeFileDownLoaderEventObserver observer:observers){
            observer.start(resultNewBeeFileDownLoaderBean);
        }
    }

    @Override
    public void pause(int taskId) {
        for(NewBeeFileDownLoaderEventObserver observer:observers){
            observer.pause(taskId);
        }
    }
}
