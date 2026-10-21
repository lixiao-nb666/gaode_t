package com.nrmyw.gaode_t.activity.set.ble;

import android.text.TextUtils;
import android.util.Log;

import com.newbee.gson_lib.gson.MyGson;
import com.nrmyw.gaode_t.BuildConfig;
import com.nrmyw.hud_manager_lib.HudManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DemoCode {

    private void deviceIsConnectGetVersionInfo(){
        //when you device is connected , can use this
        String url= HudManager.getInstance().getUpdateUrl(BuildConfig.DEBUG);
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
}
