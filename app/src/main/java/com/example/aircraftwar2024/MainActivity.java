package com.example.aircraftwar2024;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }
}