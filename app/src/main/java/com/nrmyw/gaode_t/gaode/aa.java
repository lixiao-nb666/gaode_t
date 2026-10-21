package com.nrmyw.gaode_t.gaode;//package com.nrmyw.gaode_t.gaode;
//
//import com.amap.api.navi.AMapNaviDriveListener;
//import com.amap.api.navi.model.AMapCalcRouteResult;
//import com.amap.api.navi.model.AMapLaneInfo;
//import com.amap.api.navi.model.AMapModelCross;
//import com.amap.api.navi.model.AMapNaviCameraInfo;
//import com.amap.api.navi.model.AMapNaviCross;
//import com.amap.api.navi.model.AMapNaviDriveComfort;
//import com.amap.api.navi.model.AMapNaviDriveEvent;
//import com.amap.api.navi.model.AMapNaviLocation;
//import com.amap.api.navi.model.AMapNaviRouteNotifyData;
//import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
//import com.amap.api.navi.model.AMapServiceAreaInfo;
//import com.amap.api.navi.model.AimLessModeCongestionInfo;
//import com.amap.api.navi.model.AimLessModeStat;
//import com.amap.api.navi.model.NaviInfo;
//import com.amap.api.navi.view.TrafficProgressBar;
//import com.nrmyw.gaode_t.ble.bluetoothgatt.BlueToothGattSendImageManager;
//import com.nrmyw.gaode_t.log.LG;
//import com.nrmyw.gaode_t.t800.type.T800BitmapQualityType;
//import com.nrmyw.gaode_t.t800.type.T800TrunType;
//import com.nrmyw.gaode_t.t800.util.T800SendUtil;
//
//public class aa {
//    private AMapNaviDriveListener aMapNaviDriveListener=new AMapNaviDriveListener() {
//        @Override
//        public void onUpdateDriveComfort(AMapNaviDriveComfort aMapNaviDriveComfort) {
//
//        }
//
//        @Override
//        public void onUpdateDriveEvent(AMapNaviDriveEvent aMapNaviDriveEvent) {
//
//        }
//
//        @Override
//        public void onUpdateNaviSpeedLimitSection(int i) {
//            LG.i("区间限速为:"+i);
//        }
//
//        @Override
//        public void onInitNaviFailure() {
//
//        }
//
//        @Override
//        public void onInitNaviSuccess() {
//
//        }
//
//        @Override
//        public void onStartNavi(int i) {
//
//        }
//
//        @Override
//        public void onTrafficStatusUpdate() {
//
//        }
//
//        @Override
//        public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
//
//        }
//
//        @Override
//        public void onGetNavigationText(int i, String s) {
//
//        }
//
//        @Override
//        public void onGetNavigationText(String s) {
//
//        }
//
//        @Override
//        public void onEndEmulatorNavi() {
//
//        }
//
//        @Override
//        public void onArriveDestination() {
//            LG.i(tag,"到达目的地后回调函数。");
//            T800SendUtil.sendTrunType(T800TrunType.numb_201,0);
//        }
//
//        @Override
//        public void onCalculateRouteFailure(int i) {
//
//        }
//
//        @Override
//        public void onReCalculateRouteForYaw() {
//
//        }
//
//        @Override
//        public void onReCalculateRouteForTrafficJam() {
//
//        }
//
//        @Override
//        public void onArrivedWayPoint(int i) {
//            LG.i(tag,"驾车路径导航到达某个途经点的回调函数。"+i);
//        }
//
//        @Override
//        public void onGpsOpenStatus(boolean b) {
//
//        }
//
//        @Override
//        public void onNaviInfoUpdate(NaviInfo naviInfo) {
//
//        }
//
//        @Override
//        public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {
//
//        }
//
//        @Override
//        public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {
//
//        }
//
//        @Override
//        public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {
//
//        }
//
//        @Override
//        public void showCross(AMapNaviCross aMapNaviCross) {
//            LG.i(tag,"打开路口实景图");
//            BlueToothGattSendImageManager.getInstance().sendBitMap(aMapNaviCross.getBitmap(), T800BitmapQualityType.LOW);
//        }
//
//        @Override
//        public void hideCross() {
//            LG.i(tag,"关闭路口实景图");
//            T800SendUtil.hideImage();
//        }
//
//        @Override
//        public void showModeCross(AMapModelCross aMapModelCross) {
//
//        }
//
//        @Override
//        public void hideModeCross() {
//            LG.i(tag,"关闭路口放大图回调(模型图)。");
//            T800SendUtil.hideImage();
//        }
//
//        @Override
//        public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {
//
//        }
//
//        @Override
//        public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {
//
//        }
//
//        @Override
//        public void hideLaneInfo() {
//            LG.i(tag,"关闭道路信息回调。");
//            T800SendUtil.sendLaneHide();
//        }
//
//        @Override
//        public void onCalculateRouteSuccess(int[] ints) {
//
//        }
//
//        @Override
//        public void notifyParallelRoad(int i) {
//            LG.i(tag,"已过时。 通知当前可以进行平行道路切换，包括主路、辅路以及高架桥上、高架桥下"+i);
//        }
//
//        @Override
//        public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
//
//        }
//
//        @Override
//        public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
//
//        }
//
//        @Override
//        public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
//
//        }
//
//        @Override
//        public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
//
//        }
//
//        @Override
//        public void onPlayRing(int i) {
//
//        }
//
//        @Override
//        public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
//
//        }
//
//        @Override
//        public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {
//
//        }
//
//        @Override
//        public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {
//
//        }
//
//        @Override
//        public void onGpsSignalWeak(boolean b) {
//            TrafficProgressBar
//        }
//    };
//
//}
