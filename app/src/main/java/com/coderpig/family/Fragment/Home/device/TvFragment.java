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

public class TvFragment extends BaseFragement {
    private static final String mTag =TvFragment.class.getSimpleName();
    private View TvFragmentView;
    private  String cookie="";
    private String commandText="";
    private Button tvOnBtn;
    private Button tvOffBtn;
    private Button tvUpBtn;
    private Button tvDownBtn;
    private Button tvSoundUpBtn;
    private Button tvSoundDownBtn;
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
        Log.e(mTag, "控制电视页面被初始化了...");
       TvFragmentView = View.inflate(mContext, R.layout.fg_control_tv, null);
       tvOnBtn=(Button)TvFragmentView.findViewById(R.id.tv_on_Btn);
       tvOffBtn=(Button)TvFragmentView.findViewById(R.id.tv_off_Btn);
       tvUpBtn=(Button)TvFragmentView.findViewById(R.id.tv_up_Btn);
       tvDownBtn=(Button)TvFragmentView.findViewById(R.id.tv_down_Btn);
       tvSoundUpBtn=(Button)TvFragmentView.findViewById(R.id.tv_soundup_Btn);
       tvSoundDownBtn=(Button)TvFragmentView.findViewById(R.id.tv_sounddown_Btn);
        tvOnBtn.setOnClickListener(new TvClickListener());
        tvOffBtn.setOnClickListener(new TvClickListener());
        tvUpBtn.setOnClickListener(new TvClickListener());
        tvDownBtn.setOnClickListener(new TvClickListener());
        tvSoundUpBtn.setOnClickListener(new TvClickListener());
        tvSoundDownBtn.setOnClickListener(new TvClickListener());
        return TvFragmentView;
    }

    protected void initData() {
        super.initDate();
        Log.e(mTag, "控制电视页面数据被初始化了...");
        Bundle args=getArguments();
        cookie=args.getString("cookie");
    }
    private class TvClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.tv_on_Btn:
                    commandText="command_turn-on";
                    break;
                case R.id.tv_off_Btn:
                    commandText="command_turn-off";
                    break;
                case R.id.tv_up_Btn:
                    commandText="command_RadioPlus";
                    break;
                case R.id.tv_down_Btn:
                    commandText="command_RadioMinus";
                    break;
                case R.id.tv_soundup_Btn:
                    commandText="command_VolumePlus";
                    break;
                case R.id.tv_sounddown_Btn:
                    commandText="command_VolumeMinus";
                    break;
                default:
                    break;

            }
            Log.e(mTag,"开始控制");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FormBody formBody=new FormBody
                            .Builder()
                            .add("device","device_Television")
                            .add("command",commandText)
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
    public String getmTag()
    {
        return mTag;
    }
}
