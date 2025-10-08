package com.nrmyw.gaode_t.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapTrafficStatus;

import com.amap.api.navi.view.AMapModeCrossOverlay;
import com.gaode.demo.activity.custom.AllCustomNaviActivity;
import com.gaode.demo.activity.custom.view.CarOverlay;
import com.gaode.demo.util.DensityUtils;
import com.nrmyw.gaode_t.R;

import com.nrmyw.gaode_t.log.LG;

import com.nrmyw.hud_data_lib.type.image.HudImageType;
import com.nrmyw.hud_manager_lib.HudManager;

import java.util.List;

public class MyGaoDeSendBitmapView extends FrameLayout {
    private HudTrafficProgressBar tpb;
    protected TextureMapView mAMapNaviView;
    private AMapModeCrossOverlay mModeCrossView;
    private Bitmap lastMoldViewBitmap,lastProgressBitmap;
    private AMapModeCrossOverlay.OnCreateBitmapFinish onCreateBitmapFinish=new AMapModeCrossOverlay.OnCreateBitmapFinish() {
        @Override
        public void onGenerateComplete(Bitmap bitmap, int i) {
            if(null==bitmap||bitmap.isRecycled()){
                return;
            }
            moldViewEndTime=System.currentTimeMillis();
            LG.i("tag","Navie-bitmap----6:显示路口放大图回调(模型图)。1111"+(bitmap==null)+"---"+(moldViewEndTime-moldViewStartTime));
            isShowMoldView=true;
//            GaoDeHudSendConfig.getInstance().getEventImp().sendImage(bitmap);
//            HudManager.getInstance().getHudEvent().sendImage(bitmap);
            Message msg=new Message();
            msg.what=MsgType.SEND_MOLD_VIEW_BITMAP.ordinal();
            msg.obj=bitmap;
//            handler.sendMessage(msg);
        }
    };
    private void clearMoldeViewBitmap(){
        if(null!=lastMoldViewBitmap){
            lastMoldViewBitmap.recycle();
            lastMoldViewBitmap=null;
        }
    }

    private void clearProgressBitmap(){
        if(null!=lastProgressBitmap){
            lastProgressBitmap.recycle();
            lastProgressBitmap=null;
        }
    }

    public MyGaoDeSendBitmapView(Context context) {
        this(context,null);
    }

    public MyGaoDeSendBitmapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyGaoDeSendBitmapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public MyGaoDeSendBitmapView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_mygaode_send_bitmap,this);
        tpb=findViewById(R.id.tpb);
        tpb.setDrawingCacheEnabled(true);
        mAMapNaviView = (TextureMapView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(null);
//        mAMapNaviView = new TextureMapView(this);
        final AMap aMap = mAMapNaviView.getMap();
        aMap.setMapType(AMap.MAP_TYPE_NAVI);
        mModeCrossView = new AMapModeCrossOverlay(getContext(), aMap);
//        int needX=      DensityUtils.getScreenWidth(getContext());
//        int needY=DensityUtils.dp2px(getContext(), 300);
        int mustX=155;
//        int mustY=needY/(needX/mustX);
        int mustY=150;
        mModeCrossView.setCrossOverlayLocation(new Rect(0, 0,mustX,mustY));

//        Log.i("kankanxy","kankanxy:"+needX+"--"+needY+"--"+mustX+"--"+mustY);

//        AMapNaviViewOptions opts = mAMapNaviView.getViewOptions();
//        opts.setEyrieCrossDisplay(false);
//        mAMapNaviView.setViewOptions(opts);

//        aMap.getUiSettings().setZoomControlsEnabled(false);
//        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
//            @Override
//            public void onMapLoaded() {
////                aMap.setPointToCenter(mAMapNaviView.getWidth()/2, mAMapNaviView.getHeight()/2);
////
////                aMap.moveCamera(CameraUpdateFactory.zoomTo(16f));
////                aMap.moveCamera(CameraUpdateFactory.changeTilt(30));
//
//
//            }
//        });
//
//        aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
//            @Override
//            public void onMapScreenShot(Bitmap bitmap) {
//                Log.e("TAG","onMapScreenShot");
//            }
//
//            @Override
//            public void onMapScreenShot(Bitmap bitmap, int i) {
//                Log.e("TAG","onMapScreenShot  i："+i);
//            }
//        });
    }

    private long lastRefreshTpbTime;
    public void update(int var1, int var2, List<AMapTrafficStatus> var3) {
        tpb.update(var1,var2,var3);
        long nowTime=System.currentTimeMillis();
        if(nowTime-lastRefreshTpbTime>1000){
            lastRefreshTpbTime=nowTime;
            handler.sendEmptyMessage(MsgType.GET_TPB_BITMAP.ordinal());
        }
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                handler.removeMessages(msg.what);
                MsgType msgType=MsgType.values()[msg.what];

                switch (msgType){
                    case GET_TPB_BITMAP:
                        clearProgressBitmap();
                        View view=tpb;
                        Bitmap viewBp = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas viewCs=new Canvas(viewBp);
                        view.draw(viewCs);
                        viewCs.save();
                        if(null!=viewBp&&!viewBp.isRecycled()){
                            lastProgressBitmap=viewBp;
                            HudManager.getInstance().getHudEvent().sendImage(viewBp, HudImageType.PROGRESS_BAR);
//                           Hud.getInstance().getEventImp().sendImage(, HudImageType.PROGRESS_BAR);
                            Log.i("kaishifasongtupianle","kaishifasongtupianle:111111");
                        }
                        break;
                    case SEND_MOLD_VIEW_BITMAP:
                        clearMoldeViewBitmap();
                        Bitmap moldBitmap= (Bitmap) msg.obj;
                        Log.i("kaishifasongtupianle","kaishifasongtupianle:2222111");
                        if(null!=moldBitmap&&!moldBitmap.isRecycled()){
                            lastMoldViewBitmap=moldBitmap;
                            HudManager.getInstance().getHudEvent().sendImage(moldBitmap);
                            Log.i("kaishifasongtupianle","kaishifasongtupianle:2222");
                        }
                        break;
                    case HIDE_MOLD_VIEW:
                        LG.i("tag","Navie-bitmap----443:关闭路口放大图回调(模型图)。");
//                        GaoDeHudSendConfig.getInstance().getEventImp().hideImage();
                        clearMoldeViewBitmap();
                        HudManager.getInstance().getHudEvent().hideImage();
                        break;
            }
            }catch (Exception e){
                Log.i("kaishifasongtupianle","kaishifasongtupianle:666"+e.toString());
            }

        }
    };




    private boolean isShowMoldView;
    private long moldViewStartTime;
    private long moldViewEndTime;
    public void showMoldView(AMapModelCross aMapModelCross){
        LG.i("tag","Navie-bitmap----5:显示路口放大图回调(模型图)。1111-----"+aMapModelCross.getPicBuf1().length+(null==mModeCrossView));
        if(null==aMapModelCross||null==aMapModelCross.getPicBuf1()||aMapModelCross.getPicBuf1().length==0){
            return;
        }
        LG.i("tag","Navie-bitmap----5:显示路口放大图回调(模型图)。11112222-----");
        moldViewStartTime=System.currentTimeMillis();
        isShowMoldView=false;
        mModeCrossView.createModelCrossBitMap(aMapModelCross.getPicBuf1(),onCreateBitmapFinish);
        mModeCrossView.showCrossOverlay(aMapModelCross.getPicBuf1());
    }


    private final long minShowMoldViewTime=2*1000;
    public void hideMoldView(){
        if(isShowMoldView){
            handler.removeMessages(MsgType.HIDE_MOLD_VIEW.ordinal());
            long nowTime=System.currentTimeMillis();
            long timeCha=nowTime-moldViewEndTime;
            if(timeCha>minShowMoldViewTime){
                LG.i("tag","Navie-bitmap----441:关闭路口放大图回调(模型图)。");
                handler.sendEmptyMessage(MsgType.HIDE_MOLD_VIEW.ordinal());
            }else {
                LG.i("tag","Navie-bitmap----442:关闭路口放大图回调(模型图)。");
                handler.sendEmptyMessageDelayed(MsgType.HIDE_MOLD_VIEW.ordinal(),minShowMoldViewTime-nowTime);
            }
            isShowMoldView=false;
            HudManager.getInstance().getHudEvent().hideImage();
//            GaoDeHudSendConfig.getInstance().getEventImp().hideImage();
        }
    }


    private enum MsgType{
        GET_TPB_BITMAP,
        SEND_MOLD_VIEW_BITMAP,
        HIDE_MOLD_VIEW,
    }
}
