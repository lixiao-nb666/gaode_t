package com.nrmyw.gaode_t.adapter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nrmyw.gaode_t.R;
import com.nrmyw.gaode_t.activity.set.ble.BleSetType;

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
        viewHodler.titleTV.setText(bleSetType.getTitleStr());
        if(TextUtils.isEmpty(bleSetType.getV())){
            itemClick.needInit(bleSetType);
        }else {
            viewHodler.setVTV.setText(bleSetType.getV());
        }
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.iv_add:
                        itemClick.nowSelectAdd(bleSetType);
                        break;
                    case R.id.iv_reduce:
                        itemClick.nowSelectReduce(bleSetType);
                        break;
                    case R.id.iv_set:
                        itemClick.nowSelectSet(bleSetType);
                        break;
                    case R.id.iv_update:
                        itemClick.nowSelectUpdate(bleSetType);
                        break;
                    default:
                        itemClick.nowSelect(bleSetType);
                        break;
                }
            }
        };
        viewHodler. addIV.setOnClickListener(onClickListener);
        viewHodler.reduceIV.setOnClickListener(onClickListener);
        viewHodler.setIV.setOnClickListener(onClickListener);
        viewHodler.updateIV.setOnClickListener(onClickListener);
        viewHodler.itemView.setOnClickListener(onClickListener);
        switch (bleSetType){
            case SOUND:
            case BRIGHTNESS:
                viewHodler.addIV.setVisibility(VISIBLE);
                viewHodler.reduceIV.setVisibility(VISIBLE);
                viewHodler.setIV.setVisibility(GONE);
                viewHodler.updateIV.setVisibility(GONE);

                break;
            case DEVICE_SOUND_STATU:
            case IMAGE_QUALITY:
            case DAYLIGHT_SEARCH:
                viewHodler.addIV.setVisibility(GONE);
                viewHodler.reduceIV.setVisibility(GONE);
                viewHodler.setIV.setVisibility(GONE);
                viewHodler.updateIV.setVisibility(VISIBLE);
                break;
            default:
                viewHodler.addIV.setVisibility(GONE);
                viewHodler.reduceIV.setVisibility(GONE);
                viewHodler.setIV.setVisibility(GONE);
                viewHodler.updateIV.setVisibility(GONE);
                break;
        }

    }




    @Override
    public int getItemCount() {

        return  BleSetType.values().length;
    }


    class ViewHodler extends RecyclerView.ViewHolder {
        private TextView titleTV,setVTV;
        private ImageView setIV,addIV,reduceIV,updateIV;

        public ViewHodler(View itemView) {
            super(itemView);
            titleTV=itemView.findViewById(R.id.tv_title);
            setVTV=itemView.findViewById(R.id.tv_v);
            setIV=itemView.findViewById(R.id.iv_set);
            addIV=itemView.findViewById(R.id.iv_add);
            reduceIV=itemView.findViewById(R.id.iv_reduce);
            updateIV=itemView.findViewById(R.id.iv_update);

        }
    }

    public interface ItemClick {
        void needInit( BleSetType bleSetType);
        void nowSelect( BleSetType bleSetType);
        void nowSelectAdd( BleSetType bleSetType);
        void nowSelectReduce( BleSetType bleSetType);
        void nowSelectSet( BleSetType bleSetType);
        void nowSelectUpdate( BleSetType bleSetType);
    }


}