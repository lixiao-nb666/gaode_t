package com.youdao.zhiyun.ydasrdemo;//package com.youdao.zhiyun.ydasrdemo;
//
//
//import android.content.DialogInterface;
//import android.media.AudioFormat;
//import android.media.MediaRecorder;
//import android.os.Bundle;
//import android.os.Environment;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.newbee.yd_translation.R;
//import com.youdao.audio.common.AudioChunkWrapper;
//import com.youdao.audio.common.AudioConsts;
//import com.youdao.audio.common.SilenceMode;
//import com.youdao.audio.recorder.AudioDefaultWavFileRecorder;
//import com.youdao.audio.recorder.AudioRecordConfig;
//import com.youdao.audio.recorder.OnAudioRecordListener;
//import com.youdao.audio.recorder.SilenceDetectorConfig;
//import com.youdao.ydasr.ASRParams;
//import com.youdao.ydasr.AsrListener;
//import com.youdao.ydasr.AsrManager;
//import com.youdao.ydasr.asrengine.model.AsrResult;
//import com.youdao.ydasr.asrengine.model.AsrResultCode;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class ASRDemoActivity1 extends AppCompatActivity implements View.OnClickListener {
//
//    static final String TAG = "ASRDemoActivity";
//    Button translateLocalFile, start, stop;
//    TextView fromTV, toTV;
//    TextView mainTvResult, languageTVResult;
//
//    String from = "中文", to = "英文";
//    String[] lang = {"中文", "英文"};
//    String rootFilePath = Environment.getExternalStorageDirectory() + "/ydasrDemo/";
//    String rootFilePath2 = rootFilePath + "temp/";
//    String localFilePath = rootFilePath2 + "ydtemp.wav";
//    String transPattern = "stream";
//
//    AsrManager mAsrManager;
//    AudioDefaultWavFileRecorder recorder;
//    AudioRecordConfig mAudioRecordConfig;
//
//    ASRParams asrParams;
//    Timer timer;
//    TimerTask timerTask;
//    int count = 0;
//
//    boolean isRecording, localFile;
//
//    private AsrListener mAsrListener = new AsrListener() {
//        @Override
//        public void onAsrSilentEnd() {
//            Toast.makeText(ASRDemoActivity1.this, "not speak for a while", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onAsrSilentStart() {
//            Toast.makeText(ASRDemoActivity1.this, "not speak after start", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onAsrRestart() {
//            Toast.makeText(ASRDemoActivity1.this, "asr restart", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onAsrStart() {
//            Toast.makeText(ASRDemoActivity1.this, "asr start", Toast.LENGTH_SHORT).show();
//            if (isRecording) {
//                startTiming();
//            }
//
//            if (localFile) {
//                readFile(localFilePath);
//            }
//        }
//
//        @Override
//        public void onAsrStop() {
//            Toast.makeText(ASRDemoActivity1.this, "asr stop", Toast.LENGTH_SHORT).show();
//            localFile = false;
//        }
//
//        @Override
//        public void onAsrReconnecting() {
//            Toast.makeText(ASRDemoActivity1.this, "asr reconnecting...", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onAsrError(@NotNull AsrResultCode error) {
//            Toast.makeText(ASRDemoActivity1.this, error.code+"----"+error.getDes(), Toast.LENGTH_SHORT).show();
//
//        }
//
//        @Override
//        public void onAsrNext(@NotNull AsrResult result, boolean isSentence) {
//            if (result.getResult() != null && !TextUtils.isEmpty(result.getResult().getContext())) {
//                mainTvResult.setText("识别结果：" + result.getResult().getContext());
//            }
//
//            if (result.getResult() != null && !TextUtils.isEmpty(result.getResult().getTranContent())) {
//                languageTVResult.setText("翻译结果：" + result.getResult().getTranContent());
//            }
//        }
//
//        @Override
//        public void onAsrVolumeChange(float volume) {
////            mainTvDecibel.setText(String.valueOf((int) (volume * 100)));
//        }
//
//        @Override
//        public void onBluetoothAudioConnected() {
//            Toast.makeText(ASRDemoActivity1.this, "bluetooth connected", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onBluetoothAudioDisconnected() {
//            Toast.makeText(ASRDemoActivity1.this, "bluetooth disconnected", Toast.LENGTH_SHORT).show();
//        }
//    };
//
//    private String appKey="22bc516de11b3365";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_demo);
//
//        translateLocalFile = findViewById(R.id.btn_local_file);
//        start = findViewById(R.id.btn_start);
//        stop = findViewById(R.id.btn_stop);
//        fromTV = findViewById(R.id.tv_from);
//        toTV = findViewById(R.id.tv_to);
//        mainTvResult = findViewById(R.id.main_tv_result);
//        languageTVResult = findViewById(R.id.tv_result_language);
//
//        createFolder(rootFilePath);
//        createFolder(rootFilePath2);
//
//        asrParams = new ASRParams.Builder()
//                .transPattern(transPattern)
//                .timeoutStart(5000L)
//                .timeoutEnd(10000L)
//                .sentenceTimeout(3000L)
//                .connectTimeout(10000L)
//                .isWaitServerDisconnect(true)
//                .build();
//        mAsrManager = AsrManager.getInstance(this, appKey, asrParams, mAsrListener);
//        getLifecycle().addObserver(mAsrManager);
//
//        translateLocalFile.setOnClickListener(this);
//        fromTV.setOnClickListener(this);
//        toTV.setOnClickListener(this);
//        start.setOnClickListener(this);
//        stop.setOnClickListener(this);
//
//        int bufferSize = 16000 * (16 / 8) * 200 / 1000;
//        mAudioRecordConfig = new AudioRecordConfig(
//                MediaRecorder.AudioSource.VOICE_RECOGNITION,
//                AudioFormat.CHANNEL_IN_MONO,
//                AudioConsts.Recorder.SAMPLE_RATE_16K,
//                AudioFormat.ENCODING_PCM_16BIT,
//                bufferSize
//        );
//
//        File tempFile = getAudioTempFile();
//        File cacheFile = new File(Environment.getExternalStorageDirectory(), "cache");
//
//        recorder = new AudioDefaultWavFileRecorder(mAudioRecordConfig, tempFile, SilenceDetectorConfig.NO_SILENCE_CONFIG,
//                SilenceMode.SILENCE_MODE_WITHOUT_SILENCE_FRAME, cacheFile,
//                new OnAudioRecordListener() {
//                    @Override
//                    public void onAudioFrameRecorded(AudioChunkWrapper audioChunkWrapper) {
//                        Log.d(TAG, "recorder onAudioFrameRecorded");
//                        mAsrManager.insertAudioBytes(audioChunkWrapper.toBytes().clone());
//                    }
//
//                    @Override
//                    public void onReady() {
//                        Log.d(TAG, "recorder onReady");
//                    }
//
//                    @Override
//                    public void onStart() {
//                        Log.d(TAG, "recorder onStart");
//                    }
//
//                    @Override
//                    public void onSilence(long l) {
//                        Log.d(TAG, "recorder onSilence");
//                    }
//
//                    @Override
//                    public void onStop() {
//                        Log.d(TAG, "recorder onStop");
//                    }
//
//                    @Override
//                    public void onRelease() {
//                        Log.d(TAG, "recorder onRelease");
//                    }
//
//                    @Override
//                    public void onError(int i) {
//                        Log.d(TAG, "recorder onError" + i);
//                    }
//                });
//    }
//
//    private File getAudioTempFile() {
//        File audioFile = new File(Environment.getExternalStorageDirectory(), "/ydasrDemo/temp/");
//
//        if (!audioFile.exists()) {
//            audioFile.mkdirs();
//        }
//        return new File(audioFile, "ydtemp.wav");
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_local_file:
//                sendLocalFile(localFilePath);
//                break;
//            case R.id.tv_from:
//                showListDialog(true);
//                break;
//            case R.id.tv_to:
//                showListDialog(false);
//                break;
//            case R.id.btn_start:
//                isRecording = true;
//                start.setEnabled(false);
//                mAsrManager.addWavHead = true;
//                recorder.start();
//                startAsrManager();
//                break;
//            case R.id.btn_stop:
//                isRecording = false;
//                start.setEnabled(true);
//                start.setText("START");
//                stopTimer();
//                recorder.stop();
//                mAsrManager.stop();
//                break;
//        }
//    }
//
//    private void startTiming() {
//        count = 0;
//        timer = new Timer();
//        timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        start.setText(count + "s");
//                        count++;
//                    }
//                });
//            }
//        };
//        timer.schedule(timerTask, 0, 1000);
//    }
//
//    private void stopTimer() {
//        if (timer != null) {
//            timer.cancel();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (recorder != null) {
//            recorder.release();
//        }
//    }
//
//    private void sendLocalFile(String filePath) {
//        localFile = true;
//        File file = new File(filePath);
//        if (!file.exists()) {
//            Toast.makeText(ASRDemoActivity1.this, "文件不存在", Toast.LENGTH_LONG).show();
//            return;
//        }
//        startAsrManager();
//    }
//
//    private void readFile(String filePath) {
//        try {
//            InputStream inputStream = new FileInputStream(filePath);
//            int read = 0;
//            byte[] bytes = new byte[6400];
//            while ((read = inputStream.read(bytes)) != -1) {
//                mAsrManager.insertAudioBytes(bytes);
//                Thread.sleep(200);
//            }
//
//            mAsrManager.stop();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void startAsrManager() {
//        mAsrManager.setASRLanguage(getLanguageCode(from), getLanguageCode(to));
//        mAsrManager.startConnect();
//    }
//
//    private String getLanguageCode(String name) {
//        if ("中文".equals(name)) {
//            return "pt-BRA";
//        }
//        return "en";
//    }
//
//    private void showListDialog(final boolean isFrom) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(ASRDemoActivity1.this);
//        dialog.setItems(lang, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                if (isFrom) {
//                    from = lang[i];
//                    fromTV.setText(from);
//                } else {
//                    to = lang[i];
//                    toTV.setText(to);
//                }
//
//            }
//        });
//
//        dialog.show();
//    }
//
//    private void createFolder(String filePath) {
//        File file = new File(filePath);
//        if (!file.exists()) {
//            file.mkdir();
//        }
//    }
//}
