package com.newbee.yd_translation.activity.lang;


import android.util.Log;

import com.newbee.bulid_lib.mybase.activity.util.ActivityManager;

import com.newbee.translation_ui_lib.activity.base.BaseSelectLangActivity;
import com.newbee.translation_ui_lib.adapter.BaseNewBeeSelectToAdapter;

import com.newbee.yd_translation.activity.NrmywYdTranslationActivity;
import com.newbee.yd_translation.adapter.NewBeeSelectToMoreAdapter;
import com.nrmyw.gaode_t.R;
import com.youdao.bean.YdLangCountBean;
import com.youdao.tools.config.YdConfig;
import com.youdao.tools.type.YdLangType;
import com.youdao.tools.util.YdLangUtil;

import java.util.ArrayList;
import java.util.List;

public class FromLangMoreActivity extends BaseSelectLangActivity {
    private YdLangCountBean ydLangCount;
    @Override
    public void initView() {
        try {
            String intentString=getIntentString();
            ydLangCount= YdLangUtil.getInstance().getShowLangCountMap().get(intentString);
        }catch (Exception e){
        }
        Log.i("kankanshenmjibawanyi","kankanshenmejibawanyi11:"+ydLangCount);
        if(null==ydLangCount||ydLangCount.getLangTypeList().size()==0){
            finish();
            return;
        }
        super.initView();
    }

    @Override
    public List<Object> getList() {
        List<Object> list=new ArrayList<>();
        for(int i = 0; i< ydLangCount.getLangTypeList().size(); i++){
            YdLangType wsLanguage=ydLangCount.getLangTypeList().get(i);
            if(wsLanguage.isNeedHide()){
                continue;
            }
            list.add(wsLanguage);
        }
        return list;
    }

    @Override
    public String activityTitleStr() {
        return getRsString(R.string.recognition_language_selection);
    }


    @Override
    public void nowSelectNeedTodo(int index,Object object) {
      if(object instanceof YdLangType){
            YdLangType ydLangType= (YdLangType) object;
            YdConfig.getInstance().setFromType(ydLangType);
            ActivityManager.getInstance().finishAllActivity();
            toActivity(NrmywYdTranslationActivity.class);
        }else {
            showToast(object.toString());
            finish();
        }


    }

    @Override
    public String nowShareStr() {
        return "from"+ydLangCount.getTitle();
    }



    @Override
    public BaseNewBeeSelectToAdapter getAdapter() {
        return new NewBeeSelectToMoreAdapter(context,"");
    }

    @Override
    public Class returnToClass() {
        return FromLangActivity.class;
    }
}
