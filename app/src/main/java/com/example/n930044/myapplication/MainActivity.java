package com.example.n930044.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DisplaySpecLayout mDisplaySpecLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("ktr", "onCreate");
        mDisplaySpecLayout = (DisplaySpecLayout) findViewById(R.id.custom_view);
        List<String> datas = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            datas.add("index:" + i);
        }

        mDisplaySpecLayout.setGoodsSizeDataOnDisplaySpecLayout(datas);

//        startActivity(new Intent(this, FloatMainActivity.class));
        startActivity(new Intent(this, Main2Activity.class));
    }
}
