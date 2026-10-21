package com.nrmyw.huynew.activity;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.newbee.gson_lib.gson.MyGson;
import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.util.HudToNeedPageUtil;
import com.nrmyw.hud_data_lib.type.type.HudStatuType;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.huynew.adapter.HuynewHintstrShowAdapter;
import com.nrmyw.huynew.bean.HintStrShowBean;
import com.nrmyw.huynew.config.HintStrConfig;
import com.nrmyw.huynew.manager.HintStrManager;
import com.nrmyw.huynew.type.HintStrPlayType;
import com.nrmyw.huynew.util.StringSplitUtil;
import java.util.List;


public class HuyNewAddAndShowHintStrActivity extends BaseCompatActivity {
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                MsgType msgType=MsgType.values()[msg.what];
                switch (msgType){
                    case SHOW_HINT_STR:
                        List<String> stringList= StringSplitUtil.getNeedSplitStrList(getHintStrShowBean().getHintStr());
                        huynewHintstrShowAdapter.setList(stringList,getHintStrShowBean().getPlayIndex());
                        break;
                    case SHOW_PLAY_SPEED_NUMB:
                        showPlaySpeedNumb();
                        break;
                    case SHOW_PLAY_TEPE:
                        showPlayMold();
                        break;
                    case START_PLAY:
                        if(HintStrConfig.getPlayType()==HintStrPlayType.REMOTE){
                            return;
                        }
                        int playIndex=  getHintStrShowBean().getPlayIndex();
                        playIndex++;
                        if(playIndex<0||playIndex>=huynewHintstrShowAdapter.getItemCount()){
                            playIndex=0;
                        }
                        getHintStrShowBean().setPlayIndex(playIndex);
                        shareBean();
                        huynewHintstrShowAdapter.setPlayIndex(playIndex);
                        hintStrRV.scrollToPosition(playIndex);
                        String nowStr=huynewHintstrShowAdapter.getItemStr();
                        if(!TextUtils.isEmpty(nowStr)){
                            int nowStrl=nowStr.length();
                            int addTime=nowStrl/(HintStrConfig.getPlaySpeed()+2);
                            long nextTime=2000+(addTime*1000);
                            handler.removeMessages(MsgType.START_PLAY.ordinal());
                            handler.sendEmptyMessageDelayed(MsgType.START_PLAY.ordinal(),nextTime);
                        }
                        setNowShowHintStr(true);
                        break;
                    case PAUSE_PLAY:
                        handler.removeMessages(MsgType.START_PLAY.ordinal());
                        setNowShowHintStr(false);
                        break;
                    case START_REPLAY:
                        getHintStrShowBean().setPlayIndex(-1);
                        handler.removeMessages(MsgType.START_PLAY.ordinal());
                        handler.sendEmptyMessage(MsgType.START_PLAY.ordinal());
                        break;
                }
            }catch (Exception e){}
        }
    };

    private boolean nowIsPlay;
    private void setNowShowHintStr(boolean setIsPlay){
        if(nowIsPlay==setIsPlay){
            return;
        }
        this.nowIsPlay=setIsPlay;
        if(nowIsPlay){
            HudToNeedPageUtil.toTranslationPage();
            nowShowHintStrLL.setVisibility(View.VISIBLE);
        }else {
            HudToNeedPageUtil.toMainPage();
            nowShowHintStrLL.setVisibility(View.GONE);
        }


    }

    private void showPlaySpeedNumb(){
        playSpeedTV.setText(HintStrConfig.getPlaySpeedStr());
    }

    private void showPlayMold(){
        switch (HintStrConfig.getPlayType()){
            case AUTO:
                HintStrConfig.setPlayType(HintStrPlayType.AUTO);
                playMoldTV.setText("自动模式");
                playSpeedLL.setVisibility(View.VISIBLE);

                break;
            case REMOTE:
                HintStrConfig.setPlayType(HintStrPlayType.REMOTE);
                playMoldTV.setText("遥控器模式");
                playSpeedLL.setVisibility(View.GONE);
                handler.removeMessages(MsgType.START_PLAY.ordinal());
                break;
        }
    }


    private EditText titleET,hintStrET;
    private RecyclerView hintStrRV;
    private HuynewHintstrShowAdapter huynewHintstrShowAdapter;
    private HuynewHintstrShowAdapter.ItemEvent itemEvent=new HuynewHintstrShowAdapter.ItemEvent() {
        @Override
        public void lastStr(String lastStr) {
            lastHintStrTV.setText(lastStr);
            HudManager.getInstance().getHudEvent().sendTranslationingResult(lastStr);
        }

        @Override
        public void nowStr(String nowStr) {
            nowHintStrTV.setText(nowStr);
            HudManager.getInstance().getHudEvent().sendTranslationing(nowStr);
        }
    };
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_add_play_speed:
                    HintStrConfig.setPlaySpeed(HintStrConfig.getPlaySpeed()+1);
                    handler.removeMessages(MsgType.SHOW_PLAY_SPEED_NUMB.ordinal());
                    handler.sendEmptyMessage(MsgType.SHOW_PLAY_SPEED_NUMB.ordinal());
                    break;
                case R.id.iv_reduce_play_speed:
                    HintStrConfig.setPlaySpeed(HintStrConfig.getPlaySpeed()-1);
                    handler.removeMessages(MsgType.SHOW_PLAY_SPEED_NUMB.ordinal());
                    handler.sendEmptyMessage(MsgType.SHOW_PLAY_SPEED_NUMB.ordinal());
                    break;
                case R.id.iv_change_play_speed_mold:
                    HintStrConfig.changePlayType();
                    handler.removeMessages(MsgType.SHOW_PLAY_TEPE.ordinal());
                    handler.sendEmptyMessage(MsgType.SHOW_PLAY_TEPE.ordinal());
                    break;
                case R.id.bt_replay:
                    handler.removeMessages(MsgType.START_REPLAY.ordinal());
                    handler.sendEmptyMessage(MsgType.START_REPLAY.ordinal());
                    break;
                case R.id.bt_pause:
                    handler.removeMessages(MsgType.PAUSE_PLAY.ordinal());
                    handler.sendEmptyMessage(MsgType.PAUSE_PLAY.ordinal());
                    break;
                case R.id.bt_play:
                    handler.removeMessages(MsgType.START_PLAY.ordinal());
                    handler.sendEmptyMessage(MsgType.START_PLAY.ordinal());
                    break;
                case R.id.bt_delete:
                    HintStrManager.getInstance().getResultHintStrShowBean().remove(getHintStrShowBean());
                    finish();
                    break;
                case R.id.bt_clear:
                    hintStrET.setText("");
                    break;
                case R.id.bt_share:
                    shareBean();
                    break;
                case R.id.bt_exit:
                    shareBean();
                    finish();
                    break;
            }
        }
    };
    private LinearLayout playSpeedLL;
    private TextView playMoldTV,playSpeedTV;
    private ImageView addPlaySpeedIV,reducePlaySpeedIV,changePlaySpeedMold;
    private Button replayBT,pauseBT,playBT,deleteBT,clearBT,shareBT,exitBT;
    private LinearLayout nowShowHintStrLL;
    private TextView lastHintStrTV,nowHintStrTV;

    @Override
    public int getViewLayoutRsId() {
        return R.layout.huynew_activity_hintstr_add_and_show;
    }

    @Override
    public void initView() {
        nowShowHintStrLL=findViewById(R.id.ll_now_show_play_hint_str);
        lastHintStrTV=findViewById(R.id.tv_last_hint_str);
        nowHintStrTV=findViewById(R.id.tv_now_hint_str);
        playSpeedLL=findViewById(R.id.ll_play_speed);
        titleET=findViewById(R.id.et_title);
        hintStrET=findViewById(R.id.et_hint_str);
        hintStrRV=findViewById(R.id.rv_hint_str);
        playMoldTV=findViewById(R.id.tv_play_speed_mold);
        playSpeedTV=findViewById(R.id.tv_play_speed);
        addPlaySpeedIV=findViewById(R.id.iv_add_play_speed);
        reducePlaySpeedIV=findViewById(R.id.iv_reduce_play_speed);
        changePlaySpeedMold=findViewById(R.id.iv_change_play_speed_mold);
        replayBT=findViewById(R.id.bt_replay);
        pauseBT=findViewById(R.id.bt_pause);
        playBT=findViewById(R.id.bt_play);
        deleteBT=findViewById(R.id.bt_delete);
        clearBT=findViewById(R.id.bt_clear);
        shareBT=findViewById(R.id.bt_share);
        exitBT=findViewById(R.id.bt_exit);
    }

    @Override
    public void initData() {
        showPlayMold();
        showPlaySpeedNumb();
        addPlaySpeedIV.setOnClickListener(onClickListener);
        reducePlaySpeedIV.setOnClickListener(onClickListener);
        changePlaySpeedMold.setOnClickListener(onClickListener);
        replayBT.setOnClickListener(onClickListener);
        pauseBT.setOnClickListener(onClickListener);
        playBT.setOnClickListener(onClickListener);
        deleteBT.setOnClickListener(onClickListener);
        shareBT.setOnClickListener(onClickListener);
        clearBT.setOnClickListener(onClickListener);
        exitBT.setOnClickListener(onClickListener);
        titleET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(null==s||TextUtils.isEmpty(s.toString())){
                    getHintStrShowBean().setTitleStr("");
                    return;
                }
                getHintStrShowBean().setTitleStr(s.toString());
            }
        });

        hintStrET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(null==s||TextUtils.isEmpty(s.toString())){
                    getHintStrShowBean().setHintStr("");
                    showHintStrTv();
                    return;
                }
                getHintStrShowBean().setHintStr(s.toString());
                showHintStrTv();
            }
        });
        huynewHintstrShowAdapter=new HuynewHintstrShowAdapter(this,itemEvent);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hintStrRV.setLayoutManager(layoutManager);
        hintStrRV.setAdapter(huynewHintstrShowAdapter);
    }

    @Override
    public void initControl() {

    }




    @Override
    public void closeActivity() {

    }

    private HintStrShowBean hintStrShowBean;
    @Override
    public void viewIsShow() {
        try {
            String shareStr=getIntentString();
            if(TextUtils.isEmpty(shareStr)){
                getHintStrShowBean();
            }else {
                hintStrShowBean= MyGson.getInstance().fromJson(shareStr,HintStrShowBean.class);
            }
        }catch (Exception e){
            getHintStrShowBean();
        }
        setData();

    }
    private void setData(){
        titleET.setText(getHintStrShowBean().getTitleStr());
        hintStrET.setText(getHintStrShowBean().getHintStr());
        showHintStrTv();
    }

    private HintStrShowBean getHintStrShowBean(){
        if(null==hintStrShowBean){
            hintStrShowBean=new HintStrShowBean();
            long nowTime=System.currentTimeMillis();
            long id=nowTime/1000;
            hintStrShowBean.setId(id);
            hintStrShowBean.setCreatTime(nowTime);
        }
        return hintStrShowBean;
    }

    @Override
    public void viewIsPause() {
        shareBean();

    }

    private void shareBean(){
        if(TextUtils.isEmpty(getHintStrShowBean().getTitleStr())&&TextUtils.isEmpty(getHintStrShowBean().getHintStr())){
            return;
        }
        getHintStrShowBean().setLastChangeTime(System.currentTimeMillis());
        if(TextUtils.isEmpty(getHintStrShowBean().getTitleStr())){
            getHintStrShowBean().setTitleStr("新建提词："+ (HintStrManager.getInstance().getResultHintStrShowBean().getHintStrShowBeanMap().size()+1));
        }
        HintStrManager.getInstance().putHintStr(getHintStrShowBean());
    }



    @Override
    public void changeConfig() {

    }

    private void showHintStrTv(){
        handler.removeMessages(MsgType.SHOW_HINT_STR.ordinal());
        handler.sendEmptyMessage(MsgType.SHOW_HINT_STR.ordinal());
    }




    private enum MsgType{
        NONE,
        SHOW_HINT_STR,
        SHOW_PLAY_SPEED_NUMB,
        SHOW_PLAY_TEPE,
        START_PLAY,
        PAUSE_PLAY,
        START_REPLAY,
    }


}
