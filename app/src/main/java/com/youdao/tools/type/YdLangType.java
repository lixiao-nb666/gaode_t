package com.youdao.tools.type;

import com.newbee.bulid_lib.mybase.share.MyShare;


import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.app.MyApp;


public enum YdLangType {

    //英文
    en("en", R.string.yd_lang_en,false),
    en_AUS("en-AUS", R.string.yd_lang_en_aus,true),
    en_CA("en-CA", R.string.yd_lang_en_ca,true),
    en_GH("en-GH", R.string.yd_lang_en_gh,true),
    en_GBR("en-GBR", R.string.yd_lang_en_gbr),
    en_IND("en-IND", R.string.yd_lang_en_ind,true),
    en_IE("en-IE", R.string.yd_lang_en_ie,true),
    en_KE("en-KE", R.string.yd_lang_en_ke,true),
    en_NZ("en-NZ", R.string.yd_lang_en_nz,true),
    en_NG("en-NG", R.string.yd_lang_en_ng,true),
    en_PH("en-PH", R.string.yd_lang_en_ph,true),
    en_SG("en-SG", R.string.yd_lang_en_sg,true),
    en_ZAF("en-ZAF", R.string.yd_lang_en_zaf,true),
    en_TZ("en-TZ", R.string.yd_lang_en_tz,true),
    en_US("en-US", R.string.yd_lang_en_us),
    //中文
    zh_CHS("zh-CHS", R.string.yd_lang_zh),
    zh_TWN("zh-TWN", R.string.yd_lang_zh_twn),
    enzh("enzh", R.string.yd_lang_enzh,true),
    //阿拉伯
    ar_IL("ar-IL", R.string.yd_lang_ar_il,true),
    ar_JO("ar-JO", R.string.yd_lang_ar_jo,true),
    ar_AE("ar-AE", R.string.yd_lang_ar_ae,true),
    ar_BH("ar-BH", R.string.yd_lang_ar_bh,true),
    ar_DZ("ar-DZ", R.string.yd_lang_ar_dz,true),
    ar_SA("ar-SA", R.string.yd_lang_ar_sa),
    ar_IQ("ar-IQ", R.string.yd_lang_ar_iq,true),
    ar_KW("ar-KW", R.string.yd_lang_ar_kw,true),
    ar_MA("ar-MA", R.string.yd_lang_ar_ma),
    ar_TN("ar-TN", R.string.yd_lang_ar_tn,true),
    ar_OM("ar-OM", R.string.yd_lang_ar_om,true),
    ar_PS("ar-PS", R.string.yd_lang_ar_ps,true),
    ar_QA("ar-QA", R.string.yd_lang_ar_qa,true),
    ar_LB("ar-LB", R.string.yd_lang_ar_lb,true),
    ar_EG("ar-EG", R.string.yd_lang_ar_eg),
    //波兰语
    pl("pl", R.string.yd_lang_pl,true),
    //丹麦
    da("da", R.string.yd_lang_da,true),
    //德语
    de("de", R.string.yd_lang_de),
    //俄语
    ru("ru", R.string.yd_lang_ru),
    //法语
    fr("fr", R.string.yd_lang_fr),
    fr_CA("fr-CA", R.string.yd_lang_fr_ca,true),
    //芬兰语
    fi("fi", R.string.yd_lang_fi,true),
    //韩语
    ko("ko", R.string.yd_lang_ko),
    //荷兰语
    nl("nl", R.string.yd_lang_nl),
    //捷克语
    cs("cs", R.string.yd_lang_cs),
    //克罗地亚语
    hr("hr", R.string.yd_lang_hr),
    //拉脱维亚语
    lv("lv", R.string.yd_lang_lv),
    //罗马尼亚语
    ro("ro", R.string.yd_lang_ro),
    //罗马尼亚语
    ms("ms", R.string.yd_lang_ms,true),
    //葡萄牙语
    pt_BRA("pt-BRA", R.string.yd_lang_pt_bra),
    pt("pt", R.string.yd_lang_pt),
    //日语
    ja("ja", R.string.yd_lang_ja),
    //瑞典语
    sv("sv", R.string.yd_lang_sv),
    //泰卢固语
    te("te", R.string.yd_lang_te,true),
    //泰米尔语
    ta("ta", R.string.yd_lang_ta),
    ta_SG("ta-SG", R.string.yd_lang_ta_sg),
    ta_LK("ta-LK", R.string.yd_lang_ta_lk),
    ta_MY("ta-MY", R.string.yd_lang_ta_my),
    //泰语
    th("th", R.string.yd_lang_th,true),
    //土耳其语
    tr("tr", R.string.yd_lang_tr),
    //西班牙语
    es("es", R.string.yd_lang_es),
    es_AR("es-AR", R.string.yd_lang_es_ar,true),
    es_BO("es-BO", R.string.yd_lang_es_bo,true),
    es_CL("es-CL", R.string.yd_lang_es_cl,true),
    es_COL("es-COL", R.string.yd_lang_es_col,true),
    es_CR("es-CR", R.string.yd_lang_es_cr,true),
    es_EC("es-EC", R.string.yd_lang_es_ec,true),
    es_SV("es-SV", R.string.yd_lang_es_sv,true),
    es_US("es-US", R.string.yd_lang_es_us,true),
    es_GT("es-GT", R.string.yd_lang_es_gt,true),
    es_HN("es-HN", R.string.yd_lang_es_hn,true),
    es_MEX("es-MEX", R.string.yd_lang_es_mex,true),
    es_NI("es-NI", R.string.yd_lang_es_ni,true),
    es_PA("es-PA", R.string.yd_lang_es_pa,true),
    es_PY("es-PY", R.string.yd_lang_es_py,true),
    es_PE("es-PE", R.string.yd_lang_es_pe,true),
    es_PR("es-PR", R.string.yd_lang_es_pr,true),
    es_DO("es-DO", R.string.yd_lang_es_do,true),
    es_UY("es-UY", R.string.yd_lang_es_uy,true),
    es_VE("es-VE", R.string.yd_lang_es_ve,true),
    //意大利语
    it("it", R.string.yd_lang_it),
    //印地语
    hi("hi", R.string.yd_lang_hi),
    //越南语
    vi("vi", R.string.yd_lang_vi),
    //老挝语
    lo("lo", R.string.yd_lang_lo,true),
    //孟加拉语
    bn("bn", R.string.yd_lang_bn,true),
    bn_IN("bn-IN", R.string.yd_lang_bn_in),
    //菲律宾语
    tl("tl", R.string.yd_lang_tl),
    ;


    private String lang;
    private int rsStr;
    private boolean needHide;

    YdLangType(String lang, int rsStr){
        this.lang=lang;
        this.rsStr=rsStr;
        getShareNeedHide(needHide);
    }

    YdLangType(String lang,int rsStr,boolean needHide){
        this.lang=lang;
        this.rsStr=rsStr;
        this.needHide=needHide;
        getShareNeedHide(needHide);
    }

    private void getShareNeedHide(boolean  setHide){
        String shareKey="YdLangType:"+getLang();
        String  shareStr= MyShare.getInstance().getString(shareKey,setHide+"");
        needHide=Boolean.valueOf(shareStr);
    }

    public String getLang() {
        return lang;
    }

    public int getRsStr() {
        return rsStr;
    }

    public String getStr(){
        return MyApp.getRsString(rsStr);
    }

    public boolean isNeedHide() {
        return needHide;
    }

    public void setNeedHide(boolean needHide) {
        this.needHide = needHide;
        String shareKey="YdLangType:"+getLang();
        MyShare.getInstance().putString(shareKey,needHide+"");
    }

    @Override
    public String toString() {
        return "YdLangType{" +
                "lang='" + lang + '\'' +
                ", rsStr=" + rsStr +
                ", needHide=" + needHide +
                '}';
    }
}
