package com.nrmyw.huynew.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.nrmyw.gaode_t.R;
import com.nrmyw.hud_data_lib.type.type.HudStatuType;
import com.nrmyw.hud_manager_lib.HudManager;

public class HuyNewCameraActivity extends BaseCompatActivity {
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            MsgType msgType=MsgType.values()[msg.what];
            switch (msgType){
                case TAKE_VIDEO:
                    showDocTV.setText("开始录像了，当前最长时间为3分钟，自动停止录像或者点击停止录像按钮");
                    HudManager.getInstance().getHudEvent().takeVideo(HudStatuType.OPEN);
                    break;
                case TAKE_VIDEO_OVER:
                    showDocTV.setText("结束录像");
                    HudManager.getInstance().getHudEvent().takeVideo(HudStatuType.CLOSE);
                    break;
                case TAKE_Picture:
                    showDocTV.setText("您拍了一张照片！");
                    HudManager.getInstance().getHudEvent().takePhoto();
                    break;
            }
        }
    };

    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_take_video:
                    handler.removeMessages(MsgType.TAKE_VIDEO.ordinal());
                    handler.sendEmptyMessage(MsgType.TAKE_VIDEO.ordinal());
                    break;
                case R.id.bt_take_video_over:
                    handler.removeMessages(MsgType.TAKE_VIDEO_OVER.ordinal());
                    handler.sendEmptyMessage(MsgType.TAKE_VIDEO_OVER.ordinal());
                    break;
                case R.id.bt_take_picture:
                    handler.removeMessages(MsgType.TAKE_Picture.ordinal());
                    handler.sendEmptyMessage(MsgType.TAKE_Picture.ordinal());
                    break;
                case R.id.bt_go_file_activity:
                    toActivity(HuyNewFileActivity.class );
                    break;
            }
        }
    };
    private Button takeVideoBT,takeVideoOverBT,takePictureBT,goFileActivityBT;
    private TextView showDocTV;
    @Override
    public int getViewLayoutRsId() {
        return R.layout.huynew_activity_camera;
    }

    @Override
    public void initView() {
        showDocTV=findViewById(R.id.tv_camera_doc_show);
        takeVideoBT=findViewById(R.id.bt_take_video);
        takeVideoOverBT=findViewById(R.id.bt_take_video_over);
        takePictureBT=findViewById(R.id.bt_take_picture);
        goFileActivityBT=findViewById(R.id.bt_go_file_activity);
    }

    @Override
    public void initData() {
        takeVideoBT.setOnClickListener(onClickListener);
        takePictureBT.setOnClickListener(onClickListener);
        takeVideoOverBT.setOnClickListener(onClickListener);
        goFileActivityBT.setOnClickListener(onClickListener);
    }

    @Override
    public void initControl() {

    }

    @Override
    public void closeActivity() {

    }

    @Override
    public void viewIsShow() {

    }

    @Override
    public void viewIsPause() {

    }

    @Override
    public void changeConfig() {

    }
    private enum MsgType{

        TAKE_VIDEO,
        TAKE_VIDEO_OVER,
        TAKE_Picture,

    }
}
