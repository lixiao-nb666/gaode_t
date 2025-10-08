package com.gaode.demo.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.AimLessMode;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviStep;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.model.NaviPoi;

import com.nrmyw.gaode_t.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SingleRouteCalculateActivity extends BaseActivity {
    Bundle savedInstanceState;
    AMapNaviLocation location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;

        setContentView(R.layout.activity_basic_navi);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        Log.e("TAG","导航版本号："+AMapNavi.getVersion()+"  mAMapNaviView.getMap()：" +mAMapNaviView.getMap());
        mAMapNaviView.setAMapNaviViewListener(this);
        mAMapNaviView.getMap().setMapType(AMap.MAP_TYPE_NAVI);
        AMapNaviViewOptions options = new AMapNaviViewOptions();
        options.setTrafficLine(true);//打开交通路况
        mAMapNaviView.setViewOptions(options);
    }

    void getMapScreenShot(){
        ImageView imageView = (ImageView) findViewById(R.id.imageview);
        mAMapNaviView.getMap().getMapScreenShot(new AMap.OnMapScreenShotListener() {
            @Override
            public void onMapScreenShot(Bitmap bitmap) {

            }
            @Override
            public void onMapScreenShot(Bitmap bitmap, int i) {
                imageView.setImageBitmap(bitmap);
                getMapScreenShot();
            }
        });
    }

    int strategy = 20;
    public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// 北京市经纬度
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


        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, true, true, false, true);
//            mAMapNavi.setBroadcastMode(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAMapNavi.setServiceAreaDetailsEnable(true);

        NaviPoi start = new NaviPoi("", new LatLng(29.830356,118.339340), "B022F0MKM0");
        //途经点
        List<NaviPoi> poiList = new ArrayList();
        //终点
        NaviPoi end = new NaviPoi("", null, "B0H1DROMIJ");
        mAMapNavi.calculateDriveRoute(start, end, poiList, strategy);




    }


    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
        super.onCalculateRouteSuccess(aMapCalcRouteResult);
        mAMapNavi.startNavi(NaviType.EMULATOR);


        HashMap<Integer, AMapNaviPath> mPaths =  mAMapNavi.getNaviPaths();


        for (HashMap.Entry<Integer, AMapNaviPath> entry : mPaths.entrySet()) {

            AMapNaviPath mNaviPath = entry.getValue();
            Log.e("TAG", "---mNaviPath ID:"+mNaviPath.getPathid()+"  长度："+mNaviPath.getAllLength());
            //获取主路段所有大路段信息
            List<AMapNaviStep> steps = mAMapNavi.getNaviPath().getSteps();
            for(AMapNaviStep step:steps){
                step.getCoords();//获取该路段所有坐标点
                step.getIconType();//获取当前路段的转向动作类型

                //获取当前大路段所有小路段的信息
//            List<AMapNaviLink> links = step.getLinks();
//            for(AMapNaviLink link:links){
//                link.getLinkType();//道路类型
//                link.getRoadName();//获取道路名称
//            }
//                Log.e("TAG", ""+steps.indexOf(step)+"---getLength:"+step.getLength());

                if(steps.indexOf(step)==8){
                    PolylineOptions mPolylineOptions = new PolylineOptions()
                            .width(40)
                            .color(Color.RED);
                    for(NaviLatLng mNaviLatLng:step.getCoords()){
                        mPolylineOptions.add(new LatLng(mNaviLatLng.getLatitude(),mNaviLatLng.getLongitude()));
                    }
                    mAMapNaviView.getMap().addPolyline(mPolylineOptions);

                }
            }
        }




//        AMapNavi.getInstance(context).getDeviceId(context)

    }

    @Override
    public void onEndEmulatorNavi() {
        super.onEndEmulatorNavi();

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

    int curLinkIndex;
    int curStepIndex;
    @Override
    public void onLocationChange(AMapNaviLocation location) {
//        super.onLocationChange(location);
        curLinkIndex = location.getCurLinkIndex();
        curStepIndex = location.getCurStepIndex();
        Log.d("TAG","Bearing:"+location.getBearing());
        //当前位置回调
//        Log.e("TAG","onLocationChange  getCurStepIndex:"+location.getCurStepIndex()  +
//                "\ngetCurStepIndex:"+location.getCurLinkIndex()+
//                "\ntime:"+location.getTime()+
//                "\nRoadBearing:"+location.getRoadBearing());

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {

        AMapNaviStep mAMapNaviStep = mAMapNavi.getNaviPath().getSteps().get(naviinfo.getCurStep());
//        super.onNaviInfoUpdate(naviinfo);

//        Log.e("TAG","onNaviInfoUpdate:  " +
//                "\ngetPathId:"+naviinfo.getPathId() +
//                "\ngetCurStep:"+naviinfo.getCurStep()+
//                "\ngetLength:"+mAMapNaviStep.getLength()+
//                "\ngetCurStepRetainDistance:"+naviinfo.getCurStepRetainDistance()+
//                "\ngetPathRetainDistance:"+naviinfo.getPathRetainDistance());


    }


}
