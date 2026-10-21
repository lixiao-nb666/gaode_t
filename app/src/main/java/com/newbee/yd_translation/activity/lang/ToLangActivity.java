package com.newbee.yd_translation.activity.lang;


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

public class ToLangActivity extends BaseSelectLangActivity {
    @Override
    public List<Object> getList() {
        List<Object> list =new ArrayList<>();
        for(YdLangCountBean ydLangCountBean: YdLangUtil.getInstance().getShowLangCountMap().values()){
            list.add(ydLangCountBean);
        }
        return list;
    }

    @Override
    public String activityTitleStr() {
        return getString(R.string.translation_language_selection);
    }


    @Override
    public void nowSelectNeedTodo(int index, Object object) {
//        if(object instanceof YdLangCount){
//            YdLangCount ydLangCount= (YdLangCount) object;
//            toActivity(ToLangMoreActivity.class,ydLangCount.ordinal()+"");
//            finish();
//        }else if(object instanceof YdLangType){
//            YdLangType ydLangType= (YdLangType) object;
//            YdConfig.getInstance().setToType(ydLangType);
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
                YdConfig.getInstance().setToType(ydLangType);
                ActivityManager.getInstance().finishAllActivity();
                toActivity(NrmywYdTranslationActivity.class);
            } else if (ydLangCountBean.getLangTypeList().size() > 1) {
                toActivity(ToLangMoreActivity.class,ydLangCountBean.getTitle());
                finish();
            }
        } else {
            showToast(object.toString());
            finish();
        }

    }

    @Override
    public String nowShareStr() {
        return "to";
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
