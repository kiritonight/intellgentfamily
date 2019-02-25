package com.coderpig.family.Fragment.Home.device;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coderpig.family.Base.BaseFragement;


import com.coderpig.family.R;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LightFragment extends BaseFragement {
    private static final String mTag = LightFragment.class.getSimpleName();
    private View LightFragmentView;
    private String cookie="";
    private SeekBar light1;
    private SeekBar light2;
    private SeekBar light3;
    private TextView tl1;
    private TextView tl2;
    private TextView tl3;
    private int progress1=10;
    private int progress2=10;
    private int progress3=10;
    private Button changeBtn;
    private RadioGroup select;
    private RadioButton colorlight1;
    private RadioButton colorlight2;
    private RadioButton colorlight3;
    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    private Button showcolorBtn;
    private int showcolor;
    private int textcolor;
    private int redprogress=0;
    private int greenprogress=0;
    private int blueprogress=0;
    private Button changeColorBtn;
    private String commandText="command_light1";
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
        Log.e(mTag, "控制灯页面被初始化了...");
        LightFragmentView = View.inflate(mContext, R.layout.fg_control_light, null);
        light1=(SeekBar)LightFragmentView.findViewById(R.id.seekbar_light1);
        light2=(SeekBar)LightFragmentView.findViewById(R.id.seekbar_light2);
        light3=(SeekBar)LightFragmentView.findViewById(R.id.seekbar_light3);
        tl1=(TextView)LightFragmentView.findViewById(R.id.seekbar_light1_text);
        tl2=(TextView)LightFragmentView.findViewById(R.id.seekbar_light2_text);
        tl3=(TextView)LightFragmentView.findViewById(R.id.seekbar_light3_text);
        changeBtn=(Button)LightFragmentView.findViewById(R.id.btn_lightchange);
        select=(RadioGroup)LightFragmentView.findViewById(R.id.equipment_select);
        select.check(R.id.btn_color_light);
        colorlight1=(RadioButton)LightFragmentView.findViewById(R.id.btn_color_light);
        colorlight2=(RadioButton)LightFragmentView.findViewById(R.id.btn_color_light2);
        colorlight3=(RadioButton)LightFragmentView.findViewById(R.id.btn_color_light3);
        redSeekBar=(SeekBar) LightFragmentView.findViewById(R.id.seekbar_Red);
        greenSeekBar=(SeekBar)LightFragmentView.findViewById(R.id.seekbar_green);
        blueSeekBar=(SeekBar)LightFragmentView.findViewById(R.id.seekbar_Blue);
        showcolorBtn=(Button)LightFragmentView.findViewById(R.id.btn_showcolor);
        showcolor=Color.rgb(redprogress,greenprogress,blueprogress);
        showcolorBtn.setBackgroundColor(showcolor);
        showcolorBtn.setText("R:"+redprogress+" "+"G:"+greenprogress+" "+"B:"+blueprogress);
        textcolor=Color.rgb(255-redprogress,255-greenprogress,255-blueprogress);
        showcolorBtn.setTextColor(textcolor);
        changeColorBtn=(Button)LightFragmentView.findViewById(R.id.changecolor_Btn);
        select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.btn_color_light:
                        commandText="command_light1";
                        break;
                    case R.id.btn_color_light2:
                        commandText="command_light2";
                        break;
                    case R.id.btn_color_light3:
                        commandText="command_light3";
                        break;
                     default:
                         break;
                }
            }
        });
        redSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               redprogress=progress;
                showcolor=Color.rgb(redprogress,greenprogress,blueprogress);
                showcolorBtn.setBackgroundColor(showcolor);
                showcolorBtn.setText("R:"+redprogress+" "+"G:"+greenprogress+" "+"B:"+blueprogress);
                textcolor=Color.rgb(255-redprogress,255-greenprogress,255-blueprogress);
                showcolorBtn.setTextColor(textcolor);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        greenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            greenprogress=progress;
                showcolor=Color.rgb(redprogress,greenprogress,blueprogress);
                showcolorBtn.setBackgroundColor(showcolor);
                showcolorBtn.setText("R:"+redprogress+" "+"G:"+greenprogress+" "+"B:"+blueprogress);
                textcolor=Color.rgb(255-redprogress,255-greenprogress,255-blueprogress);
                showcolorBtn.setTextColor(textcolor);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        blueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                blueprogress=progress;
                showcolor=Color.rgb(redprogress,greenprogress,blueprogress);
                showcolorBtn.setBackgroundColor(showcolor);
                showcolorBtn.setText("R:"+redprogress+" "+"G:"+greenprogress+" "+"B:"+blueprogress);
                textcolor=Color.rgb(255-redprogress,255-greenprogress,255-blueprogress);
                showcolorBtn.setTextColor(textcolor);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                      FormBody  formBody = new FormBody
                                .Builder()
                                .add("device","device_Light")
                                .add("command","command_brightness")
                                .add("data",progress1+"")
                                 .add("reserve_1",progress2+"")
                                 .add("reserve_2",progress3+"")
                                .build();
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
        });
        changeColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FormBody formBody=new FormBody
                                .Builder()
                                .add("device","device_Light")
                                .add("command",commandText)
                                .add("data",redprogress+"")
                                .add("reserve_1",greenprogress+"")
                                .add("reserve_2",blueprogress+"")
                                .build();
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
        });
        light1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress1=progress+10;
                tl1.setText("灯1:"+progress1);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        light2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress2=progress+10;
                tl2.setText("灯2："+progress2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        light3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress3=progress+10;
                tl3.setText("灯3："+progress3);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return LightFragmentView;
    }
    protected void initData() {
        super.initDate();
        Log.e(mTag, "控制灯页面数据被初始化了...");
        Bundle args=getArguments();
        cookie=args.getString("cookie");
    }
    public String getmTag()
    {
        return mTag;
    }

}
