package com.nrmyw.gaode_t.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PermissionUtils {

    public static boolean isAndroidQOrAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

    public static boolean isAndroidROrAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

    public static boolean isAndroidSOrAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S;
    }

    /**
     * Checks for required permissions.
     *
     * @return true if permissions are already granted, false otherwise.
     */
    public static boolean isBluetoothScanAndConnectPermissionsGranted(final Context context) {
        if(!isAndroidSOrAbove())
            return true;
        return ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED;
    }


    public static boolean checkMediaFile (Context context) {
        try {

            // 查询所有图片
            ContentResolver resolver = context.getContentResolver();
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME};
            Cursor cursor = resolver.query(uri, projection, null, null, null);
            if(null!=cursor){
                return true;
            }else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

    }




    public static boolean isHasWritePermission() {
        try {
            File file=new File(Environment.getExternalStorageDirectory() + File.separator + "newbee");
            if(file.exists()){
                return true;
            }else {
                file.mkdirs();
                return true;
            }
        } catch (Exception e) {
            return false;

        }



    }



    public static void toAppSetPermission(Context context){
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static boolean isHasAudioPermission() {

// 音频获取源

        int audioSource = MediaRecorder.AudioSource.MIC;

// 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025

        int sampleRateInHz = 44100;

// 设置音频的录制的声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道

        int channelConfig = AudioFormat.CHANNEL_IN_STEREO;

// 音频数据格式:PCM 16位每个样本。保证设备支持。PCM 8位每个样本。不一定能得到设备支持。

        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

// 缓冲区字节大小

        int bufferSizeInBytes = 0;

        bufferSizeInBytes = 0;

        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz,

                channelConfig, audioFormat);

        @SuppressLint("MissingPermission") AudioRecord audioRecord = new AudioRecord(audioSource, sampleRateInHz,

                channelConfig, audioFormat, bufferSizeInBytes);

//开始录制音频

        try {

// 防止某些手机崩溃，例如联想

            audioRecord.startRecording();

        } catch (IllegalStateException e) {

            e.printStackTrace();

        }

/**

 * 根据开始录音判断是否有录音权限

 */

        if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {

            return false;

        }

        audioRecord.stop();

        audioRecord.release();

        audioRecord = null;

        return true;

    }





}
