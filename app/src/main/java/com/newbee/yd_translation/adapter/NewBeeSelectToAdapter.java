package com.newbee.yd_translation.adapter;

import android.content.Context;
import android.text.TextUtils;


import com.newbee.translation_ui_lib.adapter.BaseNewBeeSelectToAdapter;
import com.newbee.translation_ui_lib.bean.ShowInfoBean;

import com.youdao.bean.YdLangCountBean;
import com.youdao.tools.type.YdLangType;


public class NewBeeSelectToAdapter extends BaseNewBeeSelectToAdapter {

    public NewBeeSelectToAdapter(Context context,  String nowStr) {
            super(context,nowStr);
    }

    @Override
    public ShowInfoBean getShowInfo(Object object) {
        if(null==object){
            return null;
        }

        ShowInfoBean showInfoBean=new ShowInfoBean();
        if(object instanceof YdLangCountBean){
            YdLangCountBean ydLangCount= (YdLangCountBean) object;
            useCountSetShowInfo(showInfoBean,ydLangCount);
        }else if(object instanceof YdLangType){
            YdLangType ydLangType= (YdLangType) object;
            useTypeSetShowInfo(showInfoBean,ydLangType);
        }else {
            showInfoBean.setTitle(object.toString());
        }
        return showInfoBean;
    }



    private void useCountSetShowInfo(ShowInfoBean showInfoBean,YdLangCountBean ydLangCount){

        for(YdLangType ydLangType:ydLangCount.getLangTypeList()){
            useTypeSetShowInfo(showInfoBean,ydLangType);

        }

    }

    private void useTypeSetShowInfo(ShowInfoBean showInfoBean, YdLangType ydLangType){

        String str=ydLangType.getStr();
        String[] ss=  str.split("-");
        if(null==ss||ss.length==0){
            return;
        }
        if(TextUtils.isEmpty(showInfoBean.getTitle())){
            showInfoBean.setTitle(ss[0]);
        }
        if(ss.length>1){

            String needStr=" ("+ss[1]+") ";
            if(TextUtils.isEmpty(showInfoBean.getContent())){
                showInfoBean.setContent(needStr);
            }else {
                showInfoBean.setContent(showInfoBean.getContent()+needStr);
            }
        }

    }

}