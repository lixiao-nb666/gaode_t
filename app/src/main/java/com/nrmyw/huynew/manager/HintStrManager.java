package com.nrmyw.huynew.manager;

import android.text.TextUtils;

import com.newbee.bulid_lib.mybase.share.MyShare;
import com.newbee.gson_lib.gson.MyGson;
import com.nrmyw.huynew.bean.HintStrShowBean;
import com.nrmyw.huynew.bean.ResultHintStrShowBean;

public class HintStrManager {
    private final String tag="HintStrManager";
    private static HintStrManager hintStrManager;
    private ResultHintStrShowBean resultHintStrShowBean;
    private HintStrManager(){
        String shareStr=MyShare.getInstance().getString(tag);
        try {
            if(TextUtils.isEmpty(shareStr)){
                resultHintStrShowBean=new ResultHintStrShowBean();
            }else {
                resultHintStrShowBean= MyGson.getInstance().fromJson(shareStr,ResultHintStrShowBean.class);

            }
        } catch (Exception e) {
            resultHintStrShowBean=new ResultHintStrShowBean();
        }

    }

    public static HintStrManager getInstance(){
        if(null==hintStrManager){
            synchronized (HintStrManager.class){
                if(null==hintStrManager){
                    hintStrManager=new HintStrManager();
                }
            }
        }
        return hintStrManager;
    }

    public ResultHintStrShowBean getResultHintStrShowBean() {
        if(null==resultHintStrShowBean){
            resultHintStrShowBean=new ResultHintStrShowBean();
        }
        return resultHintStrShowBean;
    }

    public void putHintStr(HintStrShowBean hintStrShowBean){
        getResultHintStrShowBean().add(hintStrShowBean);
        MyShare.getInstance().putString(tag,MyGson.getInstance().toGsonStr(getResultHintStrShowBean()));
    }

    public HintStrShowBean getIndexBean(int index){
        try {
            String key=getResultHintStrShowBean().getHintStrShowBeanMap().keySet().toArray()[index].toString();
            return getResultHintStrShowBean().getHintStrShowBeanMap().get(key);

        }catch (Exception e){
            return new HintStrShowBean();
        }
    }
}
