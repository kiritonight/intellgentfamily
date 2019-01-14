package com.coderpig.family.Fragment;

import android.util.Log;
import android.view.View;

import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.R;

public class VoiceFragment extends BaseFragement {
    private static final String TAG =VoiceFragment.class.getSimpleName();
    private View voiceFragmentView;
    @Override
    protected View initView() {
        Log.e(TAG, "声音识别页面Fragment页面被初始化了...");
        voiceFragmentView = View.inflate(mContext, R.layout.fg_voice, null);
        return voiceFragmentView;
    }

    protected void initData() {
        super.initDate();
        Log.e(TAG, "声音识别Fragment页面数据被初始化了...");
    }
}
