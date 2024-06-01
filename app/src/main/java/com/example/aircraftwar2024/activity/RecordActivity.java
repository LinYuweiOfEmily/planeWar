package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aircraftwar2024.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RecordActivity extends AppCompatActivity {
    private String file = "Score.txt";
    private LinkedList<Map<String, Object>> listitem;
    private SimpleAdapter listItemAdapter;
    private ListView list;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityManager activityManager = ActivityManager.getActivityManager();
        activityManager.addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        TextView gameTypeView = (TextView) findViewById(R.id.gameType);
        int gameType = getIntent().getIntExtra("gameType", 0);
        if(gameType==1){
            gameTypeView.setText("简单模式");
            file = "easy"+file;
        }else if (gameType==2){
            gameTypeView.setText("普通模式");
            file = "common"+file;

        }else {
            gameTypeView.setText("困难模式");
            file = "hard"+file;
        }
        add(getIntent().getIntExtra("score",0));
        //获得Layout里面的ListView
        list = (ListView) findViewById(R.id.ListView1);

        //生成适配器的Item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(
                this,
                getData(),
                R.layout.activity_item,
                new String[]{"rank","username","score","time"},
                new int[]{R.id.rank,R.id.username,R.id.score,R.id.time});

        //添加并且显示
        list.setAdapter(listItemAdapter);

        //添加单击监听
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);

                String format = String.format(getString(R.string.dialog_message), arg2);
                builder.setMessage(format)
                        .setTitle(R.string.dialog_title);

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(arg2);
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
        Button returnBtn = (Button)findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityManager activityManager = ActivityManager.getActivityManager();
                activityManager.finishActivity();
                activityManager.finishActivity();
                activityManager.finishActivity();
            }
        });
    }
    private List<Map<String, Object>> getData() {
        listitem = new LinkedList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            FileInputStream fis = openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line;
            // 读取文件的每一行
            while ((line = br.readLine()) != null) {
                // 处理每一行数据
                String[] split = line.split("  ");
                if(!"得分".equals(split[1])){
                    map = new HashMap<String, Object>();
                    map.put("username", split[0]);
                    map.put("score", Integer.parseInt(split[1]));
                    map.put("time",split[2]);
                    listitem.add(map);
                }
            }

            // 关闭流
            br.close();
            isr.close();
            fis.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Collections.sort(listitem, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                // 确保两个Map都包含"score"键，并转换为可比较的类型（如Integer或Double）
                if (o1.containsKey("score") && o2.containsKey("score")) {
                    // 假设"score"是Integer类型，如果是其他类型需要相应转换
                    Integer score1 = (Integer) o1.get("score");
                    Integer score2 = (Integer) o2.get("score");
                    return score2.compareTo(score1); // 升序排序
                }
                // 如果某个Map不包含"score"键，可以决定如何处理这种情况（例如抛出异常或返回0）
                throw new IllegalArgumentException("Map does not contain 'score' key");
            }
        });
        for(int i=0;i<listitem.size();i++){
            Map<String, Object> stringObjectMap = listitem.get(i);
            stringObjectMap.put("rank",i+1);
        }
        map = new HashMap<String, Object>();
        map.put("rank","排名");
        map.put("username","用户" );
        map.put("score", "得分");
        map.put("time","时间");
        listitem.addFirst(map);
        return listitem;
    }
    private void add(int score){
        // 获取当前时间（ZonedDateTime，使用UTC时区）
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        // 创建一个 DateTimeFormatter 来格式化日期时间为 RFC 1123 格式
        DateTimeFormatter rfc1123Formatter = DateTimeFormatter.RFC_1123_DATE_TIME;

        // 使用 DateTimeFormatter 来格式化日期时间
        String formattedDateTime = now.format(rfc1123Formatter);
        String s = "test  "+score+"  "+formattedDateTime+"\n";
        try {
            FileOutputStream fos = openFileOutput(file, MODE_APPEND);
            fos.write(s.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void delete(int i){
        listitem.remove(i);
        listitem.remove(0);
        try {
            FileOutputStream fos = openFileOutput(file, MODE_PRIVATE);
            for (Map<String, Object> a : listitem) {
                String s = "test  "+a.get("score")+"  "+a.get("time")+"\n";
                fos.write(s.getBytes());
            }
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listItemAdapter = new SimpleAdapter(this,
                getData(),
                R.layout.activity_item,
                new String[]{"rank","username","score","time"},
                new int[]{R.id.rank,R.id.username,R.id.score,R.id.time});
        list.setAdapter(listItemAdapter);

    }
}