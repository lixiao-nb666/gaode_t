package com.nrmyw.gaode_t.activity.set.user;


import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.adapter.UserSetAdapter;
import com.nrmyw.gaode_t.util.share.HudShareUtil;
import com.nrmyw.hud_manager_lib.config.HudUserSetConfig;


public class UserSetActivity extends BaseCompatActivity {

    private RecyclerView rv;
    private UserSetAdapter adapter;
    private UserSetAdapter.ItemClick itemClick=new UserSetAdapter.ItemClick() {
        @Override
        public void needInit(UserSetType bleSetType) {

        }

        @Override
        public void nowSelect(UserSetType bleSetType) {

        }

        @Override
        public void nowSelectAdd(UserSetType bleSetType) {
            switch (bleSetType){
                case MAX_BRIGHTNESS:
                    int max= HudUserSetConfig.getInstance().getUserConfigBean().getBrightnessMaxV();
                    max++;
                    if(max>14){
                        max=10;
                    }
                    HudUserSetConfig.getInstance().getUserConfigBean().setBrightnessMaxV(max);
                    HudShareUtil.putUserConfigBean();
                    setItemV(bleSetType,max+"");
                    break;
                case TURN_TYPE_MIN:
                    int minC=HudUserSetConfig.getInstance().getUserConfigBean().getTurnMCha();
                    minC++;
                    if(minC>66){
                        minC=0;
                    }
                    HudUserSetConfig.getInstance().getUserConfigBean().setTurnMCha(minC);
                    HudShareUtil.putUserConfigBean();
                    setItemV(bleSetType,minC+"");
                    break;
            }
        }

        @Override
        public void nowSelectReduce(UserSetType bleSetType) {
            switch (bleSetType){
                case MAX_BRIGHTNESS:
                    int max= HudUserSetConfig.getInstance().getUserConfigBean().getBrightnessMaxV();
                    max--;
                    if(max<10){
                        max=14;
                    }
                    HudUserSetConfig.getInstance().getUserConfigBean().setBrightnessMaxV(max);
                    HudShareUtil.putUserConfigBean();
                    setItemV(bleSetType,max+"");
                    break;
                case TURN_TYPE_MIN:
                    int minC=HudUserSetConfig.getInstance().getUserConfigBean().getTurnMCha();
                    minC--;
                    if(minC<0){
                        minC=14;
                    }
                    HudUserSetConfig.getInstance().getUserConfigBean().setTurnMCha(minC);
                    HudShareUtil.putUserConfigBean();
                    setItemV(bleSetType,minC+"");
                    break;
            }
        }

        @Override
        public void nowSelectSet(UserSetType bleSetType) {

        }

        @Override
        public void nowSelectUpdate(UserSetType bleSetType) {
            switch (bleSetType){
                case MIN_BRIGHTNESS:
                    int min=HudUserSetConfig.getInstance().getUserConfigBean().getBrightnessMinV();
                    if(min==0){
                        min=1;
                    }else {
                        min=0;
                    }
                    HudUserSetConfig.getInstance().getUserConfigBean().setBrightnessMinV(min);
                    HudShareUtil.putUserConfigBean();
                    setItemV(bleSetType,min+"");
                    break;
            }
        }
    };
    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_user_set;
    }

    @Override
    public void initView() {
        rv=findViewById(R.id.rv);
    }

    @Override
    public void initData() {

        adapter=new UserSetAdapter(context,itemClick);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
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


    private void setItemV(UserSetType userSetType,String v){
        userSetType.setV(v);
        Message msg=new Message();
        msg.what=UserSetMsgType.NOTIFY_ITEM.ordinal();
        msg.arg1=userSetType.ordinal();
        handler.sendMessage(msg);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                UserSetMsgType userSetMsgType=UserSetMsgType.values()[msg.what];
                switch (userSetMsgType){
                    case NOTIFY_ITEM:
                        adapter.notifyItemChanged(msg.arg1);
                        break;
                }

            }catch (Exception e){

            }

        }
    };
}
