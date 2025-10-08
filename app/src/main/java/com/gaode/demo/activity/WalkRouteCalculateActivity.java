package com.gaode.demo.activity;

import android.os.Bundle;
import android.util.Log;

import com.amap.api.maps.AMapException;
import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.enums.TravelStrategy;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviPoi;

import com.gaode.demo.util.NaviUtil;
import com.nrmyw.gaode_t.R;

import java.util.ArrayList;
import java.util.List;


public class WalkRouteCalculateActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            AMapNavi.getInstance(getApplicationContext()).setIsNaviTravelView(true);
        } catch (AMapException e) {
            throw new RuntimeException(e);
        }
        setContentView(R.layout.activity_basic_navi);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
        mAMapNaviView.setNaviMode(AMapNaviView.NORTH_UP_MODE);



    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        NaviPoi start = new NaviPoi("", new LatLng(39.925846, 116.435765),"");
        NaviPoi end = new NaviPoi("", new LatLng(39.925846, 116.532765),"");

        NaviPoi poi1 = new NaviPoi("", new LatLng(30.284426,120.193128), "BX10020608");
//        NaviPoi poi2 = new NaviPoi("", new LatLng(30.282264,120.200756), "B0FFGFW9U1");
        NaviPoi poi2 = new NaviPoi("", new LatLng(30.282264,120.200756), "BV09056731");
        List<NaviPoi> wList = new ArrayList<>();

//        wList.add(new NaviPoi("",new LatLng(39.926455,116.434703),""));
//        wList.add(new NaviPoi("",new LatLng(39.926891,116.436162),""));


        mAMapNavi.calculateWalkRoute(poi1,null,poi2, TravelStrategy.MULTIPLE);
    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
        super.onCalculateRouteSuccess(aMapCalcRouteResult);
        Log.e("TAG","onCalculateRouteSuccess:"+mAMapNavi.getNaviPaths().size());
        mAMapNavi.startNavi(NaviType.EMULATOR);
    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        super.onNaviInfoUpdate(naviInfo);

        /**
         * 更新下一路口 路名及 距离
         */
        Log.e("TAG","下个路口:"+naviInfo.getNextRoadName()+" 当前路段剩余距离:"+ NaviUtil.formatKM(naviInfo.getCurStepRetainDistance())+"当前路段剩余时间"+naviInfo.getCurStepRetainTime()+"  总路程剩余距离："+naviInfo.getPathRetainDistance());

    }
}
