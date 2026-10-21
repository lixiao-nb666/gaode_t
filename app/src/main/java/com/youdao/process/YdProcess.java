package com.youdao.process;

import android.content.Context;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.newbee.translation_ui_lib.event.VoiceToTextEventSubscriptionSubject;
import com.newbee.translation_ui_lib.process.BaseVoiceToTextProcess;


import com.newbee.yd_translation.util.NrmywTranslationUtil;
import com.nrmyw.gaode_t.R;

import com.youdao.tools.config.YdConfig;
import com.youdao.tools.util.YdErrUtil;
import com.youdao.audio.common.AudioChunkWrapper;
import com.youdao.audio.common.AudioConsts;
import com.youdao.audio.common.SilenceMode;
import com.youdao.audio.recorder.AudioDefaultWavFileRecorder;
import com.youdao.audio.recorder.AudioRecordConfig;
import com.youdao.audio.recorder.OnAudioRecordListener;
import com.youdao.audio.recorder.SilenceDetectorConfig;
import com.youdao.ydasr.ASRParams;
import com.youdao.ydasr.AsrListener;
import com.youdao.ydasr.AsrManager;
import com.youdao.ydasr.asrengine.model.AsrResult;
import com.youdao.ydasr.asrengine.model.AsrResultCode;


import java.io.File;


public class YdProcess implements BaseVoiceToTextProcess {
    static final String TAG = "lixiaokankan";
    private final  String defFilePath="/myw_translation/";
    private final String  defChildFilePath="temp/";
    private final String rootFilePath = Environment.getExternalStorageDirectory() + defFilePath;
    private final String rootFilePath2 = rootFilePath +defChildFilePath ;
    AudioDefaultWavFileRecorder recorder;
    AudioRecordConfig mAudioRecordConfig;

    private Context context;
    private AsrManager mAsrManager;
    private Listen listen;

    public YdProcess(Context context,Listen listen){
        this.context=context;
        this.listen=listen;
        createFolder(rootFilePath);
        createFolder(rootFilePath2);
        String transPattern = "stream";
        ASRParams asrParams;
        asrParams = new ASRParams.Builder()
                .transPattern(transPattern)
                .timeoutStart(5000L)
                .timeoutEnd(10000L)
                .sentenceTimeout(3000L)
                .connectTimeout(10000L)
                .isWaitServerDisconnect(true)
                .build();
        mAsrManager = AsrManager.getInstance(context, YdConfig.appKey, asrParams, mAsrListener);
        int bufferSize = 16000 * (16 / 8) * 200 / 1000;
        mAudioRecordConfig = new AudioRecordConfig(
                MediaRecorder.AudioSource.VOICE_RECOGNITION,
                AudioFormat.CHANNEL_IN_MONO,
                AudioConsts.Recorder.SAMPLE_RATE_16K,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize
        );

        File tempFile = getAudioTempFile();
        File cacheFile = new File(Environment.getExternalStorageDirectory(), "cache");
        recorder = new AudioDefaultWavFileRecorder(mAudioRecordConfig, tempFile, SilenceDetectorConfig.NO_SILENCE_CONFIG,
                SilenceMode.SILENCE_MODE_WITHOUT_SILENCE_FRAME, cacheFile,
                new OnAudioRecordListener() {
                    @Override
                    public void onAudioFrameRecorded(AudioChunkWrapper audioChunkWrapper) {
                        mAsrManager.insertAudioBytes(audioChunkWrapper.toBytes().clone());
                    }

                    @Override
                    public void onReady() {
                        Log.i(TAG, "recorder onReady");
                    }

                    @Override
                    public void onStart() {
                        Log.i(TAG, "recorder onStart");
                    }

                    @Override
                    public void onSilence(long l) {
                        Log.i(TAG, "recorder onSilence");
                    }

                    @Override
                    public void onStop() {
                        Log.i(TAG, "recorder onStop");
                    }

                    @Override
                    public void onRelease() {
                        Log.i(TAG, "recorder onRelease");
                    }

                    @Override
                    public void onError(int i) {
                        Log.i(TAG, "recorder onError" + i);


                    }
                });
    }


    private AsrListener mAsrListener = new AsrListener() {
        @Override
        public void onAsrSilentEnd() {
            VoiceToTextEventSubscriptionSubject.getInstence().getStatu(useRsgetString(R.string.not_speak_for_a_while));
            listen.needWaitFinsh();
        }

        @Override
        public void onAsrSilentStart() {
            VoiceToTextEventSubscriptionSubject.getInstence().getStatu(useRsgetString(R.string.not_speak_after_start));
            listen.needWaitFinsh();
        }

        @Override
        public void onAsrRestart() {
            VoiceToTextEventSubscriptionSubject.getInstence().startOk();
        }

        @Override
        public void onAsrStart() {
            VoiceToTextEventSubscriptionSubject.getInstence().startOk();
        }

        @Override
        public void onAsrStop() {
            VoiceToTextEventSubscriptionSubject.getInstence().isColse(useRsgetString(R.string.asr_stop));
            listen.nowMustFinsh();
        }

        @Override
        public void onAsrReconnecting() {
            VoiceToTextEventSubscriptionSubject.getInstence().getStatu(useRsgetString(R.string.asr_reconnecting));
        }

        @Override
        public void onAsrError( AsrResultCode error) {
            try {
                Log.i(TAG, "recorder onError" + error+"--"+error.code);
                if(error.toString().contains("PERMISSION")){
                    listen.needToSetPermission();
                }
//                VoiceToTextEventSubscriptionSubject.getInstence().getStatu(useRsgetString(R.string.not_speak_for_a_while));
//                VoiceToTextEventSubscriptionSubject.getInstence().onError(0,error+"--"+error.code );
            }catch (Exception e){}



        }


        @Override
        public void onAsrNext(AsrResult result, boolean isSentence) {
            if(null==result){
                return;
            }
            if(isSentence){
                //这里是识别结果
                String recogStr=result.getResult().getContext();
                recogStr=getNewString(recogStr);
                NrmywTranslationUtil.sendTranslationed(recogStr);

                String tanslationStr= result.getResult().getTranContent();
                tanslationStr=getNewString(tanslationStr);
                NrmywTranslationUtil.sendTranslationedResult(tanslationStr);
                Log.i(TAG, "kankan text 1:"+recogStr );
                Log.i(TAG, "kankan text 2:"+tanslationStr );
                if ( !TextUtils.isEmpty(recogStr)&& !TextUtils.isEmpty(tanslationStr)) {
                    VoiceToTextEventSubscriptionSubject.getInstence().getRecogFinshAndTranslationStr(recogStr,tanslationStr);
                }
            }else {
                //这里是识别中
                String recogStr=result.getResult().getContext();
                recogStr=getNewString(recogStr);
                NrmywTranslationUtil.sendTranslationing(recogStr);

//                HudManager.getInstance().getHudEvent().sendTranslationed(recogStr);
                String tanslationStr= result.getResult().getTranContent();
                tanslationStr=getNewString(tanslationStr);

                NrmywTranslationUtil.sendTranslationingResult(tanslationStr);
//                HudManager.getInstance().getHudEvent().sendTranslationedResult(tanslationStr);
                Log.i(TAG, "kankan text 3:"+recogStr );
                Log.i(TAG, "kankan text 4:"+tanslationStr );
                if ( !TextUtils.isEmpty(recogStr)&& !TextUtils.isEmpty(tanslationStr)) {
                    VoiceToTextEventSubscriptionSubject.getInstence().getRecogingAndTranslationStr(recogStr,tanslationStr);
                }
            }
        }

        private boolean needGetNewStr=false;
        public String getNewString(String oldStr){
            if(!needGetNewStr){
                return oldStr;
            }

            if(TextUtils.isEmpty(oldStr)){
                return oldStr;
            }
            Log.i(TAG, "kankan text 5:" +oldStr);
            if(oldStr.length()<12){
                return oldStr;
            }
            int index=oldStr.lastIndexOf(".");
            Log.i(TAG, "kankan text 61:" +oldStr);
            if(index!=-1&&index+1<oldStr.length()){

                return oldStr.substring(index+1);
            }
            index=oldStr.lastIndexOf("!");
            Log.i(TAG, "kankan text 62:" +oldStr);
            if(index!=-1&&index+1<oldStr.length()){
                return oldStr.substring(index+1);
            }
            index=oldStr.lastIndexOf("?");
            Log.i(TAG, "kankan text 63:" +oldStr);
            if(index!=-1&&index+1<oldStr.length()){
                return oldStr.substring(index+1);
            }
            index=oldStr.lastIndexOf("！");
            Log.i(TAG, "kankan text 64:" +oldStr);
            if(index!=-1&&index+1<oldStr.length()){
                return oldStr.substring(index+1);
            }
            index=oldStr.lastIndexOf("？");
            Log.i(TAG, "kankan text 65:" +oldStr);
            if(index!=-1&&index+1<oldStr.length()){
                return oldStr.substring(index+1);
            }

            index=oldStr.lastIndexOf("。");
           
            Log.i(TAG, "kankan text 67:" +oldStr);
            if(index!=-1&&index+1<oldStr.length()){
                return oldStr.substring(index+1);
            }
            index=oldStr.lastIndexOf("？");
            Log.i(TAG, "kankan text 68:" +oldStr);
            if(index!=-1&&index+1<oldStr.length()){
                return oldStr.substring(index+1);
            }
            index=oldStr.lastIndexOf("，");
            Log.i(TAG, "kankan text 66:" +oldStr);
            if(index!=-1&&index+1<oldStr.length()){
                return oldStr.substring(index+1);
            }
            Log.i(TAG, "kankan text 69:" +oldStr);
            return oldStr;
        }



        @Override
        public void onAsrVolumeChange(float volume) {
//            mainTvDecibel.setText(String.valueOf((int) (volume * 100)));
        }

        @Override
        public void onBluetoothAudioConnected() {
            Toast.makeText(context, useRsgetString(R.string.bluetooth_audio_connected), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBluetoothAudioDisconnected() {
            Toast.makeText(context, useRsgetString(R.string.bluetooth_audio_disconnected), Toast.LENGTH_SHORT).show();
        }
    };

    private String useRsgetString(int rsStr){
        return context.getApplicationContext().getResources().getString(rsStr);
    }

    boolean isRecording;
    @Override
    public void startReadAudio(Context context) {
        isRecording = true;
        mAsrManager.addWavHead = true;
        recorder.start();
        startAsrManager();
    }

    public AsrManager getmAsrManager() {
        return mAsrManager;
    }

    @Override
    public void pause() {
        isRecording = false;
        recorder.stop();
        mAsrManager.stop();
    }

    @Override
    public void close() {
        isRecording = false;
        if (recorder != null) {
            recorder.stop();
            recorder.release();
        }
        if(null!=mAsrManager){
            mAsrManager.destroy();
        }

    }

    private void startAsrManager() {
        mAsrManager.setASRLanguage(YdConfig.getInstance().getFromType().getLang(),YdConfig.getInstance().getToType().getLang());
//        mAsrManager.setASRLanguage("yue","yue");
        mAsrManager.startConnect();
    }


    private File getAudioTempFile() {


        File audioFile = new File(Environment.getExternalStorageDirectory(), defFilePath+defChildFilePath);

        if (!audioFile.exists()) {
            audioFile.mkdirs();
        }
        return new File(audioFile, "myw_temp.wav");
    }

    private void createFolder(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
    }


    public interface Listen{
        public  void needWaitFinsh();

        public void nowMustFinsh();

        public void needToSetPermission();

    }
}
