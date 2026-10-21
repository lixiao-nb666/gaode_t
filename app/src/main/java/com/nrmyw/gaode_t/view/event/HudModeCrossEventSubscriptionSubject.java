package com.nrmyw.gaode_t.view.event;



import java.util.ArrayList;
import java.util.List;


public class HudModeCrossEventSubscriptionSubject implements HudModeCrossEventSubject{

    private List<HudModeCrossEventObserver> observers;
    private static HudModeCrossEventSubscriptionSubject subscriptionSubject;

    private HudModeCrossEventSubscriptionSubject() {
        observers = new ArrayList<>();
    }

    public static HudModeCrossEventSubscriptionSubject getInstence() {
        if (subscriptionSubject == null) {
            synchronized (HudModeCrossEventSubscriptionSubject.class) {
                if (subscriptionSubject == null)
                    subscriptionSubject = new HudModeCrossEventSubscriptionSubject();
            }
        }
        return subscriptionSubject;

    }






    @Override
    public void attach(HudModeCrossEventObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(HudModeCrossEventObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void setBitmapBytes(byte[] bitmapBytes) {
        for(HudModeCrossEventObserver observer:observers){
            observer.setBitmapBytes(bitmapBytes);
        }
    }
}
