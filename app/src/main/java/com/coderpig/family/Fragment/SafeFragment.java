package com.coderpig.family.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.coderpig.family.Base.BaseFragement;
import com.coderpig.family.Base.Constants;
import com.coderpig.family.Base.ImageDispose;
import com.coderpig.family.R;
import com.coderpig.family.unit.MqttManager;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SafeFragment extends BaseFragement {
    private static final String mTag =SafeFragment.class.getSimpleName();
    public static int tempI = 0;
    public static int nameCount = 0;
    private View safeFragmentView;
    private String cookie="";
    private Button showVideoBtn;
   public ImageView videoView;
   private Socket socket=new Socket();
    private int socketfalg=0;

    private static   InputStream inputStream;

    public static ThreadPoolExecutor executor;

    private MqttManager mqttManager=null;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        initData();


    }


    @Override
    protected View initView() {
        Log.e(mTag, "安全页面Fragment页面被初始化了...");
        safeFragmentView = View.inflate(mContext, R.layout.fg_safe, null);
        showVideoBtn=(Button)safeFragmentView.findViewById(R.id.show_video_btn);
        videoView=(ImageView)safeFragmentView.findViewById(R.id.video_view);


        Log.e(mTag,"开启MQTT服务");
        mqttManager=new MqttManager(getContext());
        mqttManager.connect();
        mqttManager.subscribe("AIResult",0);
        showVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.show_video_btn:
                    {

                        if(showVideoBtn.getText().equals("显示")) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                            try {

                             //   videoView.setImageResource(R.mipmap.ic_stop_on);


                                executor = new ThreadPoolExecutor(30, 50, 500, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10000));
                                // executor.prestartAllCoreThreads();
                                executor.allowCoreThreadTimeOut(true);
                                Log.e(mTag,"正在连接socket");
                                socket= new Socket("148.70.56.247", 8997);
                                if(socket.isConnected())
                                {
                                socketfalg=1;
                                showVideoBtn.setText("停止");
                                ReceivePic(socket);
                                }

                            } catch (IOException e) {
                                Log.e(mTag,"socket连接中断");

                                Looper.prepare();
                                Toast.makeText(getActivity(),"socket连接中断",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                                }
                            }).start();

                        }
                        else if (showVideoBtn.getText().equals("停止"))
                        {
                            if(socketfalg==1)
                            {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {

                                        String url="http://148.70.56.247:8999/request/videoShutDown";
                                        OkHttpClient okHttpClient=new OkHttpClient();
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
                                                if("success".equals(json))
                                                {
                                                    try {
                                                        inputStream.close();
                                                        socket.close();
                                                        Log.e(mTag,"socket关闭");
                                                    }
                                                    catch (IOException e) {
                                                        Log.e(mTag,"关闭出错");
                                                    }
                                                    executor.shutdown();
                                                    socketfalg=0;
                                                    showVideoBtn.setText("显示");

                                                }
                                            }
                                        });
                                    }
                                }).start();

                            }

                        }
                    }
                    break;
                    default:
                    break;
                }
            }
        });
        return safeFragmentView;
    }
    public  void onStop()
    {
      super.onStop();
      mqttManager.onDestroy();
      if((socketfalg==1)&&(showVideoBtn.getText().equals("停止")))
        {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    String url="http://148.70.56.247:8999/request/videoShutDown";
                    OkHttpClient okHttpClient=new OkHttpClient();
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
                            if("success".equals(json))
                            {
                                try {
                                    inputStream.close();
                                    socket.close();
                                    Log.e(mTag,"socket关闭");
                                }
                                catch (IOException e) {
                                    Log.e(mTag,"关闭出错");
                                }
                                executor.shutdown();
                                socketfalg=0;
                                showVideoBtn.setText("显示");

                            }
                        }
                    });
                }
            }).start();
        }
 //       if(socketfalg==1)
  //      {
  //          try {
   //             socket.close();
   //             executor.shutdown();
  //          } catch (IOException e) {
   //             e.printStackTrace();
   //         }
   //         socketfalg=0;
    //    }
    }
    protected void initData() {
        super.initDate();
        Log.e(mTag, "安全Fragment页面数据被初始化了...");
        Bundle args=getArguments();
        cookie=args.getString("cookie");


    }
    public String getmTag()
    {
        return mTag;
    }
    public void ReceivePic(Socket socket) throws Exception
    {
        System.out.println("获取输入流...");
        tempI = 0;
        nameCount = 0;

       inputStream = socket.getInputStream();

        while (true) {

            tempI++;

            if (!socket.isConnected() || socket.isClosed()) {
                return;
            }
            boolean isHead = true;

            for (int i = 0; i < Constants.PICTURE_PACKAGE_HEAD.length; i++) {
                byte head = (byte) inputStream.read();
                if (head != Constants.PICTURE_PACKAGE_HEAD[i]) {
                    isHead = false;
                    break;
                }
            }

            if (isHead == true)

                if (isHead) {
                  DataInputStream  inputData = new DataInputStream(inputStream);

                    byte[] temp = new byte[4];
                    for (int i = 0; i < 4; i++) {
                        temp[i] = inputData.readByte();
                    }
                    int picLen = Integer.parseInt(printHexString(temp), 16);

                    ByteArrayOutputStream fos = new ByteArrayOutputStream();

                    byte[] buffer = new byte[Constants.CACHE_SIZE];
                    int len = -1;

                    while (picLen > 0 && (len = inputData.read(buffer, 0,
                            picLen < buffer.length ? (int) picLen : buffer.length)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                        picLen -= len;
                    }
                    fos.flush();
                    buffer = null;
                    Log.e(getmTag(),"成功接收一张图片");

                  ShowTask showTask = new ShowTask(fos);
                 executor.execute(showTask);
                    // byte[] picture = fos.toByteArray();
                    // Byte2File(picture, Constants.PATH);
                }
        }
    }
    public static String printHexString(byte[] b) {
        String returnString = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            // System.out.println(hex.toUpperCase());
            returnString = hex + returnString;
        }
        return returnString;
    }
    public class ShowTask implements Runnable{
        private ByteArrayOutputStream fos;
        public ShowTask(ByteArrayOutputStream fos){
            this.fos=fos;
        }
        @Override
        public void run()
        {
            byte[] picture=fos.toByteArray();
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds=false;
            options.inSampleSize=2;
            final Bitmap bitmap= ImageDispose.getPicFromBytes(picture,options);
            safeFragmentView.post(new Runnable() {
                @Override
                public void run() {

                    videoView.setImageBitmap(bitmap);
                }
            });
        }
    }
}
