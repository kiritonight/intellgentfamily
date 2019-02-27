package com.coderpig.family.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.coderpig.family.Activity.LoginActivity;
import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ModeFragment extends BaseFragement {
    private static final String mTag = ModeFragment.class.getSimpleName();
    private String cookie="";
    private View modeFragmentView;
    private LinearLayout modeView;
    private Button btnGetMode;
    private String [] commandTexts=new String[100];
    private  TextView controltext;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        initData();
    }
    @Override
    protected View initView() {
        Log.e(mTag, "模式页面Fragment页面被初始化了...");
        modeFragmentView = View.inflate(mContext, R.layout.fg_mode, null);
        modeView=(LinearLayout) modeFragmentView.findViewById(R.id.mode_view);
        btnGetMode=(Button)modeFragmentView.findViewById(R.id.btn_getmode);
        btnGetMode.setOnClickListener(new ModeClickListener());

        return modeFragmentView;
    }
   private class ModeClickListener implements  View.OnClickListener {
       @Override
       public void onClick(View v) {
           switch (v.getId()) {
               case R.id.btn_getmode: {
                    modeView.removeAllViews();
                   new Thread(new Runnable() {
                       @Override
                       public void run() {


                           String url = "http://148.70.56.247:8999/request/getProfiles";
                           OkHttpClient okHttpClient = new OkHttpClient();
                           final Request request = new Request.Builder()
                                   .url(url)
                                   .header("Cookie", cookie)
                                   .get()
                                   .build();
                           Call call = okHttpClient.newCall(request);
                           call.enqueue(new Callback() {
                               @Override
                               public void onFailure(Call call, IOException e) {
                                   Looper.prepare();
                                   Toast.makeText(getActivity(), "网络连接异常", Toast.LENGTH_SHORT).show();
                                   Looper.loop();
                               }

                               @Override
                               public void onResponse(Call call, Response response) throws IOException {
                                   String json = response.body().string();
                                   try {
                                       JSONArray jsonArray = new JSONArray(json);

                                       for (int i = 0; i < jsonArray.length(); i++) {
                                           final JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Message msg=new Message();
                                                msg.what=0;
                                                msg.obj=jsonObject;

                                                mHandler.sendMessage(msg);
                                            }
                                        }).start();



                                       }
                                   } catch (JSONException e) {
                                       Looper.prepare();
                                       Toast.makeText(getActivity(), "Json解析错误", Toast.LENGTH_SHORT).show();
                                       Looper.loop();
                                   }


                               }
                           });
                       }
                   }).start();

               } break;
               case R.id.mode_item_on:
               {
                 Log.e(mTag,"启用按键已被触发");

                 Log.e(mTag,v.getTag().toString());
                 final String num=v.getTag().toString();
                 final String command=commandTexts[Integer.parseInt(num)];
                 new Thread(new Runnable() {
                     @Override
                     public void run() {
                         String url="http://148.70.56.247:8999/request/Ctrl";
                         FormBody formBody=new FormBody
                                 .Builder()
                                 .add("device","device_Profile")
                                 .add("command",command)
                                 .build();
                         final Request request= new Request
                                 .Builder()
                                 .header("Cookie",cookie)
                                 .url(url)
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
                                     Toast.makeText(getActivity(),num+"号模式"+"设置成功",Toast.LENGTH_SHORT).show();
                                     Looper.loop();
                                 }
                                 else
                                 {
                                     Looper.prepare();
                                     Toast.makeText(getActivity(),num+"号模式"+"设置失败",Toast.LENGTH_SHORT).show();
                                     Looper.loop();
                                 }
                             }
                         });
                     }
                 }).start();

               }
                   break;
           }

       }
   }
   private void addViewItem(String id,String mode_name,String mode_command) {

       Log.e(mTag,id+" "+mode_name+" "+mode_command);
        View item = View.inflate(getActivity(), R.layout.item_mode, null);
       TextView modeid=(TextView)item.findViewById(R.id.mode_id);
       modeid.setText(id);
       Log.e(mTag,modeid.getText()+"");
       TextView modename=(TextView)item.findViewById(R.id.text_modename);
         modename.setText(mode_name);
         Log.e(mTag,modename.getText()+"");
         commandTexts[Integer.parseInt(id)]=mode_command;
         Button modeBtn=(Button)item.findViewById(R.id.mode_item_on);
         modeBtn.setOnClickListener(new ModeClickListener());
         modeBtn.setTag(id);

         modeView.addView(item);
    }
    protected void initData() {
        super.initDate();
        Bundle args=getArguments();
        cookie=args.getString("cookie");

        Log.e(mTag,"模式Fragment页面数据被初始化了...");
    }
    public String getmTag()
    {
        return mTag;
    }
 @SuppressLint("HandlerLeak")
 private Handler mHandler=new Handler()
 {
     @Override
     public void handleMessage(Message msg)
     {
         super.handleMessage(msg);
         switch (msg.what)
         {
             case 0:
          try {
                     JSONObject jsonObject = (JSONObject) msg.obj;
                     Log.e(mTag,String.valueOf(jsonObject));
             String id = jsonObject.getString("id");
             String mode_name = jsonObject.getString("mode_name");
            String mode_command=jsonObject.getString("commandText");

                addViewItem(id,mode_name,mode_command);
 }catch (JSONException e){
               Toast.makeText(getActivity(),"Json解析失败",Toast.LENGTH_SHORT);
              }
                 break;
         }
     }
 };

}
