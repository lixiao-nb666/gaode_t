package com.nrmyw.gaode_t.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapTrafficStatus;
import com.gaode.demo.activity.custom.view.CarOverlay;
import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.activity.set.ble.BleSetActivity;
import com.nrmyw.gaode_t.activity.t800.T800BleDataTestActivity;
import com.nrmyw.gaode_t.log.LG;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.type.HudDevice;

import java.util.List;

public class HudManagerAndSetView extends FrameLayout {

//    private FrameLayout mapFL;
    private HudTrafficProgressBar tpb;
    private HudAMapModeCrossOverlay aMapModeCrossOverlay;
    protected TextureMapView mAMapNaviView;



    private Handler uiHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                MsgType msgType=MsgType.values()[msg.what];
                LG.i("tag","Navie-bitmap----555:"+msgType);
                switch (msgType){
                    case SHOW_MOLD_VIEW:
                        LG.i("tag","Navie-bitmap----5:显示路口放大图回调(模型图)。1111"+(null==aMapModelCross)+(null==aMapModelCross.getPicBuf1())+(aMapModelCross.getPicBuf1().length)+(null==aMapModeCrossOverlay));
                        if(null==aMapModelCross||null==aMapModelCross.getPicBuf1()||aMapModelCross.getPicBuf1().length==0){
                            return;
                        }
                        if(null==aMapModeCrossOverlay){
                            return;
                        }
                        moldViewStartTime=System.currentTimeMillis();
                        isShowMoldView=false;
//        mModeCrossView.createModelCrossBitMap(aMapModelCross.getPicBuf1(),onCreateBitmapFinish);
//        mModeCrossView.(aMapModelCross.getPicBuf1());
//                        HudModeCrossEventSubscriptionSubject.getInstence().setBitmapBytes(aMapModelCross.getPicBuf1());
                        aMapModeCrossOverlay.setDataShow(aMapModelCross.getPicBuf1());
                        break;
                    case HIDE_MOLD_VIEW:
                        aMapModeCrossOverlay.hide();
                        break;
                    case SHOW_TB:
                        HudDevice hudDevice=HudManager.getInstance().getHudDevice();
                        if(null==hudDevice){
                            return;
                        }
                        switch (hudDevice){
                            case T800:
                                break;
                            default:
                                tpb.update(var1,var2,var3);
                                break;
                        }
                        break;
                }
            }catch (Exception e){}

        }
    };


    public HudManagerAndSetView(Context context) {
        this(context,null);
    }

    public HudManagerAndSetView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HudManagerAndSetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public HudManagerAndSetView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


//    private boolean isStart;

//    public void start(){
//        isStart=true;
//    }
//    private void pause(){
//
//    }


    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if(visibility==GONE){
            Log.i("viewclose","viewclose11111111111111");

        }else {
            Log.i("viewclose","viewclose2222");
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i("viewclose","viewclose11111111111111----");

    }

    public void close(int type){
        Log.i("viewclose","viewclose11111111111111"+type);
        if (carOverlay != null) {
            carOverlay.destroy();
        }
        if (mAMapNaviView != null) {
            mAMapNaviView.onDestroy();
        }
        if(null!=aMapModeCrossOverlay){
            aMapModeCrossOverlay.close();
        }


    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_mygaode_send_bitmap,this);
//        mapFL=findViewById(R.id.fl_map);
        tpb=findViewById(R.id.tpb);
        tpb.setDrawingCacheEnabled(true);
        LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) tpb.getLayoutParams();

        layoutParams.height=HudManager.getInstance().getHudSetConfig().getImageMaxH();
        tpb.setLayoutParams(layoutParams);
        mAMapNaviView = (TextureMapView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(null);
//        mAMapNaviView = new TextureMapView(this);
        final AMap aMap = mAMapNaviView.getMap();
        aMap.setMapType(AMap.MAP_TYPE_NAVI);
        aMapModeCrossOverlay=new HudAMapModeCrossOverlay(getContext(),aMap);
        initCarOverlay();
        findViewById(R.id.iv_set).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BleSetActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);

            }
        });
        findViewById(R.id.iv_set_car).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), T800BleDataTestActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);

            }
        });

    }
    CarOverlay carOverlay;
    private void initCarOverlay(){
        mAMapNaviView.getMap().getUiSettings().setZoomControlsEnabled(false);
        mAMapNaviView.getMap().setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                mAMapNaviView.getMap().setPointToCenter(mAMapNaviView.getWidth()/2, mAMapNaviView.getHeight()/2);
//                mAMapNaviView.getMap().moveCamera(CameraUpdateFactory.zoomTo(16f));
                carOverlay = new CarOverlay(getContext(), mAMapNaviView);
            }
        });
    }

    private int var1;
    private int var2;
    private List<AMapTrafficStatus> var3;
    public void update(int var1, int var2, List<AMapTrafficStatus> var3) {
        this.var1=var1;
        this.var2=var2;
        this.var3=var3;
        uiHandler.sendEmptyMessage(MsgType.SHOW_TB.ordinal());




    }








    private boolean isShowMoldView;
    private long moldViewStartTime;
    private long moldViewEndTime;

    private AMapModelCross aMapModelCross;
    public void showMoldView(AMapModelCross aMapModelCross){
            this.aMapModelCross=aMapModelCross;
            uiHandler.sendEmptyMessage(MsgType.SHOW_MOLD_VIEW.ordinal());
    }


    private final long minShowMoldViewTime=2*1000;
    public void hideMoldView(){
//        handler.removeMessages(MsgType.HIDE_MOLD_VIEW.ordinal());


        uiHandler.sendEmptyMessage(MsgType.HIDE_MOLD_VIEW.ordinal());
//        if(isShowMoldView){
//
//            long nowTime=System.currentTimeMillis();
//            long timeCha=nowTime-moldViewEndTime;
//            if(timeCha>minShowMoldViewTime){
//                LG.i("tag","Navie-bitmap----441:关闭路口放大图回调(模型图)。");
//                handler.sendEmptyMessage(MsgType.HIDE_MOLD_VIEW.ordinal());
//            }else {
//                LG.i("tag","Navie-bitmap----442:关闭路口放大图回调(模型图)。");
//                handler.sendEmptyMessageDelayed(MsgType.HIDE_MOLD_VIEW.ordinal(),minShowMoldViewTime-nowTime);
//            }
//            isShowMoldView=false;
//            HudManager.getInstance().getHudEvent().hideImage();
////            GaoDeHudSendConfig.getInstance().getEventImp().hideImage();
//        }
    }


    public void setLocationChange(AMapNaviLocation location){
//        mAMapNaviView.getMap().moveCamera(CameraUpdateFactory.changeBearing(location.getBearing()));
        if (carOverlay != null && location != null) {
            carOverlay.draw(mAMapNaviView.getMap(), new LatLng(location.getCoord().getLatitude(), location.getCoord().getLongitude()), location.getBearing());
        }
    }


    private enum MsgType{
        NONE,
        SHOW_MOLD_VIEW,
        HIDE_MOLD_VIEW,
        SHOW_TB,
    }
}
