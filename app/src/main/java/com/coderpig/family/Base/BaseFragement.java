package com.coderpig.family.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragement extends Fragment {
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView();
    }

    protected abstract View initView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
         super.onActivityCreated(savedInstanceState);
         initDate();

    }

    protected  void initDate(){}



}
