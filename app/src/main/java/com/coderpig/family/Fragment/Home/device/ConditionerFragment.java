package com.coderpig.family.Fragment.Home.device;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.R;
import com.gc.materialdesign.views.ButtonIcon;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConditionerFragment  extends BaseFragement {
    private static final String mTag = ConditionerFragment.class.getSimpleName();
    private View ConditionFragmentView;
    private  String cookie="";
    private RadioGroup airconGroup;
    private RadioButton aircon1;
    private RadioButton aircon2;
    private RadioButton aircon3;
    private Button conOnBtn;
    private Button conOffBtn;
    private Button conTempUpBtn;
    private Button conTempDownBtn;
    private Button conWindBtn;
    private Button conTurnHotBtn;
    private Button conTurnColdBtn;
    private Button conVentilateBtn;
    private String deviceNum="1";
    private String commandText="";
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
        Log.e(mTag, "控制空调页面被初始化了...");
       ConditionFragmentView = View.inflate(mContext, R.layout.fg_control_condition, null);
       airconGroup=(RadioGroup)ConditionFragmentView.findViewById(R.id.condition_select);
       airconGroup.check(R.id.btn_conditioner1);
       aircon1=(RadioButton)ConditionFragmentView.findViewById(R.id.btn_conditioner1);
       aircon2=(RadioButton)ConditionFragmentView.findViewById(R.id.btn_conditioner2);
       aircon3=(RadioButton)ConditionFragmentView.findViewById(R.id.btn_conditioner3);
       conOnBtn=(Button)ConditionFragmentView.findViewById(R.id.con_on_Btn);
       conOffBtn=(Button)ConditionFragmentView.findViewById(R.id.con_off_Btn);
       conTempUpBtn=(Button)ConditionFragmentView.findViewById(R.id.con_tempup_Btn);
       conTempDownBtn=(Button)ConditionFragmentView.findViewById(R.id.con_tempdown_Btn);
       conWindBtn=(Button)ConditionFragmentView.findViewById(R.id.con_wind_Btn);
       conTurnHotBtn=(Button)ConditionFragmentView.findViewById(R.id.con_turnhot_Btn);
       conTurnColdBtn=(Button)ConditionFragmentView.findViewById(R.id.con_turncold_Btn);
       conVentilateBtn=(Button)ConditionFragmentView.findViewById(R.id.con_ventilate_Btn);
       conOnBtn.setOnClickListener(new ConditionClickListener());
       conOffBtn.setOnClickListener(new ConditionClickListener());
       conTempUpBtn.setOnClickListener(new ConditionClickListener());
       conTempDownBtn.setOnClickListener(new ConditionClickListener());
       conWindBtn.setOnClickListener(new ConditionClickListener());
       conTurnHotBtn.setOnClickListener(new ConditionClickListener());
       conTurnColdBtn.setOnClickListener(new ConditionClickListener());
       conVentilateBtn.setOnClickListener(new ConditionClickListener());
       airconGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (checkedId)
               { case R.id.btn_conditioner1:
                   deviceNum=1+"";
                   break;
                   case R.id.btn_conditioner2:
                       deviceNum=2+"";
                   break;
                   case R.id.btn_conditioner3:
                       deviceNum=3+"";
                   break;
                   default:
                       break;
               }
           }
       });

        return ConditionFragmentView;
    }
    private class ConditionClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.con_on_Btn:
                    commandText="command_turn-on";
                    break;
                case R.id.con_off_Btn:
                    commandText="command_turn-off";
                    break;
                case R.id.con_tempup_Btn:
                    commandText="command_TemperRise";
                    break;
                case R.id.con_tempdown_Btn:
                    commandText="command_TemperReduce";
                    break;
                case R.id.con_wind_Btn:
                    commandText="command_WindDirection";
                    break;
                case R.id.con_turnhot_Btn:
                    commandText="command_TurnHot";
                    break;
                case R.id.con_turncold_Btn:
                    commandText="command_TurnCold";
                    break;
                case R.id.con_ventilate_Btn:
                    commandText="command_Ventilate";
                 break;
                 default:
                     break;
            }
           new Thread(new Runnable() {
               @Override
               public void run() {
                   FormBody formBody=new FormBody
                           .Builder()
                           .add("device","device_AirConditioner")
                           .add("command",commandText)
                           .add("data",deviceNum)
                           .build();
                   Log.e(getmTag(),commandText+" "+deviceNum);
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
        Log.e(mTag, "控制空调页面数据被初始化了...");
        Bundle args=getArguments();
        cookie=args.getString("cookie");
    }
    public String getmTag()
    {
        return mTag;
    }
}
