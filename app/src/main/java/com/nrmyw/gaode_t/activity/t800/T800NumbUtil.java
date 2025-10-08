package com.nrmyw.gaode_t.activity.t800;

public class T800NumbUtil {

    public static String[] getNeedNumb(int start,int end){
        String[] ss=new String[end-start+1];

        for(int i=0;i<ss.length;i++){
            ss[i]=(start+i)+"";
        }
        return ss;
    }
}
