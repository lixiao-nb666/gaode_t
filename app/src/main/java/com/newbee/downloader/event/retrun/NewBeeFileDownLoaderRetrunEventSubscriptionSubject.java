package com.newbee.downloader.event.retrun;//package com.newbee.ble_lib.event.send;


import com.newbee.downloader.bean.NewBeeFileDownLoaderBean;
import com.newbee.downloader.bean.NewBeeFileDownLoaderRetrunBean;
import com.newbee.downloader.bean.ResultNewBeeFileDownLoaderBean;

import java.util.ArrayList;
import java.util.List;


public class NewBeeFileDownLoaderRetrunEventSubscriptionSubject implements NewBeeFileDownLoaderRetrunEventSubject {

    private List<NewBeeFileDownLoaderRetrunEventObserver> observers;
    private static NewBeeFileDownLoaderRetrunEventSubscriptionSubject subscriptionSubject;

    private NewBeeFileDownLoaderRetrunEventSubscriptionSubject() {
        observers = new ArrayList<>();
    }

    public static NewBeeFileDownLoaderRetrunEventSubscriptionSubject getInstance() {
        if (subscriptionSubject == null) {
            synchronized (NewBeeFileDownLoaderRetrunEventSubscriptionSubject.class) {
                if (subscriptionSubject == null)
                    subscriptionSubject = new NewBeeFileDownLoaderRetrunEventSubscriptionSubject();
            }
        }
        return subscriptionSubject;

    }



    @Override
    public void attach(NewBeeFileDownLoaderRetrunEventObserver observer) {
            observers.add(observer);
    }

    @Override
    public void detach(NewBeeFileDownLoaderRetrunEventObserver observer) {
            observers.remove(observer);
    }

    @Override
    public void completed(NewBeeFileDownLoaderRetrunBean retrunBean) {
        for(NewBeeFileDownLoaderRetrunEventObserver observer:observers){
            observer.completed(retrunBean);
        }
    }

    @Override
    public void progress(NewBeeFileDownLoaderRetrunBean retrunBean, int progress, int soFarBytes, int totalBytes) {
        for(NewBeeFileDownLoaderRetrunEventObserver observer:observers){
            observer.progress(retrunBean,progress,soFarBytes,totalBytes);
        }
    }

    @Override
    public void err(NewBeeFileDownLoaderRetrunBean retrunBean, Throwable e) {
        for(NewBeeFileDownLoaderRetrunEventObserver observer:observers){
            observer.err(retrunBean,e);
        }
    }

    @Override
    public void statu() {
        for(NewBeeFileDownLoaderRetrunEventObserver observer:observers){
            observer.statu();
        }
    }

}
