package com.nrmyw.gaode_t.service.manager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;

public class HudManagerServiceDao  {
    private static HudManagerServiceDao  hudManagerServiceDao;

    private HudManagerServiceDao() {

    }

    public static HudManagerServiceDao getInstance(){
        if(null==hudManagerServiceDao){
            synchronized (HudManagerServiceDao.class){
                if(null==hudManagerServiceDao){
                    hudManagerServiceDao=new HudManagerServiceDao();

                }
            }
        }
        return hudManagerServiceDao;
    }

    private Intent serviceIntent;

    public void start(Context context){
        // Android 8.0使用startForegroundService在前台启动新服务
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            serviceIntent= new Intent(context.getApplicationContext(), HudManagerService.class);
            serviceIntent.putExtra("foregroundServiceType", ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION|ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE);
            context.getApplicationContext().startForegroundService(serviceIntent);
        } else {
            serviceIntent = new Intent(context.getApplicationContext(), HudManagerService.class);
            context.getApplicationContext().startService(serviceIntent);
        }
    }

    public void stop(Context context){
        if(null!=serviceIntent){
            context.stopService(serviceIntent);
        }
    }
}
