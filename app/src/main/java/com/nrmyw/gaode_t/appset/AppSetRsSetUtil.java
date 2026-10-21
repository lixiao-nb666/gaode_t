package com.nrmyw.gaode_t.appset;

import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.app.MyApp;

public class AppSetRsSetUtil {


    public static int getServiceIcon(){
        return R.drawable.icon_hud_launcher;
    }

    public static String getServiceTitle(){
        return MyApp.getRsString(R.string.hud_title);
    }

    public static String getServiceTitleContent(){
        return MyApp.getRsString(R.string.hud_title_content);
    }

}
