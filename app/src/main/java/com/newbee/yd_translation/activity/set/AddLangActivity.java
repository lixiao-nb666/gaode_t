package com.newbee.yd_translation.activity.set;



import com.newbee.bulid_lib.mybase.activity.util.ActivityManager;
import com.newbee.translation_ui_lib.activity.base.BaseAddOrDelectLangActivity;
import com.newbee.translation_ui_lib.bean.ShowInfoBean;

import com.newbee.yd_translation.activity.NrmywYdTranslationActivity;
import com.nrmyw.gaode_t.R;
import com.youdao.tools.type.YdLangType;
import com.youdao.tools.util.YdLangUtil;
import java.util.ArrayList;
import java.util.List;

public class AddLangActivity extends BaseAddOrDelectLangActivity {
    @Override
    public List<Object> getList() {
        List<Object> list=new ArrayList<>();
        for(YdLangType ydLangType: YdLangUtil.getInstance().getHideLangList()){
            list.add(ydLangType);
        }
        return list;
    }

    @Override
    public void nowSelectNeedTodo(int i, Object o) {
        YdLangType ydLangType= (YdLangType) o;
        ydLangType.setNeedHide(false);
        YdLangUtil.getInstance().startCheck();
        showToast(activityTitleStr()+":("+ydLangType.getStr()+"), ok !");
        ActivityManager.getInstance().finishAllActivity();
        toActivity(NrmywYdTranslationActivity.class);
    }

    @Override
    public String activityTitleStr() {
        return getRsString(R.string.add_language);
    }

    @Override
    public String nowShareStr() {
        return "AddLang";
    }

    @Override
    public Class returnToClass() {
        return SetActivity.class;
    }

    @Override
    public ShowInfoBean getShowInfo(Object object) {
        if(null==object){
            return null;
        }
        YdLangType ydLangType= (YdLangType) object;
        ShowInfoBean showInfoBean=new ShowInfoBean();
        showInfoBean.setContent(ydLangType.getStr());
        return showInfoBean;
    }
}
