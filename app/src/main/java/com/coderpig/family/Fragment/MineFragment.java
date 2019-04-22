package com.coderpig.family.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coderpig.family.Activity.LoginActivity;
import com.coderpig.family.Activity.MainActivity;
import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.R;

import org.w3c.dom.Text;

public class MineFragment  extends BaseFragement {
    private static final String mTag =MineFragment.class.getSimpleName();
    private View mineFragmentView;
    private  String username="";
    private String logintime="";
    private TextView usernameText;
    private TextView loginText;
    private Button exitBtn;
    private  Button aboutBtn;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        initData();
    }
    @Override
    protected View initView() {
        Log.e(mTag, "我的页面Fragment页面被初始化了...");
        mineFragmentView = View.inflate(mContext, R.layout.fg_mine, null);
        usernameText=(TextView) mineFragmentView.findViewById(R.id.username_text);
        usernameText.setText(username);
        loginText=(TextView)mineFragmentView.findViewById(R.id.logintime_text);
        loginText.setText(logintime);
        exitBtn=(Button)mineFragmentView.findViewById(R.id.exit_btn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(R.drawable.tab_menu_voice)
                        .setTitle("提示！")
                        .setMessage("确认注销？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                                Intent intent=new Intent(getActivity(),LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
        aboutBtn=(Button)mineFragmentView.findViewById(R.id.about_btn);
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AboutUsFramgment(),new AboutUsFramgment().getmTag());
            }
        });
        return mineFragmentView;
    }

    protected void initData() {
        super.initDate();
        Log.e(mTag, "我的Fragment页面数据被初始化了...");
        Bundle args=getArguments();
        username=args.getString("username");
        logintime=args.getString("logintime");
        Log.e("获得时间",username);

    }
    public String getmTag()
    {
        return mTag;
    }

    private void replaceFragment(BaseFragement fragment,String tag)
    {
        Bundle bundle=new Bundle();

        fragment.setArguments(bundle);

        FragmentManager fm=getFragmentManager();

        FragmentTransaction transaction=fm.beginTransaction();

        transaction.replace(R.id.fl_main,fragment);
        transaction.addToBackStack(tag);

        transaction.commit();
    }
}
