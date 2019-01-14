package com.coderpig.family.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.R;

public class HomeControllFragment  extends  BaseFragement{
    private static final String TAG =MineFragment.class.getSimpleName();
    private View mineFragmentView;
    private  String cookie;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext=getActivity();

        initData();
    }
    @Override
    protected View initView() {
        Log.e(TAG, "我的页面Fragment页面被初始化了...");
        mineFragmentView = View.inflate(mContext, R.layout.fg_home_controll, null);
        return mineFragmentView;
    }

    protected void initData() {
        super.initDate();
        Log.e(TAG, "我的Fragment页面数据被初始化了...");
    }
}
