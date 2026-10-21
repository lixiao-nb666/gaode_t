package com.newbee.yd_translation.activity.lang;


import android.util.Log;

import com.newbee.bulid_lib.mybase.activity.util.ActivityManager;

import com.newbee.translation_ui_lib.activity.base.BaseSelectLangActivity;
import com.newbee.translation_ui_lib.adapter.BaseNewBeeSelectToAdapter;

import com.newbee.yd_translation.activity.NrmywYdTranslationActivity;

import com.newbee.yd_translation.adapter.NewBeeSelectToAdapter;
import com.nrmyw.gaode_t.R;
import com.youdao.bean.YdLangCountBean;
import com.youdao.tools.config.YdConfig;
import com.youdao.tools.type.YdLangType;
import com.youdao.tools.util.YdLangUtil;
import java.util.ArrayList;
import java.util.List;

public class FromLangActivity extends BaseSelectLangActivity {


//    @Override
//    public List<Object> getList() {
//        List<Object> list=new ArrayList<>();
//        list.addAll(YdLangUtil.getYdLangCountlist());
//
//        for(int i = 0; i< YdLangType.values().length; i++){
//            YdLangType wsLanguage=YdLangType.values()[i];
////            String a=wsLanguage.getLang();
////            String b=wsLanguage.name();
////            String c=b.replace("_","-");
////            if(!a.equals(c)){
////                return list;
////            }
//            if(YdLangUtil.checkIsExit(wsLanguage)){
//                continue;
//            }
//            if(wsLanguage.isNeedHide()){
//                continue;
//            }
//            list.add(wsLanguage);
////            if(!YdLangUtil.checkIsExit(wsLanguage)){
////
//////                numb++;
////            }
//        }
////        Log.i("kankanduibudui","numb is ok:"+list.size());
//        return list;
//    }

    @Override
    public List<Object> getList() {
        List<Object> list =new ArrayList<>();
        for(YdLangCountBean ydLangCountBean: YdLangUtil.getInstance().getShowLangCountMap().values()){
            list.add(ydLangCountBean);
            Log.i(tag,"kankanshenmejibawanyi1:"+ydLangCountBean);
        }
        return list;
    }

    @Override
    public String activityTitleStr() {
        return getRsString(R.string.recognition_language_selection);
    }


    @Override
    public void nowSelectNeedTodo(int index, Object object) {
//        if(object instanceof YdLangCount){
//            YdLangCount ydLangCount= (YdLangCount) object;
//            toActivity(FromLangMoreActivity.class,ydLangCount.ordinal()+"");
//            finish();
//        }else if(object instanceof YdLangType){
//            YdLangType ydLangType= (YdLangType) object;
//            YdConfig.getInstance().setFromType(ydLangType);
//            ActivityManager.getInstance().finishAllActivity();
//            toActivity(NrmywYdTranslationActivity.class);
//        }else {
//            showToast(object.toString());
//            finish();
//        }
        if (object instanceof YdLangCountBean) {
            YdLangCountBean ydLangCountBean = (YdLangCountBean) object;
            if (ydLangCountBean.getLangTypeList().size() == 1) {
                YdLangType ydLangType = ydLangCountBean.getLangTypeList().get(0);
                YdConfig.getInstance().setFromType(ydLangType);
                ActivityManager.getInstance().finishAllActivity();
                toActivity(NrmywYdTranslationActivity.class);
            } else if (ydLangCountBean.getLangTypeList().size() > 1) {

                toActivity(FromLangMoreActivity.class, ydLangCountBean.getTitle());
                finish();
            }
        } else {
            showToast(object.toString());
            finish();
        }
    }

    @Override
    public String nowShareStr() {
        return "from";
    }


    @Override
    public BaseNewBeeSelectToAdapter getAdapter() {
        return new NewBeeSelectToAdapter(context, "");
    }

    @Override
    public Class returnToClass() {
        return NrmywYdTranslationActivity.class;
    }


}
