package com.gaode.demo.activity;

import android.os.Bundle;
import android.util.Log;

import com.amap.api.maps.AMapException;
import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.enums.TravelStrategy;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapNaviToViaInfo;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviPoi;
import com.nrmyw.gaode_t.R;

import java.util.ArrayList;


public class RideRouteCalculateActivity extends BaseActivity {

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
//        mAMapNaviView = new AMapNaviView(this);

        mAMapNaviView.onCreate(savedInstanceState);
//        mAMapNaviView.setAMapNaviViewListener(this);
//        mAMapNaviView.setNaviMode(3);
//
        AMapNaviViewOptions options = new AMapNaviViewOptions();

        mAMapNaviView.setViewOptions(options);
        mAMapNaviView.setNaviMode(AMapNaviView.NORTH_UP_MODE);
    }

    //途经点
//    LatLng mLatLng = new LatLng(22.697948,114.244904);
    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();

        calculateRideRoute();

//        mAMapNavi.calculateRideRoute(mStartLatlng,mEndLatlng);
    }

    void calculateRideRoute(){
        //起点
        NaviPoi start = new NaviPoi("", new LatLng(39.900712,116.411773), "");


        ArrayList<NaviPoi> poiList = new ArrayList();
//        poiList.add(new NaviPoi("",mLatLng,""));
        //终点
        NaviPoi end = new NaviPoi("", new LatLng(39.882914,116.375037), "");
        mAMapNavi.calculateRideRoute(start,
                poiList,end, TravelStrategy.MULTIPLE
        );
    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {


//        List<AMapNaviStep> steps = mAMapNavi.getNaviPath().getSteps();
//        for(AMapNaviStep step:steps){
//            List<NaviLatLng> list =  step.getCoords();//获取该路段所有坐标点
//            Log.d("TAG"," step.getCoords():"+ step.getCoords());
//            step.getIconType();//获取当前路段的转向动作类型
//            for(AMapNaviLink link:step.getLinks()){
//                for(NaviLatLng mNaviLatLng:link.getCoords()){
//                    float distance = AMapUtils.calculateLineDistance(new LatLng(mNaviLatLng.getLatitude(),mNaviLatLng.getLongitude()),mLatLng);
//                    Log.e("TAG", "途经点"+steps.indexOf(step)+"---是否有途经点："+distance);
//                }
//
//            }
//            Log.e("TAG", ""+steps.indexOf(step)+"---getLength:"+step.getLength());
//        }

//        Log.e("TAG","主路线Id："+mAMapNavi.getNaviPath().getPathid());
//        int size = mAMapNavi.getNaviPaths().size();
//        Log.e("TAG","规划路线数量"+size);
//        if(size>1){
//            HashMap<Integer, AMapNaviPath> map =  mAMapNavi.getNaviPaths();
//            Iterator<Map.Entry<Integer, AMapNaviPath>> iterator = map.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<Integer, AMapNaviPath> entry = iterator.next();
//                Integer key = entry.getKey();
//                AMapNaviPath mAMapNaviPath = entry.getValue();
//                Log.e("TAG",key+":"+mAMapNaviPath.getPathid());
//                if(mAMapNaviPath.getPathid() != mAMapNavi.getNaviPath().getPathid()){
//                    mAMapNavi.selectRouteId(Integer.parseInt(""+mAMapNaviPath.getPathid()));
//                    Log.e("TAG","当前Id"+mAMapNaviPath.getPathid());
//                    break;
//                }
//            }
//
//        }
//
//        mAMapNaviView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.e("TAG","修改后的路线Id："+mAMapNavi.getNaviPath().getPathid());
//            }
//        },2000);

        mAMapNavi.startNavi(NaviType.EMULATOR);
    }

    @Override
    public void  onNaviInfoUpdate(NaviInfo naviInfo){
        Log.e("TAG","onNaviInfoUpdate naviInfo:"+naviInfo.getToViaInfo());
        for(AMapNaviToViaInfo mAMapNaviToViaInfo :naviInfo.getToViaInfo()){
            Log.e("TAG","mAMapNaviToViaInfo:"+mAMapNaviToViaInfo.getDistance()+"   time:"+mAMapNaviToViaInfo.getTime());
        }
    }

    @Override
    public void onEndEmulatorNavi() {
        super.onEndEmulatorNavi();
//        mAMapNavi.destroy();
        calculateRideRoute();
//        mAMapNaviView.getMap().moveCamera(CameraUpdateFactory.changeBearing(70));
    }
}
