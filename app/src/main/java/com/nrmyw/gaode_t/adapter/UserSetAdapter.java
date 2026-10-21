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
import com.nrmyw.gaode_t.activity.set.user.UserSetType;


public class UserSetAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";

    private ItemClick itemClick;
    private LayoutInflater layoutInflater;


    public UserSetAdapter(Context context, ItemClick itemClick) {
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
        UserSetType userSetType=UserSetType.values()[position];
        viewHodler.titleTV.setText(userSetType.getTitleStr());
        if(TextUtils.isEmpty(userSetType.getV())){
            itemClick.needInit(userSetType);
        }else {
            viewHodler.setVTV.setText(userSetType.getV());
        }
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.iv_add:
                        itemClick.nowSelectAdd(userSetType);
                        break;
                    case R.id.iv_reduce:
                        itemClick.nowSelectReduce(userSetType);
                        break;
                    case R.id.iv_set:
                        itemClick.nowSelectSet(userSetType);
                        break;
                    case R.id.iv_update:
                        itemClick.nowSelectUpdate(userSetType);
                        break;
                    default:
                        itemClick.nowSelect(userSetType);
                        break;
                }
            }
        };
        viewHodler. addIV.setOnClickListener(onClickListener);
        viewHodler.reduceIV.setOnClickListener(onClickListener);
        viewHodler.setIV.setOnClickListener(onClickListener);
        viewHodler.updateIV.setOnClickListener(onClickListener);
        viewHodler.itemView.setOnClickListener(onClickListener);
        switch (userSetType){
            case MAX_BRIGHTNESS:
            case TURN_TYPE_MIN:
                viewHodler.addIV.setVisibility(VISIBLE);
                viewHodler.reduceIV.setVisibility(VISIBLE);
                viewHodler.setIV.setVisibility(GONE);
                viewHodler.updateIV.setVisibility(GONE);

                break;
            case MIN_BRIGHTNESS:

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

        return  UserSetType.values().length;
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
        void needInit( UserSetType bleSetType);
        void nowSelect( UserSetType bleSetType);
        void nowSelectAdd(UserSetType bleSetType);
        void nowSelectReduce( UserSetType bleSetType);
        void nowSelectSet(UserSetType bleSetType);
        void nowSelectUpdate(UserSetType bleSetType);
    }


}