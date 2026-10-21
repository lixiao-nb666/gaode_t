package com.nrmyw.gaode_t.service.manager;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.amap.api.maps.MapsInitializer;
import com.newbee.ble_lib.NewBeeBleManager;
import com.newbee.bulid_lib.mybase.ToastUtil;
import com.newbee.bulid_lib.mybase.service.BaseService;
import com.newbee.bulid_lib.mybase.share.MyShare;
import com.newbee.gson_lib.gson.MyGson;
import com.nrmyw.ble_event_lib.bean.BleDeviceBean;
import com.nrmyw.ble_event_lib.bean.BleSendImageEndInfoBean;
import com.nrmyw.ble_event_lib.bean.BleSendImageStartInfoBean;
import com.nrmyw.ble_event_lib.statu.BleStatu;
import com.nrmyw.ble_event_lib.statu.BleStatuEventObserver;
import com.nrmyw.ble_event_lib.statu.BleStatuEventSubscriptionSubject;
import com.nrmyw.ble_event_lib.type.BleSendBitmapQualityType;
import com.nrmyw.ble_event_lib.util.BleByteUtil;
import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.appset.AppSetRsSetUtil;
import com.nrmyw.gaode_t.config.GaoDeHudSendConfig;
import com.nrmyw.gaode_t.config.GaoDeUserSetConfig;
import com.nrmyw.gaode_t.log.LG;
import com.nrmyw.gaode_t.type.GaoDeNaviLanguageType;
import com.nrmyw.gaode_t.util.HudImageUtil;
import com.nrmyw.gaode_t.view.HudManagerAndSetView;
import com.nrmyw.hud_data_lib.type.HudCmdType;
import com.nrmyw.hud_data_lib.type.turn.HudTurnType;
import com.nrmyw.hud_data_lib.type.warningproint.HudWarningPointType;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.manager.HudSetManager;
import com.nrmyw.hud_manager_lib.type.HudDevice;
import com.nrmyw.hud_manager_lib.util.DistanceToHudUtil;


public class HudManagerService extends BaseService {




//    public void onCreate() {
//        super.onCreate();
//
////        // 创建通知渠道（仅适用于 Android 8.0 及以上版本）
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////            NotificationChannel channel = new NotificationChannel(
////                    "channel_id",
////                    "Foreground Service Channel",
////                    NotificationManager.IMPORTANCE_LOW
////            );
////            NotificationManager manager = getSystemService(NotificationManager.class);
////            if (manager != null) {
////                manager.createNotificationChannel(channel);
////            }
////        }
////
////        // 构建通知
////        Notification notification = new NotificationCompat.Builder(this, "channel_id")
////                .setContentTitle("Service Running")
////                .setContentText("Your service is running in the foreground")
////                .setSmallIcon(R.drawable.ble_is_connected) // 替换为实际的图标资源
////                .setPriority(NotificationCompat.PRIORITY_LOW)
////                .build();
//        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.activity_welcome);
//        contentView.setImageViewResource(R.id.image, R.drawable.ble_is_disconnected);
//        contentView.setTextViewText(R.id.title, "Custom notification");
//        contentView.setTextViewText(R.id.text, "This is a custom layout");
////        notification.contentView = contentView;
//        Log.i("yijingqidong","yijingqidong111111");
//
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 执行服务的逻辑
        return START_STICKY;
    }

    @Override
    public void init() {
        initNotification();
        initOther();
        Log.i(tag,"service-close11:关闭了");
    }

    private int serviceId=527;
    private void initNotification(){
        Notification notification=buildNotification();
        // 启动前台服务
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
             startForeground(serviceId, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION);
            canShowNotification=true;
        } else {
            startForeground(serviceId, notification);
        }

    }

    private void initOther(){
        MapsInitializer.loadWorldVectorMap(true);
        String systemLangStr=getResources().getConfiguration().locale.getLanguage();
        MapsInitializer.initRegionLanguageType(GaoDeUserSetConfig.getInstance().getLanguageType().getSetCode(systemLangStr));
        MapsInitializer.setTerrainEnable(false);
        MapsInitializer.updatePrivacyShow(this,true,true);
        MapsInitializer.updatePrivacyAgree(this,true);
        BleStatuEventSubscriptionSubject.getInstance().attach(bleStatuEventObserver);
        //1 step
        HudSetManager.HudSetConfigListen hudSetConfigListen=new HudSetManager.HudSetConfigListen() {
            @Override
            public void publicConfigInit() {

            }

            @Override
            public void nowConnectedResetConfig(HudDevice hudDevice, BleDeviceBean bleDeviceBean) {
                //public set in here
//                HudManager.getInstance().getHudSetConfig().getHudSetBean().setTimeDifference(-30*60);
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setAutoHideIntervalSpeed(true);
//                BleSendBitmapQualityType.ULTRA_HIGH.setZoomScaling(1f);
//                BleSendBitmapQualityType.ULTRA_HIGH.setQualityV(75);
                HudManager.getInstance().getHudEvent().daylightingStatuOpen();
//                HudManager.getInstance().getHudEvent().setLuminanceTestMode(0,55);
//                HudManager.getInstance().getHudSetConfig().getHudSetBean().setAutoHideNotifiction(false);
//                HudManager.getInstance().getHudSetConfig().getHudSetBean().setAutoHideIntervalSpeed(false);
                //different device set in here
                switch (hudDevice.getSendDataType()){
                    case T900:
                    case H5000:
                    case T850:

//                        BleSendBitmapQualityType.ULTRA_HIGH.setZoomScaling(0.8f);
//                        BleSendBitmapQualityType.ULTRA_HIGH.setQualityV(50);
                        //you want do
//                        HudManager.getInstance().getHudSetConfig().getHudSetBean().setImageMaxW(180);
//                    `    HudManager.getInstance().getHudSetConfig().getHudSetBean().setImageMaxH(216);
//                        HudManager.getInstance().getHudSetConfig().getHudSetBean().setProgressMaxW(8);
//                        HudManager.getInstance().getHudSetConfig().getHudSetBean().setProgressMaxH(216);
//                        //4.设置能显示大警示图
//                        HudManager.getInstance().getHudSetConfig().getHudSetBean().setNeedBigWarningPoint(false);
                        break;
                    case T700_GUI3:
                    case T700:
                        //you want do
                        break;
                }
//                   int imageMaxW= HudManager.getInstance().getHudSetConfig().getHudSetBean().getImageMaxW();
//                    int imageMaxH=HudManager.getInstance().getHudSetConfig().getHudSetBean().getImageMaxH();
                //todo
                // to set Tmap image size
            }
        };
        HudManager.getInstance().init(getApplicationContext(),hudSetConfigListen);
//        setShareBleDevice();

    }



    @Override
    public void start(Intent intent, int flags, int startId) {

    }



    @Override
    public void close() {
        handler.removeCallbacksAndMessages(null);
        closeOther();
    }

    private void closeOther(){

         Log.i(tag,"service-close:关闭了");
        // 2 step
        HudManager.getInstance().close();
        //------------------------------
        BleStatuEventSubscriptionSubject.getInstance().detach(bleStatuEventObserver);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    NotificationManager notificationManager;
    boolean isCreateChannel;
    private String NOTIFICATION_CHANNEL_NAME="HUD";

    private Notification.Builder builder = null;
    private Notification notification = null;
    private Notification buildNotification() {

        if (Build.VERSION.SDK_INT >= 26) {
            //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
            if (notificationManager == null) {
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            }
            String channelId= getPackageName();
            if (!isCreateChannel) {
                NotificationChannel notificationChannel = new NotificationChannel(channelId, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.enableLights(true);//是否在桌面icon右上角展示小圆点
                notificationChannel.setLightColor(Color.BLUE); //小圆点颜色
                notificationChannel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
                notificationManager.createNotificationChannel(notificationChannel);
                isCreateChannel = true;
            }
            builder = new Notification.Builder(getApplicationContext(), channelId);
        } else {
            builder = new Notification.Builder(getApplicationContext());
        }
        builder.setSmallIcon(AppSetRsSetUtil.getServiceIcon())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),AppSetRsSetUtil.getServiceIcon()))
                .setContentTitle(AppSetRsSetUtil.getServiceTitle())
                .setContentText(AppSetRsSetUtil.getServiceTitle())
                .setOngoing(true)//不能被取消删除
                .setPriority(Notification.PRIORITY_MIN)//设置最低优先级不用显示
                .setWhen(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= 16) {
            notification = builder.build();
        } else {
            notification = builder.getNotification();
        }
        notification.contentView = initLayout();

        return notification;
    }


    private HudManagerAndSetView hudManagerAndSetView;
    private @SuppressLint("RemoteViewLayout") RemoteViews contentView;
    private RemoteViews initLayout(){
        contentView= new RemoteViews(getPackageName(), R.layout.service_location);
        contentView.setImageViewResource(R.id.image, R.drawable.hud_icon_easy_show);
        contentView.setTextViewText(R.id.title, "正在获取定位。");
        contentView.setTextViewText(R.id.text, "请不要关闭后台！");
        return contentView;
    }


    private BleStatuEventObserver bleStatuEventObserver=new BleStatuEventObserver() {
        @Override
        public void sendBleStatu(BleStatu bleStatu, Object... objects) {
            //It's useless. Just print it out and view it.
            String bleStatuStr=getResources().getString(bleStatu.getStrId());
            switch (bleStatu){
                case NONE:
                    break;
                case RUN_ERR:
                    //It's useless. Just print it out and view it.
                    String runErrStr=getBleStatuRsStr(objects);
                    LG.i("kaishifasongtupianle","kaishifasongtupianle-zhiling:"+runErrStr);
                    break;
                case SEARCHING:
                case FOUND_BLE_DEVICE:
//                    handler.sendEmptyMessage(bleStatu.ordinal());
                    break;
                case CONNECTED:
                    try {
                        if(objects.length>0){
                            LG.i("kaishifasongtupianle","kaishifasongtupianle-zhilingconnefjksd:"+objects[0]);
                            ToastUtil.showToast(objects[0].toString());

                        }
                    }catch (Exception e){
                        LG.i("kaishifasongtupianle","kaishifasongtupianle-zhilingconnefjksd:"+e.toString());
                    }



//                    putShareBleDevice();
//                    handler.sendEmptyMessage(bleStatu.ordinal());
//                    HudDevice hudDevice= HudManager.getInstance().getHudDevice();
//                    if(null==hudDevice){
//                        return;
//                    }
//                    switch (hudDevice.getSendDataType()){
//                        case T800:
//                            //----------------------------------------------
//                            GaoDeHudSendConfig.getInstance().setNeedAddLaneInfo(true);
//                            GaoDeHudSendConfig.getInstance().setUseNewIcon(false);
//                            BleSendBitmapQualityType.HIGH.setZoomScaling(0.7f);
//                            BleSendBitmapQualityType.HIGH.setQualityV(30);
//                            HudManager.getInstance().getHudSetConfig().setBleSendBitmapQualityType(BleSendBitmapQualityType.HIGH);
//                            HudManager.getInstance().getHudSetConfig().setImageMaxW(200);
//                            HudManager.getInstance().getHudSetConfig().setImageMaxH(260);
//                            HudManager.getInstance().getHudSetConfig().setAutoChangerTrunTypeOldAndNew(false);
//                            HudManager.getInstance().getHudSetConfig().setNeedBigWarningPoint(true);
//                            HudManager.getInstance().getHudSetConfig().setOneShowBigWarningPoint(true);
//                            HudManager.getInstance().getHudSetConfig().setCanNotShowProgress(true);
//                            HudManager.getInstance().getHudEvent().hideReach();
//                            break;
//                        case T900:
//                            HudManager.getInstance().getHudSetConfig().setImageMaxW(150);
//                            HudManager.getInstance().getHudSetConfig().setImageMaxH(120);//320,238
//                            BleSendBitmapQualityType.ULTRA_HIGH.setZoomScaling(0.8f);
//                            BleSendBitmapQualityType.ULTRA_HIGH.setQualityV(70);
//                            HudManager.getInstance().getHudSetConfig().setBleSendBitmapQualityType(BleSendBitmapQualityType.ULTRA_HIGH);
//                            //2设置进度条
//                            HudManager.getInstance().getHudSetConfig().setProgressMaxW(14);//最大是14
//                            HudManager.getInstance().getHudSetConfig().setProgressMaxH(238);
//                            BleSendBitmapQualityType.PROGRESS.setQualityV(8);
//                            BleSendBitmapQualityType.PROGRESS.setZoomScaling(1f);
//                            HudManager.getInstance().getHudSetConfig().setBleSendProgressQualityType(BleSendBitmapQualityType.PROGRESS);
////        HudManager.getInstance().getHudSetConfig().setTimeDifference(-60*60);
//                            //3设置基础参数
//                            HudManager.getInstance().getHudSetConfig().setAutoChangerTrunTypeOldAndNew(false);
//                            HudManager.getInstance().getHudSetConfig().setNeedBigWarningPoint(true);
//                            HudManager.getInstance().getHudSetConfig().setOneShowBigWarningPoint(true);
//                            HudManager.getInstance().getHudSetConfig().setCanNotShowProgress(false);
//                            HudManager.getInstance().getHudSetConfig().setHideIntervalSpeedUseWarningPointCmd(false);
//
//                            break;
//                        case T700_GUI3:
//                        case T700:
//                        default:
//                            //----------------------------------------------
//                            GaoDeHudSendConfig.getInstance().setNeedAddLaneInfo(false);
//                            GaoDeHudSendConfig.getInstance().setUseNewIcon(true);
//                            BleSendBitmapQualityType.PROGRESS.setQualityV(8);
//                            BleSendBitmapQualityType.PROGRESS.setZoomScaling(1f);
////        HudManager.getInstance().getHudSetConfig().setTimeDifference(-60*60);
//                            BleSendBitmapQualityType.ULTRA_HIGH.setZoomScaling(0.7f);
//                            BleSendBitmapQualityType.ULTRA_HIGH.setQualityV(30);
//                            HudManager.getInstance().getHudSetConfig().setBleSendBitmapQualityType(BleSendBitmapQualityType.ULTRA_HIGH);
//                            HudManager.getInstance().getHudSetConfig().setImageMaxW(360);
//                            HudManager.getInstance().getHudSetConfig().setImageMaxH(320);//320
//                            HudManager.getInstance().getHudSetConfig().setProgressMaxW(22);
//                            HudManager.getInstance().getHudSetConfig().setProgressMaxH(320);
//                            HudManager.getInstance().getHudSetConfig().setAutoChangerTrunTypeOldAndNew(false);
//                            HudManager.getInstance().getHudSetConfig().setNeedBigWarningPoint(false);
//                            HudManager.getInstance().getHudSetConfig().setOneShowBigWarningPoint(false);
//                            HudManager.getInstance().getHudSetConfig().setCanNotShowProgress(false);
//
//                            break;
//
//                    }
////


                    break;
                case CONNECTING:
                case DISCONNECTED:
//                    handler.sendEmptyMessage(bleStatu.ordinal());
//                    HudDevice hudDevice= HudManager.getInstance().getHudDevice();
//                    if(null!=hudDevice){
//                        String deviceName=hudDevice.name();
//                        boolean isConnected=NewBeeBleManager.getInstance().isConnect();
//                    }

                    break;
                case USER_DO:
                case OPENING:
                case STATE_ON:
                case STATE_OFF:
                case CAN_SEND_DATA:

                    //It's useless. Just print it out and view it.
                    String bleOtherStr=getBleStatuRsStr(objects);
                    break;
                case RETRUN_BYTES:
                    //It's useless. Just print it out and view it.
                    byte[] retrunCmd= (byte[]) objects[0];
                    String retrunCmdStr= BleByteUtil.parseByte2HexStr(retrunCmd);
                    LG.i("kaishifasongtupianle","kaishifasongtupianle-zhiling:bitmaptime--retrunCmdStr-"+retrunCmdStr);
                    break;
                case SENDING_DATA:
                    //It's useless. Just print it out and view it.
                    byte[] sendCmd= (byte[]) objects[0];
                    String sendCmdStr= BleByteUtil.parseByte2HexStr(sendCmd);





                    LG.i("kaishifasongtupianle","kaishifasongtupianle-zhiling:bitmaptime--111-"+sendCmdStr);
                    break;
                case SEND_IMAGE_START:
                    //It's useless. Just print it out and view it.
                    BleSendImageStartInfoBean startInfoBean= (BleSendImageStartInfoBean) objects[0];
                    break;
                case SEND_IMAGE_END:
                    //It's useless. Just print it out and view it.
                    BleSendImageEndInfoBean endInfoBean= (BleSendImageEndInfoBean) objects[0];
                    break;
                case SEND_IMAGE_DATA_END:
                    LG.i("kaishifasongtupianle","kaishifasongtupianle-zhiling:bitmaptime--222-"+objects[0].toString());
                    break;
            }

        }

        private String getBleStatuRsStr(Object... objects){
            try {
                if(null==objects||objects.length==0){
                    return "";
                }
                String str=null;
                if(objects[0] instanceof  String){
                    str= objects[0].toString();
                }else {
                    int rsId= (int) objects[0];
                    str= getResources().getString(rsId);
                }
                if(objects.length>1){
                    return str+"-"+objects[1].toString();
                }else {
                    return str;
                }
            }catch (Exception e){
                return "";
            }

        }
    };

    private boolean canShowNotification=true;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                if(null==notification){
                    return;
                }
                if(!canShowNotification){
                    return;
                }

                BleStatu bleStatu=BleStatu.values()[msg.what];
                int imgRs=0;
                String titleStr="";
                String bodyStr="";
                switch (bleStatu){
                    case INIT:
                        imgRs=R.drawable.hud_icon_easy_show;
                        titleStr="使用中....";
                        bodyStr="请不要关闭后台！";
                        canShowNotification=false;
                        break;
                    case SEARCHING:
                        imgRs=R.drawable.hud_icon_easy_show;
                        titleStr="搜索中....";
                        bodyStr="自动匹配，请不要关闭后台！";
                        break;
                    case FOUND_BLE_DEVICE:
                        imgRs= HudImageUtil.getNowHudEasyImageRs();
                        titleStr="搜索到设备！";
                        bodyStr="请等待,请不要关闭后台!";
                        break;
                    case CONNECTING:
                        imgRs= HudImageUtil.getNowHudEasyImageRs();
                        titleStr="连接中....";
                        bodyStr="检测中,请不要关闭后台!";
                        break;
                    case CONNECTED:
                        imgRs= HudImageUtil.getNowHudEasyImageRs();
                        titleStr="连接成功";
                        bodyStr="发送数据中，请不要关闭后台！";
                        break;
                        case CONNECTING_ERR:
                        imgRs=R.drawable.hud_icon_easy_show;
                        titleStr="连接异常！！！";
                        bodyStr="请等待重连，请不要关闭后台！";
                        break;
                    case DISCONNECTED:
                        imgRs=R.drawable.hud_icon_easy_show;
                        titleStr="断开连接！！！";
                        bodyStr="请等待连接新设备，请不要关闭后台！";
                        break;
                    case SENDING_DATA:
                        if (System.currentTimeMillis()-lastCheckTime>=60*1000) {
                            lastCheckTime=System.currentTimeMillis();
                            if(null==notification||null==notification.contentView){
                                initNotification();
                            }
                        }
                        return;

                }
                notification.contentView.setImageViewResource(R.id.image, imgRs);
                notification.contentView.setTextViewText(R.id.title, titleStr);
                notification.contentView.setTextViewText(R.id.text, bodyStr);
                notificationManager.notify(serviceId,builder.build());
            }catch (Exception e){

            }
        }
    };
    private long lastCheckTime;
//    public static final String bleDeviceShareKeyStr="bleDeviceShareStr";
//    public void putShareBleDevice(){
//        if(null!=NewBeeBleManager.getInstance().getNowUseBleDevice()){
//            String bleDevicePutShareStr= MyGson.getInstance().toGsonStr(NewBeeBleManager.getInstance().getNowUseBleDevice());
//            Log.i("checkShareBleDevice","checkShareBleDevice1111:"+bleDevicePutShareStr);
//            MyShare.getInstance().putString(bleDeviceShareKeyStr,bleDevicePutShareStr);
//        }
//    }
//
//    private void setShareBleDevice(){
//        try {
//
//            String bleDeviceShareStr= MyShare.getInstance().getString(bleDeviceShareKeyStr);
//            if(!TextUtils.isEmpty(bleDeviceShareStr)){
//                BleDeviceBean bleDeviceBean= MyGson.getInstance().fromJson(bleDeviceShareStr,BleDeviceBean.class);
//                Log.i("checkShareBleDevice","checkShareBleDevice1112:"+bleDeviceShareStr);
//                HudManager.getInstance().setDefBleDevice(bleDeviceBean);
//            }
//        }catch (Exception e){}
//    }

}
