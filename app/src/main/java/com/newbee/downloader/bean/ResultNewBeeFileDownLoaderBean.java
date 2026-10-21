package com.newbee.downloader.bean;

import android.text.TextUtils;
import android.util.ArrayMap;

import com.nrmyw.gaode_t.util.PermissionUtils;

import java.util.HashMap;
import java.util.Map;


public class ResultNewBeeFileDownLoaderBean {
    private Map<String,NewBeeFileDownLoaderBean> map;

    public ResultNewBeeFileDownLoaderBean(){
        map=new ArrayMap<>();
    }

    public Map<String, NewBeeFileDownLoaderBean> getMap() {
        if(null==map){
            map=new HashMap<>();
        }
        return map;
    }

    public void setMap(Map<String, NewBeeFileDownLoaderBean> map) {
        this.map = map;
    }

    public synchronized void add(NewBeeFileDownLoaderBean downLoaderBean){
        if(null==downLoaderBean|| TextUtils.isEmpty(downLoaderBean.getFileUrl())){
            //如果URL是空的，就直接返回了，URL为空TAG一定不为空
            return;
        }
        if(null==map){
            map=new HashMap<>();
        }
        map.put(downLoaderBean.getTag(),downLoaderBean);

    }
}
