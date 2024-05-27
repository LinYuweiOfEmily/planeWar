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
                startActivity(intent);
            }
        });
        commonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OfflineActivity.this, GameActivity.class);
                intent.putExtra("gameType",2);
                startActivity(intent);
            }
        });
        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OfflineActivity.this, GameActivity.class);
                intent.putExtra("gameType",3);
                startActivity(intent);
            }
        });
    }
}