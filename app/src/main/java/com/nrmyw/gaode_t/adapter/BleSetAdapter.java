package com.nrmyw.gaode_t.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.activity.t800.T800TestDataType;
import com.nrmyw.gaode_t.type.BleSetType;

import java.util.List;

;

public class BleSetAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";

    private ItemClick itemClick;
    private LayoutInflater layoutInflater;


    public BleSetAdapter(Context context, ItemClick itemClick) {
        layoutInflater = LayoutInflater.from(context);
        this.itemClick = itemClick;

    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.adapter_ble_set, parent, false);
        ViewHodler viewHodler = new ViewHodler(itemView);

        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHodler viewHodler = (ViewHodler) holder;
        BleSetType bleSetType=BleSetType.values()[position];
        viewHodler.titleTV.setText(viewHodler.itemView.getContext().getResources().getText(bleSetType.getRsId()));
        if(TextUtils.isEmpty(bleSetType.getV())){
            itemClick.needInit(bleSetType);
        }else {
            viewHodler.setVTV.setText(bleSetType.getV());
        }
        viewHodler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.nowSelect(bleSetType);
            }
        });

    }




    @Override
    public int getItemCount() {

        return  BleSetType.values().length;
    }


    class ViewHodler extends RecyclerView.ViewHolder {
        private TextView titleTV,setVTV;


        public ViewHodler(View itemView) {
            super(itemView);
            titleTV=itemView.findViewById(R.id.tv_title);
            setVTV=itemView.findViewById(R.id.tv_v);
        }
    }

    public interface ItemClick {
        void needInit( BleSetType bleSetType);
        void nowSelect( BleSetType bleSetType);
    }


}