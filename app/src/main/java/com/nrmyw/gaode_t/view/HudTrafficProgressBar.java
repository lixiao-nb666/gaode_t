package com.nrmyw.gaode_t.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.amap.api.navi.model.AMapTrafficStatus;
import com.gaode.demo.activity.custom.view.TmcBarView;
import com.nrmyw.gaode_t.R;
import com.nrmyw.hud_data_lib.type.image.HudImageType;
import com.nrmyw.hud_manager_lib.HudManager;

import java.util.List;

/**
 * 实时交通信息路况光柱图view类
 */

public class HudTrafficProgressBar extends FrameLayout {
    private final String tag="HudTrafficProgressBar>>>>";
    private RelativeLayout rv;
    private TmcBarView mDefaultTmcBarView;
    private ImageView mDefaultTmcBarCarView;

    private Bitmap lastProgressBitmap;

    public HudTrafficProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HudTrafficProgressBar(Context context) {
        this(context, null);
    }
//    private View view;
    private void init() {
        try {
            View view= LayoutInflater.from(getContext()).inflate(R.layout.view_hud_trafficbar, null);
            addView(view);
            rv=(RelativeLayout) view.findViewById(R.id.rl_progress);
            LayoutParams rvLP= (LayoutParams) rv.getLayoutParams();
            rvLP.width=HudManager.getInstance().getHudSetConfig().getProgressMaxW();
            rvLP.height=HudManager.getInstance().getHudSetConfig().getProgressMaxH();
            rv.setLayoutParams(rvLP);
            Log.i(tag,"kaishifasongtupianle:1111111111:--------"+HudManager.getInstance().getHudSetConfig().getProgressMaxW()+"*"+HudManager.getInstance().getHudSetConfig().getProgressMaxH());
            mDefaultTmcBarView = (TmcBarView) view.findViewById(R.id.navi_sdk_apiTmcBarView);
            RelativeLayout.LayoutParams dtvLP= (RelativeLayout.LayoutParams) mDefaultTmcBarView.getLayoutParams();
            int needW= (int) (HudManager.getInstance().getHudSetConfig().getProgressMaxW()-(HudManager.getInstance().getHudSetConfig().getProgressMaxW()/4.5));
            dtvLP.width=needW/2;
            dtvLP.height=HudManager.getInstance().getHudSetConfig().getProgressMaxH();
            int needM=(int) HudManager.getInstance().getHudSetConfig().getProgressMaxW()/6;
            dtvLP.topMargin=needM;
            dtvLP.bottomMargin=needM;
            mDefaultTmcBarView.setLayoutParams(dtvLP);
            mDefaultTmcBarCarView = (ImageView) view.findViewById(R.id.navi_sdk_apiTmcBarCar);
            RelativeLayout.LayoutParams dtbLP= (RelativeLayout.LayoutParams) mDefaultTmcBarCarView.getLayoutParams();
            dtbLP.width=HudManager.getInstance().getHudSetConfig().getProgressMaxW();
            dtbLP.height=HudManager.getInstance().getHudSetConfig().getProgressMaxW();
            mDefaultTmcBarCarView.setLayoutParams(dtbLP);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    private long lastRefreshTpbTime;
    /**
     * 更新光柱条
     *
     * @param allLength    整条路线长度
     * @param retainLength 剩余距离
     * @param traffics     交通状态列表
     */
    private int pb;
    public void update(int allLength, int retainLength, List<AMapTrafficStatus> traffics) {
        try {
            mDefaultTmcBarView.setData(traffics, allLength);
            mDefaultTmcBarView.setCarView(mDefaultTmcBarCarView);
            //绘制光柱
            if (mDefaultTmcBarView.getHeight() > 0) {
                mDefaultTmcBarView.setCursorPos(retainLength);
                mDefaultTmcBarView.invalidate();
            }

            long nowTime=System.currentTimeMillis();
            int nowPb=(int)retainLength*100/allLength;
            if(nowPb==pb&&nowTime-lastRefreshTpbTime<=3000){
                return;
            }
            if(nowTime-lastRefreshTpbTime>1000){
                pb=nowPb;
                lastRefreshTpbTime=nowTime;
                handler.sendEmptyMessage(MsgType.GET_TPB_BITMAP.ordinal());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                handler.removeMessages(msg.what);
               MsgType msgType= MsgType.values()[msg.what];
                switch (msgType){
                    case GET_TPB_BITMAP:
                        clearProgressBitmap();
                        lastProgressBitmap = Bitmap.createBitmap(rv.getWidth(), rv.getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas viewCs=new Canvas(lastProgressBitmap);
                        rv.draw(viewCs);
                        viewCs.save();
                        if(null==lastProgressBitmap||lastProgressBitmap.isRecycled()){
                            lastProgressBitmap=null;
                        }else {
                            Log.i(tag,"kaishifasongtupianle:111111222:--------"+lastProgressBitmap.getWidth()+"*"+lastProgressBitmap.getHeight());
                            HudManager.getInstance().getHudEvent().sendImage(lastProgressBitmap, HudImageType.PROGRESS_BAR);
//                           Hud.getInstance().getEventImp().sendImage(, HudImageType.PROGRESS_BAR);
                            Log.i(tag,"kaishifasongtupianle:1111113333");
                        }


                        break;

                }
            }catch (Exception e){
                Log.i(tag,"kaishifasongtupianle:666"+e.toString());
            }

        }
    };

    private void clearProgressBitmap(){
        if(null!=lastProgressBitmap){
            lastProgressBitmap.recycle();
            lastProgressBitmap=null;
        }
    }

    private enum MsgType{
        GET_TPB_BITMAP,
//        SEND_MOLD_VIEW_BITMAP,
//        HIDE_MOLD_VIEW,
    }

}
