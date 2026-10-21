package com.nrmyw.gaode_t.view.event;


public interface HudModeCrossEventSubject {
    /**
     * 增加订阅者
     *
     * @param observer
     */
    public void attach(HudModeCrossEventObserver observer);

    /**
     * 删除订阅者
     *
     * @param observer
     */
    public void detach(HudModeCrossEventObserver observer);

    public void setBitmapBytes (byte[] bitmapBytes);

}
