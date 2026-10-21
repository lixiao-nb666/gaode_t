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
public class MainActivity extends Activity {

         SensorManager sensorManager;//管理器对象

         Sensor accSensor;//传感器对象

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
                accSensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                if(accSensor==null){
                        Toast.makeText(this, "您的设备不支持该功能！", 0).show();
                }else{
                String str="\n名字："+accSensor.getName()+"\n电池："+accSensor.getPower()+"\n类型："+accSensor.getType()+"\nVendor:"+accSensor.getVendor()+"\n版本："+accSensor.getVersion()+"\n幅度："+accSensor.getMaximumRange();

                tv_info.setText(str);
                }

                /**
                 * 注册监听器
                 */
                sensorManager.registerListener(sensoreventlistener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
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




                                tv_X.setText("手机沿Yaw轴转过的角度为："+String.valueOf(valuse[0]));
                                tv_Y.setText("手机沿Pitch轴转过的角度为："+String.valueOf(valuse[1]));
                                tv_Z.setText("手机沿Roll轴转过的角度为："+String.valueOf(valuse[2]));
                                break;
                        }
                        super.handleMessage(msg);
                }



        };


        private String getCurrentOrientation(float[] gravity) {
                float x = gravity[0], y = gravity[1], z = gravity[2];
                float gx = Math.abs(x), gy = Math.abs(y), gz = Math.abs(z);
                String xStr=x > 0 ? "RIGHT_SIDE" : "LEFT_SIDE";
                String yStr=y > 0 ? "TOP_UP" : "BOTTOM_UP";
                String zStr=z > 0 ? "往前" : "往后";


                return xStr+"--"+yStr+"--"+zStr;
//                if (gz > gx && gz > gy) {
//                        return z > 0 ? "SCREEN_UP" : "SCREEN_DOWN";
//                } else if (gx > gy && gx > gz) {
//                        return x > 0 ? "RIGHT_SIDE" : "LEFT_SIDE";
//                } else {
//                        return y > 0 ? "TOP_UP" : "BOTTOM_UP";
//                }


        }



        /**
         * 传感器的监听
         */
        private SensorEventListener sensoreventlistener=new SensorEventListener() {

                @Override
                public void onSensorChanged(SensorEvent event) {
                        // TODO Auto-generated method stub

                        float[] valuse=event.values;
                        Message message=new Message();
                        message.obj=valuse;
                        message.what=CHANGE;
                        handler.sendMessage(message);

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                        // TODO Auto-generated method stub


                }
        };

        private static final byte CHANGE=0x1;
}
