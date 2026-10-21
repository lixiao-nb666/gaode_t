package com.nrmyw.huynew.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.newbee.gson_lib.gson.MyGson;
import com.nrmyw.gaode_t.R;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.huynew.adapter.HuynewHintstrAdapter;
import com.nrmyw.huynew.bean.HintStrShowBean;
import com.nrmyw.huynew.manager.HintStrManager;

public class   HuyNewHintStrActivity extends BaseCompatActivity {
    private RecyclerView rv;
    private HuynewHintstrAdapter huynewHintstrAdapter;
    private HuynewHintstrAdapter.ItemClick huynewHintstrAdapterItem=new HuynewHintstrAdapter.ItemClick() {

        @Override
        public void toAdd() {
            toActivity(HuyNewAddAndShowHintStrActivity.class);
        }

        @Override
        public void needDelete(HintStrShowBean hintStrShowBean) {
            HintStrManager.getInstance().getResultHintStrShowBean().remove(hintStrShowBean);
            huynewHintstrAdapter.notifyDataSetChanged();
            showToast(hintStrShowBean.toString());
        }

        @Override
        public void open(HintStrShowBean hintStrShowBean) {
            try {
                toActivity(HuyNewAddAndShowHintStrActivity.class, MyGson.getInstance().toGsonStr(hintStrShowBean));
            }catch (Exception e){

            }

        }
    };



    @Override
    public int getViewLayoutRsId() {
        return R.layout.huynew_activity_hintstr;
    }

    @Override
    public void initView() {
        rv=findViewById(R.id.rv_hn_hintstr);
    }

    @Override
    public void initData() {
        huynewHintstrAdapter=new HuynewHintstrAdapter(context,huynewHintstrAdapterItem);
        GridLayoutManager layoutManager=new GridLayoutManager(context,3);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(huynewHintstrAdapter);

    }

    @Override
    public void initControl() {

    }

    @Override
    public void closeActivity() {

    }

    @Override
    public void viewIsShow() {
        huynewHintstrAdapter.notifyDataSetChanged();

    }

    @Override
    public void viewIsPause() {

    }

    @Override
    public void changeConfig() {

    }
}
