package com.coderpig.family.Fragment.Home;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.coderpig.family.Activity.LoginActivity;
import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.Fragment.MineFragment;
import com.coderpig.family.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * The type Home controll fragment.
 */
public class HomeControllFragment  extends  BaseFragement{
    private static final String mTag = HomeControllFragment.class.getSimpleName();
    private View mineFragmentView;
    private  String cookie="";
    private SeekBar seekBartime;
    private TextView seekBartimeText;
    private RadioGroup equipmentgroup;
    private RadioGroup modegroup;
    private Button  controllBtn;
    private  String equipmentText="";
    private  String modeText="";
    private String timeText="0";
    private String HOST="http://148.70.56.247:8999";

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
        mineFragmentView = View.inflate(mContext, R.layout.fg_home_controll, null);
        seekBartime=(SeekBar)mineFragmentView.findViewById(R.id.seekbar_time);
        seekBartimeText=(TextView)mineFragmentView.findViewById(R.id.seekbar_time_text);
        seekBartime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBartimeText.setText("当前定时时间为"+progress+"s");
                timeText=String.valueOf(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        equipmentgroup=(RadioGroup)mineFragmentView.findViewById(R.id.equipment_selection);
        modegroup=(RadioGroup)mineFragmentView.findViewById(R.id.mode_selection);
        equipmentgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                case R.id.btn_light:
                    equipmentText="device_light";
                    break;
                case R.id.btn_humidifier:
                    equipmentText="device_humidifier";
                    break;
                case R.id.btn_conditioner:
                        equipmentText="device_air-conditioner";
                        break;
                default:
                       equipmentText="";
                       break;
                }
            }
        });
        modegroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_on:
                        modeText = "command_turn-on";
                   break;
                   case R.id.btn_off:
                   modeText="command_turn-off";
                   break;
                   case R.id.btn_mode:
                        modeText="command_mode";
                        break;
                }
            }
        });
        controllBtn=(Button)mineFragmentView.findViewById(R.id.home_controll_btn);
        controllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((equipmentText.equals("")) || (modeText.equals(""))) {

                    Toast.makeText(getActivity(),getResources().getString(R.string.Lack_of_control_data),Toast.LENGTH_SHORT).show();

                } else {
                    if (!timeText.equals("0")) {
                        if (modeText.equals("command_turn-on"))
                            modeText = "command_timer-on";
                        else if (modeText.equals("command_turn-off"))
                            modeText = "command_timer-off";
                    } else if (timeText.equals("0")) {
                        if (modeText.equals("command_timer-on"))
                            modeText = "command_turn-on";
                        else if (modeText.equals("command_timer-off"))
                            modeText = "command_turn-off";
                    }
                    Log.e("内容", equipmentText + "    " + modeText + "      " + timeText);
                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                           FormBody formBody;
                           if(timeText.equals("0")) {
                               formBody = new FormBody
                                       .Builder()
                                       .add("device",equipmentText)
                                       .add("command",modeText)
                                       .build();
                           } else
                           {
                               formBody =new FormBody
                                       .Builder()
                                       .add("device",equipmentText)
                                       .add("command",modeText)
                                       .add("time",timeText)
                                       .build();
                           }
                           Log.e("url",HOST+"/request/Ctrl"+formBody.toString());
                           final Request request=new Request
                                   .Builder()
                                   .header("Cookie",cookie)
                                   .url(HOST+"/request/Ctrl")
                                   .post(formBody)
                                   .build();

                           OkHttpClient client=new OkHttpClient();
                           Call call=client.newCall(request);
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Looper.prepare();
                                    Toast.makeText(getActivity(),"网络连接异常",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {

                                        String msg= response.body().string();
                                        response.body().close();
                                        Log.e("通知",msg);
                                        if(msg.equals("success"))
                                        {
                                            Looper.prepare();
                                            Toast.makeText(getActivity(),"控制成功",Toast.LENGTH_SHORT).show();
                                            Looper.loop();
                                        }
                                        else
                                        {
                                            Looper.prepare();
                                            Toast.makeText(getActivity(),"控制失败",Toast.LENGTH_SHORT).show();
                                            Looper.loop();
                                        }


                                }
                            });
                       }
                   }).start();
                }
            }
        });
        return mineFragmentView;
    }

    /**
     * Init data.
     */
    protected void initData() {
        super.initDate();
        Bundle args=getArguments();
        cookie=args.getString("cookie");
        Log.e(mTag, "我的Fragment页面数据被初始化了...");
    }
    public String getmTag()
    {
        return mTag;
    }


}
