package com.nrmyw.huynew.type;

public enum HuynewFunctionType {


    AR_SET("眼镜设置","默认开机参数调整"),
    USER_SET("用户设置","用户根据习惯可设置对应参数"),
    TRANSLATION("翻译","支持全球几十种语言模型"),
    HINT_STR("提词"),

    NANI_LANG("导航NAVI语言","支持17种语言导航(" +
                    "简体中文" +
                    ",繁体中文" +
                    ",英文" +
                    ",西班牙语" +
                    ",葡萄牙语" +
                    ",法语" +
                    ",德语" +
                    ",泰语" +
                    ",日语" +
                    ",韩语" +
                    ",土耳其语" +
                    ",希伯来语" +
                    ",意大利语" +
                    ",俄语" +
                    ",马来语" +
                    ",印尼语" +
                    ",越南语)"),
    NANI("导航NAVI","支持全球地图"),

    HUD_OTA("HUD OTA","HUD设备远程升级（T700,T850,T900,H5000...）"),
    AI("AI语音大模型",""),
//    MEETING("会议纪要",""),
    CAMERA("相机","拍照，录像"),
    FILE("文件传输","连接眼镜热点，可读取文件"),
    DISPALY_SET("显示画面调整"),
    DEBUG_PAGE("测试页面"),
    ;
    private String functionName;
    private String functionStr;

    HuynewFunctionType(String functionName){
        this.functionName=functionName;
        this.functionStr="";
    }
    HuynewFunctionType(String functionName,String functionStr){
        this.functionName=functionName;
        this.functionStr=functionStr;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getFunctionStr() {
        return functionStr;
    }
}
