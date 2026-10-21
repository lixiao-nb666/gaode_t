package com.nrmyw.gaode_t.view;//package com.nrmyw.gaode_t.view;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Rect;
//import android.os.Handler;
//import android.os.Message;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.amap.api.maps.AMap;
//import com.amap.api.maps.CameraUpdateFactory;
//import com.amap.api.maps.TextureMapView;
//import com.amap.api.maps.model.LatLng;
//import com.amap.api.navi.AMapNaviViewOptions;
//import com.amap.api.navi.model.AMapModelCross;
//import com.amap.api.navi.model.AMapNaviLocation;
//import com.amap.api.navi.model.AMapTrafficStatus;
//
//import com.amap.api.navi.view.AMapModeCrossOverlay;
//import com.gaode.demo.activity.custom.AllCustomCarRouteActivity;
//import com.gaode.demo.activity.custom.AllCustomNaviActivity;
//import com.gaode.demo.activity.custom.view.CarOverlay;
//import com.gaode.demo.util.DensityUtils;
//import com.nrmyw.gaode_t.R;
//
//import com.nrmyw.gaode_t.config.UiConfig;
//import com.nrmyw.gaode_t.log.LG;
//
//import com.nrmyw.hud_data_event_lib.HudEventManager;
//import com.nrmyw.hud_data_lib.type.image.HudImageType;
//import com.nrmyw.hud_data_lib.type.warningproint.HudWarningPointType;
//import com.nrmyw.hud_manager_lib.HudManager;
//import com.nrmyw.hud_manager_lib.type.HudDevice;
//
//import java.util.List;
//
//public class MyGaoDeSendBitmapView  {
//
//    private static MyGaoDeSendBitmapView myGaoDeSendBitmapView;
//    private HudManagerAndSetView hudManagerAndSetView;
//    private MyGaoDeSendBitmapView(){
//    }
//
//    public static MyGaoDeSendBitmapView getInstance(){
//        if(null==myGaoDeSendBitmapView){
//            synchronized (MyGaoDeSendBitmapView.class){
//                if(null==myGaoDeSendBitmapView){
//                    myGaoDeSendBitmapView=new MyGaoDeSendBitmapView();
//                }
//            }
//        }
//        return myGaoDeSendBitmapView;
//    }
//
//    public synchronized HudManagerAndSetView getHudManagerAndSetView(Context context){
//        if(null==hudManagerAndSetView){
//            hudManagerAndSetView=new HudManagerAndSetView(context);
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UiConfig.getMapViewSetViewH());
//            hudManagerAndSetView.setLayoutParams(params);
//        }
//        return hudManagerAndSetView;
//    }
//
//
//
//}
