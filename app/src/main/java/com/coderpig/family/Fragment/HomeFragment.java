package com.coderpig.family.Fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.coderpig.family.Base.BaseFragement;

import com.coderpig.family.R;


import java.util.List;

public class HomeFragment extends BaseFragement {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private View mainFragmentView;

    private  Button homeControllBtn;
    private Button homeDataBtn;
    private String cookie="";
@Override
    public void onCreate(Bundle savedInstanceState)
    {
     super.onCreate(savedInstanceState);
     mContext=getActivity();

     initData();
    }
    @Override
    protected View initView() {
        Log.e(TAG, "主页页面Fragment页面被初始化了...");
        mainFragmentView = View.inflate(mContext, R.layout.fg_home, null);
        homeControllBtn=(Button)mainFragmentView.findViewById(R.id.bn_home_controll);
        homeControllBtn.setOnClickListener(new HomeClickListener());
        homeDataBtn=(Button)mainFragmentView.findViewById(R.id.bn_home_data);
        homeDataBtn.setOnClickListener(new HomeClickListener());
        return mainFragmentView;
    }
    private  class HomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bn_home_data:
                    replaceFragment(new HomeDataFragment());
                    break;
                case R.id.bn_home_controll:
                    replaceFragment(new HomeControllFragment());
                default:
                    break;
            }
        }
    }
    protected void initData() {
       super.initDate();
      Bundle args=getArguments();
      cookie=args.getString("cookie");
        Log.e(TAG, "主页面Fragment页面数据被初始化了...");
    }

    private void replaceFragment(BaseFragement fragment)
    {
        Bundle bundle=new Bundle();
        bundle.putString("cookie",cookie);
        fragment.setArguments(bundle);

        FragmentManager fm=getFragmentManager();

        FragmentTransaction transaction=fm.beginTransaction();

        transaction.replace(R.id.fl_main,fragment);

        transaction.commit();
    }



}


