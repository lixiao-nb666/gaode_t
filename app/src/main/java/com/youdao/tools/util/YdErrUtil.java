package com.youdao.tools.util;




import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.app.MyApp;

public class YdErrUtil {

    public static String getErrStr(int errCode){
        if(errCode==-2){
            return MyApp.getRsString(R.string.err_net);
        }else {
            return MyApp.getRsString(R.string.err_code);
        }
    }
}
