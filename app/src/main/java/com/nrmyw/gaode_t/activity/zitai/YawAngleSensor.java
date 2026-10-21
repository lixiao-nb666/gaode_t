package com.nrmyw.gaode_t.activity.zitai;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class YawAngleSensor implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor rotationVectorSensor;
    private float[] rotationMatrix = new float[16];
    private float[] orientation = new float[3];

    public YawAngleSensor(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    public void startListening() {
        sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopListening() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            // 将旋转向量转换为旋转矩阵
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
            // 从旋转矩阵中获取方向值
            SensorManager.getOrientation(rotationMatrix, orientation);
            // 获取 YAW 角（方位角），单位是弧度
            float yaw = orientation[0];
            // 将弧度转换为角度
            float yawInDegrees = (float) Math.toDegrees(yaw);
            // 这里可以对获取到的 YAW 角进行处理，例如打印或者传递给其他方法
            System.out.println("Yaw angle: " + yawInDegrees);
            Log.i("shenmegui","Yaw angle: " + yawInDegrees);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 当传感器精度改变时的回调方法，可根据需要实现
    }
}
