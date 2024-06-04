package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.aircraftwar2024.R;

public class OnlineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        TextView yourScoreText = (TextView)findViewById(R.id.yourScoreText);
        TextView enemyScoreText = (TextView)findViewById(R.id.enemyScoreText);
        yourScoreText.setText("你的分数:    "+MainActivity.scoreYour);
        enemyScoreText.setText("对手分数:    "+MainActivity.scoreEnemy);
    }
}