package com.coderpig.family.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * The type Base fragement.
 */
public abstract class BaseFragement extends Fragment {
    private static final String mTag=BaseFragement.class.getSimpleName();
    /**
     * The M context.
     */
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }
    public String getmTag()
    {
        return mTag;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView();
    }

    /**
     * Init view view.
     *
     * @return the view
     */
    protected abstract View initView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
         super.onActivityCreated(savedInstanceState);
         initDate();

    }

    /**
     * Init date.
     */
    protected  void initDate(){}



}
