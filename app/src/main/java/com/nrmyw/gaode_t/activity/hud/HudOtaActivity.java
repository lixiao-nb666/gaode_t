package com.nrmyw.gaode_t.activity.hud;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.newbee.downloader.NewBeeFileDownLoaderManager;
import com.newbee.downloader.bean.NewBeeFileDownLoaderRetrunBean;
import com.newbee.downloader.event.retrun.NewBeeFileDownLoaderRetrunEventObserver;
import com.newbee.downloader.event.retrun.NewBeeFileDownLoaderRetrunEventSubscriptionSubject;
import com.newbee.downloader.event.send.NewBeeFileDownLoaderEventSubscriptionSubject;
import com.newbee.downloader.util.UrlToFileDownLoaderBeanUtil;
import com.newbee.gson_lib.gson.MyGson;
import com.nrmyw.gaode_t.BuildConfig;
import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.activity.set.ble.HudUpdateBean;
import com.nrmyw.hud_manager_lib.HudManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HudOtaActivity extends BaseCompatActivity {

    private TextView otaInfoTV,otvDownInfoTV;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            MsgType msgType=MsgType.values()[msg.what];
            switch (msgType){
                case SHOW_OTA_TV_INFO :
                    otaInfoTV.setText(msg.obj.toString());
                    HudUpdateBean hb= (HudUpdateBean) msg.obj;
                    if(null!=hb&&!TextUtils.isEmpty(hb.getDownUrl())){
                        NewBeeFileDownLoaderEventSubscriptionSubject.getInstance().start(UrlToFileDownLoaderBeanUtil.getDownLoaderBean(hb.getDownUrl()));
                    }
                    break;
                case SHOW_OTA_DOWN_INFO:
                    otvDownInfoTV.setText(msg.obj.toString());

                    break;
            }

        }
    };
    private NewBeeFileDownLoaderRetrunEventObserver downLoaderRetrunEventObserver=new NewBeeFileDownLoaderRetrunEventObserver() {
        @Override
        public void completed(NewBeeFileDownLoaderRetrunBean retrunBean) {
            Message msg=new Message();
            msg.what=MsgType.SHOW_OTA_DOWN_INFO.ordinal();
            msg.obj="下载完成:"+retrunBean.getFilePath();
            handler.sendMessage(msg);
            HudManager.getInstance().getHudEvent().setOtaMustHaveFilePermission(retrunBean.getFilePath());
        }

        @Override
        public void progress(NewBeeFileDownLoaderRetrunBean retrunBean, int progress, int soFarBytes, int totalBytes) {
            Message msg=new Message();
            msg.what=MsgType.SHOW_OTA_DOWN_INFO.ordinal();
            msg.obj="进度:"+retrunBean.getFileName();
            handler.sendMessage(msg);
        }

        @Override
        public void err(NewBeeFileDownLoaderRetrunBean retrunBean, Throwable e) {
            Message msg=new Message();
            msg.what=MsgType.SHOW_OTA_DOWN_INFO.ordinal();
            msg.obj="异常:"+retrunBean.getFileName()+e.toString();
            handler.sendMessage(msg);
        }

        @Override
        public void statu() {
            Message msg=new Message();
            msg.what=MsgType.SHOW_OTA_DOWN_INFO.ordinal();
            msg.obj="状态:";
            handler.sendMessage(msg);
        }
    };



    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_hud_ota;
    }

    @Override
    public void initView() {
        otaInfoTV=findViewById(R.id.tv_hud_ota_info);
        otvDownInfoTV=findViewById(R.id.tv_hud_ota_down_info);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initControl() {
        NewBeeFileDownLoaderRetrunEventSubscriptionSubject.getInstance().attach(downLoaderRetrunEventObserver);
    }

    @Override
    public void closeActivity() {
        NewBeeFileDownLoaderRetrunEventSubscriptionSubject.getInstance().detach(downLoaderRetrunEventObserver);
    }

    @Override
    public void viewIsShow() {
        basehandler.post(new Runnable() {
            @Override
            public void run() {
                deviceIsConnectGetVersionInfo();
            }
        });

    }

    @Override
    public void viewIsPause() {

    }

    @Override
    public void changeConfig() {

    }

    private void deviceIsConnectGetVersionInfo(){
        //when you device is connected , can use this
        String url= HudManager.getInstance().getUpdateUrl(BuildConfig.DEBUG);
        url="https://hud-map.oss-cn-hongkong.aliyuncs.com/update/hud_850_gui_3";
        //when you device is disconnected , can use this
        //String url=HudManager.getInstance().getUpdateUrl(HudDevice.T700,BuildConfig.DEBUG);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 请求失败时的回调
                Log.i("testUpdateVersion","testUpdateVersion:1---"+e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 请求成功时的回调
                String gs=response.body().string();
                Log.i("testUpdateVersion","testUpdateVersion:2---"+gs);
                HudUpdateBean hb= MyGson.getInstance().fromJson(gs,HudUpdateBean.class);
                if(null==hb|| TextUtils.isEmpty(hb.getVersion())||TextUtils.isEmpty(hb.getDownUrl())){
                    Log.i("testUpdateVersion","testUpdateVersion:3");
                    return;
                }
                Message msg=new Message();
                msg.what=MsgType.SHOW_OTA_TV_INFO.ordinal();
                msg.obj=hb;
                handler.sendMessage(msg);

                String nowVersion="????";
                if(!nowVersion.equals(hb.getVersion())){
                    // do update
                    Log.i("testUpdateVersion","testUpdateVersion:4--"+hb);
                }else {
                    // is new version ,can show ui
                    Log.i("testUpdateVersion","testUpdateVersion:5--"+hb);
                }


            }
        });


    }


    //        HudUpdateBean hudUpdateBean=new HudUpdateBean();
//        hudUpdateBean.setDownUrl("https://hud-map.oss-cn-hongkong.aliyuncs.com/firmware/cpu1_260208.bin");
//        hudUpdateBean.setVersion("1.2.00");
//        String gs= MyGson.getInstance().toGsonStr(hudUpdateBean);
//        Log.i("kankanjieguo","kankanjieguo:3---"+gs);
//        hudUpdateBean=MyGson.getInstance().fromJson(gs,HudUpdateBean.class);
//        if(null!=hudUpdateBean){
//            Log.i("kankanjieguo","kankanjieguo:6---"+hudUpdateBean);
//        }else {
//            Log.i("kankanjieguo","kankanjieguo:7---");
//        }

    private enum MsgType{
        SHOW_OTA_TV_INFO,
        SHOW_OTA_DOWN_INFO;
    }

}
