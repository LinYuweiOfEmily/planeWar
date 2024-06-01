package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aircraftwar2024.R;

public class OfflineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityManager activityManager = ActivityManager.getActivityManager();

        activityManager.addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        Button easyBtn = (Button) findViewById(R.id.easyBtn);
        Button commonBtn = (Button) findViewById(R.id.commonBtn);
        Button hardBtn = (Button) findViewById(R.id.hardBtn);
        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OfflineActivity.this, GameActivity.class);
                intent.putExtra("gameType",1);
                intent.putExtra("MusicOnOff",getIntent().getIntExtra("MusicOnOff",R.id.radio_off));
                startActivity(intent);
            }
        });
        commonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OfflineActivity.this, GameActivity.class);
                intent.putExtra("gameType",2);
                intent.putExtra("MusicOnOff",getIntent().getIntExtra("MusicOnOff",R.id.radio_off));
                startActivity(intent);
            }
        });
        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OfflineActivity.this, GameActivity.class);
                intent.putExtra("gameType",3);
                intent.putExtra("MusicOnOff",getIntent().getIntExtra("MusicOnOff",R.id.radio_off));
                startActivity(intent);
            }
        });
    }
}