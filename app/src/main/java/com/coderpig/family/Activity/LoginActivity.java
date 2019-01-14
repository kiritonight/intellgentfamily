package com.coderpig.family.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coderpig.family.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    public static String HOST = "http://148.70.56.247:8999";
    String cookie="";
    private EditText username;
    private EditText password;

    private Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
        public void initView()
        {
          username = (EditText)findViewById(R.id.username);
         password = (EditText)findViewById(R.id.password);

        loginBtn = (Button)findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ((username.getText().toString().trim().length()==0) || (password.getText().toString().trim().length()==0)) {
                    Toast.makeText(getApplicationContext(), "用户名或密码信息不完全，请重试", Toast.LENGTH_SHORT).show();

                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                FormBody formBody = new FormBody
                                        .Builder()
                                        .add("username", username.getText().toString())
                                        .add("password", password.getText().toString())
                                        .build();

                                final Request request = new Request
                                        .Builder()
                                        .url(HOST + "/security/user/dologin")
                                        .post(formBody)
                                        .build();

                                OkHttpClient client = new OkHttpClient();
                               Call call= client.newCall(request);
                               call.enqueue(new Callback(){
                                   @Override
                                   public  void onFailure(Call call, IOException e)
                                   {
                                       Looper.prepare();
                         Toast.makeText(LoginActivity.this,"网络连接异常",Toast.LENGTH_SHORT).show();
                                  Looper.loop();
                                   }
                                   @Override
                                   public  void  onResponse(Call call, Response response)throws  IOException
                                   {
                                       Headers loginResponseHeader = response.headers();
                                       List<String> cookieList = loginResponseHeader.values("Set-Cookie");
                                       System.out.println("cookieList为：" + cookieList);
                                   cookie = cookieList.get(0);
                                       System.out.println("cookie为：" + cookie);
                                       try {
                                           JSONObject jsonObject=new JSONObject(response.body().string());
                                           response.body().close();
                                           String msg=jsonObject.getString("msg");
                                           System.out.println(msg);
                                           if(msg.equals("登录失败，密码不匹配"))
                                           {
                                               Looper.prepare();
                                               Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                                               Looper.loop();
                                           }
                                           else if(msg.equals("登录成功！"))
                                           {
                                               Looper.prepare();
                                               Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();

                                               Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                               intent.putExtra("cookie",cookie);
                                               startActivity(intent);
                                               finish();
                                               Looper.loop();
                                           }
                                           else if(msg.equals("帐号不存在"))
                                           {
                                               Looper.prepare();
                                               Toast.makeText(LoginActivity.this,"账号不存在",Toast.LENGTH_SHORT).show();
                                               Looper.loop();
                                           }

                                       } catch (JSONException e) {
                                           e.printStackTrace();
                                       }
                                   }
                               });
                            }catch (Exception e)
                            {
                                Looper.prepare();
                                Toast.makeText(LoginActivity.this,"网络连接线程出错",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                    }).start();
                }
            }

        });


//        getUserIDBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request
//                        .Builder()
//                        .get()
//                        .url("http://10.16.146.220:8999/app/user/isLogined")
//                        .header("Cookie",cookie)
//                        .build();
//                Call call = client.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Looper.prepare();
//                        Toast.makeText(LoginActivity.this, "失败："+e.getMessage(),Toast.LENGTH_LONG).show();
//                        Looper.loop();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        Looper.prepare();
//                        Toast.makeText(LoginActivity.this, "成功："+response.body().toString(),Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }
//                });
//            }
//        });
    }
}


