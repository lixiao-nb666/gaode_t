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

import java.util.ArrayList;
import java.util.List;


public class HuynewHintstrShowAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";


    private LayoutInflater layoutInflater;
    private ItemEvent itemEvent;

    public HuynewHintstrShowAdapter(Context context,ItemEvent itemEvent) {
        layoutInflater = LayoutInflater.from(context);
        this.itemEvent=itemEvent;


    }
   private List<String> list;
    private int playIndex;

    public void setList(List<String> list,int playIndex){
        if(null==list){
            list=new ArrayList<>();
            playIndex=-1;
        }


        this.list=list;
        this.playIndex=playIndex;


        notifyDataSetChanged();
    }

    public void setPlayIndex(int playIndex){
        this.playIndex=playIndex;


        notifyDataSetChanged();
    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.adapter_huynew_hintstr_show, parent, false);
        ViewHodler viewHodler = new ViewHodler(itemView);

        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHodler viewHodler = (ViewHodler) holder;
        String showStr=list.get(position);
        viewHodler.indexTV.setText((position+1)+"");
        viewHodler.showTV.setText(showStr);
        if(playIndex>=position){
            viewHodler.indexTV.setTextColor(viewHodler.indexTV.getResources().getColor(com.newbee.translation_ui_lib.R.color.text_translation_over_color));
            viewHodler.showTV.setTextColor(viewHodler.showTV.getResources().getColor(com.newbee.translation_ui_lib.R.color.text_translation_over_color));
        }else {
            viewHodler.indexTV.setTextColor(viewHodler.indexTV.getResources().getColor(com.newbee.translation_ui_lib.R.color.white1));
            viewHodler.showTV.setTextColor(viewHodler.showTV.getResources().getColor(com.newbee.translation_ui_lib.R.color.white1));
        }
        if(position==playIndex){
            itemEvent.nowStr(showStr);
        }else if(position+1==playIndex){
            itemEvent.lastStr(showStr);
        }

    }




    @Override
    public int getItemCount() {
        if(null==list){
            return 0;
        }
        return list.size();
    }

    public String getItemStr(){
        try {
            return list.get(playIndex);
        }catch (Exception e){
            return "";
        }

    }


    class ViewHodler extends RecyclerView.ViewHolder {


        private TextView indexTV,showTV;

        public ViewHodler(View itemView) {
            super(itemView);
            indexTV=itemView.findViewById(R.id.tv_hint_str_show_index);
            showTV=itemView.findViewById(R.id.tv_hint_str_show);
        }
    }

    public interface ItemEvent{

        public void lastStr(String lastStr);
        public void nowStr(String nowStr);
    }



}