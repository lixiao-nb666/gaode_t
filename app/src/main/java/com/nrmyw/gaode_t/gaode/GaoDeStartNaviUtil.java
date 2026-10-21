package com.nrmyw.gaode_t.gaode;

import android.text.TextUtils;

import com.amap.api.maps.AMapException;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.navi.model.AMapCarInfo;
import com.newbee.bulid_lib.mybase.share.MyShare;
import com.newbee.gson_lib.gson.MyGson;
import com.nrmyw.gaode_t.activity.MyGaoDeActivity;
import com.nrmyw.gaode_t.app.MyApp;
import com.nrmyw.gaode_t.gaode.bean.GaoDeNaviBean;
import com.nrmyw.gaode_t.log.LG;

public class GaoDeStartNaviUtil {

    private final String tag="GaoDeStartNaviUtil";
    private static GaoDeStartNaviUtil gaoDeStartNaviUtil;
    private GaoDeNaviBean gaoDeNaviBean=new GaoDeNaviBean();
    private GaoDeStartNaviUtil(){
        try {
            String gs= MyShare.getInstance().getString(tag);
            if(!TextUtils.isEmpty(gs)){
                gaoDeNaviBean= MyGson.getInstance().fromJson(gs,GaoDeNaviBean.class);
            }
        }catch (Exception e){
            gaoDeNaviBean=new GaoDeNaviBean();
        }



    }

    public static GaoDeStartNaviUtil getInstance(){
        if(null==gaoDeStartNaviUtil){
            synchronized (GaoDeStartNaviUtil.class){
                if(null==gaoDeStartNaviUtil){
                    gaoDeStartNaviUtil=new GaoDeStartNaviUtil();
                }
            }
        }
        return gaoDeStartNaviUtil;
    }

    public void starNavi(){
        starNavi(null);
    }


    public void starNavi(AmapNaviType amapNaviType){
        if(null==gaoDeNaviBean){
            gaoDeNaviBean=new GaoDeNaviBean();
        }
        if(null==amapNaviType){
            amapNaviType=gaoDeNaviBean.getAmapNaviType();
        }




        //AmapPageType.ROUTE  先进入选择，然后进入导航     AmapPageType.NAVI  直接开始导航，但是需要提前确定好地址
        AmapNaviParams params = new AmapNaviParams(null, null, null,amapNaviType, AmapPageType.ROUTE);
//                    null
        //新增加的---开始
//      params.setNaviMode(1);//设置直接导航模式（仅在使用组件直接导航（不调起算路页面）时生效）naviType - 1-实时导航 2-模拟导航 ，没用的参数，忽略
        params.setTheme(gaoDeNaviBean.getAmapNaviTheme());//设置导航主题，3种颜色可选择
//      params.setNeedCalculateRouteWhenPresent(true);//启动组件进行直接导航时，设置是否进行算路 (只有在直接跳转导航页的情况下才生效)  ,没用的参数，忽略
        params.setNeedDestroyDriveManagerInstanceWhenNaviExit(false);//设置退出导航组件是否销毁导航实例 ,这里要固定设置成false
        params.setShowExitNaviDialog(true); //设置退出导航组件是否显示退出弹框 ，不设置就默认为true
        params.setUseInnerVoice(true);//设置是否使用内部语音播报 ，一定要默认为true，要不就自己接TTS，不设置就默认为true
        params.setShowRouteStrategyPreferenceView(true);//表示设置路径规划偏好策略页面是否显示,一定要显示
        params.setTrafficEnabled(true);//导航组件路况开关是否打开
        params.setSecondActionVisible(true);//设置组件导航页是否显示下下个路口引导，默认不显示,直接改成显示
        params.setDayAndNightMode(MyApp.getContext(),gaoDeNaviBean.getDayAndNightType().ordinal());//mode - 0-自动切换 1-白天 2-夜间
        params.setBroadcastMode(MyApp.getContext(),gaoDeNaviBean.getBroadCastType().getIndex()); //设置播报模式 mode - 1-简洁播报 2-详细播报 3-静音模式,代码自动处理了0直接改成2
        params.setCarDirectionMode(MyApp.getContext(),2);//设置导航视角  1-正北向上 2-车头向上
        params.setScaleAutoChangeEnable(MyApp.getContext(),true);//设置比例尺智能缩放是否开启
        params.setShowVoiceSetings(true);//设置是否展示导航语音播报设置项
        params.showEagleMap(MyApp.getContext(),true);//不知道啥玩意

        switch (amapNaviType){
            case DRIVER:
                params.setShowCrossImage(true);//驾车导航时是否显示路口放大图,仅驾车模式有效，必须要设置
                params.setMultipleRouteNaviMode(true);//设置多路线导航模式(导航中拥有若干条备选路线供用户选择), 或单路线导航模式(默认模式),注意:此方法仅限于在开始导航前调用有效,步行与骑行导航无效，以下情况不会出现多备选路线：
                AMapCarInfo aMapCarInfo=new AMapCarInfo();
                aMapCarInfo.setCarType(gaoDeNaviBean.getCarType().getIndex()+"");
                if(!TextUtils.isEmpty(gaoDeNaviBean.getCarNumb())){
                    aMapCarInfo.setCarNumber(gaoDeNaviBean.getCarNumb());
                }
                aMapCarInfo.setRestriction(gaoDeNaviBean.isRestriction());
                params.setCarInfo(aMapCarInfo);
                if(gaoDeNaviBean.getCarType().isTruck()){
                    params.setTruckMultipleRouteNaviMode(true);//设置货车多路线导航模式(导航中拥有若干条备选路线供用户选择), 或单路线导航模式（默认模式）
                }
                break;
        }
//                    //传递上下文和导航参数
        AmapNaviPage.getInstance().showRouteActivity(MyApp.getContext(), params, GaoDeListenManager.getInstance().getiNaviInfoCallback(), MyGaoDeActivity.class);
        try {
            AMapNavi.getInstance(MyApp.getContext()).addAMapNaviListener(GaoDeListenManager.getInstance().getaMapNaviListener());
//                      AMapNavi.getInstance(getApplicationContext()).addParallelRoadListener(GaoDeListenManager.getInstance().getParallelRoadListener());
        } catch (AMapException e) {
            LG.i("kankanshenmegui"+e.toString());
        }
    }


}
