package com.gaode.demo.activity;

import android.content.res.AssetManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.enums.PathPlanningStrategy;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;

import com.nrmyw.gaode_t.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class UseExtraGpsDataActivity extends BaseActivity {

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basic_navi);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        AMapNaviViewOptions options = mAMapNaviView.getViewOptions();
        options.setSettingMenuEnabled(true);
        mAMapNaviView.setViewOptions(options);
        mAMapNaviView.setCarOverlayVisible(true);

        mAMapNaviView.setAMapNaviViewListener(this);
        if (mAMapNavi!=null){
            //使用外部GPS数据
            mAMapNavi.setIsUseExtraGPSData(true);

        }

        Toast.makeText(this, "点击右下角menu按钮实现设置外部GPS", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNaviSetting() {
        super.onNaviSetting();

    }

    public void setExtraGPSData() {

        AMapNaviPath mAMapNaviPath = null;
        for (AMapNaviPath path : mAMapNavi.getNaviPaths().values()) {
            if (path.getPathid() == mAMapNavi.getNaviPath().getPathid()) {
                continue;
            }
            mAMapNaviPath = path;
        }

        if(mAMapNaviPath == null)
            return;

        List<NaviLatLng> list =  mAMapNaviPath.getCoordList();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(list.size()==0 || i>=list.size())
                    return;
                NaviLatLng mNaviLatLng = list.get(i++);
                Location location = new Location(LocationManager.GPS_PROVIDER);
                location.setLongitude(mNaviLatLng.getLongitude());
                location.setLatitude(mNaviLatLng.getLatitude());
                location.setSpeed(15);
                location.setAccuracy(1);
                location.setBearing(new Random().nextInt(65)+ 64);
                location.setTime(System.currentTimeMillis());

                //以上6项数据缺一不可!!!
                mAMapNavi.setExtraGPSData(2, location);

                //Toast.makeText(getApplicationContext(), location.toString(), Toast.LENGTH_SHORT).show();
            }
        },0,1000);

    }
//    public void setExtraGPSData() {
//        List<Location> points = parseLocationsData(this.getAssets(),"test.txt");
//
//
//
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if(points.size()==0 || i>=points.size())
//                    return;
//                Location location = points.get(i++);
//                location.setTime(System.currentTimeMillis());
//                //以上6项数据缺一不可!!!
//                mAMapNavi.setExtraGPSData(2, location);
//
//                //Toast.makeText(getApplicationContext(), location.toString(), Toast.LENGTH_SHORT).show();
//            }
//        },0,1000);
//
//
//    }


    @Override
    public void onInitNaviSuccess() {

//        NaviPoi start = new NaviPoi("", new LatLng(31.419376,120.64007), "");
//        //途经点
//        List<NaviPoi> poiList = new ArrayList();
////        poiList.add(new NaviPoi("",new LatLng(30.550361, 104.063925),""));
//        //终点
//        NaviPoi end = new NaviPoi("", new LatLng(31.316626,120.677934), "");

//        mAMapNavi.calculateDriveRoute(start, end, poiList, PathPlanningStrategy.DRIVING_MULTIPLE_ROUTES_DEFAULT);
//        mAMapNavi.setMultipleRouteNaviMode(true);
        sList.clear();
        sList.add(new NaviLatLng(38.113001,114.437611));
        eList.clear();
        eList.add(new NaviLatLng(38.22397,114.152966));
//
        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, PathPlanningStrategy.DRIVING_MULTIPLE_ROUTES_DEFAULT);


    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
        super.onCalculateRouteSuccess(aMapCalcRouteResult);
        Log.e("TAG","mAMapNavi.getNaviPaths():"+mAMapNavi.getNaviPaths().size());

        mAMapNavi.startNavi(NaviType.GPS);
        setExtraGPSData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAMapNavi!=null){
            mAMapNavi.setIsUseExtraGPSData(false);
        }

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
        super.onNaviInfoUpdate(naviinfo);
        Log.e("TAG","onNaviInfoUpdate:"+naviinfo.getNextRoadName()+"    时间："+naviinfo.getCurStepRetainTime());
    }

    public  List<Location> parseLocationsData(
            AssetManager mAssetManager, String filePath) {
        List<Location> locLists = new ArrayList<Location>();
        InputStream input = null;
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            input = mAssetManager.open(filePath);
            inputReader = new InputStreamReader(input);
            bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                JSONObject obj = new JSONObject(line);
                Location location = new Location("gps");;
                location.setLongitude(obj.optDouble("mLongitude"));
                location.setLatitude(obj.optDouble("mLatitude"));
                location.setSpeed(Float.parseFloat(obj.getString("mSpeed")));
                location.setAccuracy(1);
                location.setBearing(new Random().nextInt(65)+ 64);
                location.setTime(obj.optLong("mTime"));
                locLists.add(location);
            }
        } catch (Exception e) {
            Log.e("TAG","Exception:"+e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (bufReader != null) {
                    bufReader.close();
                    bufReader = null;
                }
                if (inputReader != null) {
                    inputReader.close();
                    inputReader = null;
                }
                if (input != null) {
                    input.close();
                    input = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return locLists;
    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] amapServiceAreaInfos) {
        super.onServiceAreaUpdate(amapServiceAreaInfos);
        Log.e("TAG","amapServiceAreaInfos--------------------------------------");
        for (AMapServiceAreaInfo mAMapServiceAreaInfo:amapServiceAreaInfos){
            Log.e("TAG","amapServiceAreaInfos----name:"+mAMapServiceAreaInfo.getName()+"     type:"+mAMapServiceAreaInfo.getType()+"     详情"+mAMapServiceAreaInfo.getDetail());
        }

    }
}
