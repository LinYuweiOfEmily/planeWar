package com.example.aircraftwar2024;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class OfflineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("我被调用了");
        setContentView(R.layout.activity_offline);
    }
}