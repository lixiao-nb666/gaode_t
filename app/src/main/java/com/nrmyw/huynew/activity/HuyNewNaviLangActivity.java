package com.nrmyw.huynew.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.nrmyw.gaode_t.R;
import com.nrmyw.hud_data_lib.type.type.HudStatuType;
import com.nrmyw.hud_manager_lib.HudManager;

public class HuyNewNaviLangActivity extends BaseCompatActivity {
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            MsgType msgType= MsgType.values()[msg.what];


            switch (msgType){
                case OPEN_HOTSPOT:
                    openHotSpotShow();
                    break;
                case CLOSE_HOTSPOT:
                    closeHotSpotShow();
                    break;
                case GO_URL:
                    Uri uri = Uri.parse("http://192.168.31.1/files/");
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setData(uri);
                    startActivity(intent);

//                    public void openWeblink(View view) {
//                    Intent intent = new Intent();
//                    intent.setData(Uri.parse("url"));//Url 要打开的网址
//                    intent.setAction(Intent.ACTION_VIEW);
//                    this.startActivity(intent); //启动浏览器

                    break;
            }
        }
    };

    private String wifiSSid="ar_g_glass";
    private String wifiPassWord="";

    private void openHotSpotShow(){
        showDocTV.setText("请按指示操作：\n" +
                "眼镜WIFI已打开，请连接指定账号密码" +
                        "账号:"+wifiSSid+"\n"+
                "密码:"+wifiPassWord
                );
        HudManager.getInstance().getHudEvent().openOrCloseHotport(HudStatuType.OPEN);
        goUrlLL.setVisibility(View.VISIBLE);
    }

    private void closeHotSpotShow(){
        showDocTV.setText("请按指示操作：\n" +
                "WIFI已经关闭，文件暂不能读取"

        );
        HudManager.getInstance().getHudEvent().openOrCloseHotport(HudStatuType.CLOSE);
        goUrlLL.setVisibility(View.GONE);
    }
    private Button openHotSpotBT,closeHotSpotBT,goUrlBT;
    private TextView showDocTV;
    private LinearLayout goUrlLL;
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_open_hotspot:
                    handler.removeMessages(MsgType.OPEN_HOTSPOT.ordinal());
                    handler.sendEmptyMessage(MsgType.OPEN_HOTSPOT.ordinal());
                    break;
                case R.id.bt_close_hotspot:
                    handler.removeMessages(MsgType.CLOSE_HOTSPOT.ordinal());
                    handler.sendEmptyMessage(MsgType.CLOSE_HOTSPOT.ordinal());
                    break;
                case R.id.bt_go_file_url:
                    handler.removeMessages(MsgType.GO_URL.ordinal());
                    handler.sendEmptyMessage(MsgType.GO_URL.ordinal());
                    break;
            }
        }
    };

    @Override
    public int getViewLayoutRsId() {
        return R.layout.huynew_activity_file;
    }

    @Override
    public void initView() {
        showDocTV=findViewById(R.id.tv_file_doc_show);
        goUrlLL=findViewById(R.id.ll_go_url);
        goUrlBT=findViewById(R.id.bt_go_file_url);
        openHotSpotBT=findViewById(R.id.bt_open_hotspot);
        closeHotSpotBT=findViewById(R.id.bt_close_hotspot);
    }

    @Override
    public void initData() {
        openHotSpotBT.setOnClickListener(onClickListener);
        closeHotSpotBT.setOnClickListener(onClickListener);
        goUrlBT.setOnClickListener(onClickListener);
    }

    @Override
    public void initControl() {

    }

    @Override
    public void closeActivity() {

    }

    @Override
    public void viewIsShow() {

    }

    @Override
    public void viewIsPause() {

    }

    @Override
    public void changeConfig() {

    }

    private enum  MsgType{
        OPEN_HOTSPOT,
        CLOSE_HOTSPOT,
        GO_URL,
    }
}
