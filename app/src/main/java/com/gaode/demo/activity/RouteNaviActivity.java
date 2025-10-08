package com.gaode.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Toast;

import androidx.core.view.MotionEventCompat;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.MyNaviListener;
import com.amap.api.navi.enums.AimLessMode;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.InnerNaviInfo;
import com.amap.api.navi.model.NaviCongestionInfo;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviPath;

import com.nrmyw.gaode_t.R;

import java.util.ArrayList;


public class RouteNaviActivity extends Activity implements MyNaviListener, AMapNaviViewListener {

    Bundle savedInstanceState;
    AMapNaviView mAMapNaviView;
    AMapNavi mAMapNavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.savedInstanceState = savedInstanceState;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_basic_navi);

        findViewById(R.id.night).setOnClickListener(v -> {

        });



        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.getMap().setMapType(AMap.MAP_TYPE_NAVI);

        mAMapNaviView.setAMapNaviViewListener(this);
//        //隐藏当前导航结束的view
        AMapNaviViewOptions options = new AMapNaviViewOptions();
        options.setLayoutVisible(true);
        options.setTrafficLine(true);
        mAMapNaviView.setViewOptions(options);
        addPolyline();
        try {
            mAMapNavi = AMapNavi.getInstance(getApplicationContext());
            mAMapNavi.addAMapNaviListener(this);
            mAMapNavi.setUseInnerVoice(true);
            mAMapNavi.setEmulatorNaviSpeed(60);
            boolean gps = getIntent().getBooleanExtra("gps", false);
            if (gps) {
                mAMapNavi.startNavi(NaviType.GPS);
            } else {
                mAMapNavi.startNavi(NaviType.EMULATOR);
            }

        } catch (AMapException e) {
            e.printStackTrace();
        }
        mAMapNaviView.getMap().setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        int index = event.getActionIndex();
                        mActivePointerId = event.getPointerId(index);
                        mPrimaryLastX = MotionEventCompat.getX(event,index);
                        mPrimaryLastY = MotionEventCompat.getY(event,index);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        index = event.getActionIndex();
                        mSecondaryPointerId = event.getPointerId(index);
                        mSecondaryLastX = event.getX(index);
                        mSecondaryLastY = event.getY(index);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        index = event.findPointerIndex(mActivePointerId);
                        int secondaryIndex = MotionEventCompat.findPointerIndex(event,mSecondaryPointerId);
                        final float x = MotionEventCompat.getX(event,index);
                        final float y = MotionEventCompat.getY(event,index);
                        final float secondX = MotionEventCompat.getX(event,secondaryIndex);
                        final float secondY = MotionEventCompat.getY(event,secondaryIndex);
                        changeCamera(CameraUpdateFactory.scrollBy(x, y),null);
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mActivePointerId = INVALID_ID;
                        mPrimaryLastX =-1;
                        mPrimaryLastY = -1;
                        break;
                }

            }
        });
    }
    private final static int INVALID_ID = -1;
    private int mActivePointerId = INVALID_ID;
    private int mSecondaryPointerId = INVALID_ID;
    private float mPrimaryLastX = -1;
    private float mPrimaryLastY = -1;
    private float mSecondaryLastX = -1;
    private float mSecondaryLastY = -1;

    void addPolyline(){

        ArrayList<LatLng> latLngs = new ArrayList<>();
        latLngs.add(new LatLng(29.60309600830078,106.31073760986328));
        latLngs.add(new LatLng(29.60285186767578,106.31073760986328));
        latLngs.add(new LatLng(29.60285186767578,106.31073760986328));
        latLngs.add(new LatLng(29.60309600830078,106.31073760986328));
        latLngs.add(new LatLng(29.603147506713867,106.31073760986328));
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.custtexture));
//        polylineOptions.width(DisplayUtil.dp2px(24));
        polylineOptions.addAll(latLngs);
        Polyline polyline = mAMapNaviView.getMap().addPolyline(polylineOptions);
        polyline.setZIndex(10);
        LatLngBounds.Builder mBuilder = new LatLngBounds.Builder();
        for(LatLng mLatLng:latLngs){
            mBuilder.include(mLatLng);
        }
        mAMapNaviView.getMap().moveCamera(CameraUpdateFactory.newLatLngBounds(mBuilder.build(),10));
    }
//    public boolean onTouchEvent(MotionEvent event) {
//        int action = MotionEventCompat.getActionMasked(event);
//
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                int index = event.getActionIndex();
//                mActivePointerId = event.getPointerId(index);
//                mPrimaryLastX = MotionEventCompat.getX(event,index);
//                mPrimaryLastY = MotionEventCompat.getY(event,index);
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN:
//                index = event.getActionIndex();
//                mSecondaryPointerId = event.getPointerId(index);
//                mSecondaryLastX = event.getX(index);
//                mSecondaryLastY = event.getY(index);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                index = event.findPointerIndex(mActivePointerId);
//                int secondaryIndex = MotionEventCompat.findPointerIndex(event,mSecondaryPointerId);
//                final float x = MotionEventCompat.getX(event,index);
//                final float y = MotionEventCompat.getY(event,index);
//                final float secondX = MotionEventCompat.getX(event,secondaryIndex);
//                final float secondY = MotionEventCompat.getY(event,secondaryIndex);
//                break;
//            case MotionEvent.ACTION_POINTER_UP:
//                xxxxxx(涉及pointer id的转换，之后的文章会讲解)
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                mActivePointerId = INVALID_ID;
//                mPrimaryLastX =-1;
//                mPrimaryLastY = -1;
//                break;
//        }
//        return true;
//    }
    private void changeCamera(CameraUpdate update, AMap.CancelableCallback callback) {

        mAMapNaviView.getMap().moveCamera(update);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapNaviView.onPause();
        //        仅仅是停止你当前在说的这句话，一会到新的路口还是会再说的
        //
        //        停止导航之后，会触及底层stop，然后就不会再有回调了，但是讯飞当前还是没有说完的半句话还是会说完
        //        mAMapNavi.stopNavi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAMapNaviView.onDestroy();
        if (mAMapNavi!=null){
            mAMapNavi.stopNavi();
            /**
             * 当前页面不销毁AmapNavi对象。
             * 因为可能会返回到RestRouteShowActivity页面再次进行路线选择，然后再次进来导航。
             * 如果销毁了就没办法在上一个页面进行选择路线了。
             * 但是AmapNavi对象始终销毁，那我们就需要在上一个页面用户回退时候销毁了。
             */
            mAMapNavi.removeAMapNaviListener(this);
        }
		AMapNavi.destroy();
    }

    @Override
    public void onInitNaviFailure() {
        Toast.makeText(this, "init navi Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitNaviSuccess() {
    }

    @Override
    public void onStartNavi(int type) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation location) {

    }

    @Override
    public void onGetNavigationText(int type, String text) {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

        mAMapNavi.stopNavi();
        mAMapNavi.stopSpeak();

        //清空导航结束页面内容
        mAMapNaviView.getMap().clear();
        mAMapNaviView.reInit(savedInstanceState);

        //隐藏当前导航结束的view
        AMapNaviViewOptions options = new AMapNaviViewOptions();
        options.setLayoutVisible(false);
        mAMapNaviView.setViewOptions(options);

        //隐藏导航自车标
        mAMapNaviView.setCarOverlayVisible(false);

        //开启巡航模式
        mAMapNavi.stopAimlessMode();
        mAMapNavi.startAimlessMode(AimLessMode.CAMERA_AND_SPECIALROAD_DETECTED);
    }

    @Override
    public void onArriveDestination() {
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int wayID) {

    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
    }

    @Override
    public void onNaviSetting() {
    }

    @Override
    public void onNaviMapMode(int isLock) {

    }

    @Override
    public void onNaviCancel() {
        finish();
    }

    @Override
    public void onNaviTurnClick() {

    }

    @Override
    public void onNextRoadClick() {

    }

    @Override
    public void onScanViewButtonClick() {
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapCameraInfos) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] amapServiceAreaInfos) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
    }

    @Override
    public void hideCross() {
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }

    @Override
    public void onLockMap(boolean isLock) {
    }

    @Override
    public void onNaviViewLoaded() {
    }

    @Override
    public void onMapTypeChanged(int i) {

    }

    @Override
    public void onNaviViewShowMode(int i) {

    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }


    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

    }

    @Override
    public void onGpsSignalWeak(boolean b) {

    }

    @Override
    public void onInnerNaviInfoUpdate(InnerNaviInfo innerNaviInfo) {

    }

    @Override
    public void onInnerNaviInfoUpdate(InnerNaviInfo[] innerNaviInfos) {

    }

    @Override
    public void onUpdateTmcStatus(NaviCongestionInfo naviCongestionInfo) {

    }

    @Override
    public void onStopNavi() {

    }

    @Override
    public void onSelectRouteId(int i) {

    }

    @Override
    public void updateBackupPath(NaviPath[] naviPaths) {

    }

    @Override
    public void onSuggestChangePath(long l, long l1, int i, String s) {

    }

    @Override
    public void onUpdateNaviPath() {

    }

    @Override
    public void onUpdateGpsSignalStrength(int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
