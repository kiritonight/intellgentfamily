package com.coderpig.family.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.R;

public class VoiceFragment extends BaseFragement {
    private static final String mTag =VoiceFragment.class.getSimpleName();
    private View voiceFragmentView;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        initData();
    }
    @Override
    protected View initView() {
        Log.e(mTag, "声音识别页面Fragment页面被初始化了...");
        voiceFragmentView = View.inflate(mContext, R.layout.fg_voice, null);
        return voiceFragmentView;
    }

    protected void initData() {
        super.initDate();
        Log.e(mTag, "声音识别Fragment页面数据被初始化了...");
    }
    public String getmTag()
    {
        return mTag;
    }

}
