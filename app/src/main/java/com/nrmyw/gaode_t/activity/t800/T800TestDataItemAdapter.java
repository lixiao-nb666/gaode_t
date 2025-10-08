package com.nrmyw.gaode_t.activity.t800;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nrmyw.gaode_t.R;
import com.nrmyw.hud_data_lib.bean.HudLaneCountBean;
import com.nrmyw.hud_data_lib.bean.HudLaneHiPassCountBean;
import com.nrmyw.hud_data_lib.type.lane.HudLaneType;

import java.util.ArrayList;
import java.util.List;


public class T800TestDataItemAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";
    private LayoutInflater layoutInflater;
    private T800TestDataType t800TestDataType;
    private int numb;
    private HudLaneType[] hudLaneTypes;
    private Integer[] hudHipassLaneTypes;

    public T800TestDataItemAdapter(Context context,T800TestDataType t800TestDataType) {
        layoutInflater = LayoutInflater.from(context);
        this.t800TestDataType=t800TestDataType;
        numb=0;
        hudLaneTypes=new HudLaneType[numb];
    }

    public void setNumb(int numb){
        this.numb=numb;
        switch (t800TestDataType){
            case LaneInformation_0:
                hudLaneTypes=new HudLaneType[this.numb];
                break;
            case LaneInformation_hi_pass_1:
                hudHipassLaneTypes=new Integer[this.numb];
                break;
        }
        notifyDataSetChanged();
    }

    public HudLaneCountBean getHudLaneTypes(){
        if(t800TestDataType!=T800TestDataType.LaneInformation_0){
            return new HudLaneCountBean();
        }

        HudLaneCountBean hudLaneCountBean=new HudLaneCountBean();
        for(HudLaneType hudLaneType:hudLaneTypes){
            if(null==hudLaneType){
                hudLaneCountBean.add(HudLaneType.NONE);
            }else {
                hudLaneCountBean.add(hudLaneType);
            }
        }
        return hudLaneCountBean;
    }

    public HudLaneHiPassCountBean getHudLaneHiPassCountBean(){
        if(t800TestDataType!=T800TestDataType.LaneInformation_hi_pass_1){
            return new HudLaneHiPassCountBean();
        }
        HudLaneHiPassCountBean hudLaneHiPassCountBean=new HudLaneHiPassCountBean();
        for(Integer numb:hudHipassLaneTypes){
            hudLaneHiPassCountBean.add(numb);
        }
        return hudLaneHiPassCountBean;

    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int rsId=0;
        switch (t800TestDataType){
            case LaneInformation_0:
                rsId=R.layout.adapter_t800_test_data_item_lane;
                break;
            case LaneInformation_hi_pass_1:
                rsId=R.layout.adapter_t800_test_data_item_lane_hi_pass;
                break;
        }
        View itemView = layoutInflater.inflate(rsId, parent, false);
        ViewHodler viewHodler = new ViewHodler(itemView);

        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHodler viewHodler = (ViewHodler) holder;
        viewHodler.tv1.setText((position+1)+" : ");
        int nowP=position;
        switch (t800TestDataType){
            case LaneInformation_0:
                ArrayAdapter laneAdapter=new ArrayAdapter(viewHodler.itemView.getContext(), android.R.layout.simple_list_item_1, HudLaneType.values());
                viewHodler.sp1.setAdapter(laneAdapter);
                viewHodler.sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        viewHodler.sp1Index=position;
                        hudLaneTypes[nowP]=HudLaneType.values()[position];
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });
                hudLaneTypes[nowP]=HudLaneType.values()[0];
                break;
            case LaneInformation_hi_pass_1:
                viewHodler.numb1ET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(null==s|| TextUtils.isEmpty(s.toString())){
                            hudHipassLaneTypes[nowP]=-1;
                        }else {
                            hudHipassLaneTypes[nowP]=Integer.valueOf(s.toString());
                        }
                    }
                });
                hudHipassLaneTypes[nowP]=-1;
                break;
        }

    }




    @Override
    public int getItemCount() {
        return  numb;
    }


    class ViewHodler extends RecyclerView.ViewHolder {
        private TextView tv1;
        private Spinner sp1;
        private int sp1Index;
        private EditText numb1ET;

        public ViewHodler(View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.tv_1);
            switch (t800TestDataType){
                case LaneInformation_0:
                    sp1=itemView.findViewById(R.id.sp_1);
                    break;
                case LaneInformation_hi_pass_1:
                    numb1ET=itemView.findViewById(R.id.et_numb1);
                    break;
            }

        }
    }




}