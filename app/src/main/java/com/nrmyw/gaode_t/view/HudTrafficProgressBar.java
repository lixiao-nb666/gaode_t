package com.nrmyw.gaode_t.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.amap.api.navi.model.AMapTrafficStatus;
import com.gaode.demo.activity.custom.view.TmcBarView;
import com.nrmyw.gaode_t.R;

import java.util.List;

/**
 * 实时交通信息路况光柱图view类
 */
public class HudTrafficProgressBar extends FrameLayout {
    private TmcBarView mDefaultTmcBarView;
    private ImageView mDefaultTmcBarCarView;

    public HudTrafficProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HudTrafficProgressBar(Context context) {
        this(context, null);
    }

    private void init() {
        try {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.view_hud_trafficbar, null);
            addView(view);
            mDefaultTmcBarView = (TmcBarView) view.findViewById(R.id.navi_sdk_apiTmcBarView);
            mDefaultTmcBarCarView = (ImageView) view.findViewById(R.id.navi_sdk_apiTmcBarCar);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新光柱条
     *
     * @param allLength    整条路线长度
     * @param retainLength 剩余距离
     * @param traffics     交通状态列表
     */
    public void update(int allLength, int retainLength, List<AMapTrafficStatus> traffics) {
        try {
            mDefaultTmcBarView.setData(traffics, allLength);
            mDefaultTmcBarView.setCarView(mDefaultTmcBarCarView);
            //绘制光柱
            if (mDefaultTmcBarView.getHeight() > 0) {
                mDefaultTmcBarView.setCursorPos(retainLength);
                mDefaultTmcBarView.invalidate();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
