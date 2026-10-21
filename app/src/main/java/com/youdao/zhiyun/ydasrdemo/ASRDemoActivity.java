package com.youdao.zhiyun.ydasrdemo;//package com.youdao.zhiyun.ydasrdemo;
//
//
//import android.os.Bundle;
//
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.newbee.yd_translation.R;
//
//import com.newbee.yd_translation.process.YdProcess;
//
//
//
//
//public class ASRDemoActivity extends AppCompatActivity {
//
//
//
//
//
//
//    private YdProcess ydProcess;
//    private YdProcess.Listen listen=new YdProcess.Listen() {
//        @Override
//        public void needWaitFinsh() {
//
//        }
//
//        @Override
//        public void nowMustFinsh() {
//
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_demo);
//        ydProcess=new YdProcess(this,listen);
////        getLifecycle().addObserver(ydProcess.getmAsrManager());
//
//
//
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        ydProcess.startReadAudio(this);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        ydProcess.pause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        ydProcess.close();
//    }
//
//
//
//
//
//
//
//}
