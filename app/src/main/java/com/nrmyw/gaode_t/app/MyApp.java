package com.nrmyw.gaode_t.app;


import android.app.Activity;
import android.util.Log;

import com.amap.api.navi.AMapNavi;
import com.liulishuo.filedownloader.FileDownloader;
import com.newbee.bulid_lib.mybase.appliction.BaseApplication;
import com.newbee.downloader.NewBeeFileDownLoaderManager;
import com.nrmyw.ble_event_lib.bean.BleDeviceBean;
import com.nrmyw.ble_event_lib.type.BleSendBitmapQualityType;
import com.nrmyw.gaode_t.BuildConfig;
import com.nrmyw.gaode_t.service.manager.HudManagerServiceDao;
import com.nrmyw.hud_data_lib.type.HudCmdType;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.manager.HudSetManager;
import com.nrmyw.hud_manager_lib.type.HudDevice;
import com.youdao.tools.util.YdLangUtil;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.onAdaptListener;


/**
 * Created by shixin on 16/8/23.
 * bug反馈QQ:1438734562
 */
public class MyApp extends BaseApplication {




    @Override
    public void onCreate() {
        super.onCreate();



    }





//    private fun createAdjustedBitMap(bitmap: Bitmap): Bitmap {
//        return bitmap;
////        val targetWidthPx = 156
////        val targetHeightPx = 232
////
////        val width = bitmap.width
////        val height = bitmap.height
////
////        val scaleX = targetWidthPx.toFloat() / width
////        val scaleY = targetHeightPx.toFloat() / height
////
////        val matrix = Matrix()
////        matrix.postScale(scaleX, scaleY)
////
////        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false)
//
//    }




    @Override
    protected void init() {
        setAutoSize();
        /**
         * setApiKey是静态方法,内部引用了Context，建议放在Application中
         * 如果你在meta-data中配置了key，那么以meta-data中的为准，此行代码
         * 可以忽略，这个方法主要是为那些不想在xml里配置key的用户使用。
         * **/
        try {
            YdLangUtil.getInstance().startCheck();
            AMapNavi.getInstance(this).setIsOpenTrafficLight("TLTRUE");
            NewBeeFileDownLoaderManager.getInstance().init(getApplicationContext());
        }catch (Exception e){}
//        AMapNavi.setApiKey(this, "你的KEY");
//        NewBeeBleConfig.getInstance().init(false, HudConfig.mtu,HudConfig.serviceID,HudConfig.writeID,HudConfig.noticeID, BleDeviceType.getBleDeviceTypeList());
//        NewBeeBleManager.getInstance().init(getBaseContext());
////        hudSendServiceDao=new HudSendServiceDao(getContext());
////        hudSendServiceDao.startService();
//        HudEventManager.getInstance().init(getContext());
    }

    @Override
    protected void needClear(String str) {

    }

    @Override
    protected void close() {
//        if(null!=hudSendServiceDao){
//            hudSendServiceDao.stopService();
//        }
//        HudEventManager.getInstance().close();
//       NewBeeBleManager.getInstance().close();
        NewBeeFileDownLoaderManager.getInstance().close();
        HudManagerServiceDao.getInstance().stop(getApplicationContext());
    }



    private void setAutoSize(){
        AutoSize.initCompatMultiProcess(this);
/**
 * 以下是 AndroidAutoSize 可以自定义的参数, [AutoSizeConfig] 的每个方法的注释都写的很详细
 * 使用前请一定记得跳进源码，查看方法的注释, 下面的注释只是简单描述!!!
 */
        AutoSizeConfig.getInstance().setCustomFragment(true);
        AutoSizeConfig.getInstance().setBaseOnWidth(true);
        AutoSizeConfig.getInstance().setLog(true);
        AutoSizeConfig.getInstance().setOnAdaptListener(new onAdaptListener() {
            @Override
            public void onAdaptBefore(Object target, Activity activity) {
//                String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.javaClass.name)
//                        .loge("AutoSizeLog");

                Log.d("AutoSize", "开始适配: " + activity.getClass().getSimpleName());
            }

            @Override
            public void onAdaptAfter(Object target, Activity activity) {
                Log.d("AutoSize", "完成适配: " + activity.getClass().getSimpleName());
            }
        });

//        AutoSizeConfig.getInstance()..apply {
//            isCustomFragment = true
//            onAdaptListener = object : onAdaptListener {
//                override fun onAdaptBefore(target: Any, activity: Activity) {
//                    //使用以下代码, 可以解决横竖屏切换时的屏幕适配问题
//                    //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
//                    //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会主动重绘当前页面, 所以这时您需要自行重绘当前页面
//                    //ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
//                    //                        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(ac
//                    //                        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(a
//                    String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.javaClass.name)
//                            .loge("AutoSizeLog")
//                }
//                override fun onAdaptAfter(target: Any, activity: Activity) {
//                    String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.javaClass.name)
//                            .loge("AutoSizeLog")
//                }
//            }
//            //是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false, 在以屏幕高度为基准进行适配时
//            //AutoSize 会将屏幕总高度减去状态栏高度来做适配
//            //设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏高度
//            //在全面屏或刘海屏幕设备中, 获取到的屏幕高度可能不包含状态栏高度, 所以在全面屏设备中不需要减去状态栏高度，所以可以 setUseDeviceSize(true)
//            isUseDeviceSize = false
//            //是否打印 AutoSize 的内部日志, 默认为 true, 如果您不想 AutoSize 打印日志, 则请设置为 false
//            setLog(true)
//            //是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize 会全局按照高度进行适配
//            isBaseOnWidth = true
//            //设置屏幕适配逻辑策略类, 一般不用设置, 使用框架默认的就好
//            //setAutoAdaptStrategy { target, activity ->
//            //}

    }



}
