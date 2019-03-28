package com.coderpig.family.Activity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.coderpig.family.Base.BaseFragement;

import com.coderpig.family.Fragment.Home.HomeFragment;
import com.coderpig.family.Fragment.MineFragment;
import com.coderpig.family.Fragment.ModeFragment;
import com.coderpig.family.Fragment.SafeFragment;
import com.coderpig.family.Fragment.VoiceFragment;
import com.coderpig.family.R;
import com.coderpig.family.unit.MqttManager;


import java.util.ArrayList;
import java.util.List;


/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {
    private static final String mTag = MainActivity.class.getSimpleName();
 private RadioGroup mRadioGroup;
 private RadioButton rb_home;
 private RadioButton rb_mode;
 private RadioButton rb_voice;
 private RadioButton rb_safe;
 private RadioButton rb_mine;
 private List<BaseFragement> mBaseFragment;
 private int position;
 private String cookie="";
 private String username="";
 private String logintime="";






 private FragmentManager fragmentManager=getSupportFragmentManager();
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
        username=intent.getStringExtra("username");
        logintime=intent.getStringExtra("logintime");
        Log.e("用户名",username);


    }

    private void initView()
    {
        mRadioGroup=(RadioGroup)findViewById(R.id.rg_main);
        rb_home=(RadioButton)findViewById(R.id.rb_home);
        rb_mode=(RadioButton)findViewById(R.id.rb_mode);
        rb_voice=(RadioButton)findViewById(R.id.rb_voice);
        rb_safe=(RadioButton)findViewById(R.id.rb_safe);
        rb_mine=(RadioButton)findViewById(R.id.rb_mine);
    }

    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group,int checkedId)
        {
            TextView textView=(TextView)findViewById(R.id.txt_topbar);
            switch (checkedId)
            {
                case R.id.rb_home:
                    textView.setText("智巢");
                    position=0;
                  break;
                case R.id.rb_mode:
                    textView.setText("模式控制");
                 position=1;
                 break;
                case R.id.rb_voice:
                    textView.setText("语音控制");
                    position=2;
                    break;
                case R.id.rb_safe:
                    textView.setText("安全防护");
                    position=3;
                    break;
                case R.id.rb_mine:
                    textView.setText("我的信息");
                    position=4;
                break;
                default:
                    position=0;
                    break;
            }
            BaseFragement currentFragment=getFragment();

            replaceFragment(currentFragment,currentFragment.getmTag());
        }

        private void replaceFragment(BaseFragement fragment,String tag)
        {
            Bundle bundle=new Bundle();
            bundle.putString("cookie",cookie);
            bundle.putString("username",username);
            bundle.putString("logintime",logintime);
            Log.e("传递系统时间",username);
            fragment.setArguments(bundle);
            FragmentManager fm=getSupportFragmentManager();

            FragmentTransaction transaction=fm.beginTransaction();

               transaction.replace(R.id.fl_main, fragment, tag);

               transaction.addToBackStack(tag);
               Log.e("tag", tag + "");

            transaction.commit();
        }

        private BaseFragement getFragment()
        {
            return mBaseFragment.get(position);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int backStackEntryCount=fragmentManager.getBackStackEntryCount();
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if(backStackEntryCount>1)
                {
                   fragmentManager.popBackStackImmediate();
                   FragmentManager.BackStackEntry backStack=fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1);
                   String tag= backStack.getName();
                   if("HomeFragment".equals(tag))
                       rb_home.setChecked(true);
                   else if("ModeFragment".equals(tag))
                       rb_mode.setChecked(true);
                   else if("VoiceFragment".equals(tag))
                     rb_voice.setChecked(true);
                   else if("SafeFragment".equals(tag))
                    rb_safe.setChecked(true);
                   else if("MineFragment".equals(tag))
                     rb_mine.setChecked(true);
                }
                else
                {
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
        }
        return false;
    }


}
