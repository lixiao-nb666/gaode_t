package com.nrmyw.gaode_t.activity.t800;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newbee.bulid_lib.mybase.ToastUtil;
import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.activity.t800.T800TestDataType;
import com.nrmyw.hud_data_lib.bean.HudLaneCountBean;
import com.nrmyw.hud_data_lib.bean.HudLaneHiPassCountBean;
import com.nrmyw.hud_data_lib.type.reach.HudReachType;
import com.nrmyw.hud_data_lib.type.speed.HudSpeedingShowBJType;
import com.nrmyw.hud_data_lib.type.turn.HudTurnBjType;
import com.nrmyw.hud_data_lib.type.turn.HudTurnType;
import com.nrmyw.hud_data_lib.type.warningproint.HudWarningPointType;
import com.nrmyw.hud_data_lib.type.yellow_statu.HudYellowStatuBjType;


;import java.util.List;

public class T800TestDataAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";

    private ItemClick itemClick;
    private LayoutInflater layoutInflater;
    private List<T800TestDataType> dataTypeList;

    public T800TestDataAdapter(Context context, ItemClick itemClick,List<T800TestDataType> dataTypeList) {
        layoutInflater = LayoutInflater.from(context);
        this.itemClick = itemClick;
        this.dataTypeList=dataTypeList;
    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int rsId=0;
        T800TestDataType t800TestDataType=T800TestDataType.values()[viewType];
        switch (t800TestDataType){
            case speed_1:
            case speed_3:
                rsId=R.layout.adapter_t800_test_data_speed_1;
                break;
            case speed_2:
                rsId=R.layout.adapter_t800_test_data_speed_2;
                break;
            case WarningPoint_LimitedSpeed:
                rsId=R.layout.adapter_t800_test_data_wp_speed;
                break;
            case IntervalSpeed:
                rsId=R.layout.adapter_t800_test_data_interval_speed;
                break;
            case speeding:
            case show_Yellow_Statu:
            case TURN_BJ:
                rsId=R.layout.adapter_t800_test_data_speeding;
                break;
            case WarningPoint_1:
            case BIG_WarningPoint_1:
            case TurnType_1:
                rsId=R.layout.adapter_t800_test_data_warning_point_1;
                break;
            case WarningPoint_2:
            case TurnType_2:
                rsId=R.layout.adapter_t800_test_data_warning_point_2;
                break;
            case ReachInfo_1:
                rsId=R.layout.adapter_t800_test_data_reachinfo_1;
                break;
            case ReachInfo_2:
            case NEXT_ROAD_NAME:
            case NOW_ROAD_NAME_1:
            case NOW_ROAD_NAME_2:
            case NOW_ROAD_NAME_3:
            case NOW_ROAD_NAME_4:
            case WarningPoint_1_T:
            case WarningPoint_1_B:
            case WarningPoint_2_T:
            case WarningPoint_2_B:
            case Yellow_Statu_STR:
            case rewrite_SN:
                rsId=R.layout.adapter_t800_test_data_string;
                break;
            case LaneInformation_0:
            case LaneInformation_hi_pass_1:
                rsId=R.layout.adapter_t800_test_data_laneinfo;
                break;
            case SET_GPS:
            case SET_SOUND:
            case Brightness_2:
                rsId=R.layout.adapter_t800_test_data_numb;
                break;
            default:
                rsId=R.layout.adapter_t800_test_data;
                break;
        }


        View itemView = layoutInflater.inflate(rsId, parent, false);
        ViewHodler viewHodler = new ViewHodler(itemView,t800TestDataType);

        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHodler viewHodler = (ViewHodler) holder;
        T800TestDataType t800TestDataType= dataTypeList.get(position);
        viewHodler.sendBt.setText(viewHodler.itemView.getContext().getResources().getText(t800TestDataType.getTitleRsId()));
        switch (t800TestDataType){
            case speeding:
                ArrayAdapter speedingAdapter=new ArrayAdapter(viewHodler.itemView.getContext(), android.R.layout.simple_list_item_1, HudSpeedingShowBJType.values());
                viewHodler.SP1.setAdapter(speedingAdapter);
                viewHodler.SP1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        viewHodler.sp1Index=position;
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });
                break;
            case TURN_BJ:
                ArrayAdapter turnBjAdapter=new ArrayAdapter(viewHodler.itemView.getContext(), android.R.layout.simple_list_item_1, HudTurnBjType.values());
                viewHodler.SP1.setAdapter(turnBjAdapter);
                viewHodler.SP1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        viewHodler.sp1Index=position;
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });
                break;
            case show_Yellow_Statu:
                ArrayAdapter ysBjAdapter=new ArrayAdapter(viewHodler.itemView.getContext(), android.R.layout.simple_list_item_1, HudYellowStatuBjType.values());
                viewHodler.SP1.setAdapter(ysBjAdapter);
                viewHodler.SP1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        viewHodler.sp1Index=position;
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });
                break;
            case WarningPoint_1:
            case BIG_WarningPoint_1:
                ArrayAdapter wpAdapter=new ArrayAdapter(viewHodler.itemView.getContext(), android.R.layout.simple_list_item_1, HudWarningPointType.values());
                viewHodler.SP1.setAdapter(wpAdapter);
                viewHodler.SP1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        viewHodler.sp1Index=position;
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });
                break;
            case ReachInfo_1:
                ArrayAdapter riAdapter=new ArrayAdapter(viewHodler.itemView.getContext(), android.R.layout.simple_list_item_1, HudReachType.values());
                viewHodler.SP1.setAdapter(riAdapter);
                viewHodler.SP1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        viewHodler.sp1Index=position;
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });
                break;
            case TurnType_1:
                ArrayAdapter ttAdapter=new ArrayAdapter(viewHodler.itemView.getContext(), android.R.layout.simple_list_item_1, HudTurnType.values());
                viewHodler.SP1.setAdapter(ttAdapter);
                viewHodler.SP1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        viewHodler.sp1Index=position;
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });
                break;
            case WarningPoint_2:
                ArrayAdapter wp1Adapter=new ArrayAdapter(viewHodler.itemView.getContext(), android.R.layout.simple_list_item_1, HudWarningPointType.values());
                viewHodler.SP1.setAdapter(wp1Adapter);
                viewHodler.SP1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        viewHodler.sp1Index=position;
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });
                ArrayAdapter wp2Adapter=new ArrayAdapter(viewHodler.itemView.getContext(), android.R.layout.simple_list_item_1, HudWarningPointType.values());
                viewHodler.SP2.setAdapter(wp2Adapter);
                viewHodler.SP2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        viewHodler.sp2Index=position;
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });
                break;
            case TurnType_2:
                ArrayAdapter tt1Adapter=new ArrayAdapter(viewHodler.itemView.getContext(), android.R.layout.simple_list_item_1,HudTurnType.values());
                viewHodler.SP1.setAdapter(tt1Adapter);
                viewHodler.SP1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        viewHodler.sp1Index=position;
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });
                ArrayAdapter tt2Adapter=new ArrayAdapter(viewHodler.itemView.getContext(), android.R.layout.simple_list_item_1, HudTurnType.values());
                viewHodler.SP2.setAdapter(tt2Adapter);
                viewHodler.SP2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        viewHodler.sp2Index=position;
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });
                break;
            case LaneInformation_0:
                setItemList(viewHodler,t800TestDataType,0,10);
                break;
            case LaneInformation_hi_pass_1:
                setItemList(viewHodler,t800TestDataType,0,8);
                break;
            case SET_GPS:
            case SET_SOUND:
                setNumbSp(viewHodler,0,10);
                break;
            case Brightness_2:
                setNumbSp(viewHodler,1,10);
                break;
        }
        viewHodler.sendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (t800TestDataType){
                    case speed_1:
                    case speed_3:
                        int nowSpeed1=getNeedSpeed(viewHodler.numb1ET,"now speed is null !");
                        if(nowSpeed1==-1){
                            return;
                        }
                        itemClick.nowSelect(t800TestDataType,nowSpeed1);
                        break;
                    case speed_2:
                        int nowSpeed2=getNeedSpeed(viewHodler.numb1ET,"now speed is null !");
                        if(nowSpeed2==-1){
                            return;
                        }
                        int limitedSpeed1=getNeedSpeed(viewHodler.numb2ET,"speed1 is null !");
                        if(limitedSpeed1==-1){
                            return;
                        }
                        int limitedSpeed2=getNeedSpeed(viewHodler.numb3ET,"speed2 is null !");
                        if(limitedSpeed2==-1){
                            return;
                        }
                        itemClick.nowSelect(t800TestDataType,nowSpeed2,limitedSpeed1,limitedSpeed2);
                        break;
                    case WarningPoint_LimitedSpeed:
                        int wpLimitedSpeed1=getNeedSpeed(viewHodler.numb2ET,"speed1 is null !");
                        if(wpLimitedSpeed1==-1){
                            return;
                        }
                        int wpLimitedSpeed2=getNeedSpeed(viewHodler.numb3ET,"speed2 is null !");
                        if(wpLimitedSpeed2==-1){
                            return;
                        }
                        itemClick.nowSelect(t800TestDataType,wpLimitedSpeed1,wpLimitedSpeed2);
                        break;
                    case IntervalSpeed:
                        int is1=getNeedSpeed(viewHodler.numb1ET,"s1 is null !");
                        if(is1==-1){
                            return;
                        }
                        int isDis=getNeedSpeed(viewHodler.numb2ET,"dis is null !");
                        if(isDis==-1){
                            return;
                        }
                        int is2=getNeedSpeed(viewHodler.numb3ET,"s2 is null !");
                        if(is2==-1){
                            return;
                        }
                        int isH=getNeedSpeed(viewHodler.numb4ET,"H is null !");
                        if(isH==-1){
                            return;
                        }
                        int isM=getNeedSpeed(viewHodler.numb5ET,"M is null !");
                        if(isM==-1){
                            return;
                        }
                        itemClick.nowSelect(t800TestDataType,is1,isDis,is2,isH,isM);
                        break;
                    case speeding:
                        itemClick.nowSelect(t800TestDataType,HudSpeedingShowBJType.values()[viewHodler.sp1Index]);
                        break;
                    case show_Yellow_Statu:
                        itemClick.nowSelect(t800TestDataType,HudYellowStatuBjType.values()[viewHodler.sp1Index]);
                        break;
                    case TURN_BJ:
                        itemClick.nowSelect(t800TestDataType,HudTurnBjType.values()[viewHodler.sp1Index]);
                        break;
                    case WarningPoint_1:
                    case BIG_WarningPoint_1:
                        int wpDis=getNeedSpeed(viewHodler.numb1ET,"Dis is null !");
                        if(wpDis==-1){
                            return;
                        }
                        itemClick.nowSelect(t800TestDataType,HudWarningPointType.values()[viewHodler.sp1Index],wpDis);
                        break;
                    case WarningPoint_2:
                        itemClick.nowSelect(t800TestDataType,HudWarningPointType.values()[viewHodler.sp1Index],HudWarningPointType.values()[viewHodler.sp2Index]);
                        break;

                    case TurnType_1:
                        int ttDis=getNeedSpeed(viewHodler.numb1ET,"Dis is null !");
                        if(ttDis==-1){
                            return;
                        }
                        itemClick.nowSelect(t800TestDataType,HudTurnType.values()[viewHodler.sp1Index],ttDis);
                        break;
                    case TurnType_2:
                        itemClick.nowSelect(t800TestDataType,HudTurnType.values()[viewHodler.sp1Index],HudTurnType.values()[viewHodler.sp2Index]);
                        break;
                    case ReachInfo_1:
                        int reach1Dis=getNeedSpeed(viewHodler.numb1ET,"dis is null !");
                        if(reach1Dis==-1){
                            return;
                        }
                        int reach1H=getNeedSpeed(viewHodler.numb2ET,"H is null !");
                        if(reach1H==-1){
                            return;
                        }
                        int reach1M=getNeedSpeed(viewHodler.numb3ET,"M is null !");
                        if(reach1M==-1){
                            return;
                        }
                        itemClick.nowSelect(t800TestDataType,reach1Dis,reach1H,reach1M,HudReachType.values()[viewHodler.sp1Index]);
                        break;
                    case ReachInfo_2:
                    case NEXT_ROAD_NAME:
                    case NOW_ROAD_NAME_1:
                    case NOW_ROAD_NAME_2:
                    case NOW_ROAD_NAME_3:
                    case NOW_ROAD_NAME_4:
                    case WarningPoint_1_T:
                    case WarningPoint_1_B:
                    case WarningPoint_2_T:
                    case WarningPoint_2_B:
                    case Yellow_Statu_STR:
                    case rewrite_SN:
                        String ss=viewHodler.str1ET.getEditableText().toString();
                        if(TextUtils.isEmpty(ss)){
                            showToast("string is null !");
                            return;
                        }
                        itemClick.nowSelect(t800TestDataType,ss);
                        break;
                    case LaneInformation_0:
                        HudLaneCountBean hudLaneCountBean=viewHodler.itemAdapter1.getHudLaneTypes();
                        if(null==hudLaneCountBean||null==hudLaneCountBean.getLaneList()||hudLaneCountBean.getLaneList().size()==0){
                            showToast("numb = 0 !");
                            return;
                        }
                        itemClick.nowSelect(t800TestDataType,hudLaneCountBean);
                        break;
                    case LaneInformation_hi_pass_1:
                        HudLaneHiPassCountBean hudLaneHiPassCountBean=viewHodler.itemAdapter1.getHudLaneHiPassCountBean();
                        if(null==hudLaneHiPassCountBean||null==hudLaneHiPassCountBean.getLaneList()||hudLaneHiPassCountBean.getLaneList().size()==0){
                            showToast("numb = 0 !");
                            return;
                        }
                        itemClick.nowSelect(t800TestDataType,hudLaneHiPassCountBean);
                        break;
                    case SET_GPS:
                    case SET_SOUND:
                        itemClick.nowSelect(t800TestDataType,viewHodler.sp1Index);
                        break;
                    case Brightness_2:
                        itemClick.nowSelect(t800TestDataType,viewHodler.sp1Index+1);
                        break;

                    default:
                        itemClick.nowSelect(t800TestDataType);
                        break;
                }


            }
        });
    }

    private void setItemList(ViewHodler viewHodler,T800TestDataType t800TestDataType,int startNumb,int endNumb){
        GridLayoutManager gridLayoutManager=new GridLayoutManager(viewHodler.itemView.getContext(), 2);
        viewHodler.rv1.setLayoutManager(gridLayoutManager);
        viewHodler.itemAdapter1=new T800TestDataItemAdapter(viewHodler.itemView.getContext(),t800TestDataType);
        viewHodler.rv1.setAdapter(viewHodler.itemAdapter1);
        String[] laneAdapterSS=T800NumbUtil.getNeedNumb(startNumb,endNumb);
        ArrayAdapter laneAdapter=new ArrayAdapter(viewHodler.itemView.getContext(), android.R.layout.simple_list_item_1, laneAdapterSS);
        viewHodler.SP1.setAdapter(laneAdapter);
        viewHodler.SP1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewHodler.sp1Index=position;
                viewHodler.itemAdapter1.setNumb(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void setNumbSp(ViewHodler viewHodler,int startNumb,int endNumb){

        String[] laneAdapterSS=T800NumbUtil.getNeedNumb(startNumb,endNumb);
        ArrayAdapter laneAdapter=new ArrayAdapter(viewHodler.itemView.getContext(), android.R.layout.simple_list_item_1, laneAdapterSS);
        viewHodler.SP1.setAdapter(laneAdapter);
        viewHodler.SP1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewHodler.sp1Index=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private int getNeedSpeed(EditText editText,String toastStr){
        String ss=editText.getEditableText().toString();
        if(TextUtils.isEmpty(ss)){
            showToast(toastStr);
            return -1;
        }
        return Integer.valueOf(ss);

    }


    private void showToast(String showStr){
        ToastUtil.showToast(showStr);
    }

    @Override
    public int getItemCount() {

        return  dataTypeList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataTypeList.get(position).ordinal();

    }

    class ViewHodler extends RecyclerView.ViewHolder {
        private Button sendBt;
        private EditText numb1ET,numb2ET,numb3ET,numb4ET,numb5ET;
        private EditText str1ET;
        private Spinner SP1,SP2;
        private RecyclerView rv1;
        private T800TestDataItemAdapter itemAdapter1;
        private int sp1Index,sp2Index;

        public ViewHodler(View itemView,T800TestDataType t800TestDataType) {
            super(itemView);
            sendBt=itemView.findViewById(R.id.bt_send);
            switch (t800TestDataType){
                case speed_1:
                case speed_3:
                    numb1ET=itemView.findViewById(R.id.et_numb1);
                    break;
                case speed_2:
                    numb1ET=itemView.findViewById(R.id.et_numb1);
                    numb2ET=itemView.findViewById(R.id.et_numb2);
                    numb3ET=itemView.findViewById(R.id.et_numb3);
                    break;
                case WarningPoint_LimitedSpeed:
                    numb2ET=itemView.findViewById(R.id.et_numb2);
                    numb3ET=itemView.findViewById(R.id.et_numb3);
                    break;
                case ReachInfo_1:
                    numb1ET=itemView.findViewById(R.id.et_numb1);
                    numb2ET=itemView.findViewById(R.id.et_numb2);
                    numb3ET=itemView.findViewById(R.id.et_numb3);
                    SP1=itemView.findViewById(R.id.sp_1);
                    break;
                case IntervalSpeed:
                    numb1ET=itemView.findViewById(R.id.et_numb1);
                    numb2ET=itemView.findViewById(R.id.et_numb2);
                    numb3ET=itemView.findViewById(R.id.et_numb3);
                    numb4ET=itemView.findViewById(R.id.et_numb4);
                    numb5ET=itemView.findViewById(R.id.et_numb5);
                    break;
                case speeding:
                case show_Yellow_Statu:
                case TURN_BJ:
                    SP1=itemView.findViewById(R.id.sp_1);
                    break;
                case WarningPoint_1:
                case BIG_WarningPoint_1:
                case TurnType_1:
                    SP1=itemView.findViewById(R.id.sp_1);
                    numb1ET=itemView.findViewById(R.id.et_numb1);
                    break;
                case WarningPoint_2:
                case TurnType_2:
                    SP1=itemView.findViewById(R.id.sp_1);
                    SP2=itemView.findViewById(R.id.sp_2);
                    break;
                case ReachInfo_2:
                case NEXT_ROAD_NAME:
                case NOW_ROAD_NAME_1:
                case NOW_ROAD_NAME_2:
                case NOW_ROAD_NAME_3:
                case NOW_ROAD_NAME_4:
                case WarningPoint_1_T:
                case WarningPoint_1_B:
                case WarningPoint_2_T:
                case WarningPoint_2_B:
                case Yellow_Statu_STR:
                case rewrite_SN:
                    str1ET=itemView.findViewById(R.id.et_str1);
                    break;
                case LaneInformation_0:
                case LaneInformation_hi_pass_1:
                    SP1=itemView.findViewById(R.id.sp_1);
                    rv1=itemView.findViewById(R.id.rv1);
                    break;
                case SET_GPS:
                case SET_SOUND:
                case Brightness_2:
                    SP1=itemView.findViewById(R.id.sp_1);
                    break;
            }
        }
    }

    public interface ItemClick {
        void nowSelect( T800TestDataType t800TestDataType,Object... objects);
    }


}