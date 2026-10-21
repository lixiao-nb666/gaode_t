package com.nrmyw.gaode_t.activity.zitai;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.nrmyw.gaode_t.R;

/**
* 姿态传感器
*
* @author pengxiaolong
*
*/
public class MainActivity1 extends Activity {
       
         SensorManager sensorManager;//管理器对象

        private Sensor accelerometer;
        private Sensor magnetometer;
       
         TextView tv_X;
       
         TextView tv_Y;
       
         TextView tv_Z;
       
         TextView tv_info;
       
       
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_zt_main);
                View_init();



                projectinit();



        }

       
       
        /**
         * 对象的初始化
         */
        private void projectinit(){
               
                sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
                accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
                magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                
       
                if(accelerometer==null||magnetometer==null){
                        Toast.makeText(this, "您的设备不支持该功能！", 0).show();

                }else{
                String str="\n名字："+accelerometer.getName()+"\n电池："+accelerometer.getPower()+"\n类型："+accelerometer.getType()+"\nVendor:"+accelerometer.getVendor()+"\n版本："+accelerometer.getVersion()+"\n幅度："+accelerometer.getMaximumRange();
           
                tv_info.setText(str);
                }
               
                /**
                 * 注册监听器
                 */
               startListening();
        }

        public void startListening() {
                sensorManager.registerListener(sensoreventlistener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                sensorManager.registerListener(sensoreventlistener, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
       
        /**
         * 小组件的初始化
         */
        private void View_init(){
               
                tv_info=(TextView) this.findViewById(R.id.info);
                tv_X=(TextView) this.findViewById(R.id.tvX);
                tv_Y=(TextView) this.findViewById(R.id.tvY);
                tv_Z=(TextView) this.findViewById(R.id.tvZ);
               
        }
   
       
        @Override
        protected void onDestroy() {
                // TODO Auto-generated method stub
                super.onDestroy();
                /**
                 * 解除注册
                 */
                sensorManager.unregisterListener(sensoreventlistener);
        }
        private String lastSS;
        private Handler handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                        // TODO Auto-generated method stub
                       
                        switch(msg.what){
                       
                        case CHANGE:
                               
                                float[] valuse=(float[]) msg.obj;
                                String ss=getCurrentOrientation(valuse);
                                if(TextUtils.isEmpty(lastSS)){
                                        lastSS=ss;
                                        Log.i("kankanshoushi","kankanshoushi:1---"+ss);
                                }else {
                                      if(lastSS.equals(ss)){

                                      } else {
                                              lastSS=ss;
                                              Log.i("kankanshoushi","kankanshoushi:2---"+ss);
                                      }
                                }


                       
                                tv_X.setText("手机沿Yaw轴转过的角度为："+Float.toString(valuse[0]));
                                tv_Y.setText("手机沿Pitch轴转过的角度为："+Float.toString(valuse[1]));
                                tv_Z.setText("手机沿Roll轴转过的角度为："+Float.toString(valuse[2]));
                                break;
                        }
                        super.handleMessage(msg);
                }
               
               
               
        };


        private String getCurrentOrientation(float[] gravity) {
                float x = gravity[0], y = gravity[1], z = gravity[2];
                float gx = Math.abs(x), gy = Math.abs(y), gz = Math.abs(z);

                if (gz > gx && gz > gy) {
                        return z > 0 ? "SCREEN_UP" : "SCREEN_DOWN";
                } else if (gx > gy && gx > gz) {
                        return x > 0 ? "RIGHT_SIDE" : "LEFT_SIDE";
                } else {
                        return y > 0 ? "TOP_UP" : "BOTTOM_UP";
                }
        }



        /**
         * 传感器的监听
         */
        private SensorEventListener sensoreventlistener=new SensorEventListener() {
               
                @Override
                public void onSensorChanged(SensorEvent event) {
                        // TODO Auto-generated method stub
                       
//                        float[] valuse=event.values;
//                        Message message=new Message();
//                        message.obj=valuse;
//                        message.what=CHANGE;
//                        handler.sendMessage(message);

//                        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//                                gravity = event.values;
//                        }
//                        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
//                                geomagnetic = event.values;
//                        }
//                        Log.i("","kankan--------0");
//                        if (gravity != null && geomagnetic != null) {
//                                float R[] = new float[9];
//                                float I[] = new float[9];
//                                Log.i("","kankan--------1");
//                                boolean success = SensorManager.getRotationMatrix(R, I, gravity, geomagnetic);
//                                if (success) {
//                                        float orientation[] = new float[3];
//                                        SensorManager.getOrientation(R, orientation);
//                                        float yaw = orientation[0]; // YAW值
//                                        // 可以在这里处理获取到的YAW值
//                                        Log.i("","kankan--------2---"+yaw);
//
//                                }
//                        }


//                        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
//                                // 将旋转向量转换为旋转矩阵
//                                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
//                                // 从旋转矩阵中获取方向值
//                                SensorManager.getOrientation(rotationMatrix, orientation);
//                                // 获取 YAW 角（方位角），单位是弧度
//                                float yaw = orientation[0];
//                                // 将弧度转换为角度
//                                float yawInDegrees = (float) Math.toDegrees(yaw);
//                                // 这里可以对获取到的 YAW 角进行处理，例如打印或者传递给其他方法
//                                System.out.println("Yaw angle: " + yawInDegrees);
//                        }
               
                }
               
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                        // TODO Auto-generated method stub
                       
                       
                }
        };
       
        private static final byte CHANGE=0x1;

        private float[] gravity;
        private float[] geomagnetic;
        
//
//        import android.content.Context;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//
//        public class YawSensorListener implements SensorEventListener {
//                private SensorManager sensorManager;
//
//                private float[] gravity;
//                private float[] geomagnetic;
//
//                public YawSensorListener(Context context) {
//                        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
//                        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//                        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//                }
//
//
//
//                public void stopListening() {
//                        sensorManager.unregisterListener(this);
//                }
//
//                @Override
//                public void onSensorChanged(SensorEvent event) {
//
//                }
//
//                @Override
//                public void onAccuracyChanged(Sensor sensor, int accuracy) {
//                        // 传感器精度改变时的处理
//                }
//        }

        
      

      
    


}
