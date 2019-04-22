package com.coderpig.family.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.R;

public class AboutUsFramgment extends BaseFragement {
    private static final String mTag =AboutUsFramgment.class.getSimpleName();
    private View aboutUsFragmentView;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        initData();
    }
    @Override
    protected View initView() {
        Log.e(mTag, "关于我们Fragment页面被初始化了...");
        aboutUsFragmentView = View.inflate(mContext, R.layout.fg_aboutus, null);
        return aboutUsFragmentView;
    }

    protected void initData() {
        super.initDate();
        Log.e(mTag, "关于我们Fragment页面数据被初始化了...");
    }
    public String getmTag()
    {
        return mTag;
    }
}
