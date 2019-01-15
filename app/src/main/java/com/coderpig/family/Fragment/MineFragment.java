package com.coderpig.family.Fragment;

import android.util.Log;
import android.view.View;

import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.R;

public class MineFragment  extends BaseFragement {
    private static final String mTag =MineFragment.class.getSimpleName();
    private View mineFragmentView;
    @Override
    protected View initView() {
        Log.e(mTag, "我的页面Fragment页面被初始化了...");
        mineFragmentView = View.inflate(mContext, R.layout.fg_mine, null);
        return mineFragmentView;
    }

    protected void initData() {
        super.initDate();
        Log.e(mTag, "我的Fragment页面数据被初始化了...");
    }
    public String getmTag()
    {
        return mTag;
    }

}
