package com.coderpig.family.Fragment.Home.device;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.R;

public class ConditionerFragment  extends BaseFragement {
    private static final String mTag = ConditionerFragment.class.getSimpleName();
    private View ConditionFragmentView;
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
        Log.e(mTag, "控制空调页面被初始化了...");
       ConditionFragmentView = View.inflate(mContext, R.layout.fg_control_condition, null);
        return ConditionFragmentView;
    }

    protected void initData() {
        super.initDate();
        Log.e(mTag, "控制空调页面数据被初始化了...");
        Bundle args=getArguments();
        cookie=args.getString("cookie");
    }
    public String getmTag()
    {
        return mTag;
    }
}
