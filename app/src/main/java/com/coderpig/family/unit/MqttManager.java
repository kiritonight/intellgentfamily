package com.coderpig.family.unit;

import android.content.Context;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


import org.eclipse.paho.android.service.MqttService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttManager  {
    private static final String mTag = MqttService.class.getSimpleName();

    private static final String host="tcp://120.78.173.152:1883";
    private static final String TOPIC = "AIResult";   //客户端订阅主题
    private static final String ClientID="App/Subscriber1";  //客户端标识
 //    private IGetMessageCallBack IGetMessageCallBack;

    private static MqttManager mqttManager=null;
    private MqttClient client;
    private MqttConnectOptions connectOptions;
    private Context context;
  public MqttManager(Context context)
  {
      this.context=context;
     Log.e(mTag,"mqttmanager初始化");
  }

  public MqttManager getInstance(Context context){
      if(mqttManager==null)
      {
          mqttManager=new MqttManager(context);
      }
      else {
          return mqttManager;
      }
      return null;
  }

  public void connect()
  {
      try{
          client=new MqttClient(host,ClientID,new MemoryPersistence());
          connectOptions=new MqttConnectOptions();
          connectOptions.setCleanSession(true);
          connectOptions.setConnectionTimeout(5);
          connectOptions.setKeepAliveInterval(20);
          connectOptions.setAutomaticReconnect(true);
          client.setCallback(mqttCallback);
          client.connect(connectOptions);
      }catch (MqttException e)
      {
          e.printStackTrace();
      }
  }

   public void subscribe(String topic,int qos) {
       if (client != null) {
           int[] Qos = {qos};
           String[] topic1 = {topic};

           try {

               client.subscribe(topic1, Qos);
               Log.d(mTag, "订阅Topic" + topic);
           } catch (MqttException e) {
               e.printStackTrace();
           }
       }
   }

    public void publish(String topic,String msg,boolean isRetained,int qos) {
        try {
            if (client!=null) {
                MqttMessage message = new MqttMessage();
                message.setQos(qos);
                message.setRetained(isRetained);
                message.setPayload(msg.getBytes());
                client.publish(topic, message);
            }
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }



       private MqttCallback mqttCallback = new MqttCallback() {
           @Override
           public void connectionLost(Throwable cause) {
               System.err.println("Subscriber连接断开，尝试重连");

           }

           @Override
           public void messageArrived(String topic, MqttMessage message){
               Log.i(mTag,"received topic : " + topic);
               final String payload =new String(message.getPayload());
               Log.i(mTag,"received msg : " + payload);
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       Looper.prepare();
                       Toast.makeText(context,payload,Toast.LENGTH_LONG).show();
                       Looper.loop();
                   }
               }).start();
           }

           @Override
           public void deliveryComplete(IMqttDeliveryToken token) {
               Log.i(mTag,"deliveryComplete");
           }
       };

    public void onDestroy() {

        try {
            client.disconnect();
            Log.e(mTag,"mqtt断开连接");
        } catch (MqttException e) {
            // TODO Auto-generated catch block
            Log.e(mTag,"mqtt断开连接失败");
        }
    }

    ///////////////
/*
    public static MqttConnectOptions option;
    private IMqttToken connectToken;
    private IMqttToken subscribeToken;

    private MyBinder binder=new MyBinder();
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    @Override
    public boolean onUnbind(Intent intent)
    {
        return super.onUnbind(intent);
    }
    public class MyBinder extends Binder{
        public MqttService getService()
        {
            return MqttService.this;
        }
    }
    @Override
    public void onCreate(){
        try {
            Log.e(mTag,"在Mqttservice中");
            client = new MqttAsyncClient(HOST, ClientID);
            option = new MqttConnectOptions();
            option.setCleanSession(true);
            option.setConnectionTimeout(5);
            option.setKeepAliveInterval(20);
            option.setAutomaticReconnect(true);
            client.setCallback(new MqttCallback() {

                @Override
                public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
                    // TODO Auto-generated method stub
//					System.out.println("Subscriber收到消息...");
//					System.out.println("\tTopic:"+arg0);
//					System.out.println("\tMessage:"+arg1);
                    final String msg = arg1.toString();

                    Log.e(mTag,"接收到mqtt信息");
                    Handler handlerThree=new Handler(Looper.getMainLooper());
                    handlerThree.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getApplicationContext() ,msg,Toast.LENGTH_LONG).show();
                        }
                    });

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken arg0) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void connectionLost(Throwable arg0) {
                    // TODO Auto-generated method stub
                    try {
                        System.err.println("Subscriber连接断开，尝试重连");
                        client.reconnect();
                        Thread.sleep(1000);
                        System.err.println("Subscriber重连结果：" + client.isConnected());
                        client.subscribe(TOPIC, 1).waitForCompletion();
                        System.err.println("Subscriber重新订阅成功");
                    } catch (MqttException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
            connectToken = client.connect(option);
            connectToken.waitForCompletion();
            System.out.println("Subscriber连接成功");
            client.subscribe(TOPIC, 1).waitForCompletion();
            System.out.println("Subscriber订阅成功");
        }catch (MqttException e)
        {
           e.printStackTrace();
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Kathy", "TestTwoService - onStartCommand - startId = " + startId + ", Thread = " + Thread.currentThread().getName());
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("Kathy", "TestTwoService - onDestroy - Thread = " + Thread.currentThread().getName());
        super.onDestroy();
        try {
            client.disconnect();
        } catch (MqttException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

*/
}
