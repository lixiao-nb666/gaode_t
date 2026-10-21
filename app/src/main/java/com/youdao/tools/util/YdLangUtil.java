package com.youdao.tools.util;



import android.util.Log;
import com.youdao.bean.YdLangCountBean;

import com.youdao.tools.type.YdLangType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class YdLangUtil {
    private static YdLangUtil ydLangUtil;
    private YdLangUtil(){}

    public static YdLangUtil getInstance(){
        if(null==ydLangUtil){
            synchronized (YdLangUtil.class){
                if(null==ydLangUtil){
                    ydLangUtil=new YdLangUtil();
                }
            }
        }
        return ydLangUtil;
    }

    private Map<String, YdLangCountBean> showLangCountMap=new LinkedHashMap<>();
    private List<YdLangType> hideLangList=new ArrayList<>();

    public void startCheck(){
        showLangCountMap.clear();
        hideLangList.clear();
        for(YdLangType ydLangType:YdLangType.values()){
            if(ydLangType.isNeedHide()){
                hideLangList.add(ydLangType);
            }else {
                String langStr=ydLangType.getLang();
                if(langStr.equals("enzh")){
                    putInMap("zh",ydLangType);
                }else {
                    String[] langSS=langStr.split("-");
                    if(langSS.length>1){
                        putInMap(langSS[0],ydLangType);
                    }else if(langSS.length==1){
                        putInMap(langSS[0],ydLangType);
                    }
                }
            }
        }
    }

    private void putInMap(String mapKey, YdLangType ydLangType){
        YdLangCountBean ydLangCountBean=showLangCountMap.get(mapKey);
        if(null==ydLangCountBean){
            ydLangCountBean=new YdLangCountBean(mapKey);

        }
        ydLangCountBean.add(ydLangType);
        showLangCountMap.put(mapKey,ydLangCountBean);
        Log.i("shenmewanyi","kankanshenmejibawanyi2:"+showLangCountMap);
    }

    public Map<String, YdLangCountBean> getShowLangCountMap() {
        return showLangCountMap;
    }

    public void setShowLangCountMap(Map<String, YdLangCountBean> showLangCountMap) {
        this.showLangCountMap = showLangCountMap;
    }

    public List<YdLangType> getHideLangList() {
        return hideLangList;
    }

    public void setHideLangList(List<YdLangType> hideLangList) {
        this.hideLangList = hideLangList;
    }


//    public static boolean checkIsExit(YdLangType ydLangType){
//        String checkName=ydLangType.name();
//        for(YdLangCount ydLangCount:YdLangCount.values()){
//            if(checkName.contains(ydLangCount.name())){
//                return true;
//            }
//            for(YdLangType checkType:ydLangCount.getTypes()){
//                if(ydLangType==checkType){
//                    return true;
//                }
//            }
//
//        }
//        return false;
//
//    }



//    public static List<Object> getYdLangCountlist(){
////        private int numb;
//        List<Object> list=new ArrayList<>();
//        for(YdLangCount ydLangCount:YdLangCount.values()){
//            list.add(ydLangCount);
////            numb+=ydLangCount.getTypes().length;
//        }
//
//        return list;
//    }
}
