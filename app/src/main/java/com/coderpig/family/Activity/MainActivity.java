package com.coderpig.family.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioGroup;

import com.coderpig.family.Base.BaseFragement;

import com.coderpig.family.Fragment.HomeFragment;
import com.coderpig.family.Fragment.MineFragment;
import com.coderpig.family.Fragment.ModeFragment;
import com.coderpig.family.Fragment.SafeFragment;
import com.coderpig.family.Fragment.VoiceFragment;
import com.coderpig.family.R;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
 private RadioGroup mRadioGroup;
 private List<BaseFragement> mBaseFragment;
 private int position;
 private String cookie="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();

        setListener();

    }
    private void setListener()
    {
        mRadioGroup.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        mRadioGroup.check(R.id.rb_home);
    }

    private void initData()
    {
        mBaseFragment=new ArrayList<>();
        mBaseFragment.add(new HomeFragment());
        mBaseFragment.add(new ModeFragment());
        mBaseFragment.add(new VoiceFragment());
        mBaseFragment.add(new SafeFragment());
        mBaseFragment.add(new MineFragment());
        Intent intent=this.getIntent();
        cookie=intent.getStringExtra("cookie");
        Log.e("错误",cookie);


    }

    private void initView()
    {
        mRadioGroup=(RadioGroup)findViewById(R.id.rg_main);
    }

    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group,int checkedId)
        {
            switch (checkedId)
            {
                case R.id.rb_home:
                    position=0;
                  break;
                case R.id.rb_mode:
                 position=1;
                 break;
                case R.id.rb_voice:
                    position=2;
                    break;
                case R.id.rb_safe:
                    position=3;
                    break;
                case R.id.rb_mine:
                    position=4;
                break;
                default:
                    position=0;
                    break;
            }
            BaseFragement currentFragment=getFragment();

            replaceFragment(currentFragment);
        }

        private void replaceFragment(BaseFragement fragment)
        {
            Bundle bundle=new Bundle();
            bundle.putString("cookie",cookie);
            fragment.setArguments(bundle);
            FragmentManager fm=getSupportFragmentManager();

            FragmentTransaction transaction=fm.beginTransaction();

            transaction.replace(R.id.fl_main,fragment);

            transaction.commit();
        }

        private BaseFragement getFragment()
        {
            return mBaseFragment.get(position);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.tab_menu_voice)
                        .setTitle("提示！")
                        .setMessage("确认退出程序？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                break;
        }
        return false;
    }


}
