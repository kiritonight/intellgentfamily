package com.coderpig.family.Fragment;

import android.util.Log;
import android.view.View;

import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.R;

public class ModeFragment extends BaseFragement {
    private static final String TAG = ModeFragment.class.getSimpleName();
    private View modeFragmentView;
    @Override
    protected View initView() {
        Log.e(TAG, "模式页面Fragment页面被初始化了...");
        modeFragmentView = View.inflate(mContext, R.layout.fg_mode, null);
        return modeFragmentView;
    }

    protected void initData() {
        super.initDate();
        Log.e(TAG, "模式Fragment页面数据被初始化了...");
    }
}
