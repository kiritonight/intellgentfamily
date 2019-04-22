package com.coderpig.family.Fragment.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.coderpig.family.Activity.MainActivity;
import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.Fragment.Home.data.HomeDataViewFragment;
import com.coderpig.family.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * The type Home data fragment.
 */
public class HomeDataFragment extends BaseFragement {
    private static final String mTag =HomeDataFragment.class.getSimpleName();
    private View homeDataFragmentView;

    private String cookie="";
    private Handler handler=new Handler();
    private Runnable runnable;
    private TextView pm25text; private  TextView pm1text;
    private  TextView pm10text; private TextView firetext;
    private  TextView watertext; private  TextView methanaltext;
    private TextView cotext;  private  TextView lighttext;
    private  TextView airtext; private TextView temptext;
    private TextView humtext; private  TextView timetext;
    private Button dataViewBtn;

    private  TextView pm25; private TextView pm1;
    private TextView pm10; private TextView water;
    private TextView methanal; private TextView co;
    private TextView air; private TextView temp;
    private  TextView hum; private TextView fire;
    private TextView light;
    private int upDateFlag=0;
   public static void reMoveThread(HomeDataFragment homeDataFragment){
    //   homeDataFragment.runnable

    }

    @SuppressLint("HandlerLeak")
    public Handler mHandler=new Handler(){
        @Override
                public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        pm25text.setText(getStr(jsonObject.getString("pm25"))+"ug/m³");
                        pm1text.setText(getStr(jsonObject.getString("pm10"))+"ug/m³");
                        pm10text.setText(getStr(jsonObject.getString("pm100"))+"ug/m³");

                        firetext.setText(getStr(jsonObject.getString("if_fire")).equals("是")?"是":"否");
                        watertext.setText(getStr(jsonObject.getString("if_rain")).equals("是")?"是":"否");
                        methanaltext.setText(getStr(jsonObject.getString("hcho"))+"ppm");
                        cotext.setText(getStr(jsonObject.getString("co"))+"ppm");
                        lighttext.setText(getStr(jsonObject.getString("light_intensity")));
                        airtext.setText(getStr(jsonObject.getString("air_quality")));
                        temptext.setText(getStr(jsonObject.getString("air_temperature"))+"°C");
                        humtext.setText(getStr(jsonObject.getString("air_humidity"))+"%");
                        timetext.setText(jsonObject.getString("time"));
                    }catch (Exception e)
                    {
                        Log.e("错误","json解析错误");
                    }
                    break;
            }
        }
    };
   @Override
   public void onStop()
   {
       handler.removeCallbacks(runnable);
       super.onStop();
   }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        runnable=new Runnable() {
            @Override
            public void run() {
                Log.e(mTag,"请求数据");
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
                        Log.d(mTag, "onFailure: ");
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws  IOException{
                        String json= response.body().string();
                        try {

                            JSONArray jsonArray = new JSONArray(json);
                            final JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                            Log.e("json", jsonObject.toString());
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    //耗时操作，完成之后发送消息给Handler，完成UI更新；

                                    //需要数据传递，用下面方法；
                                    Message msg = new Message();
                                    msg.what=0;
                                    msg.obj = jsonObject;//可以是基本类型，可以是对象，可以是List、map等；
                                    mHandler.sendMessage(msg);
                                }

                            }).start();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            handler.postDelayed(this,1000);
            }
        };
        handler.postDelayed(runnable,10);
        initData();
    }
    @Override
    protected View initView() {
        Log.e(mTag, "环境数据页面Fragment页面被初始化了...");
        homeDataFragmentView = View.inflate(mContext, R.layout.fg_home_data, null);


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
        dataViewBtn=(Button)homeDataFragmentView.findViewById(R.id.data_view);

        pm25=(TextView)homeDataFragmentView.findViewById(R.id.pm25);
        pm25.setOnClickListener(new HomeDateClickListener());
        pm1=(TextView)homeDataFragmentView.findViewById(R.id.pm1);
        pm1.setOnClickListener(new HomeDateClickListener());
        pm10=(TextView)homeDataFragmentView.findViewById(R.id.pm10);
        pm10.setOnClickListener(new HomeDateClickListener());
        water=(TextView)homeDataFragmentView.findViewById(R.id.water);
        water.setOnClickListener(new HomeDateClickListener());
        methanal=(TextView)homeDataFragmentView.findViewById(R.id.methanal);
        methanal.setOnClickListener(new HomeDateClickListener());
        co=(TextView)homeDataFragmentView.findViewById(R.id.co);
        co.setOnClickListener(new HomeDateClickListener());
        air=(TextView)homeDataFragmentView.findViewById(R.id.air);
        air.setOnClickListener(new HomeDateClickListener());
        temp=(TextView)homeDataFragmentView.findViewById(R.id.temp);
        temp.setOnClickListener(new HomeDateClickListener());
        hum=(TextView)homeDataFragmentView.findViewById(R.id.hum);
        hum.setOnClickListener(new HomeDateClickListener());
        fire=(TextView)homeDataFragmentView.findViewById(R.id.fire);
        fire.setOnClickListener(new HomeDateClickListener());
        light=(TextView)homeDataFragmentView.findViewById(R.id.light);
        light.setOnClickListener(new HomeDateClickListener());
        dataViewBtn.setOnClickListener(new HomeDateClickListener());
        return homeDataFragmentView;
    }

    /**
     * Init data.
     */
    protected void initData() {
        super.initDate();
        Bundle args=getArguments();
        cookie=args.getString("cookie");


        Log.e(mTag, "环境数据Fragment页面数据被初始化了...");
    }
    private class HomeDateClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v)
        {
           switch (v.getId()){

               case R.id.data_view:

                   replaceFragment(new HomeDataViewFragment(),new HomeDataViewFragment().getmTag());
                   break;
               case R.id.pm25:
                   showNormalDialog(R.mipmap.ic_data_haze,"pm2.5","细颗粒物指环境空气中空气动力学当量直径小于等于 2.5 微米的颗粒物。它能较长时间悬浮于空气中，其在空气中含量浓度越高，就代表空气污染越严重。虽然PM2.5只是地球大气成分中含量很少的组分，但它对空气质量和能见度等有重要的影响。");
                   break;
               case R.id.pm1:
                   showNormalDialog(R.mipmap.ic_data_pm1,"pm1.0","PM1，是对空气中直径小于或等于1微米的固体颗粒或液滴的总称，也称为可入肺颗粒物。PM1粒径小，富含大量的有毒、有害物质且在大气中的停留时间长、输送距离远，因而对人体健康和大气环境质量的影响更大。");
               break;
               case R.id.pm10:
                   showNormalDialog(R.mipmap.ic_data_pm10,"pm10","通常把粒径在10微米以下的颗粒物称为可吸入颗粒物，又称PM10。可吸入颗粒物可以被人体吸入，沉积在呼吸道、肺泡等部位从而引发疾病。颗粒物的直径越小，进入呼吸道的部位越深。10微米直径的颗粒物通常沉积在上呼吸道，5微米直径的可进入呼吸道的深部，2微米以下的可100%深入到细支气管和肺泡。");
               break;
               case R.id.water:
                   showNormalDialog(R.mipmap.ic_data_water,"水灾","水灾泛指洪水泛滥、暴雨积水和土壤水分过多对人类社会造成的灾害。一般所指的水灾，以洪涝灾害为主。水灾威胁人民生命安全。造成巨大财产损失，并对社会经济发展产生深远的不良影响。防治水灾已成为世界各国保证社会安定和经济发展的重要公共安全保障事业。但根除是困难的。至今世界上水灾仍是一种影响最大的自然灾害。");
                break;
               case R.id.methanal:
                   showNormalDialog(R.mipmap.ic_data_meth,"甲醛","甲醛，化学式HCHO或CH₂O，分子量30.03，又称蚁醛。无色，对人眼、鼻等有刺激作用。在常温下是气态，通常以水溶液和气态形式出现。急性甲醛中毒为接触高浓度甲醛蒸气引起的以眼、呼吸系统损害为主的全身性疾病。");
                   break;
               case R.id.co:
                   showNormalDialog(R.mipmap.ic_data_co,"co","一氧化碳（carbon monoxide），一种碳氧化合物，化学式为CO，化学式量为28.0101，标准状况下为无色、无臭、无刺激性的气体。在理化性质方面，一氧化碳的熔点为-205.1℃，沸点为-191.5℃，微溶于水，不易液化和固化，在空气中燃烧时为蓝色火焰，较高温度时分解产生二氧化碳和碳，在血液中极易与血红蛋白结合，形成碳氧血红蛋白，使血红蛋白丧失携氧的能力和作用，造成组织窒息，严重时死亡。");
                   break;
               case R.id.air:
                   showNormalDialog(R.mipmap.ic_data_air,"空气质量","空气指数，就是根据空气中的各种成分占比，将监测的空气浓度简化成为单一的概念性指数值形式，它将空气污染程度和空气质量状况分级表示，适合于表示城市的短期空气质量状况和变化趋势。");
                   break;
               case R.id.temp:
                   showNormalDialog(R.mipmap.ic_data_temp,"温度","温度（temperature）是表示物体冷热程度的物理量，微观上来讲是物体分子热运动的剧烈程度。温度只能通过物体随温度变化的某些特性来间接测量，而用来量度物体温度数值的标尺叫温标。从分子运动论观点看，温度是物体分子运动平均动能的标志。温度是大量分子热运动的集体表现，含有统计意义。对于个别分子来说，温度是没有意义的。根据某个可观察现象（如水银柱的膨胀），按照几种任意标度之一所测得的冷热程度。");
                   break;
               case R.id.hum:
                   showNormalDialog(R.mipmap.ic_data_hum,"湿度","湿度，表示大气干燥程度的物理量。在一定的温度下在一定体积的空气里含有的水汽越少，则空气越干燥；水汽越多，则空气越潮湿。空气的干湿程度叫做“湿度”。");
                   break;
               case R.id.fire:
                   showNormalDialog(R.mipmap.ic_data_fire,"火灾","火灾是指在时间或空间上失去控制的灾害性燃烧现象。在各种灾害中，火灾是最经常、最普遍地威胁公众安全和社会发展的主要灾害之一。 人类能够对火进行利用和控制，是文明进步的一个重要标志。所以说人类使用火的历史与同火灾作斗争的历史是相伴相生的，人们在用火的同时，不断总结火灾发生的规律，尽可能地减少火灾及其对人类造成的危害。在遇到火灾时人们需要安全、尽快的逃生。");
                   break;
               case R.id.light:
                   showNormalDialog(R.mipmap.ic_data_light,"光强","发光强度简称光强，国际单位是candela（坎德拉）简写cd，其他单位有烛光，支光。1cd即1000mcd是指单色光源（频率540X10^12HZ）的光，在给定方向上（该方向上的辐射强度为（1/683）瓦特/球面度））的单位立体角发出的光通量。可以用基尔霍夫积分定理计算。");
                   break;
                default:
                    break;
           }
        }
    }


    public String getmTag()
    {
        return mTag;
    }
    private String getStr(String str)
    {
       int len=str.length();
       int index=0;
       char strs[]=str.toCharArray();
       for(int i=0;i<len;i++)
       {
           if('0'!=strs[i])
           {
               index=i;
               break;
           }
       }
       String strLast=str.substring(index,len);
       return strLast;
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

    private void showNormalDialog(int iconid,String title,String message){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getActivity());
        normalDialog.setIcon(iconid);
        normalDialog.setTitle(title);
        normalDialog.setMessage(message);
        // 显示
        AlertDialog dialog =normalDialog.show();
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                });

    }
}
