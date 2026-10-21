package com.nrmyw.gaode_t.gaode;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviDriveListener;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.ParallelRoadListener;
import com.amap.api.navi.enums.AMapNaviParallelRoadStatus;
import com.amap.api.navi.enums.CarEnterCameraStatus;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviDriveComfort;
import com.amap.api.navi.model.AMapNaviDriveEvent;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AMapTrafficStatus;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.nrmyw.gaode_t.app.MyApp;
import com.nrmyw.gaode_t.config.GaoDeHudSendConfig;
import com.nrmyw.gaode_t.event.GaoDeNaviEventImp;
import com.nrmyw.gaode_t.event.navi.GaoDeEventManager;
import com.nrmyw.gaode_t.log.LG;
import com.nrmyw.gaode_t.type.NaviSdkGpsType;
import com.nrmyw.gaode_t.view.HudManagerAndSetView;
import com.nrmyw.hud_data_lib.type.image.HudImageType;
import com.nrmyw.hud_manager_lib.type.HudDisplayMold;

import java.util.List;


public class GaoDeListenManager {
    private static GaoDeListenManager gaoDeListenManager;
    private final String tag="GaoDeListenManager";
    private final String amap_tag=tag+"_t";
    private final String amap_road_tag=tag+"_road_t";
    private INaviInfoCallback iNaviInfoCallback=new INaviInfoCallback() {
        @Override
        public void onInitNaviFailure() {
            //导航初始化失败时的回调函数。
        }

        /**
         * 导航播报信息回调函数。
         *
         * @param s 播报文字。
         * @since 5.2.0
         */
        @Override
        public void onGetNavigationText(String s) {
            //导航播报信息回调函数。
            eventImp().sendNavigationText(s);
        }


        /**
         * 当GPS位置有更新时的回调函数。
         *
         * @param aMapNaviLocation 当前位置的定位信息。
         * @since 5.2.0
         */
        @Override
        public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
//            Log.i(tag,"AMapNaviLocation()"+aMapNaviLocation.getLocationType());
            //这里可以获取当前的速度
//            GaoDeEventManager.getInstance().eventSpeed((int) aMapNaviLocation.getSpeed());

            int nowSpeed= (int) aMapNaviLocation.getSpeed();
            eventImp().updateNowSpeed(nowSpeed);
            Log.i("kankanindex","kankanindex1:---"+aMapNaviLocation.getCurLinkIndex());
            Log.i("kankanindex","kankanindex2:---"+aMapNaviLocation.getCurPointIndex());
            Log.i("kankanindex","kankanindex3:---"+aMapNaviLocation.getCurStepIndex());
            hudManagerAndSetView.setLocationChange(aMapNaviLocation);

            //DEMO
//            HudManager.getInstance().getHudEvent().sendWarningPoint();
        }

        @Override
        public void onArriveDestination(boolean b) {
            //到达目的地后回调函数。
            eventImp().onArriveDestination();
        }

        @Override
        public void onStartNavi(int i) {
            //启动导航后的回调函数
        }

        @Override
        public void onCalculateRouteSuccess(int[] ints) {
            //算路成功回调
        }

        @Override
        public void onCalculateRouteFailure(int i) {
            //驾车路径规划失败后的回调函数。
        }

        @Override
        public void onStopSpeaking() {
            //停止播报回调
            //当退出组件导航页，或切换组件的播报模式为静音的时候，会触发该回调。
        }

        @Override
        public void onReCalculateRoute(int i) {


            //重新规划的回调
        }

        @Override
        public void onExitPage(int i) {
                //退出组件或退出组件导航的回调函数
        }

        @Override
        public void onStrategyChanged(int i) {
            //

        }

        @Override
        public View getCustomNaviBottomView() {
            //返回null则不显示自定义区域
//            return getCustomView("底部自定义区域");
            //获取导航地图自定义View，该View在导航整体界面的下面，注意要设置setLayoutParams并且设置高度
            return getCustomView(MyApp.getContext(),"1234");
        }

        @Override
        public View getCustomNaviView() {
            //返回null则不显示自定义区域
//            return getCustomView("中部自定义区域");
            //获取导航地图自定义View,该View在导航界面的当前路名位置，使用该方法以后，将不会显示当前路名
            return null;
        }

        @Override
        public void onArrivedWayPoint(int i) {
            //经过途经点
        }

        @Override
        public void onMapTypeChanged(int i) {
//            public static final int MAP_TYPE_NORMAL = 1;
//            public static final int MAP_TYPE_SATELLITE = 2;
//            public static final int MAP_TYPE_NIGHT = 3;
//            public static final int MAP_TYPE_NAVI = 4;
//            public static final int MAP_TYPE_BUS = 5;
//            public static final int MAP_TYPE_NAVI_NIGHT = 6;
            switch (i){
                case 1:
                case 4:
                    HudDisplayMold.DAY.setLowLuminance(1);
                    HudDisplayMold.DAY.setHightLuminance(100);
//                    HudLuminaceManager.getInstance().setHudDisplayMold(HudDisplayMold.DAY);
                    break;
                case 3:
                case 6:
                    HudDisplayMold.NIGHT.setLowLuminance(1);
                    HudDisplayMold.NIGHT.setHightLuminance(100);
//                    HudLuminaceManager.getInstance().setHudDisplayMold(HudDisplayMold.NIGHT);
                    break;
            }

            //组件地图白天黑夜模式切换回调
            Log.e("TAG","onDayAndNightModeChanged11:"+i);
        }

        @Override
        public View getCustomMiddleView() {
            //获取导航地图自定义View,该View在导航界面的垂直居中，水平靠左位置
            return null;
        }

        @Override
        public void onNaviDirectionChanged(int i) {
            //  Log.e("TAG","onNaviDirectionChanged");

        }

        @Override
        public void onDayAndNightModeChanged(int i) {
                 Log.e("TAG","onDayAndNightModeChanged22:"+i);


        }

        @Override
        public void onBroadcastModeChanged(int i) {
//            Log.e("TAG","onBroadcastModeChanged");
            //播报模式变化回调
        }

        @Override
        public void onScaleAutoChanged(boolean b) {
          //比例尺智能缩放设置变化回调

        }
    };

    AMapNaviListener aMapNaviListener=new AMapNaviListener() {
        @Override
        public void onInitNaviFailure() {
//            onInitNaviFailure()
//            导航初始化失败时的回调函数。
        }

        @Override
        public void onInitNaviSuccess() {
//            onInitNaviSuccess()
//            导航初始化成功时的回调函数。
        }

        @Override
        public void onStartNavi(int i) {
//            LG.i(tag,"启动导航后的回调函数。"+i);
            eventImp().start(i);

        }

        @Override
        public void onTrafficStatusUpdate() {
            //当前方路况光柱信息有更新时回调函数。
        }

        @Override
        public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

//            当位置信息有更新时的回调函数。
            //当GPS位置有更新时的回调函数。
        }

        @Override
        public void onGetNavigationText(int i, String s) {
//            onGetNavigationText(int type, java.lang.String text)
//            导航播报信息回调函数。
        }

        @Override
        public void onGetNavigationText(String s) {

//            onGetNavigationText(java.lang.String text)
//            已过时。
        }

        @Override
        public void onEndEmulatorNavi() {
//            LG.i(tag,"模拟导航停止后回调函数。");

            eventImp().onArriveDestination();
        }

        @Override
        public void onArriveDestination() {
//            LG.i(tag,"到达目的地后回调函数。");
            eventImp().onArriveDestination();

        }

        @Override
        public void onCalculateRouteFailure(int i) {
//            onCalculateRouteFailure(int errorInfo)
//            已过时。
//            该方法在6.1.0版本废弃，但是还会正常回调，建议使用AMapNaviListener.onCalculateRouteFailure(AMapCalcRouteResult) 方法替换
        }

        @Override
        public void onReCalculateRouteForYaw() {

//            偏航后准备重新规划路线前的通知回调。
        }

        @Override
        public void onReCalculateRouteForTrafficJam() {

//            驾车导航时，当前方遇到拥堵时准备重新规划路线前的通知回调。
        }

        @Override
        public void onArrivedWayPoint(int i) {
//            LG.i(tag,"驾车路径导航到达某个途经点的回调函数。"+i);
            //驾车路径导航到达某个途经点的回调函数。
        }

        @Override
        public void onGpsOpenStatus(boolean b) {
//            用户手机位置信息设置是否开启的回调函数。
            if(b){
                eventImp().sendGpsStatu(NaviSdkGpsType.ON);
            }else {
                eventImp().sendGpsStatu(NaviSdkGpsType.OFF);
            }

        }

        @Override
        public void onGpsSignalWeak(boolean isWeak) {
//            onGpsSignalWeak(boolean isWeak)
//            手机卫星定位信号强弱变化的回调
            if(isWeak){
                eventImp().sendGpsStatu(NaviSdkGpsType.LOST);
            }else {
                eventImp().sendGpsStatu(NaviSdkGpsType.ON);
            }
        }

        @Override
        public void onNaviInfoUpdate(NaviInfo naviInfo) {
//            onNaviInfoUpdate(NaviInfo naviInfo)
//            导航引导信息回调。

            GaoDeEventManager.getInstance().event(naviInfo);
            Log.i("kankanyichang","kankanqichang1111:"+naviInfo.toString());
            try {
                AMapNavi  mAMapNavi = AMapNavi.getInstance(MyApp.getContext());
                int allLength = mAMapNavi.getNaviPath().getAllLength();
                List<AMapTrafficStatus> trafficStatuses = mAMapNavi.getTrafficStatuses(0, 0);
                hudManagerAndSetView.update(allLength, naviInfo.getPathRetainDistance(), trafficStatuses);
            }catch (Exception e){
                Log.i("kankanyichang","kankanqichang:"+e.toString());
            }

        }

        @Override
        public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {
//            updateCameraInfo(AMapNaviCameraInfo[] infoArray)
//            导航过程中的摄像头信息回调函数 注意：该接口仅驾车模式有效
            /**
             * todo 摄像头信息这里要做
             */
            GaoDeEventManager.getInstance().cameraEvent(aMapNaviCameraInfos);

        }

        @Override
        public void updateIntervalCameraInfo(AMapNaviCameraInfo startCameraInfo, AMapNaviCameraInfo endCameraInfo, int status) {
//            updateIntervalCameraInfo(AMapNaviCameraInfo startCameraInfo, AMapNaviCameraInfo endCameraInfo, int status)
//            导航过程中的区间测速信息回调函数 注意：该接口仅驾车模式有效
            if(status== CarEnterCameraStatus.ENTER){
                int intervalSpeed=startCameraInfo.getCameraSpeed();
                int interval =startCameraInfo.getIntervalRemainDistance();
                int averageSpeed=startCameraInfo.getAverageSpeed();
                eventImp().sendIntervalInfo(intervalSpeed,interval,averageSpeed);
            }

        }

        @Override
        public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {
//            onServiceAreaUpdate(AMapServiceAreaInfo[] infoArray)
//            服务区信息回调函数 注意：该接口仅驾车模式有效
            /**
             * todo 这里要整，服务区信息
             */
            GaoDeEventManager.getInstance().eventServiceArea(aMapServiceAreaInfos);
//            LG.i("服务区信息有没有","服务区信息有没有:::"+aMapServiceAreaInfos.length);
        }

        @Override
        public void showCross(AMapNaviCross aMapNaviCross) {
            LG.i(tag,"Navie-bitmap----1:开启路口实景图");
//            showCross(AMapNaviCross aMapNaviCross)
//            显示路口放大图回调(实景图)。
           eventImp().sendImage(aMapNaviCross.getBitmap(), HudImageType.IMAGE);

        }

        @Override
        public void hideCross() {
            LG.i(tag,"Navie-bitmap----2:关闭路口实景图");
            eventImp().hideImage();

        }



        @Override
        public void showModeCross(AMapModelCross aMapModelCross) {
            LG.i(tag,"Navie-bitmap----3:显示路口放大图回调(模型图)。1111");
            //这里和客服沟通过了，确定不行，必须要重写ACTIVITY工程量太大了
            hudManagerAndSetView.showMoldView(aMapModelCross);
        }

        @Override
        public void hideModeCross() {
            LG.i(tag,"Navie-bitmap----4:关闭路口放大图回调(模型图)。");
//            GaoDeHudSendUtil.hideImage();
            hudManagerAndSetView.hideMoldView();
        }

        @Override
        public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {
//            LG.i(tag,"已过时。 \n" +
//                    "建议使用AMapNaviListener.showLaneInfo(AMapLaneInfo) 方法替换");
        }

        @Override
        public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {
//            showLaneInfo(AMapLaneInfo laneInfo)
//            显示道路信息回调。
            eventImp().sendLaneInfo(aMapLaneInfo);
        }

        @Override
        public void hideLaneInfo() {
            LG.i(tag,"关闭道路信息回调。");
            eventImp().hideLaneInfo();
        }

        @Override
        public void onCalculateRouteSuccess(int[] ints) {
//            onCalculateRouteSuccess(int[] routeIds)
//            已过时。
//            该方法在6.1.0版本废弃，但是还会正常回调，建议使用AMapNaviListener.onCalculateRouteSuccess(AMapCalcRouteResult) 方法替换
        }

        @Override
        public void notifyParallelRoad(int i) {
            LG.i(tag,"已过时。 通知当前可以进行平行道路切换，包括主路、辅路以及高架桥上、高架桥下"+i);
        }

        @Override
        public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
//            OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] infos)
//            已过时。
//            已过期，建议使用AimlessModeListener.onUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[])
        }

        @Override
        public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
//            OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo)
//            已过时。
        }

        @Override
        public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
//            updateAimlessModeStatistics(AimLessModeStat aimLessModeStat)
//            已过时。
//            已过期，建议使用AimlessModeListener.updateAimlessModeStatistics(AimLessModeStat)
        }

        @Override
        public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
//            updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo)
//            已过时。
//            已过期，建议使用AimlessModeListener.updateAimlessModeCongestionInfo(AimLessModeCongestionInfo)
        }

        @Override
        public void onPlayRing(int i) {
//            onPlayRing(int type)
//            回调各种类型的提示音，类似高德导航"叮".
        }

        @Override
        public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
//            onCalculateRouteSuccess(AMapCalcRouteResult routeResult)
//            路线规划成功回调，包括算路、导航中偏航、用户改变算路策略、行程点等触发的重算，具体算路结果可以通过AMapCalcRouteResult获取 可以通过CalcRouteResult获取算路错误码、算路类型以及路线id
        }

        @Override
        public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {
//            onCalculateRouteFailure(AMapCalcRouteResult routeResult)
//            路线规划失败回调，包括算路、导航中偏航、用户改变算路策略、行程点等触发的重算，具体算路结果可以通过AMapCalcRouteResult获取 可以通过CalcRouteResult获取算路错误码、算路类型以及路线id
        }

        @Override
        public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {
//            onNaviRouteNotify(AMapNaviRouteNotifyData notifyData)
//            导航过程中道路信息通知 注意：该接口仅驾车模式有效 导航过程中针对拥堵区域、限行区域、禁行区域、道路封闭等情况的躲避通知。
            /**
             * to do 这里要整，导航中间的信息
             */
        }


    };
    ParallelRoadListener parallelRoadListener=new ParallelRoadListener() {
        @Override
        public void notifyParallelRoad(AMapNaviParallelRoadStatus aMapNaviParallelRoadStatus) {
            LG.i(amap_road_tag, "notifyParallelRoad:" + aMapNaviParallelRoadStatus.getmElevatedRoadStatusFlag());
        }
    };

    private AMapNaviDriveListener aMapNaviDriveListener=new AMapNaviDriveListener() {
        @Override
        public void onUpdateDriveComfort(AMapNaviDriveComfort aMapNaviDriveComfort) {

        }

        @Override
        public void onUpdateDriveEvent(AMapNaviDriveEvent aMapNaviDriveEvent) {

        }

        @Override
        public void onUpdateNaviSpeedLimitSection(int i) {
            Log.i("kankanxiansu","看看限速："+i);
        }

        @Override
        public void onInitNaviFailure() {

        }

        @Override
        public void onInitNaviSuccess() {

        }

        @Override
        public void onStartNavi(int i) {

        }

        @Override
        public void onTrafficStatusUpdate() {

        }

        @Override
        public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

        }

        @Override
        public void onGetNavigationText(int i, String s) {

        }

        @Override
        public void onGetNavigationText(String s) {

        }

        @Override
        public void onEndEmulatorNavi() {

        }

        @Override
        public void onArriveDestination() {

        }

        @Override
        public void onCalculateRouteFailure(int i) {

        }

        @Override
        public void onReCalculateRouteForYaw() {

        }

        @Override
        public void onReCalculateRouteForTrafficJam() {

        }

        @Override
        public void onArrivedWayPoint(int i) {

        }

        @Override
        public void onGpsOpenStatus(boolean b) {

        }

        @Override
        public void onNaviInfoUpdate(NaviInfo naviInfo) {

        }

        @Override
        public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

        }

        @Override
        public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

        }

        @Override
        public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

        }

        @Override
        public void showCross(AMapNaviCross aMapNaviCross) {

        }

        @Override
        public void hideCross() {

        }

        @Override
        public void showModeCross(AMapModelCross aMapModelCross) {
            hudManagerAndSetView.showMoldView(aMapModelCross);
        }

        @Override
        public void hideModeCross() {
            hudManagerAndSetView.hideMoldView();
        }

        @Override
        public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

        }

        @Override
        public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

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
        public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

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
    };


    private GaoDeListenManager(){
//        aMapNavi = AMapNavi.getInstance(this);
//        aMapNavi.addAMapNaviListener(this);
//        aMapNavi.addAimlessModeListener(this);
//        aMapNavi.addParallelRoadListener(this);
//        aMapNavi.setUseInnerVoice(true,true);
    }

    public static GaoDeListenManager getInstance(){
        if(null==gaoDeListenManager){
            synchronized (GaoDeListenManager.class){
                gaoDeListenManager=new GaoDeListenManager();
            }
        }
        return gaoDeListenManager;
    }



    public INaviInfoCallback getiNaviInfoCallback() {
        return iNaviInfoCallback;
    }

    public AMapNaviListener getaMapNaviListener() {
        return aMapNaviListener;
    }

    public GaoDeNaviEventImp eventImp(){
        return GaoDeHudSendConfig.getInstance().getEventImp();
    }
//    public ParallelRoadListener getParallelRoadListener() {
//        return parallelRoadListener;
//    }

//    public AMapNaviDriveListener getaMapNaviDriveListener() {
//        return aMapNaviDriveListener;
//    }



    private HudManagerAndSetView hudManagerAndSetView;
    private View getCustomView(Context context,String title) {
//        LinearLayout linearLayout = new LinearLayout(context);
////        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300));
//        try {
//            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//            text1 = new TextView(context);
//            text1.setGravity(Gravity.CENTER);
//            text1.setHeight(150);
//            text1.setMinWidth(300);
//            text1.setText(title);
//
//            text2 = new TextView(context);
//            text2.setGravity(Gravity.CENTER);
//            text1.setHeight(150);
//            text2.setMinWidth(300);
//            text2.setText(title);
//            linearLayout.setGravity(Gravity.CENTER);
//            mTrafficBarView= new TrafficProgressBar(context);
////            mTrafficBarView.setUnknownTrafficColor(Color.parseColor("#0091FF"));
////            mTrafficBarView.setSmoothTrafficColor(Color.parseColor("#00BA1F"));
////            mTrafficBarView.setSlowTrafficColor(Color.parseColor("#FFBA00"));
////            mTrafficBarView.setJamTrafficColor(Color.parseColor("#F31D20"));
////            mTrafficBarView.setVeryJamTrafficColor(Color.parseColor("#A8090B"));
//
//            ViewGroup.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 300);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                mTrafficBarView.setBackgroundColor(context.getColor(R.color.blue));
//            }
//            linearLayout.addView(mTrafficBarView,lp );
////            linearLayout.addView(text1, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
////            linearLayout.addView(text2, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            params.height = 300;
//            linearLayout.setLayoutParams(params);
//
////            NaviLatLng mLatLng = new NaviLatLng(40.0805223, 116.603099);
////            AMapNaviMarkerOptions markerOption = new AMapNaviMarkerOptions();
////            markerOption.setPosition(mLatLng);
////            markerOption.setBitmapDescriptor(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.start)));
////            Log.e("TAG","markerOption:"+markerOption.getTitle());
////            AmapNaviPage.getInstance().addMarker(markerOption);
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
        //最后的代码是下面3行
//        myGaoDeSendBitmapView=new MyGaoDeSendBitmapView(context);
//        Log.i("kankanguodajfkja","kankanguodajfkja:"+UiConfig.getMapViewSetViewH()+"--"+UiConfig.h+"---"+UiConfig.w);
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UiConfig.getMapViewSetViewH());
//        myGaoDeSendBitmapView.setLayoutParams(params);
//        myGaoDeSendBitmapView.setMapViewNeedH(UiConfig.getMapViewSetViewH());
//        hudManagerAndSetView=MyGaoDeSendBitmapView.getInstance().getHudManagerAndSetView(context);
        hudManagerAndSetView=new HudManagerAndSetView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hudManagerAndSetView.setLayoutParams(params);
        return hudManagerAndSetView;
    }

}
