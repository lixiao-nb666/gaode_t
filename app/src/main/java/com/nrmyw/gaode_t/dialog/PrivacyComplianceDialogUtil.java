package com.nrmyw.gaode_t.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.amap.api.maps.MapsInitializer;

public class PrivacyComplianceDialogUtil {
    private static PrivacyComplianceDialogUtil dialogUtil;
    private PrivacyComplianceDialogUtil(){

    }

    public static PrivacyComplianceDialogUtil getInstance(){
        if(null==dialogUtil){
            synchronized (PrivacyComplianceDialogUtil.class){
                if(null==dialogUtil){
                    dialogUtil=new PrivacyComplianceDialogUtil();
                }
            }
        }
        return dialogUtil;
    }


    public  void privacyCompliance(Context context) {
        MapsInitializer.updatePrivacyShow(context,true,true);
        SpannableStringBuilder spannable = new SpannableStringBuilder("\"亲，感谢您对XXX一直以来的信任！我们依据最新的监管要求更新了XXX《隐私权政策》，特向您说明如下\n1.为向您提供交易相关基本功能，我们会收集、使用必要的信息；\n2.基于您的明示授权，我们可能会获取您的位置（为您提供附近的商品、店铺及优惠资讯等）等信息，您有权拒绝或取消授权；\n3.我们会采取业界先进的安全措施保护您的信息安全；\n4.未经您同意，我们不会从第三方处获取、共享或向提供您的信息；\n");
        spannable.setSpan(new ForegroundColorSpan(Color.BLUE), 35, 42, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        new AlertDialog.Builder(context)
                .setTitle("温馨提示(隐私合规示例)")
                .setMessage(spannable)
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MapsInitializer.updatePrivacyAgree(context,true);
                    }
                })
                .setNegativeButton("不同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MapsInitializer.updatePrivacyAgree(context,false);
                    }
                })
                .show();
    }

}
