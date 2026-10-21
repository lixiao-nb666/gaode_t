package com.nrmyw.huynew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nrmyw.gaode_t.R;
import com.nrmyw.huynew.bean.HintStrShowBean;
import com.nrmyw.huynew.manager.HintStrManager;


public class HuynewHintstrAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";

    private ItemClick itemClick;
    private LayoutInflater layoutInflater;


    public HuynewHintstrAdapter(Context context, ItemClick itemClick) {
        layoutInflater = LayoutInflater.from(context);
        this.itemClick = itemClick;

    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.adapter_huynew_hintstr_page, parent, false);
        ViewHodler viewHodler = new ViewHodler(itemView);

        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHodler viewHodler = (ViewHodler) holder;
        int index=position-1;
        if(position==0){
            viewHodler.rv.setBackgroundResource(R.drawable.hint_str_add_1);
            viewHodler.deleteIV.setVisibility(View.GONE);
            viewHodler.titleTV.setText("");
            viewHodler.rv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.toAdd();
                }
            });
        }else {
            try {
                HintStrShowBean hintStrShowBean=HintStrManager.getInstance().getIndexBean(index);
                viewHodler.rv.setBackgroundResource(R.drawable.hint_str_show_1);
                viewHodler.deleteIV.setVisibility(View.VISIBLE);
                viewHodler.titleTV.setText(hintStrShowBean.getTitleStr());
                viewHodler.rv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClick.open(hintStrShowBean);
                    }
                });
                viewHodler.deleteIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClick.needDelete(hintStrShowBean);
                    }
                });
            }catch (Exception e){}

        }



    }




    @Override
    public int getItemCount() {

        return HintStrManager.getInstance().getResultHintStrShowBean().getHintStrShowBeanMap().size()+1;
    }


    class ViewHodler extends RecyclerView.ViewHolder {

        private RelativeLayout rv;

        private ImageView deleteIV;
        private TextView titleTV;

        public ViewHodler(View itemView) {
            super(itemView);
            rv=itemView.findViewById(R.id.rl_item_hint_str);
            deleteIV=itemView.findViewById(R.id.iv_delete);
            titleTV=itemView.findViewById(R.id.tv_title);
        }
    }

    public interface ItemClick {
        void toAdd( );
        void needDelete(HintStrShowBean hintStrShowBean);
        void open(HintStrShowBean hintStrShowBean);

    }


}