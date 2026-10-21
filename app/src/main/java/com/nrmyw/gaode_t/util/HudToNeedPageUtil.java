package com.nrmyw.gaode_t.util;

import com.nrmyw.hud_data_lib.type.type.HudStatuType;
import com.nrmyw.hud_data_lib.type.ui.HudUiType;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.type.HudDevice;

public class HudToNeedPageUtil {

    public static void toTranslationPage(){
        HudDevice hudDevice= HudManager.getInstance().getHudDevice();
        if(null==hudDevice){
            return;
        }
        switch (hudDevice){
            case HUYNEW_AR_DEBUG:
            case HUYNEW_AR:
                HudManager.getInstance().getHudEvent().setUiType(HudUiType.TRANSLATION);
                break;
            default:
                HudManager.getInstance().getHudEvent().openOrCloseTranslationUi(HudStatuType.OPEN);
                break;
        }
    }

    public static void toMainPage(){
        HudDevice hudDevice= HudManager.getInstance().getHudDevice();
        if(null==hudDevice){
            return;
        }
        switch (hudDevice){
            case HUYNEW_AR_DEBUG:
            case HUYNEW_AR:
                HudManager.getInstance().getHudEvent().setUiType(HudUiType.MAIN);
                break;
            default:
                HudManager.getInstance().getHudEvent().openOrCloseTranslationUi(HudStatuType.CLOSE);
                break;
        }
    }

    public static void toHintStrPage(){
        HudDevice hudDevice= HudManager.getInstance().getHudDevice();
        if(null==hudDevice){
            return;
        }
        switch (hudDevice){
            case HUYNEW_AR_DEBUG:
            case HUYNEW_AR:
                HudManager.getInstance().getHudEvent().setUiType(HudUiType.TRANSLATION);
                break;
            default:
                HudManager.getInstance().getHudEvent().openOrCloseTranslationUi(HudStatuType.CLOSE);
                break;
        }
    }

}
