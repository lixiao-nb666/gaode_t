package com.nrmyw.gaode_t.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

import com.amap.api.maps.AMap;
import com.amap.api.navi.view.AMapModeCrossOverlay;
import com.nrmyw.gaode_t.config.GaoDeHudSendConfig;
import com.nrmyw.gaode_t.event.navi.GaoDeEventManager;
import com.nrmyw.gaode_t.log.LG;
import com.nrmyw.gaode_t.view.event.HudModeCrossEventObserver;
import com.nrmyw.gaode_t.view.event.HudModeCrossEventSubscriptionSubject;
import com.nrmyw.hud_data_lib.type.image.HudImageType;
import com.nrmyw.hud_manager_lib.HudManager;

public class HudAMapModeCrossOverlay {

    private AMapModeCrossOverlay mModeCrossView;
    private Bitmap lastMoldViewBitmap;

    private void clearMoldeViewBitmap(){
        if(null!=lastMoldViewBitmap){
            lastMoldViewBitmap.recycle();
            lastMoldViewBitmap=null;
        }
    }


//    private Listen listen;
    private AMapModeCrossOverlay.OnCreateBitmapFinish onCreateBitmapFinish=new AMapModeCrossOverlay.OnCreateBitmapFinish() {
        @Override
        public void onGenerateComplete(Bitmap bitmap, int i) {

            try {

                LG.i("HudAMapModeCrossOverlay","Navie-bitmap----51:显示路口放大图回调(模型图)。1111"+(null==bitmap)+"--"+i);
                clearMoldeViewBitmap();
                if(!canShow){
                    return;
                }
                if(null==bitmap||bitmap.isRecycled()){
                    return;
                }
                nowIsShow=true;
                GaoDeHudSendConfig.getInstance().getEventImp().sendImage(bitmap, HudImageType.IMAGE);

//            GaoDeHudSendConfig.getInstance().getEventImp().sendImage(bitmap);

//                Message msg=new Message();
//                msg.what= MyGaoDeSendBitmapView.MsgType.SEND_MOLD_VIEW_BITMAP.ordinal();
//                msg.obj=bitmap;
//                HudDevice hudDevice=HudManager.getInstance().getHudDevice();
//                if(null==hudDevice){
//                    return;
//                }
//                switch (hudDevice){
//                    case T800:
//                        break;
//                    default:
//                        handler.sendMessage(msg);
//                        break;
//                }
                lastMoldViewBitmap=bitmap;
            }catch (Exception e){
                LG.i("HudAMapModeCrossOverlay","Navie-bitmap----6:显示路口放大图回调(模型图)。11114444555"+e.toString());
            }
        }
    };


    private HudModeCrossEventObserver hudModeCrossEventObserver=new HudModeCrossEventObserver() {
        @Override
        public void setBitmapBytes(byte[] bitmapBytes) {
            setDataShow(bitmapBytes);
        }
    };

    public HudAMapModeCrossOverlay(Context context, AMap aMap){
        mModeCrossView = new AMapModeCrossOverlay(context, aMap);
        int mustX= HudManager.getInstance().getHudSetConfig().getImageMaxW();
//        int mustY=needY/(needX/mustX);
        int mustY= HudManager.getInstance().getHudSetConfig().getImageMaxH();
        LG.i("HudAMapModeCrossOverlay","Navie-bitmap----6:显示路口放大图回调(模型图)。11114444555----"+mustX+"--"+mustY);
        mModeCrossView.setCrossOverlayLocation(new Rect(0, 0,mustX,mustY));
        mModeCrossView.setWidth(mustX);
        mModeCrossView.setHeight(mustY);
        HudModeCrossEventSubscriptionSubject.getInstence().attach(hudModeCrossEventObserver);
//        this.listen=listen;
    }

    public void close(){
        HudModeCrossEventSubscriptionSubject.getInstence().detach(hudModeCrossEventObserver);
    }

    private boolean canShow=false;
    private boolean nowIsShow=false;
    public void setDataShow(byte[] bitmapBytes){
        LG.i("HudAMapModeCrossOverlay","Navie-bitmap----566:"+bitmapBytes.length);
        canShow=true;
        nowIsShow=false;
        mModeCrossView.createModelCrossBitMap(bitmapBytes,onCreateBitmapFinish);
    }

    public void  hide(){
        canShow=false;
        nowIsShow=false;

        HudManager.getInstance().getHudEvent().hideImage();
        clearMoldeViewBitmap();
    }


//    public interface Listen{
//        public void canShowBitmap(Bitmap bitmap);
//
//        public void hideBitmap();
//
//        public void hideBitmapDelayed(long delayedTime);
//
//
//    }


    //                    case SEND_MOLD_VIEW_BITMAP:
//                        clearMoldeViewBitmap();
//                        lastMoldViewBitmap= (Bitmap) msg.obj;
//                        Log.i("kaishifasongtupianle","kaishifasongtupianle:2222111");
//                        if(null==lastMoldViewBitmap||lastMoldViewBitmap.isRecycled()){
//                            lastMoldViewBitmap=null;
//                        }else {
//                            HudManager.getInstance().getHudEvent().sendImage(lastMoldViewBitmap);
//                            Log.i("kaishifasongtupianle","kaishifasongtupianle:2222");
//                        }
//
//                        break;
//                    case HIDE_MOLD_VIEW:
//                        LG.i("tag","Navie-bitmap----443:关闭路口放大图回调(模型图)。");
////                        GaoDeHudSendConfig.getInstance().getEventImp().hideImage();
//                        clearMoldeViewBitmap();
//                        HudManager.getInstance().getHudEvent().hideImage();
//                        break;


//        int needX=      DensityUtils.getScreenWidth(getContext());
//        int needY=DensityUtils.dp2px(getContext(), 300);

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
//                Message msg=new Message();
//                msg.what=MsgType.SEND_MOLD_VIEW_BITMAP.ordinal();
//                msg.obj=bitmap;
//                handler.sendMessage(msg);
//            }
//
//            @Override
//            public void onMapScreenShot(Bitmap bitmap, int i) {
//                Log.e("TAG","onMapScreenShot  i："+i);
//            }
//        });
}
