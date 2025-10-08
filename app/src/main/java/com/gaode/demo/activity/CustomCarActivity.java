package com.gaode.demo.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.GroundOverlayOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.MapStyle;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.NaviPoi;
import com.nrmyw.gaode_t.R;


import java.util.ArrayList;


public class CustomCarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basic_navi);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
//        mAMapNaviView.getMap().setMapType(AMap.MAP_TYPE_NIGHT);

        AMapNaviViewOptions options = new AMapNaviViewOptions();
        options.setCarBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.customcar));
        options.setFourCornersBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.navi_direction));
        options.setStartPointBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.navi_start));
        options.setWayPointBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.navi_way));
        options.setEndPointBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.navi_end));
        options.setMapStyle(MapStyle.NIGHT,null);
        options.setSettingMenuEnabled(true);

        mAMapNaviView.setViewOptions(options);

        mWayPointList = new ArrayList();
//        mWayPointList.add(new NaviLatLng(39.925846, 116.442765));

        addOverlayToMap();
//
//        findViewById(R.id.night).setOnClickListener(v -> {
            AMapNaviViewOptions option = mAMapNaviView.getViewOptions();
            options.setMapStyle(MapStyle.NIGHT,null);
            mAMapNaviView.setViewOptions(option);
//            startActivity(new Intent(this,EmulatorActivity.class));
//
//        });
//        findViewById(R.id.day).setOnClickListener(v -> {
//            AMapNaviViewOptions option = mAMapNaviView.getViewOptions();
//            options.setMapStyle(MapStyle.DAY,null);
//            mAMapNaviView.setViewOptions(option);
//        });
    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
        int strategy = 0;
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //起点经纬度
        NaviPoi startPoi = new NaviPoi("起点地址",new LatLng(mStartLatlng.getLatitude(),mStartLatlng.getLongitude()),"");
        //终点经纬度
        NaviPoi endPoi = new NaviPoi("终点地址",new LatLng(mEndLatlng.getLatitude(),mEndLatlng.getLongitude()),"");
        //开启导航路径规划
        mAMapNavi.calculateDriveRoute(startPoi, endPoi, null,strategy);
    }

    //规划路径成功
    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
        super.onCalculateRouteSuccess(aMapCalcRouteResult);
        //开启导航
        mAMapNavi.startNavi(NaviType.EMULATOR);
    }

    private void addOverlayToMap() {

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(new LatLng(45.119587,179.831113,false))
                .include(new LatLng(45.731286,185.993105)).build();

        mAMapNaviView.getMap().addGroundOverlay(new GroundOverlayOptions()
                .anchor(0.5f, 0.5f)
                .transparency(0.7f)
//				.zIndex(GlobalConstants.ZindexLine - 1)
                .image(BitmapDescriptorFactory
                        .fromResource(R.drawable.groundoverlay))
						.position(new LatLng(39.825934,116.342972),1000f,1000f));


//                .positionFromBounds(bounds));

        mAMapNaviView.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.119587,179.831113), 18));// 设置当前地图显示为北京市恭王府
    }
}
