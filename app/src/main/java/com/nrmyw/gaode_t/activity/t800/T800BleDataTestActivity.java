package com.nrmyw.gaode_t.activity.t800;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newbee.bulid_lib.mybase.LG;
import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.newbee.bulid_lib.util.RandomUtil;
import com.nrmyw.ble_event_lib.bean.BleSendImageEndInfoBean;
import com.nrmyw.ble_event_lib.statu.BleStatu;
import com.nrmyw.ble_event_lib.statu.BleStatuEventObserver;
import com.nrmyw.ble_event_lib.statu.BleStatuEventSubscriptionSubject;
import com.nrmyw.ble_event_lib.util.BleByteUtil;
import com.nrmyw.gaode_t.R;

import com.nrmyw.hud_data_lib.bean.HudLaneCountBean;
import com.nrmyw.hud_data_lib.bean.HudLaneHiPassCountBean;
import com.nrmyw.hud_data_lib.event.retrun.HudRetrunEventObserver;
import com.nrmyw.hud_data_lib.event.retrun.HudRetrunEventSubscriptionSubject;
import com.nrmyw.hud_data_lib.type.display.HudSetDisplayDirectionType;
import com.nrmyw.hud_data_lib.type.image.HudImageType;
import com.nrmyw.hud_data_lib.type.lane.HudLaneType;
import com.nrmyw.hud_data_lib.type.lane.HudNowLaneStrType;
import com.nrmyw.hud_data_lib.type.notification.HudNotificationIconType;
import com.nrmyw.hud_data_lib.type.reach.HudReachType;
import com.nrmyw.hud_data_lib.type.set.HudGpsStatuType;
import com.nrmyw.hud_data_lib.type.speed.HudSpeedingShowBJType;
import com.nrmyw.hud_data_lib.type.speed.HudSpeedingTextType;
import com.nrmyw.hud_data_lib.type.speed.SpeedType;
import com.nrmyw.hud_data_lib.type.turn.HudTurnBjType;
import com.nrmyw.hud_data_lib.type.turn.HudTurnType;
import com.nrmyw.hud_data_lib.type.type.HudStatuType;
import com.nrmyw.hud_data_lib.type.warningproint.HudWarningPointType;
import com.nrmyw.hud_data_lib.type.yellow_statu.HudYellowStatuBjType;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.hud.util.HudLuminaceManager;

import com.nrmyw.hud_manager_lib.manager.set_child.HudSpeedManager;
import com.nrmyw.hud_manager_lib.type.HudDisplayMold;
import com.nrmyw.hud_manager_lib.util.DistanceToHudUtil;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class T800BleDataTestActivity extends BaseCompatActivity {
    private RecyclerView rv;
    private TextView showUseTV,showCmdSendTV,showCmdRetrunTV;
    private T800TestTitleAdapter adapter;
    long firstTime;
    long lastTime;
    int numb=0;
    private BleStatuEventObserver bleStatuEventObserver=new BleStatuEventObserver() {
        @Override
        public void sendBleStatu(BleStatu bleStatu, Object... objects) {
            switch (bleStatu){
                case CAN_SEND_DATA:
                    numb++;
//                    LG.i("收到指令没");
//                    BLEDataTools.testSpeed();
                    long nowTime=System.currentTimeMillis();
                    if(firstTime==0){
                        firstTime=nowTime;
//                        LG.i("指令时间差为:次数为--,当前发送总次数为:"+numb);
                    }else {
                        long timezongcha=(nowTime-firstTime)/1000;
//                        LG.i("指令时间差为:次数为---"+timezongcha+"s,当前发送总次数为:"+numb);
                    }
                    if(lastTime!=0){
                        long shijiancha=nowTime-lastTime;
//                        LG.i("指令时间差为:"+shijiancha);
                    }
                    lastTime=nowTime;
                    break;
                case SENDING_DATA:
                    byte[] cmd= (byte[]) objects[0];
                    String cmdStr= BleByteUtil.parseByte2HexStr(cmd);
                    setShowCmdSendTV(cmdStr);
                    LG.i( "shenmegui","发送 === "+cmd.length+"--"+BleByteUtil.parseByte2HexStr(cmd));
                    break;
                case RETRUN_BYTES:
//                    byte[] retrunBytes= (byte[]) objects[0];

                    break;
                case SEND_IMAGE_START:

                    break;
                case SEND_IMAGE_END:

                    BleSendImageEndInfoBean bleSendImageEndInfoBean= (BleSendImageEndInfoBean) objects[0];

//                    HudManager.getInstance().getHudEvent().setShowImageBitmapQualityType(BleSendBitmapQualityType.ULTRA_LOW);
                    break;
                case SEND_IMAGE_DATA_END:
                    if(null!=objects&&objects.length>0){
                        long sendTime= (long) objects[0];
                        long sendImageEndTime=System.currentTimeMillis();
                        setShowUseTV("Send Image time :"+sendTime+"---"+(sendImageEndTime-startSendImageTime));
                    }
                    break;
                case RUN_ERR:
                    break;
            }
        }
    };




    private HudRetrunEventObserver hudRetrunEventObserver=new HudRetrunEventObserver() {
        @Override
        public void none(byte[] bytes) {
            setShowCmdRetrunTV("none :"+new String(bytes, StandardCharsets.UTF_8));
        }

        @Override
        public void getBrightnessV(int mode, int autoV, int handV) {
            setShowCmdRetrunTV("Brightness V:"+mode+"-"+autoV+"-"+handV);
        }



        @Override
        public void getSoundV(int v) {
            setShowCmdRetrunTV("Sound V:"+v);
        }

        @Override
        public void getDeviceSoundStatu(int deviceSoundStatu) {
            setShowCmdRetrunTV("DeviceSoundStatu:"+ HudStatuType.values()[deviceSoundStatu]);
        }

        @Override
        public void getSN(String sn) {
            Log.i("checksncode","checksncode:1---"+sn+"---"+sn.length());

            setShowCmdRetrunTV("SN Code:"+sn);
        }


        /**
         * 清除ASCII码中的换行符等特殊字符
         * @param dirtyString
         * @return
         */
        public  String cleanString(String dirtyString) {
            char[] charArray = dirtyString.toCharArray();
            String cleanStr = "";
            for (char charactor : charArray) {

                Log.i("checksncode","checksncode:5---"+charactor+"--"+ Character.isSpaceChar(charactor));
                Integer[] wrongChar = {1, 2, 3, 4, 5, 6, 7, 8, 12, 13, 14, 15, 16, 17, 18, 19, 20, 12, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
                List<Integer> arr = Arrays.asList(wrongChar);
                Integer thisChar = new Integer(charactor);
                if (arr.contains(thisChar)) {//如果包含非法字符，就跳过
                    Log.i("Alex", "警告：该字符串包含非法ASCII字符" + dirtyString);
                    continue;
                } else if (thisChar == 10) {//换行符处理，把换行符换成\n，修复不同android版本不兼容的问题
                    cleanStr = cleanStr.concat("\n");
                } else {//正常字符
                    cleanStr = cleanStr.concat(String.valueOf(charactor));
                }
            }
            return cleanStr;
        }



        @Override
        public void getDeviceVersion(String deviceVersion) {
            Log.i("checksncode","checksncode:3---"+deviceVersion);
            setShowCmdRetrunTV("Device version:"+deviceVersion);
        }

        @Override
        public void getGPSSpeed(int v) {
            setShowCmdRetrunTV("gps speed v:"+v);
        }


    };

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String showStr= (String) msg.obj;
                    showCmdSendTV.setText(showStr);
                    break;
                case 1:
                    String cmdStr=(String) msg.obj;
                    LG.i("kankanshemeguu","----kankansjfsdjfs:"+cmdStr);
                    showCmdRetrunTV.setText(cmdStr);
                    break;
                case 2:
                    String showUse= (String) msg.obj;
                    showUseTV.setText(showUse);

            }




        }
    };

    private void setShowCmdSendTV(String cmd){
        Log.i(tag,"setShowCmdSendTV()>>>:"+cmd);
        Message message=new Message();
        message.what=1;
        message.obj=cmd;
        handler.sendMessage(message);
    }

    private void setShowCmdRetrunTV(String str){
        Log.i(tag,"setShowCmdRetrunTV()>>>:"+str);
        Message message=new Message();
        message.what=0;
        message.obj=str;
        handler.sendMessage(message);
    }

    private void setShowUseTV(String str){
        Message message=new Message();
        message.what=2;
        message.obj=str;
        handler.sendMessage(message);
    }

    private int wpIndex=0;
    private int twLoopIndex=0;
    private int speedingStyle=-1;
//    private int yellowStatuStyle=0;
    private T800TestDataAdapter.ItemClick itemClick=new T800TestDataAdapter.ItemClick() {
        @Override
        public void nowSelect(T800TestDataType t800TestDataType,Object... objects) {
            switch (t800TestDataType){
                case mq_test:
                    for(int i=0;i<6;i++){
                        HudManager.getInstance().getHudEvent().sendTime();
                        HudManager.getInstance().getHudEvent().sendNowSpeed(getRandomIntBySpeed());
                        HudManager.getInstance().getHudEvent().sendReachInfo(getRandomIntBySpeedByMeter(10000),15,33, HudReachType.NONE);
                        HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.roadName,"T800Lane:"+RandomUtil.getInstance().getRandomInt(100));
                        HudManager.getInstance().getHudEvent().queDeviceVersion();
                    }
                    break;
                case time:
                    HudManager.getInstance().getHudEvent().sendTime();
                    break;
                case speed_1:
//                    int speed1=getRandomIntBySpeed();
                    int speed1= (int) objects[0];
                    HudManager.getInstance().getHudEvent().sendNowSpeed(speed1);
//                    HudSpeedManager.getInstance().setNowSpeed(speed1);


                    setShowUseTV("send speed:"+speed1);
                    break;
                case speed_2:
//                    int speed2=getRandomIntBySpeed();
//                    int limitedSpeed1 =getRandomIntBySpeed();
//                    int limitedSpeed2 =getRandomIntBySpeed();
                    int speed2= (int) objects[0];
                    int limitedSpeed1= (int) objects[1];
                    int limitedSpeed2= (int) objects[2];
//                    HudManager.getInstance().getHudEvent().sendNowSpeed(speed2,limitedSpeed1,limitedSpeed2);
                    HudSpeedManager.getInstance().setNowSpeed(speed2);
                    HudSpeedManager.getInstance().setLimitSpeedToCountShowSpeedBj(limitedSpeed1,limitedSpeed2);
                    setShowUseTV("send speed:"+speed2+"-"+limitedSpeed1+"-"+limitedSpeed2);
                    break;
                case speed_3:
                    int speed3= (int) objects[0];
                    HudManager.getInstance().getHudEvent().sendNowSpeed(speed3, SpeedType.NO_DESTINATION);
                    setShowUseTV("send speed NO_DESTINATION:"+speed3);
                    break;
                case IntervalSpeed:
                    int is1= (int) objects[0];
                    int isDis= (int) objects[1];
                    int is2= (int) objects[2];
                    int isH= (int) objects[3];
                    int isM= (int) objects[4];
                    HudManager.getInstance().getHudEvent().sendIntervalSpeed(is1,isDis,is2,isH,isM);

//                    HudDataEvent.getInstance().setIntervalSpeed(is1,isDis,is2);
                    break;
                case Hide_IntervalSpeed:
                    HudManager.getInstance().getHudEvent().hideIntervalSpeed();
                    break;
                case speeding:
//                    speedingStyle++;
//                    if(speedingStyle>=HudSpeedingShowBJType.values().length){
//                        speedingStyle=0;
//                    }
//                    HudSpeedingTextType speedingTextType=null;
//                    if(speedingStyle==0){
//                        speedingTextType=HudSpeedingTextType.WRITE;
//                    }else {
//                        speedingTextType=HudSpeedingTextType.RED;
//                    }
//                    HudSpeedingShowBJType speedingShowBJType=HudSpeedingShowBJType.values()[speedingStyle];
                    HudSpeedingShowBJType speedingShowBJType= (HudSpeedingShowBJType) objects[0];
                    HudSpeedingTextType speedingTextType=null;
                    switch (speedingShowBJType){
                        case NONE:
                            speedingTextType=HudSpeedingTextType.WRITE;
                            break;
                        case YELLOW_15:
                        case YELLOW_30:
                            speedingTextType=HudSpeedingTextType.YELLOW;
                            break;
                        case RED_40:
                            speedingTextType=HudSpeedingTextType.PINK;
                            break;
                        default:
                            speedingTextType=HudSpeedingTextType.RED;
                            break;
                    }
                    LG.i("kankandaima","kankandaima--"+speedingTextType+"---"+speedingShowBJType);
                    HudManager.getInstance().getHudEvent().sendSpeeding(speedingTextType,speedingShowBJType);
                    break;
                case WarningPoint_LimitedSpeed:
                    int wpLimitedSpeed1= (int) objects[0];
                    int wpLimitedSpeed2= (int) objects[1];
                    HudManager.getInstance().getHudEvent().sendWarningPointLimitedSpeed(wpLimitedSpeed1,wpLimitedSpeed2);
                    setShowUseTV("send Speed:"+wpLimitedSpeed1+"-"+wpLimitedSpeed2);
                    break;
                case WarningPoint_1:
//                case BIG_WarningPoint_1:
//                    int w1=RandomUtil.getInstance().getRandomInt(HudWarningPointType.values().length);
//                    int d1=getRandomIntBySpeedByMeter(2000);
                    HudWarningPointType hudWarningPointType= (HudWarningPointType) objects[0];
                    int wpDis= (int) objects[1];
//                    HudManager.getInstance().getHudEvent().sendWarningPoint(HudWarningPointType.values()[w1],d1);
                    HudManager.getInstance().getHudEvent().sendWarningPoint(hudWarningPointType,wpDis);
                    break;
                case WarningPoint_2:
                    HudWarningPointType hudWarningPointType1= (HudWarningPointType) objects[0];
                    HudWarningPointType hudWarningPointType2= (HudWarningPointType) objects[1];
//                    int w21=RandomUtil.getInstance().getRandomInt(HudWarningPointType.values().length);
                    int d21=getRandomIntBySpeedByMeter(2000);
//                    int w22=RandomUtil.getInstance().getRandomInt(HudWarningPointType.values().length);
                    int d22=getRandomIntBySpeedByMeter(2000);
//                    HudManager.getInstance().getHudEvent().sendWarningPoint(HudWarningPointType.values()[w21],d21,HudWarningPointType.values()[w22],d22);
                    HudManager.getInstance().getHudEvent().sendWarningPoint(hudWarningPointType1,d21,hudWarningPointType2,d22);
                    break;
                case BIG_WarningPoint_1:
//                    int bw1=RandomUtil.getInstance().getRandomInt(T800WarningPointType.values().length);
//                    int bd1=getRandomIntBySpeedByMeter(2000);
//                    HudManager.getInstance().getHudEvent().sendBigWarningPoint(HudWarningPointType.values()[wpIndex],bd1);
//                    wpIndex++;
//                    if(wpIndex>=HudWarningPointType.values().length){
//                        wpIndex=0;
//                    }
                    HudWarningPointType hudWarningBigPointType= (HudWarningPointType) objects[0];
                    int wpBigDis= (int) objects[1];
                    HudManager.getInstance().getHudEvent().sendBigWarningPoint(hudWarningBigPointType,wpBigDis);
                    break;
                case WarningPoint_1_T:
                    String wpT1S= (String) objects[0];
                    HudManager.getInstance().getHudEvent().sendWarningPoint1TitleStr(wpT1S);
                    break;
                case WarningPoint_1_B:
                    String wpB1S= (String) objects[0];
                    HudManager.getInstance().getHudEvent().sendWarningPoint1BodyStr(wpB1S);
                    break;
                case WarningPoint_2_T:
                    String wpT2S= (String) objects[0];
                    HudManager.getInstance().getHudEvent().sendWarningPoint2TitleStr(wpT2S);
                    break;
                case WarningPoint_2_B:
                    String wpB2S= (String) objects[0];
                    HudManager.getInstance().getHudEvent().sendWarningPoint2BodyStr(wpB2S);
                    break;
                case WarningPoint_PASS_SHOW_1:
                    HudManager.getInstance().getHudEvent().setWarningPointPass(HudStatuType.OPEN,HudStatuType.OPEN,HudStatuType.CLOSE);
                    break;
                case WarningPoint_PASS_SHOW_2:
                    HudManager.getInstance().getHudEvent().setWarningPointPass(HudStatuType.OPEN,HudStatuType.CLOSE,HudStatuType.CLOSE);
                    break;
                case WarningPoint_PASS_SHOW_3:
                    HudManager.getInstance().getHudEvent().setWarningPointPass(HudStatuType.CLOSE,HudStatuType.OPEN,HudStatuType.CLOSE);
                    break;
                case WarningPoint_PASS_SHOW_4:
                    HudManager.getInstance().getHudEvent().setWarningPointPass(HudStatuType.CLOSE,HudStatuType.CLOSE,HudStatuType.OPEN);
                    break;
                case WarningPoint_PASS_HIDE:
                    HudManager.getInstance().getHudEvent().hideWarningPointPass();
                    break;
                case ReachInfo_1:
                    int reach1Dis= (int) objects[0];
                    int reach1H= (int) objects[1];
                    int reach1M= (int) objects[2];
                    HudReachType hudReachType= (HudReachType) objects[3];
//                    HudManager.getInstance().getHudEvent().sendReachInfo(getRandomIntBySpeedByMeter(10000),14,30);
                    HudManager.getInstance().getHudEvent().sendReachInfo(reach1Dis,reach1H,reach1M,hudReachType);
//                    HudDataEvent.getInstance().setReachInfo(reach1Dis,reach1M);
                    break;
                case ReachInfo_2:
                    String reachStr= (String) objects[0];
//                    HudManager.getInstance().getHudEvent().sendReachInfo("fdsfsfjskljf");
                    HudManager.getInstance().getHudEvent().sendReachInfo(reachStr);
                    break;

                case LaneInformation_hide:
                    if(showLaneIsNew){
                        HudManager.getInstance().getHudEvent().sendTurnLaneHide();
                    }else {
                        HudManager.getInstance().getHudEvent().sendLaneHide();
                    }

                    break;
                case LaneInformation_0:
//                    int r0=RandomUtil.getInstance().getRandomInt(10);
//                    r0++;
//
//                    int indexLI0=RandomUtil.getInstance().getRandomInt(HudLaneType.values().length-r0);
                    HudLaneCountBean hudLaneCountBean0= (HudLaneCountBean) objects[0];
                    if(null==hudLaneCountBean0||hudLaneCountBean0.getLaneList().size()==0){
                        return;
                    }
                    if(hudLaneCountBean0.getLaneList().size()%2==0){
                        hudLaneCountBean0.setRightIndex(2);
                    }else {
                        hudLaneCountBean0.setLeftIndex(2);
                    }
                    LG.i(tag,"lixiaokankanshuju:"+hudLaneCountBean0);
                    if(showLaneIsNew){
                        HudManager.getInstance().getHudEvent().sendTurnLaneInformation(hudLaneCountBean0);
                    }else {
                        HudManager.getInstance().getHudEvent().sendLaneInformation(hudLaneCountBean0);
                    }

                    break;
                case LaneInformation_1:
                    int ln1=0;
                    if(showLaneIsNew){
                        HudManager.getInstance().getHudEvent().sendTurnLaneInformation(getNeedT800LaneCountBean(ln1));
                    }else {
                        HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(ln1));
                    }

                    break;
                case LaneInformation_2:
                    int ln2=10;
                    if(showLaneIsNew){
                        HudManager.getInstance().getHudEvent().sendTurnLaneInformation(getNeedT800LaneCountBean(ln2));
                    }else {
                        HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(ln2));
                    }
                    break;
                case LaneInformation_3:
                    int ln3=20;
                    if(showLaneIsNew){
                        HudManager.getInstance().getHudEvent().sendTurnLaneInformation(getNeedT800LaneCountBean(ln3));
                    }else {
                        HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(ln3));
                    }
                    break;
                case LaneInformation_4:
                    int ln4=30;
                    if(showLaneIsNew){
                        HudManager.getInstance().getHudEvent().sendTurnLaneInformation(getNeedT800LaneCountBean(ln4));
                    }else {
                        HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(ln4));
                    }

                    break;
                case LaneInformation_5:
                    int ln5=40;
                    if(showLaneIsNew){
                        HudManager.getInstance().getHudEvent().sendTurnLaneInformation(getNeedT800LaneCountBean(ln5));
                    }else {
                        HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(ln5));
                    }
                    break;
                case LaneInformation_6:
                    int ln6=50;
                    if(showLaneIsNew){
                        HudManager.getInstance().getHudEvent().sendTurnLaneInformation(getNeedT800LaneCountBean(ln6));
                    }else {
                        HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(ln6));
                    }
                    break;
                case LaneInformation_7:
                    int ln7=60;
                    if(showLaneIsNew){
                        HudManager.getInstance().getHudEvent().sendTurnLaneInformation(getNeedT800LaneCountBean(ln7));
                    }else {
                        HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(ln7));
                    }
                    break;
                case LaneInformation_8:
                    int ln8=70;
                    if(showLaneIsNew){
                        HudManager.getInstance().getHudEvent().sendTurnLaneInformation(getNeedT800LaneCountBean(ln8));
                    }else {
                        HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(ln8));
                    }
                    break;
//                case LaneInformation_hi_pass_auto_l:
//                    HudCmdDataUtil.autoGetHipassL=!T800CmdDataUtil.autoGetHipassL;
//                    break;
                case LaneInformation_hi_pass_1:
//                    HudLaneHiPassCountBean t800LaneHiPassCountBean=new HudLaneHiPassCountBean();
                    HudLaneHiPassCountBean t800LaneHiPassCountBean= (HudLaneHiPassCountBean) objects[0];
//                    int lihpr=RandomUtil.getInstance().getRandomInt(8)+1;
//                    for(int i=0;i<lihpr;i++){
//                        int nowRi=RandomUtil.getInstance().getRandomInt(20)+1;
//                        t800LaneHiPassCountBean.add(nowRi);
//                    }
                    Log.i(tag,"kankanzhide:"+t800LaneHiPassCountBean.toString());
                    if(showLaneIsNew){
                        HudManager.getInstance().getHudEvent().sendTurnLaneHiPass(t800LaneHiPassCountBean);
                    }else {
                        HudManager.getInstance().getHudEvent().sendLaneHiPass(t800LaneHiPassCountBean);
                    }

                    break;
                case LaneInformation_hi_pass_2:
                    HudLaneHiPassCountBean t800LaneHiPassCountBean2=new HudLaneHiPassCountBean();
                    t800LaneHiPassCountBean2.add(1);
                    t800LaneHiPassCountBean2.add(-1);
                    t800LaneHiPassCountBean2.add(10);
                    t800LaneHiPassCountBean2.add(-1);
                    t800LaneHiPassCountBean2.add(20);
                    t800LaneHiPassCountBean2.add(-1);
                    t800LaneHiPassCountBean2.add(30);
                    if(showLaneIsNew){
                        HudManager.getInstance().getHudEvent().sendTurnLaneHiPass(t800LaneHiPassCountBean2);
                    }else {
                        HudManager.getInstance().getHudEvent().sendLaneHiPass(t800LaneHiPassCountBean2);
                    }
                    break;
                case TurnType_1:
//                    int tw1=RandomUtil.getInstance().getRandomInt(HudTurnType.values().length);
//                    int td1=getRandomIntBySpeedByMeter(2000);
//                    HudManager.getInstance().getHudEvent().sendTurnType(HudTurnType.values()[tw1],td1);
                    HudTurnType tw1= (HudTurnType) objects[0];
                    int td1= (int) objects[1];
                    if(showTrunTypeisNew){
                        HudManager.getInstance().getHudEvent().sendNewTurnType(tw1,td1);
                    }else {
                        HudManager.getInstance().getHudEvent().sendTurnType(tw1,td1);
                    }

                    break;
                case TurnType_2:
//                    int tw21=RandomUtil.getInstance().getRandomInt(HudTurnType.values().length);
                    HudTurnType tw21= (HudTurnType) objects[0];
                    HudTurnType tw22= (HudTurnType) objects[1];
                    int td21=getRandomIntBySpeedByMeter(2000);
//                    int tw22=RandomUtil.getInstance().getRandomInt(HudTurnType.values().length);
                    int td22=1100;
                    if(showTrunTypeisNew){
                        HudManager.getInstance().getHudEvent().sendNewTurnType(tw21,td21,tw22,td22);
                    }else {
                        HudManager.getInstance().getHudEvent().sendTurnType(tw21,td21,tw22,td22);
                    }

//                    HudManager.getInstance().getHudEvent().sendTurnType(HudTurnType.values()[tw21],td21,HudTurnType.values()[tw22],td22);
                    break;
                case TurnType_3:
                    if(showTrunTypeisNew){
                        HudManager.getInstance().getHudEvent().sendNewTurnType(HudTurnType.values()[twLoopIndex],60);
                    }else {
                        HudManager.getInstance().getHudEvent().sendTurnType(HudTurnType.values()[twLoopIndex],60);
                    }

                    setShowUseTV("trun type index："+ BleByteUtil.parseByte2HexStr(HudTurnType.values()[twLoopIndex].getType())+"--"+HudTurnType.values()[twLoopIndex]);
                    twLoopIndex++;
                    if(twLoopIndex>=HudTurnType.values().length){
                        twLoopIndex=0;
                    }
                    break;



                case NOTIFICTION_ICON:
                    HudNotificationIconType hnit1= (HudNotificationIconType) objects[0];
                    HudNotificationIconType hnit2= (HudNotificationIconType) objects[1];

                    HudManager.getInstance().getHudEvent().notifictionIcon(hnit1,hnit2);

                    break;
                case BIG_TurnType_SHOW:
                    HudManager.getInstance().getHudEvent().showBigTurnType();

                    break;
                case BIG_TurnType_HIDE:
                    HudManager.getInstance().getHudEvent().hideBigTurnType();
                    break;
                case NEXT_ROAD_NAME:
                    String nextRoadName= (String) objects[0];
                    HudManager.getInstance().getHudEvent().sendNextLaneName(nextRoadName);
//                    HudManager.getInstance().getHudEvent().sendNextLaneName("下一条道路名:"+RandomUtil.getInstance().getRandomInt(100));
                    break;
                case NOW_ROAD_NAME_HIDE:
                    HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.none,"");
                    break;
                case NOW_ROAD_NAME_1:
                    String nowRoadName1= (String) objects[0];
//                    if(nowRoadName1.length()>8){
//                        nowRoadName1=nowRoadName1+"            ";
//                    }
                    Log.i("kankanroadsize-----","kankanroadsize-----"+nowRoadName1+"---"+nowRoadName1.length());
                    HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.destination,nowRoadName1);
                    break;
                case NOW_ROAD_NAME_2:
                    String nowRoadName2= (String) objects[0];
                    HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.location,nowRoadName2);
                    break;
                case NOW_ROAD_NAME_3:
                    String nowRoadName3= (String) objects[0];
                    HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.roadName,nowRoadName3);
                    break;
                case NOW_ROAD_NAME_4:
                    String nowRoadName4= (String) objects[0];
//                    HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.def,"EDF----道路名:"+RandomUtil.getInstance().getRandomInt(100));
                    HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.def,nowRoadName4);
                    break;
                case gps_0:
                    HudManager.getInstance().getHudEvent().sendGpsStatu(HudGpsStatuType.CLOSE);
                    break;
                case gps_1:
                    HudManager.getInstance().getHudEvent().sendGpsStatu(HudGpsStatuType.OPEN);
                    break;
                case gps_2:
                    HudManager.getInstance().getHudEvent().sendGpsStatu(HudGpsStatuType.LOST);
                    break;
                case SET_GPS:
//                    int gpsSpeedV=RandomUtil.getInstance().getRandomInt(11);
                    int gpsSpeedV= (int) objects[0];
                    setShowUseTV("set gps speed v:"+gpsSpeedV);
                    HudManager.getInstance().getHudEvent().setGpsSpeed(gpsSpeedV);
                    break;
                case que_GPS:
                    HudManager.getInstance().getHudEvent().queGpsSpeed();
                    break;
                case Brightness_1:
                    HudManager.getInstance().getHudEvent().sendBrightnessAuto();
                    break;
                case Brightness_2:
//                    int brightness=RandomUtil.getInstance().getRandomInt(10)+1;
                    int brightness= (int) objects[0];
                    setShowUseTV("set brightness:"+brightness);
                    if(brightness>10){
                        HudManager.getInstance().getHudEvent().sendBrightnessHandDebug(brightness);
                    }else {
                        HudManager.getInstance().getHudEvent().sendBrightnessHand(brightness);
                    }

                    break;
                case LuminancePercent:
                    int lowV=RandomUtil.getInstance().getRandomInt(40);
                    int hightV=lowV+50;
                    setShowUseTV("set Luminance:"+lowV+"-"+hightV);
                    HudManager.getInstance().getHudEvent().setLuminancePercent(lowV,hightV);
                    break;
                case que_Brightness:
                    HudManager.getInstance().getHudEvent().queBrightness();
                    break;
                case que_SN:
                    HudManager.getInstance().getHudEvent().queSN();
                    break;
                case rewrite_SN:
//                    String sn="T700";
//                    sn+=RandomUtil.getInstance().getRandomString(8);
                    String sn= (String) objects[0];
                    HudManager.getInstance().getHudEvent().rewriteSN(sn.toUpperCase());
                    break;
                case que_DeviceVersion:
                    HudManager.getInstance().getHudEvent().queDeviceVersion();
                    break;
                case SET_BLE_DATA_NAME:
                    String nowBleDataName=HudManager.getInstance().getHudDevice().getBleDevice().getDeviceName();
                    int index=nowBleDataName.indexOf("!");
                    if(index==-1){
                        nowBleDataName=nowBleDataName+"!"+RandomUtil.getInstance().getRandomString(5);
                    }else {
                        nowBleDataName=nowBleDataName.substring(0,index)+"!"+RandomUtil.getInstance().getRandomString(5);
                    }
                    HudManager.getInstance().getHudEvent().setBleName(nowBleDataName);

                    break;

                case SET_BLE_TWS_NAME:
                    String newTwsName="HUD-T700-"+RandomUtil.getInstance().getRandomString(5);
                    HudManager.getInstance().getHudEvent().setTwsName(newTwsName);
                    break;


                case SET_SOUND:
//                    int soundV=RandomUtil.getInstance().getRandomInt(11);
                    int soundV= (int) objects[0];
                    setShowUseTV("set sound:"+soundV);
                    HudManager.getInstance().getHudEvent().setSound(soundV);
                    break;
                case que_SOUND:
                    HudManager.getInstance().getHudEvent().queSound();
                    break;
                case send_Image1:

                    startSendImageTime=System.currentTimeMillis();
                    Bitmap bt1 = BitmapFactory.decodeResource(context.getResources(),R.drawable.image_test_5);
                    HudManager.getInstance().getHudEvent().sendImage(bt1);
                    break;
                case send_Image2:

                    startSendImageTime=System.currentTimeMillis();
                    Bitmap bt2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.image_test_6);
                    HudManager.getInstance().getHudEvent().sendImage(bt2);
                    break;
                case send_Image3:

                    startSendImageTime=System.currentTimeMillis();
                    Bitmap bt3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_4);
                    HudManager.getInstance().getHudEvent().sendImage(bt3);
                    break;
                case send_Image4:


                    startSendImageTime=System.currentTimeMillis();
                    Bitmap bt4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_4);
                    HudManager.getInstance().getHudEvent().sendImage(bt4);
                    break;
                case send_Image5:

                    startSendImageTime=System.currentTimeMillis();
                    isJd=!isJd;
                    int rsId;
                    if(isJd){
                        rsId=R.drawable.img_test_jd;
                    }else {

                    }
                    rsId=R.drawable.img_test_jd_2;
                    Bitmap bt5 = BitmapFactory.decodeResource(context.getResources(), rsId);
                    HudManager.getInstance().getHudEvent().sendImage(bt5, HudImageType.PROGRESS_BAR);

                    break;
                case show_Image:
//                    Bitmap bt6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_test_1);
//                    HudManager.getInstance().getHudEvent().sendImage(bt6);
                    HudManager.getInstance().getHudEvent().showImage();
                    break;
                case hide_Image:
                    HudManager.getInstance().getHudEvent().hideImage();
                    break;
                case hide_Image_bar:
                    HudManager.getInstance().getHudEvent().hideProgressBar();
                    break;
                case show_wp_Yellow_Statu:
                    HudYellowStatuBjType hysbt1= (HudYellowStatuBjType) objects[0];
                    HudYellowStatuBjType hysbt2= (HudYellowStatuBjType) objects[1];
                    Log.i("kankanzenmehuishHudYellowStatuBjType","kankanzenmehuishHudYellowStatuBjType:"+hysbt1+"--"+hysbt2);
                    HudManager.getInstance().getHudEvent().setWarningPointYellowStatu(hysbt1,hysbt2);
                    break;
                case hide_WP_Yellow_Statu:
                    HudManager.getInstance().getHudEvent().setWarningPointYellowStatu(HudYellowStatuBjType.HIDE,HudYellowStatuBjType.HIDE);
                    break;
                case show_title_Yellow_Statu:
//                    yellowStatuStyle++;
//                    if(yellowStatuStyle>4){
//                        yellowStatuStyle=1;
//                    }
                    HudYellowStatuBjType hudYellowStatuBjType= (HudYellowStatuBjType) objects[0];
//                    HudManager.getInstance().getHudEvent().setYellowStatu(HudYellowStatuBjType.values()[yellowStatuStyle]);
                    HudManager.getInstance().getHudEvent().setTitleYellowStatu(hudYellowStatuBjType);
                    break;
                case hide_title_Yellow_Statu:
                    HudManager.getInstance().getHudEvent().setTitleYellowStatu(HudYellowStatuBjType.HIDE);
                    break;
                case hide_Yellow_Statu:
                    HudManager.getInstance().getHudEvent().hideYellowStatu();
                    break;
                case icon_flicker_open:
                    HudManager.getInstance().getHudEvent().iconFlicherOpen();
                    break;
                case icon_flicker_close:
                    HudManager.getInstance().getHudEvent().iconFlicherClose();
                    break;
                case Yellow_Statu_STR:
                    HudManager.getInstance().getHudEvent().sendYellowStatuStr("黄色状态栏");
                    break;
                case factory_set:
                    HudManager.getInstance().getHudEvent().factorySet();
                    break;
                case device_Sound_open:
                    HudManager.getInstance().getHudEvent().deviceSoundOpen();
                    break;
                case device_Sound_close:
                    HudManager.getInstance().getHudEvent().deviceSoundClose();
                    break;
                case que_device_Sound_Statu:
                    HudManager.getInstance().getHudEvent().queDeviceSound();
                    break;
                case daylight_Statu_open:
                    HudManager.getInstance().getHudEvent().daylightingStatuOpen();
                    break;
                case daylight_Statu_close:
                    HudManager.getInstance().getHudEvent().daylightingStatuClose();
                    break;
                case TURN_BJ:
//                    int numb1=turnBjNumb%4;
//                    HudTurnBjType hudTurnBjType= HudTurnBjType.values()[numb1];
                    HudTurnBjType hudTurnBjType= (HudTurnBjType) objects[0];
                    HudManager.getInstance().getHudEvent().setTurnBj(hudTurnBjType);
//                    turnBjNumb++;
                    break;
//                case SPEEDING_BJ:
//                    int numb2=bjNumb%4;
//                    HudStyleType styleType2=HudStyleType.values()[numb2];
//                    HudManager.getInstance().getHudEvent().setSpeedingBj(styleType2);
//                    bjNumb++;
//                    break;
//                case YELLOW_STATU_BJ:
//                    int numb3=bjNumb%4;
//                    HudStyleType styleType3=HudStyleType.values()[numb3];
//                    HudManager.getInstance().getHudEvent().setYellowStatuBj(styleType3);
//                    bjNumb++;
//                    break;
                case DISPLAY_RECT_1:
                    HudManager.getInstance().getHudEvent().initDisplayRect();
                    break;
                case DISPLAY_RECT_2:
                    HudManager.getInstance().getHudEvent().setDisplayRect(HudSetDisplayDirectionType.TOP,displayTopV);
                    displayTopV++;
                    if(displayTopV>60){
                        displayTopV=0;
                    }
                    break;
                case DISPLAY_RECT_3:
                    HudManager.getInstance().getHudEvent().setDisplayRect(HudSetDisplayDirectionType.BOTTOM,displayBottomV);
                    displayBottomV++;
                    if(displayBottomV>60){
                        displayBottomV=0;
                    }
                    break;
                case TurnType_new_or_old:
                    showTrunTypeisNew=!showTrunTypeisNew;
                    break;
                case LaneInformation_new_or_old:
                    showLaneIsNew=!showLaneIsNew;
                    break;
                case NOTIFICTION_MSG_1:
                    HudManager.getInstance().getHudEvent().notifictionMsg("lixi",33);

                    break;
                case NOTIFICTION_MSG_2:
                    notifictionChange=!notifictionChange;
                    if(notifictionChange){
                        HudManager.getInstance().getHudEvent().notifictionMsg("lixiaogegefsdkjfklsdjfklsdjfsfks",11000,"Bfksffdskfkls",11200);
                    }else {
                        HudManager.getInstance().getHudEvent().notifictionMsg("기흥휴게소휴게소", DistanceToHudUtil.getTmapExitDistance(11400),"기흥휴게소휴",DistanceToHudUtil.getTmapExitDistance(11600));
                    }

                    break;
                case NOTIFICTION_MSG_HIDE:
                    HudManager.getInstance().getHudEvent().notifictionMsgHide();

                    break;
                case TURN_MSG_SHOW:
                    HudManager.getInstance().getHudEvent().showTurnMsgKorean();
                    break;
                case TURN_MSG_HIDE:
                    HudManager.getInstance().getHudEvent().hideTurnMsgKorean();
                    break;
                case FACTORY_TEST:
                    HudManager.getInstance().getHudEvent().factoryTest();
                    break;
            }
        }
    };
    private boolean notifictionChange;

    private boolean showTrunTypeisNew,showLaneIsNew;

    private int displayTopV,displayBottomV;
//    private int turnBjNumb=0;
    private boolean isJd;

    private int getRandomIntByHour(){
        return RandomUtil.getInstance().getRandomInt(24);
    }

    private int getRandomIntByMin(){
        return RandomUtil.getInstance().getRandomInt(60);
    }

    private int getRandomIntBySpeed(){
        return RandomUtil.getInstance().getRandomInt(200);
    }

    private int getRandomIntBySpeedByMeter(int needMi){
        return RandomUtil.getInstance().getRandomInt(needMi);
    }

    public HudLaneCountBean getNeedT800LaneCountBean(int numb){
        int l=HudLaneType.values().length;
        HudLaneCountBean laneCountBean=new HudLaneCountBean();
        for(int i=0;i<10;i++){
            int index=numb+i;
            if(index<l){
                laneCountBean.add(HudLaneType.values()[index]);
            }
        }
        switch (numb){
            case 0:
                laneCountBean.setLeftIndex(4);
                laneCountBean.setRightIndex(4);
                break;
            case 10:
                laneCountBean.setLeftIndex(2);
                laneCountBean.setRightIndex(2);
                break;
            case 20:
                laneCountBean.setLeftIndex(0);
                laneCountBean.setRightIndex(8);
                break;
            case 30:
                laneCountBean.setLeftIndex(9);
                laneCountBean.setRightIndex(0);
                break;
            case 40:
                laneCountBean.setLeftIndex(2);
                laneCountBean.setRightIndex(6);
                break;
            case 50:
                laneCountBean.setLeftIndex(2);
                laneCountBean.setRightIndex(9);
                break;
            case 60:
                laneCountBean.setLeftIndex(10);
                laneCountBean.setRightIndex(1);
                break;
            case 70:
                laneCountBean.setLeftIndex(4);
                laneCountBean.setRightIndex(5);
                break;
        }

        LG.i(tag,"lixiaokankanshuju:11--"+laneCountBean);
        return laneCountBean;
    }

    public HudLaneCountBean getNeedT800LaneCountBean(int numb, int needl){
        int l=HudLaneType.values().length;
        HudLaneCountBean t800LaneCountBean=new HudLaneCountBean();
        for(int i=0;i<needl;i++){
            int index=numb+i;
            if(index<l){
                t800LaneCountBean.add(HudLaneType.values()[index]);
            }
        }
        return t800LaneCountBean;
    }

    private long startSendImageTime;

    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_t800_test;
    }

    @Override
    public void initView() {
        rv=findViewById(R.id.rv);
    }

    private EditText lnET1,lnET2;
    private Button lnQueBTl;

    @Override
    public void initData() {
        showUseTV=findViewById(R.id.tv_show_use);
        showCmdSendTV=findViewById(R.id.tv_show_cmd);
        showCmdRetrunTV=findViewById(R.id.tv_show_cmd_retrun);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        adapter=new T800TestTitleAdapter(context,itemClick);
        rv.setAdapter(adapter);
        lnET1=findViewById(R.id.et_l_numb1);
        lnET2=findViewById(R.id.et_l_numb2);
        lnQueBTl=findViewById(R.id.bt_que_l);
        lnQueBTl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null==lnET1.getText()||null==lnET2.getText()){
                    showToast("numb is null");
                    return;
                }
                String ls1=lnET1.getText().toString();
                String ls2=lnET2.getText().toString();
                if(TextUtils.isEmpty(ls1)||TextUtils.isEmpty(ls2)){
                    showToast("numb is null");
                    return;
                }
                int i1=Integer.valueOf(ls1);
                int i2=Integer.valueOf(ls2);
//                HudDisplayMold.DAY.setLowLuminance(i1);
//                HudDisplayMold.DAY.setHightLuminance(i2);
                showToast("亮度为："+HudDisplayMold.DAY.getLowLuminance()+"--"+HudDisplayMold.DAY.getHightLuminance());
//                HudLuminaceManager.getInstance().setHudDisplayMold(HudDisplayMold.DAY);

                HudManager.getInstance().getHudEvent().setLuminanceTestMode(i1,i2);
            }
        });
        LG.i("kankanwpnumb","kankanwpnumb:"+HudWarningPointType.values().length);
    }

    @Override
    public void initControl() {
//        BleCmdRetrunEventSubscriptionSubject.getInstance().attach(bleCmdRetrunEventObserver);
        HudRetrunEventSubscriptionSubject.getInstence().attach(hudRetrunEventObserver);
        BleStatuEventSubscriptionSubject.getInstance().attach(bleStatuEventObserver);
    }

    @Override
    public void closeActivity() {
        handler.removeCallbacksAndMessages(null);
        BleStatuEventSubscriptionSubject.getInstance().detach(bleStatuEventObserver);
        HudRetrunEventSubscriptionSubject.getInstence().detach(hudRetrunEventObserver);
//        BleCmdRetrunEventSubscriptionSubject.getInstance().detach(bleCmdRetrunEventObserver);
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
}
