package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.aircraftwar2024.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    private Socket socket;
    private PrintWriter writer;
    public static Handler handler;
    private EditText txt;
    private static  final String TAG = "MainActivity";
    private String doubleMsg;
    private AlertDialog alertDialog;
    private NetConn netConn;
    public static int scoreEnemy = 0;
    public static int scoreYour = 0;
    public static boolean isHeroExit = true;
    public static boolean isEnemyHeroExit = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityManager activityManager = ActivityManager.getActivityManager();
        activityManager.addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button singleBtn = (Button)findViewById(R.id.singleBtn);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        singleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
                intent.putExtra("MusicOnOff",radioGroup.getCheckedRadioButtonId());
                startActivity(intent);
            }
        });
        Button  doubelBtn= (Button)findViewById(R.id.doubleBtn);
        doubelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                alertDialog = builder.setMessage("匹配中，请等待...")
                        .setCancelable(false)
                        .create();
                alertDialog.show();
                new NetConn(handler).start();
            }
        });
        handler = new Handler(Looper.getMainLooper()){
            //当数据处理子线程更新数据后发送消息给UI线程，UI线程更新UI
            @Override
            public void handleMessage(Message msg){
                if(msg.what == 1){
                    if(alertDialog!=null && alertDialog.isShowing() && "success match!".equals((String) msg.obj)) {
                        alertDialog.dismiss();
                        Intent intent = new Intent(MainActivity.this,GameActivity.class);
                        intent.putExtra("gameType",4);
                        startActivity(intent);
                    }else if("true".equals((String) msg.obj)||"false".equals((String) msg.obj)){
                        isEnemyHeroExit = Boolean.parseBoolean((String) msg.obj);
                    }else {
                        scoreEnemy = Integer.parseInt((String) msg.obj);
                    }
                }
            }
        };
    }
    protected class NetConn extends Thread{
        private BufferedReader in;
        private Handler toClientHandler;

        public NetConn(Handler myHandler){
            this.toClientHandler = myHandler;
        }
        @Override
        public void run(){
            try{
                socket = new Socket();

                socket.connect(new InetSocketAddress
                        ("10.0.2.2",9999),5000);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
                writer = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream(),"utf-8")),true);
                Log.i(TAG,"connect to server");
                //接收服务器返回的数据
                Thread receiveServerMsg =  new Thread(){
                    @Override
                    public void run(){
                        String fromserver = null;
                        try{
                            while((fromserver = in.readLine())!=null)
                            {
                                //发送消息给UI线程
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = fromserver;
                                toClientHandler.sendMessage(msg);
                            }
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }
                    }
                };
                //发送数据到服务器
                receiveServerMsg.start();
                while (true){
                    if(!isHeroExit){
                        writer.println(isHeroExit);
                    }else {
                        Log.d(TAG,""+scoreYour);
                        writer.println(scoreYour);
                    }
                }
            }catch(UnknownHostException ex){
                ex.printStackTrace();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
}