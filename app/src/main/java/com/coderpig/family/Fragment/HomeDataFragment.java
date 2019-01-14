package com.coderpig.family.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeDataFragment extends BaseFragement {
    private static final String TAG =HomeDataFragment.class.getSimpleName();
    private View homeDataFragmentView;
    private Button up_btn;
    private String cookie="";

    private TextView pm25text; private  TextView pm1text;
    private  TextView pm10text; private TextView firetext;
    private  TextView watertext; private  TextView methanaltext;
    private TextView cotext;  private  TextView lighttext;
    private  TextView airtext; private TextView temptext;
    private TextView humtext; private  TextView timetext;

    private  Handler mHandler=new Handler(){
        @Override
                public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        pm25text.setText(jsonObject.getString("pm25"));
                        pm1text.setText(jsonObject.getString("pm10"));
                        pm10text.setText(jsonObject.getString("pm100"));
                        firetext.setText(jsonObject.getString("if_fire"));
                        watertext.setText(jsonObject.getString("if_rain"));
                        methanaltext.setText(jsonObject.getString("hcho"));
                        cotext.setText(jsonObject.getString("co"));
                        lighttext.setText(jsonObject.getString("light_intensity"));
                        airtext.setText(jsonObject.getString("air_quality"));
                        temptext.setText(jsonObject.getString("air_temperature"));
                        humtext.setText(jsonObject.getString("air_humidity"));
                        timetext.setText(jsonObject.getString("time"));
                    }catch (Exception e)
                    {
                        Log.e("错误","json解析错误");
                    }
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        initView();
        initData();
    }
    @Override
    protected View initView() {
        Log.e(TAG, "环境数据页面Fragment页面被初始化了...");
        homeDataFragmentView = View.inflate(mContext, R.layout.fg_home_data, null);
        up_btn=(Button)homeDataFragmentView.findViewById(R.id.update_data);
        up_btn.setOnClickListener(new HomeDateClickListener());

        pm25text=(TextView)homeDataFragmentView.findViewById(R.id.pm25_text);
        pm1text=(TextView)homeDataFragmentView.findViewById(R.id.pm1_text);
        pm10text=(TextView)homeDataFragmentView.findViewById(R.id.pm10_text);
        firetext=(TextView)homeDataFragmentView.findViewById(R.id.fire_text);
        watertext=(TextView)homeDataFragmentView.findViewById(R.id.water_text);
        methanaltext=(TextView)homeDataFragmentView.findViewById(R.id.methanal_text);
        cotext=(TextView)homeDataFragmentView.findViewById(R.id.co_text);
        lighttext=(TextView)homeDataFragmentView.findViewById(R.id.light_text);
        airtext=(TextView)homeDataFragmentView.findViewById(R.id.air_text);
        temptext=(TextView)homeDataFragmentView.findViewById(R.id.temp_text);
        humtext=(TextView)homeDataFragmentView.findViewById(R.id.hum_text);
        timetext=(TextView)homeDataFragmentView.findViewById(R.id.time_text);

        return homeDataFragmentView;
    }

    protected void initData() {
        super.initDate();
        Bundle args=getArguments();
        cookie=args.getString("cookie");


        Log.e(TAG, "环境数据Fragment页面数据被初始化了...");
    }
    private class HomeDateClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v)
        {
           switch (v.getId()){
               case R.id.update_data: {
                   Log.e(TAG,"点击按钮");
                   String url = "http://148.70.56.247:8999/request/requestData";
                   OkHttpClient okHttpClient = new OkHttpClient();
                   final Request request = new Request.Builder()
                           .url(url)
                           .header("Cookie",cookie)
                           .get()//默认就是GET请求，可以不写
                           .build();
                   Call call = okHttpClient.newCall(request);
                   call.enqueue(new Callback() {
                       @Override
                       public void onFailure(Call call, IOException e) {
                           Log.d(TAG, "onFailure: ");
                       }
                       @Override
                       public void onResponse(Call call, Response response) throws  IOException{
                         String json= response.body().string();
                           try {

                               JSONArray jsonArray = new JSONArray(json);
                            final JSONObject jsonObject = (JSONObject) jsonArray.get(jsonArray.length()-1);

                              Log.e("json", jsonObject.toString());
                               new Thread(new Runnable() {
                                   @Override
                                   public void run() {
                                       //耗时操作，完成之后发送消息给Handler，完成UI更新；
                                       mHandler.sendEmptyMessage(0);
                                       //需要数据传递，用下面方法；
                                       Message msg = new Message();
                                   msg.obj = jsonObject;//可以是基本类型，可以是对象，可以是List、map等；
                                       mHandler.sendMessage(msg);
                                   }

                               }).start();
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }
                   });
               }
                   break;
                default:
                    break;
           }
        }
    }
}
