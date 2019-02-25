package com.coderpig.family.Fragment.Home.device;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class HumFragment extends BaseFragement {
    private static final String mTag = HumFragment.class.getSimpleName();
    private View HumFragmentView;
    private Button humOnBtn;
    private Button humOffBtn;
    private Button humUpBtn;
    private Button humDownBtn;
    private String Command;
    private String cookie="";
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
        Log.e(mTag, "控制加湿器页面被初始化了...");
        HumFragmentView = View.inflate(mContext, R.layout.fg_control_hum, null);
        humOnBtn=(Button)HumFragmentView.findViewById(R.id.hum_on_Btn);
        humOffBtn=(Button)HumFragmentView.findViewById(R.id.hum_off_Btn);
        humUpBtn=(Button)HumFragmentView.findViewById(R.id.hum_up_Btn);
        humDownBtn=(Button)HumFragmentView.findViewById(R.id.hum_down_Btn);
        humOnBtn.setOnClickListener(new HumClicklistener());
        humOffBtn.setOnClickListener(new HumClicklistener());
        humUpBtn.setOnClickListener(new HumClicklistener());
        humDownBtn.setOnClickListener(new HumClicklistener());
        return HumFragmentView;
    }
    private class HumClicklistener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.hum_on_Btn:
                    Command="command_turn-on";
                    Log.e(mTag,Command);
                    break;
                case R.id.hum_off_Btn:
                    Command="command_turn-off";

                    break;
                case R.id.hum_up_Btn:
                    Command="command_HumidityPlus";

                    break;
                case R.id.hum_down_Btn:
                    Command="command_HumidityMinus";

                default:
                    break;
            }
            Log.e(mTag,"开始控制");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FormBody formBody=new FormBody
                            .Builder()
                            .add("device","device_Humidifier")
                            .add("command",Command)
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

    }
    protected void initData() {
        super.initDate();
        Log.e(mTag, "控制加湿器页面数据被初始化了...");
        Bundle args=getArguments();
        cookie=args.getString("cookie");
    }
    public String getmTag()
    {
        return mTag;
    }
}
