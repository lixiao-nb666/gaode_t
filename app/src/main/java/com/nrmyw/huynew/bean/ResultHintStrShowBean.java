package com.nrmyw.huynew.bean;

import android.util.ArrayMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultHintStrShowBean implements Serializable {
    private Map<String,HintStrShowBean> hintStrShowBeanMap;

    public ResultHintStrShowBean(){
        hintStrShowBeanMap=new ArrayMap<>();
//        HintStrShowBean hintStrShowBean1=new HintStrShowBean();
//        hintStrShowBean1.setTitleStr("11111");
//        hintStrShowBean1.setId(111);
//        HintStrShowBean hintStrShowBean2=new HintStrShowBean();
//        hintStrShowBean2.setTitleStr("22222");
//        hintStrShowBean2.setId(222);
//        HintStrShowBean hintStrShowBean3=new HintStrShowBean();
//        hintStrShowBean3.setTitleStr("33333");
//        hintStrShowBean3.setId(333);
//        add(hintStrShowBean1);
//        add(hintStrShowBean2);
//        add(hintStrShowBean3);
    }
    public Map<String, HintStrShowBean> getHintStrShowBeanMap() {
        if(null==hintStrShowBeanMap){
            hintStrShowBeanMap=new ArrayMap<>();
        }
        return hintStrShowBeanMap;
    }



    public synchronized void add(HintStrShowBean hintStrShowBean){
        if(null==hintStrShowBeanMap){
            hintStrShowBeanMap=new ArrayMap<>();
        }
        hintStrShowBeanMap.put(hintStrShowBean.getId()+"",hintStrShowBean);
    }

    public synchronized void remove(HintStrShowBean hintStrShowBean){
        if(null==hintStrShowBeanMap){
            hintStrShowBeanMap=new ArrayMap<>();
            return;
        }
        hintStrShowBeanMap.remove(hintStrShowBean.getId()+"");
    }

    public synchronized void remove(String keyStr){
        if(null==hintStrShowBeanMap){
            hintStrShowBeanMap=new ArrayMap<>();
            return;
        }
        hintStrShowBeanMap.remove(keyStr);
    }

}
