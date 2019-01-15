package com.coderpig.family.Fragment;

import android.util.Log;
import android.view.View;

import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.R;

public class SafeFragment extends BaseFragement {
    private static final String mTag =SafeFragment.class.getSimpleName();
    private View safeFragmentView;
    @Override
    protected View initView() {
        Log.e(mTag, "安全页面Fragment页面被初始化了...");
        safeFragmentView = View.inflate(mContext, R.layout.fg_safe, null);
        return safeFragmentView;
    }

    protected void initData() {
        super.initDate();
        Log.e(mTag, "安全Fragment页面数据被初始化了...");
    }
    public String getmTag()
    {
        return mTag;
    }

}
