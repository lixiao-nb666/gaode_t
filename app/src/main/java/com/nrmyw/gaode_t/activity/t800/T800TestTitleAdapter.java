package com.nrmyw.gaode_t.activity.t800;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nrmyw.gaode_t.R;


public class T800TestTitleAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";

    private T800TestDataAdapter.ItemClick itemClick;
    private LayoutInflater layoutInflater;


    public T800TestTitleAdapter(Context context, T800TestDataAdapter.ItemClick itemClick) {
        layoutInflater = LayoutInflater.from(context);
        this.itemClick = itemClick;
    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.adapter_t800_test_title, parent, false);
        ViewHodler viewHodler = new ViewHodler(itemView);

        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHodler viewHodler = (ViewHodler) holder;
        T800TestTitleType titleType= T800TestTitleType.values()[position];
        viewHodler.titleTV.setText(viewHodler.itemView.getContext().getResources().getText(titleType.getTitleRsId()));
        int tsId=titleType.getTsStrRsId();
        if(tsId==0){
            viewHodler.tsTV.setVisibility(View.GONE);
        }else {
            viewHodler.tsTV.setVisibility(View.VISIBLE);
            viewHodler.tsTV.setText(viewHodler.itemView.getContext().getResources().getText(tsId));
        }
        LinearLayoutManager layoutManager=new LinearLayoutManager(viewHodler.itemView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewHodler.dataRV.setLayoutManager(layoutManager);
        T800TestDataAdapter adapter=new T800TestDataAdapter(viewHodler.itemView.getContext(),itemClick,titleType.getNeedDataTypeList());
        viewHodler.dataRV.setAdapter(adapter);
    }




    @Override
    public int getItemCount() {
        return  T800TestTitleType.values().length;
    }


    class ViewHodler extends RecyclerView.ViewHolder {
        private TextView titleTV,tsTV;
        private RecyclerView dataRV;

        public ViewHodler(View itemView) {
            super(itemView);
            titleTV=itemView.findViewById(R.id.tv_test_title);
            tsTV=itemView.findViewById(R.id.tv_test_ts);
            dataRV=itemView.findViewById(R.id.rv_test_data);
        }
    }




}