package com.gaode.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviMarkerOptions;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.NaviLatLng;
import com.gaode.demo.activity.custom.AllCustomCameraActivity;
import com.gaode.demo.activity.custom.AllCustomCarRouteActivity;
import com.gaode.demo.activity.custom.AllCustomCrossingActivity;
import com.gaode.demo.activity.custom.AllCustomDriveWayActivity;
import com.gaode.demo.activity.custom.AllCustomNaviActivity;
import com.gaode.demo.activity.custom.AllCustomNextRoadInfoActivity;
import com.gaode.demo.activity.custom.AllCustomTrafficBarActivity;
import com.gaode.demo.activity.custom.view.ZoomInIntersectionView;
import com.gaode.demo.activity.view.FeatureView;
import com.nrmyw.gaode_t.R;
import com.gaode.demo.util.CheckPermissionsActivity;
import com.nrmyw.gaode_t.dialog.PrivacyComplianceDialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shixin on 16/8/23.
 * bug反馈QQ:1438734562
 */
public class IndexActivity extends CheckPermissionsActivity implements INaviInfoCallback {

    private static class DemoDetails {
        private final int titleId;
        private final int descriptionId;
        private final Class<? extends android.app.Activity> activityClass;

        public DemoDetails(int titleId, int descriptionId,
                           Class<? extends android.app.Activity> activityClass) {
            super();
            this.titleId = titleId;
            this.descriptionId = descriptionId;
            this.activityClass = activityClass;
        }
    }

    private static class CustomArrayAdapter extends ArrayAdapter<DemoDetails> {
        public CustomArrayAdapter(Context context, DemoDetails[] demos) {
            super(context, R.layout.feature, R.id.title, demos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FeatureView featureView;
            if (convertView instanceof FeatureView) {
                featureView = (FeatureView) convertView;
            } else {
                featureView = new FeatureView(getContext());
            }
            DemoDetails demo = getItem(position);
            featureView.setTitleId(demo.titleId, demo.activityClass!=null);
            return featureView;
        }
    }

    private static final DemoDetails[] DEMOS = {
//		    // 导航组件
            new DemoDetails(R.string.navi_ui, R.string.blank, null),
			// 组件起终点算路
            new DemoDetails(R.string.navi_start_end_poi_calculate_title, R.string.navi_start_end_poi_calculate_desc, IndexActivity.class),
            // 组件起终点算路
            new DemoDetails(R.string.navi_end_poi_calculate_title, R.string.navi_end_poi_calculate_desc, IndexActivity.class),
            // 组件起终点算路
            new DemoDetails(R.string.navi_bywayof_poi_calculate_title, R.string.navi_bywayof_poi_calculate_desc, IndexActivity.class),
            // 直接导航
            new DemoDetails(R.string.navi_ui_navi_title, R.string.navi_ui_navi_desc, IndexActivity.class),

            // 组件起终点算路（白色主题）
            new DemoDetails(R.string.navi_ui_custom_activity, R.string.navi_ui_custom_activity, IndexActivity.class),

            // 路径规划
            new DemoDetails(R.string.navi_route_line, R.string.blank, null),
            new DemoDetails(R.string.navi_map, R.string.navi_map, RouteNaviActivity.class),
            // 驾车路径规划
            new DemoDetails(R.string.navi_route_driver_title, R.string.navi_route_driver_desc, DriverListActivity.class),
            // 摩托车路径规划
            new DemoDetails(R.string.navi_route_motorcycle_title, R.string.navi_route_motorcycle_desc, MotorcycleRouteCalculateActivity.class),
            // 骑行电动车路径规划
            new DemoDetails(R.string.navi_route_ele_bike_title, R.string.navi_route_ele_bike_desc, EleBikeRouteCalculateActivity.class),
            // 步行路径规划
            new DemoDetails(R.string.navi_route_walk_title, R.string.navi_route_walk_desc, WalkRouteCalculateActivity.class),
            // 骑行路径规划
            new DemoDetails(R.string.navi_route_ride_title, R.string.navi_route_ride_desc, RideRouteCalculateActivity.class),
            // 货车路径规划导航
            new DemoDetails(R.string.navi_route_truck_title, R.string.navi_route_truck_title, TruckRouteCalculateActivity.class),
            // 独立路线规划
            new DemoDetails(R.string.navi_route_independent_title, R.string.navi_route_independent_title, IndependentRouteCalculateActivity.class),
            // 导航类型
            new DemoDetails(R.string.navi_type, R.string.blank, null),
            // 内置语音导航
            new DemoDetails(R.string.navi_type_inner_voice, R.string.blank, EmulatorActivity.class),
            // 实时导航
            new DemoDetails(R.string.navi_type_gps_title, R.string.navi_type_gps_desc, GPSNaviActivity.class),
            // 模拟导航
            new DemoDetails(R.string.navi_type_emu_title, R.string.navi_type_emu_desc, EmulatorActivity.class),
            // 货车导航
            new DemoDetails(R.string.navi_type_truck, R.string.blank, SetTruckParamsActivity.class),
            // 智能巡航
            new DemoDetails(R.string.navi_type_intelligent_title, R.string.navi_type_intelligent_desc, IntelligentBroadcastActivity.class),
            // HUD导航
            new DemoDetails(R.string.navi_type_hud_title, R.string.navi_type_hud_desc, HudDisplayActivity.class),


            // 导航UI在自定义
            new DemoDetails(R.string.navi_ui_custom, R.string.blank, null),
            // 自定义车标
            new DemoDetails(R.string.navi_ui_custom_car_icon, R.string.blank, CustomCarActivity.class),
            // 自定义路线UI
            new DemoDetails(R.string.navi_ui_custom_route, R.string.blank, CustomRouteActivity.class),
            // 自定义路线纹理
            new DemoDetails(R.string.navi_ui_custom_route_style, R.string.blank, CustomRouteTextureInAMapNaviViewActivity.class),
            // 自定义路口转向提示
            new DemoDetails(R.string.navi_ui_custom_trun_across_tip, R.string.blank, CustomNextTurnTipViewActivity.class),
            // 正被漠视
            new DemoDetails(R.string.navi_ui_custom_northmode, R.string.blank, NorthModeActivity.class),
            // 自定义全览模式
            new DemoDetails(R.string.navi_ui_custom_wholescan_button, R.string.blank, OverviewModeActivity.class),
            // 自定义指南针
            new DemoDetails(R.string.navi_ui_custom_compass, R.string.blank, CustomDirectionViewActivity.class),
            // 自定义路况按钮
            new DemoDetails(R.string.navi_ui_custom_traffic_button, R.string.blank, CustomTrafficButtonViewActivity.class),
            // 自定义放大缩小按钮
            new DemoDetails(R.string.navi_ui_custom_zoom_button, R.string.blank, CustomZoomButtonViewActivity.class),
            // 自定义路口放大图
            new DemoDetails(R.string.navi_ui_custom_across_overlay, R.string.blank, CustomZoomInIntersectionViewActivity.class),
            // 自定义导航光柱(New)
            new DemoDetails(R.string.navi_ui_custom_traffic_bar_new, R.string.blank, CustomTrafficProgressBarActivity.class),
            // 自定义道路选择
            new DemoDetails(R.string.navi_ui_custom_route_select, R.string.blank, CustomDriveWayViewActivity.class),

            // 导航完全自定义示例
            new DemoDetails(R.string.navi_custom_all, R.string.blank, null),
            // 完全自定义自车位置和绘制路线
            new DemoDetails(R.string.navi_custom_car_route, R.string.blank, AllCustomCarRouteActivity.class),
            // 完全自定义路名、距离、下一路口图标
            new DemoDetails(R.string.navi_custom_road_distance_nexttip, R.string.blank, AllCustomNextRoadInfoActivity.class),
            // 完全自定义路况导航条
            new DemoDetails(R.string.navi_custom_traffic_bar, R.string.blank, AllCustomTrafficBarActivity.class),
            // 完全自定义车道信息
            new DemoDetails(R.string.navi_custom_route_way, R.string.blank, AllCustomDriveWayActivity.class),
            // 完全自定义路口放大图
            new DemoDetails(R.string.navi_custom_crossing, R.string.blank, AllCustomCrossingActivity.class),
            // 完全自定义摄像头违章摄影
            new DemoDetails(R.string.navi_custom_camera, R.string.blank, AllCustomCameraActivity.class),
            // 完全自定义导航
            new DemoDetails(R.string.navi_custom_navi, R.string.blank, AllCustomNaviActivity.class),


            // 导航扩展
            new DemoDetails(R.string.navi_expand, R.string.blank, null),
            // 传入GPS数据导航
            new DemoDetails(R.string.navi_expand_set_gps_data, R.string.blank, UseExtraGpsDataActivity.class),
            // 展示导航路径详情
            new DemoDetails(R.string.navi_expand_route_detail, R.string.blank, GetNaviStepsAndLinksActivity.class),
            // 主辅路切换
            new DemoDetails(R.string.navi_expand_switch_road, R.string.blank, SwitchMasterRoadNaviActivity.class),
            // 科大讯飞语音集成播报
            new DemoDetails(R.string.navi_expand_iflyt_voice, R.string.blank, IflyVoiceActivity.class),
            new DemoDetails(R.string.navi_start_end_poi_calculate_title, R.string.navi_start_end_poi_calculate_desc, TestActivity.class),
            new DemoDetails(R.string.navi_expand_iflyt_voice_, R.string.navi_start_end_poi_calculate_desc, TestActivity.class),
//            new DemoDetails("测试", "", RestRouteShowActivity.class)


    };

    LatLng p1 = new LatLng(39.993266, 116.473193);//首开广场
    LatLng p2 = new LatLng(39.917337, 116.397056);//故宫博物院
    LatLng p3 = new LatLng(39.904556, 116.427231);//北京站
    LatLng p4 = new LatLng(39.773801, 116.368984);//新三余公园(南5环)
    LatLng p5 = new LatLng(40.041986, 116.414496);//立水桥(北5环)

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Log.e("TAG","设备号："+AMapNavi.getDeviceId(getApplicationContext()));
            if (position == 1) {
//                AmapNaviParams params = new AmapNaviParams(new Poi("北京站", p3, ""), null, new Poi("故宫博物院", p2, ""), AmapNaviType.DRIVER);
//                params.setUseInnerVoice(true);
//                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, IndexActivity.this);

                LatLng start = new LatLng(22.600243, 113.846663);//荔景名苑
                LatLng end = new LatLng(22.539784, 113.979431);//深圳世界之窗
//                LatLng start =  new LatLng(30.284426,120.193128);
//                LatLng end =  new LatLng(30.282264,120.200756);
                Poi poi1 = new Poi("荔景", start, "");
                Poi poi2 = new Poi("", end, "之窗");


                AmapNaviParams params = new AmapNaviParams(poi1, null, poi2, AmapNaviType.WALK, AmapPageType.ROUTE);
                params.setMultipleRouteNaviMode(true);
                //传递上下文和导航参数
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params,IndexActivity.this );
            } else if (position == 2) {
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(null, null, new Poi("故宫博物院", p2, ""), AmapNaviType.DRIVER), IndexActivity.this);
            } else if (position == 3) {
                List<Poi> poiList = new ArrayList();
                poiList.add(new Poi("首开广场", p1, ""));
                poiList.add(new Poi("故宫博物院", p2, ""));
                poiList.add(new Poi("北京站", p3, ""));
                AmapNaviParams params = new AmapNaviParams(new Poi("立水桥(北5环)", p5, ""), poiList, new Poi("新三余公园(南5环)", p4, ""), AmapNaviType.DRIVER);
                params.setUseInnerVoice(true);
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, IndexActivity.this);
            } else if (position == 4) {
                //起点
                Poi start = new Poi("", new LatLng(31.52587,120.405323), "");
                //途经点
                List<Poi> poiList = new ArrayList();
                //终点
                Poi end = new Poi("T2机场航站楼", new LatLng(31.505853,120.434371), "B0FFG1A2UI");
                AmapNaviParams amapNaviParams = new AmapNaviParams(start, poiList, end, AmapNaviType.DRIVER, AmapPageType.NAVI);
                amapNaviParams.setUseInnerVoice(true);

                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), amapNaviParams, IndexActivity.this);
            } else if (position == 5) {

                //起点
                Poi start = new Poi("北京首都机场", new LatLng(40.080525, 116.603039), "");
                //终点
                Poi end = new Poi("北京大学", new LatLng(39.941823, 116.426319), "");
                // 组件参数配置
                AmapNaviParams params = new AmapNaviParams(start, new ArrayList<>(), end, AmapNaviType.DRIVER,AmapPageType.ROUTE);
//                AmapNaviParams params = new AmapNaviParams(new Poi("北京站", p3, ""), null, new Poi("故宫博物院", p2, ""), AmapNaviType.DRIVER,AmapPageType.NAVI);
//                params.setUseInnerVoice(true);
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, IndexActivity.this, CustomAmapRouteActivity.class);
            }else {
                DemoDetails demo = (DemoDetails) adapter.getItem(position);
                if (demo.activityClass != null) {
                    startActivity(new Intent(IndexActivity.this, demo.activityClass));
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG","onCreate------------");
        //这是为了应用程序安装完后直接打开，按home键退出后，再次打开程序出现的BUG
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            Log.e("TAG","onCreate------------getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0");
            //结束你的activity
            finish();
            return;

        }
        if (!isTaskRoot()) {
            Log.e("TAG","onCreate------------isTaskRoot");
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent
                    .ACTION_MAIN)) {
                finish();
                return;
            }
        }
        if (!isTaskRoot()) {

            finish();

            return;

        }
        setContentView(R.layout.activity_index);
        initView();
        PrivacyComplianceDialogUtil.getInstance().privacyCompliance(this);







//        Log.e("TAG","init 设备号："+AMapNavi.getDeviceId(getApplicationContext()));

    }


    ListAdapter adapter;
    private void initView() {
        ListView listView = (ListView) findViewById(R.id.list);
        setTitle("导航SDK " + AMapNavi.getVersion());

        adapter = new CustomArrayAdapter(
                this.getApplicationContext(), DEMOS);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(mItemClickListener);

    }

    /**
     * 返回键处理事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            System.exit(0);// 退出程序
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        Log.e("TAG","onCalculateRouteSuccess");
    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }

    @Override
    public void onReCalculateRoute(int i) {
        Log.e("TAG","onReCalculateRoute");
    }

    @Override
    public void onExitPage(int i) {

    }

    @Override
    public void onStrategyChanged(int i) {
        Log.e("TAG","onStrategyChanged");

    }

    @Override
    public View getCustomNaviBottomView() {
        //返回null则不显示自定义区域
        return getCustomView("底部自定义区域");
    }

    @Override
    public View getCustomNaviView() {
        //返回null则不显示自定义区域
        return getCustomView("中部自定义区域");
    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onMapTypeChanged(int i) {
        Log.e("TAG","onMapTypeChanged");

    }

    @Override
    public View getCustomMiddleView() {
        return null;
    }

    @Override
    public void onNaviDirectionChanged(int i) {
        Log.e("TAG","onNaviDirectionChanged");

    }

    @Override
    public void onDayAndNightModeChanged(int i) {
        Log.e("TAG","onDayAndNightModeChanged");

    }

    @Override
    public void onBroadcastModeChanged(int i) {
        Log.e("TAG","onBroadcastModeChanged");

    }

    @Override
    public void onScaleAutoChanged(boolean b) {
        Log.e("TAG","onScaleAutoChanged");

    }

    TextView text1;
    TextView text2;
    private View getCustomView(String title) {
        LinearLayout linearLayout = new LinearLayout(this);
//        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300));
        try {
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            text1 = new TextView(this);
            text1.setGravity(Gravity.CENTER);
            text1.setHeight(150);
            text1.setMinWidth(300);
            text1.setText(title);

            text2 = new TextView(this);
            text2.setGravity(Gravity.CENTER);
            text1.setHeight(150);
            text2.setMinWidth(300);
            text2.setText(title);
            linearLayout.setGravity(Gravity.CENTER);

            linearLayout.addView(text1, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            linearLayout.addView(text2, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.height = 300;
            linearLayout.setLayoutParams(params);

//            NaviLatLng mLatLng = new NaviLatLng(40.0805223, 116.603099);
//            AMapNaviMarkerOptions markerOption = new AMapNaviMarkerOptions();
//            markerOption.setPosition(mLatLng);
//            markerOption.setBitmapDescriptor(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.start)));
//            Log.e("TAG","markerOption:"+markerOption.getTitle());
//            AmapNaviPage.getInstance().addMarker(markerOption);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return linearLayout;
    }
}
