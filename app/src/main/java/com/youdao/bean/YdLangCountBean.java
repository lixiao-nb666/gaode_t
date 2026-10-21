package com.youdao.bean;




import com.youdao.tools.type.YdLangType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class YdLangCountBean implements Serializable {
    private String title;
    private List<YdLangType> langTypeList;

    public YdLangCountBean(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<YdLangType> getLangTypeList() {
        if(null==langTypeList){
            langTypeList=new ArrayList<>();
        }

        return langTypeList;
    }

    public void setLangTypeList(List<YdLangType> langTypeList) {
        this.langTypeList = langTypeList;
    }


    public void add(YdLangType ydLangType){
        if(null==langTypeList){
            langTypeList=new ArrayList<>();
        }
        langTypeList.add(ydLangType);
    }

    @Override
    public String toString() {
        return "YdLangCountBean{" +
                "title='" + title + '\'' +
                ", langTypeList=" + langTypeList +
                '}';
    }
}
