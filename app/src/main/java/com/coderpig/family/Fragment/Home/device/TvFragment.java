package com.coderpig.family.Fragment.Home.device;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.R;

public class TvFragment extends BaseFragement {
    private static final String mTag =TvFragment.class.getSimpleName();
    private View TvFragmentView;
    private  String cookie="";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        initData();
    }
    @Override
    protected View initView() {
        Log.e(mTag, "控制电视页面被初始化了...");
       TvFragmentView = View.inflate(mContext, R.layout.fg_control_tv, null);
        return TvFragmentView;
    }

    protected void initData() {
        super.initDate();
        Log.e(mTag, "控制电视页面数据被初始化了...");
        Bundle args=getArguments();
        cookie=args.getString("cookie");
    }
    public String getmTag()
    {
        return mTag;
    }
}
