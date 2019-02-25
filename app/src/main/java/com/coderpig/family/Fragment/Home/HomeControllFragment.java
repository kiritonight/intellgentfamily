package com.coderpig.family.Fragment.Home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coderpig.family.Base.BaseFragement;

import com.coderpig.family.Fragment.Home.device.ConditionerFragment;
import com.coderpig.family.Fragment.Home.device.HumFragment;
import com.coderpig.family.Fragment.Home.device.LightFragment;
import com.coderpig.family.Fragment.Home.device.TvFragment;
import com.coderpig.family.R;

public class HomeControllFragment extends BaseFragement {
    private static final String mTag = HomeControllFragment.class.getSimpleName();
    private View homecontrollFragmentView;
    private Button lightBtn;
    private Button humBtn;
    private Button airBtn;
    private Button telBtn;
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
        Log.e(mTag, "HomeControll页面被初始化了...");
        homecontrollFragmentView = View.inflate(mContext, R.layout.fg_home_controll, null);
        lightBtn=(Button)homecontrollFragmentView.findViewById(R.id.btn_light);
        humBtn=(Button)homecontrollFragmentView.findViewById(R.id.btn_humidifier);
        airBtn=(Button)homecontrollFragmentView.findViewById(R.id.btn_conditioner);
        telBtn=(Button)homecontrollFragmentView.findViewById(R.id.btn_tv);
        lightBtn.setOnClickListener(new HomeControllClickListener());
        humBtn.setOnClickListener(new HomeControllClickListener());
        airBtn.setOnClickListener(new HomeControllClickListener());
        telBtn.setOnClickListener(new HomeControllClickListener());
        return homecontrollFragmentView;
    }

    protected void initData() {
        super.initDate();
        Log.e(mTag, "HomeControll页面数据被初始化了...");
        Bundle args=getArguments();
        cookie=args.getString("cookie");
    }
    public String getmTag()
    {
        return mTag;
    }

    private  class HomeControllClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            TextView textView=(TextView)getActivity().findViewById(R.id.txt_topbar);
            switch (v.getId()) {
                case R.id.btn_light:
                    textView.setText("控制灯");
                    replaceFragment(new LightFragment(),new LightFragment().getmTag());
                    break;
                case R.id.btn_humidifier:
                    textView.setText("控制加湿器");
                    replaceFragment(new HumFragment(),new HumFragment().getmTag());
                    break;
                case R.id.btn_conditioner:
                    textView.setText("控制空调");
                    replaceFragment(new ConditionerFragment(),new ConditionerFragment().getmTag());
                    break;
                case R.id.btn_tv:
                    textView.setText("控制电视");
                    replaceFragment(new TvFragment(),new TvFragment().getmTag());
                    break;
                default:
                    break;
            }
        }
    }

    private void replaceFragment(BaseFragement fragment,String tag)
    {
        Bundle bundle=new Bundle();
        bundle.putString("cookie",cookie);
        fragment.setArguments(bundle);

        FragmentManager fm=getFragmentManager();

        FragmentTransaction transaction=fm.beginTransaction();

        transaction.replace(R.id.fl_main,fragment);
        transaction.addToBackStack(tag);

        transaction.commit();
    }

}
